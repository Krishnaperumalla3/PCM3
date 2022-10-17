/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../../environments/environment";
import {Observable, Observer} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class ChunkFileUploadService {
  private chunkSize = 104857600;
  constructor(private http: HttpClient) {}

  private static getErrorResult(reason: any) {
    return {
      result: {
        succeeded: false,
        errorMessage: reason
      },
      tmpDirectoryName: ''
    };
  }

  public upload(file: File, mailbox?): Observable<boolean> {
    return new Observable<boolean>(observer => {
      const reader = new FileReader();
      const fileName = file.name;
      const mailBox = mailbox;
      reader.onload = (e: ProgressEvent) => this.sendBufferData(reader.result, fileName, observer, mailBox);
      reader.readAsArrayBuffer(file);
    });
  }

  private async sendBufferData(readResult: string|ArrayBuffer,
                               fileName: string,
                               observer: Observer<boolean>, mailBox) {
    if (readResult == null) {
      observer.error('failed reading file data');
      return;
    }
    // create directory for saving chunks by server side application.
    await this.startSendingData(fileName)
      .then(result => this.sendChunks(
        new Uint8Array(readResult as ArrayBuffer),
        result.statusMessage
      ))
      .then(result => this.endSendingData(
        fileName,
        result.tmpDirectoryName,
        mailBox
      ))
      .then(result => {
        if (result.statusCode) {
          observer.next(result);
          observer.complete();
        } else {
          observer.error(result);
        }
      })
      .catch(reason => observer.error(reason));
  }

  private startSendingData(fileName: string): Promise<any> {
    return new Promise<any>(((resolve, reject) => {
      const formData = new FormData();
      formData.append('fileName', fileName);
      this.http.get(
        `${environment.CHUNK_UPLOAD_START}?fileName=${fileName}`)
        .toPromise()
        .then(result => {
          if (result['statusCode'] === 200) {
            resolve(result);
          } else {
            reject(result);
          }
        })
        .catch(reason => reject(ChunkFileUploadService.getErrorResult(reason)));
    }));
  }

  private async sendChunks(buffer: Uint8Array, tmpDirectoryName: string): Promise<any> {
    return new Promise<any>(async (resolve, reject) => {
      let fileIndex = 0;
      const sendChunkPromises = new Array<Promise<any>>();
      for (let i = 0; i < buffer.length; i += this.chunkSize) {
        let indexTo = i + this.chunkSize;
        if (indexTo >= buffer.length) {
          indexTo = buffer.length - 1; // for last data.
        }
        const formData = new FormData();
        formData.append('file', new Blob([buffer.subarray(i, indexTo)]));
        formData.append('fileIndex', fileIndex.toString());
        formData.append('tmpDirectoryName', tmpDirectoryName)
        let head = new HttpHeaders();
        head.append('Content-Type', 'multipart/form-data');
        const promise = this.http.post(
          environment.CHUNK_UPLOAD,
          formData, {headers: head})
          .toPromise();
        sendChunkPromises.push(promise);
        fileIndex += 1;
      }
      await Promise.all(sendChunkPromises)
        .then(results => {
          if (results.some(r => r.statusCode !== 200)) {
            reject('failed uploading');
          } else {
            resolve({
              result: {
                statusCode: 200,
                statusMessage: 'Chunk Uploaded Successfully!'
              },
              tmpDirectoryName
            });
          }
        })
        .catch(reason => reject(ChunkFileUploadService.getErrorResult(reason)));
    });
  }

  private endSendingData(fileName: string, tmpDirectoryName: string, mailBox): Promise<any> {
    return new Promise<any>(((resolve, reject) => {
      const formData = new FormData();
      formData.append('fileName', fileName);
      formData.append('mailbox', mailBox);
      formData.append('tmpDirectoryName', tmpDirectoryName);
      const url = `${environment.CHUNK_UPLOAD_END}?fileName=${fileName}&mailbox=${encodeURIComponent(mailBox)}&tmpDirectoryName=${tmpDirectoryName}`
      this.http.get(url)
        .toPromise()
        .then(result => {
          if (result['statusCode'] === 200) {
            resolve(result);
          } else {
            reject(result);
          }
        })
        .catch(reason => reject(ChunkFileUploadService.getErrorResult(reason).result));
    }));
  }

}
