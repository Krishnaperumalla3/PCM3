///*
// * Copyright (c) 2020 Pragma Edge Inc
// *
// * Licensed under the Pragma Edge Inc
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://pragmaedge.com/licenseagreement
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.pe.pcm.file;
//
//import org.apache.tomcat.util.http.fileupload.FileItemIterator;
//import org.apache.tomcat.util.http.fileupload.FileItemStream;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import static com.pe.pcm.exception.GlobalExceptionHandler.*;
//import static org.springframework.http.HttpStatus.INSUFFICIENT_STORAGE;
//
//@Service
//public class ManageFileServiceTest {
//
//    public void uploadFile(HttpServletRequest request) {
//        try {
//            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//            if (!isMultipart) {
//                throw badRequest("Not a multipart request");
//            }
//
//            // Create a new file upload handler
//            ServletFileUpload upload = new ServletFileUpload();
//
//            // Parse the request
//            FileItemIterator iter = upload.getItemIterator(request);
//            while (iter.hasNext()) {
//                FileItemStream item = iter.next();
//
//                try(InputStream stream = item.openStream()) {
//                    if (!item.isFormField()) {
//                        String filename = item.getName();
//                        // Process the input stream
//                        OutputStream out = Files.newOutputStream(Paths.get(filename)); //new FileOutputStream(filename);
//                        IOUtils.copy(stream, out);
//                        out.close();
//                    }
//                }
//            }
//        } catch (FileUploadException e) {
//            throw customError(INSUFFICIENT_STORAGE.value(), e.getMessage());
//        } catch (IOException e) {
//            throw internalServerError(e.getMessage());
//        }
//    }
//}
