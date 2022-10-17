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

package com.pe.pcm.pgp;

import com.pe.pcm.miscellaneous.PGPUtilsService;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;

/**
 * @author Kiran Reddy, Shameer.
 */
@Service
public class PGPManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PGPManagerService.class);

    private final PGPUtilsService pgpUtilsService;

    @Autowired
    public PGPManagerService(PGPUtilsService pgpUtilsService) {
        this.pgpUtilsService = pgpUtilsService;
    }


    public void encryptFile(String originalFile, FileInputStream keyFile, FileOutputStream encryptFile,
                            boolean asciiArmored, boolean integrityCheck) throws IOException, PGPException {
        LOGGER.debug("encryptFile()");
        pgpUtilsService.encryptFile(encryptFile, originalFile, pgpUtilsService.readPublicKey(keyFile), asciiArmored, integrityCheck);
        keyFile.close();
        encryptFile.close();
    }

    public void decryptFile(FileInputStream encryptFile, FileInputStream keyFile,
                              String passphrase, File fileToSave) throws NoSuchProviderException, IOException, PGPException {
        LOGGER.debug("decryptFile()");
        pgpUtilsService.decryptFile(encryptFile, keyFile, passphrase.toCharArray(), fileToSave);
        encryptFile.close();
        keyFile.close();
    }


}
