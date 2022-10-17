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

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.enums.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.enums.Protocol.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */
@Service
public class ProtocolUtilityService {

    private final boolean isB2bActive;
    private final Environment environment;
    private boolean isCm;

    @Autowired
    public ProtocolUtilityService(@Value("${sterling-b2bi.b2bi-api.active}") boolean isB2bActive, Environment environment) {
        this.isB2bActive = isB2bActive;
        this.environment = environment;
    }

    public List<String> getProtocolList() {
        if (isB2bActive) {
            return Arrays.stream(values())
                    .map(Protocol::getProtocol)
                    .collect(Collectors.toList());
        }
        return Arrays.stream(values())
                .filter(protocol ->
                        protocol != SFG_FTP
                                && protocol != SFG_FTPS
                                && protocol != SFG_SFTP
                )
                .map(Protocol::getProtocol)
                .collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getProtocolMap() {
        List<CommunityManagerKeyValueModel> communityManagerKeyValueModels = new ArrayList<>();

        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("AS2", "AS2"));

        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("AWS_S3", "AWS-S3"));

        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SFG_CONNECT_DIRECT", "B2Bi-CONNECT-DIRECT"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SFGFTP", "B2Bi-FTP"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SFGFTPS", "B2Bi-FTPS"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SFGSFTP", "B2Bi-SFTP"));
        if (!isCm) {
            communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("CONNECT_DIRECT", "CONNECT-DIRECT"));
        }
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("ExistingConnection", "EXISTING-CONNECTION"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("FileSystem", "FILESYSTEM"));
        if (!isCm) {
            communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("FTP", "FTP"));
            communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("FTPS", "FTPS"));
            communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SFTP", "SFTP"));
        }
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("HTTP", "HTTP"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("HTTPS", "HTTPS"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("Mailbox", "MAILBOX"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("MQ", " MQ/JMS"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SAP", "SAP"));

        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("Webservice", "WEBSERVICE"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("CUSTOM_PROTOCOL", "CUSTOM_PROTOCOL"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("GOOGLE_DRIVE","GOOGLE_DRIVE"));

        //communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("SMTP", "SMTP"))
        //communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("ORACLE_EBS", "Oracle EBS"))
        return communityManagerKeyValueModels;
    }

    @PostConstruct
    public void loadValues() {
        String dplType = environment.getProperty("cm.cm-deployment");
        isCm = !isNotNull(dplType) || !dplType.equalsIgnoreCase("FALSE");
    }

}
