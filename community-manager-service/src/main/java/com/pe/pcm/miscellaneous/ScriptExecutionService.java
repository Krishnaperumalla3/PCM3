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

package com.pe.pcm.miscellaneous;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.pem.RemoteServerDetailsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.addToList;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */
@Service
public class ScriptExecutionService {

    private final String pemKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptExecutionService.class);
    private final PasswordUtilityService passwordUtilityService;

    @Autowired
    public ScriptExecutionService(@Value("${pem.remote.server.pem-key}") String pemKey,
                                  PasswordUtilityService passwordUtilityService) {
        this.pemKey = pemKey;
        this.passwordUtilityService = passwordUtilityService;
    }


    public String execute(RemoteServerDetailsModel remoteServerDetailsModel) {
        if (remoteServerDetailsModel.isLocalFile()) {
            return executeLocalScript(remoteServerDetailsModel);
        } else {
            return executeRemoteScript(remoteServerDetailsModel);
        }
    }

    private String executeRemoteScript(RemoteServerDetailsModel remoteServerDetailsModel) {
        LOGGER.info("In script Execution method.");
        StringBuilder stringBuilder = new StringBuilder();
        JSch jsch = new JSch();
        Session session;
        try {
            if (remoteServerDetailsModel.getIsPemKey() != null && remoteServerDetailsModel.getIsPemKey()) {
                jsch.addIdentity(pemKey);
            }
            session = jsch.getSession(remoteServerDetailsModel.getUserName(), remoteServerDetailsModel.getHost(), remoteServerDetailsModel.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword(passwordCheck(remoteServerDetailsModel.getPassword()));
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            LOGGER.info("Opening Execution Channel.");
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");

            LOGGER.info("Preparing CommandBuilder.");
            StringBuilder commandBuilder = new StringBuilder().append(remoteServerDetailsModel.getCommand());
            List<String> inputParamList = new ArrayList<>();
            addToList(inputParamList, remoteServerDetailsModel.getInputParam1());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam2());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam3());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam4());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam5());

            LOGGER.info("Appending input parameters to the commandBuilder");
            inputParamList.forEach(inputParam -> commandBuilder.append(" ").append(inputParam));

            LOGGER.info("Connecting and Executing the the script.");
            LOGGER.info("Command Builder {}", commandBuilder);
            channelExec.setCommand(commandBuilder.toString());
            channelExec.connect();
            LOGGER.info("Successfully connected and executed the script");

            InputStream in = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            LOGGER.info("Reading All the echos");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                LOGGER.info("Echo {}", line);
            }
            LOGGER.info("Response from Remote Server: {}", stringBuilder);
            int exitStatus = channelExec.getExitStatus();
            if (exitStatus > 0) {
                LOGGER.info("Remote script execution Status {}:  ", exitStatus);
            }
            session.disconnect();
            LOGGER.info("Successfully completed the script execution.");
            return isNotNull(stringBuilder.toString()) ? stringBuilder.toString() : "Successfully Executed the script.";
        } catch (Exception e) {
            LOGGER.error("Exception occurred while executing remote script", e);
            return "Exception occurred while executing remote script {} " + e.getMessage();
        }

    }

    private String executeLocalScript(RemoteServerDetailsModel remoteServerDetailsModel) {
        LOGGER.info("Executing Local File");
        StringBuilder sb = new StringBuilder();
        Process process;
        try {
            List<String> inputParamList = new ArrayList<>();
            inputParamList.add(remoteServerDetailsModel.getCommand());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam1());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam2());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam3());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam4());
            addToList(inputParamList, remoteServerDetailsModel.getInputParam5());

            ProcessBuilder pb = new ProcessBuilder(inputParamList);
            process = pb.start();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                LOGGER.info("Echo : {}", line);
            }
        } catch (Exception e) {
            LOGGER.error("Error : ", e);
            throw internalServerError(e.getMessage());
        }
        return sb.toString();
    }

    private String passwordCheck(String password) {
        if (isNotNull(password)) {
            try {
                password = passwordUtilityService.decrypt(password);
            } catch (CommunityManagerServiceException e) {
                return password;
            }
        }
        return password;
    }
}