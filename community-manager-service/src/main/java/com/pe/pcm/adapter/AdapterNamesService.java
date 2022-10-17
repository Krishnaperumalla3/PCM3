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

package com.pe.pcm.adapter;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@Service
public class AdapterNamesService {

    private String ftpServerAdapterName;
    private String ftpClientAdapterName;
    private String ftpsServerAdapterName;
    private String ftpsClientAdapterName;
    private String sftpServerAdapterName;
    private String sftpClientAdapterName;
    private String as2ServerAdapterName;
    private String as2ClientAdapterName;
    private String as2HttpClientAdapter;
    private String cdClientAdapterName;
    private String httpServerAdapterName;
    private String httpsServerAdapterName;
    private String mqAdapterName;
    private String wsServerAdapterName;
    private String fsAdapter;
    private String sfgSftpClientAdapterName;
    private String sfgSftpServerAdapterName;
    private String sfgFtpClientAdapterName;
    private String sfgFtpServerAdapterName;
    private String sfgFtpsClientAdapterName;
    private String sfgFtpsServerAdapterName;

    @Autowired
    public AdapterNamesService(@Value("${adapters.ftpServerAdapterName}") String ftpServerAdapterName, @Value("${adapters.ftpClientAdapterName}") String ftpClientAdapterName,
                               @Value("${adapters.ftpsServerAdapterName}") String ftpsServerAdapterName, @Value("${adapters.ftpsClientAdapterName}") String ftpsClientAdapterName,
                               @Value("${adapters.sftpServerAdapterName}") String sftpServerAdapterName, @Value("${adapters.sftpClientAdapterName}") String sftpClientAdapterName,
                               @Value("${adapters.as2ServerAdapterName}") String as2ServerAdapterName, @Value("${adapters.as2ClientAdapterName}") String as2ClientAdapterName,
                               @Value("${adapters.as2HttpClientAdapter}") String as2HttpClientAdapter, @Value("${adapters.cdClientAdapterName}") String cdClientAdapterName,
                               @Value("${adapters.httpServerAdapterName}") String httpServerAdapterName, @Value("${adapters.httpsServerAdapterName}") String httpsServerAdapterName,
                               @Value("${adapters.mqAdapterName}") String mqAdapterName, @Value("${adapters.wsServerAdapterName}") String wsServerAdapterName,
                               @Value("${adapters.fsAdapter}") String fsAdapter, @Value("${adapters.sfgSftpClientAdapterName}") String sfgSftpClientAdapterName,
                               @Value("${adapters.sfgSftpServerAdapterName}") String sfgSftpServerAdapterName, @Value("${adapters.sfgFtpClientAdapterName}") String sfgFtpClientAdapterName,
                               @Value("${adapters.sfgFtpServerAdapterName}") String sfgFtpServerAdapterName, @Value("${adapters.sfgFtpsClientAdapterName}") String sfgFtpsClientAdapterName,
                               @Value("${adapters.sfgFtpsServerAdapterName}") String sfgFtpsServerAdapterName) {
        this.ftpServerAdapterName = ftpServerAdapterName;
        this.ftpClientAdapterName = ftpClientAdapterName;
        this.ftpsServerAdapterName = ftpsServerAdapterName;
        this.ftpsClientAdapterName = ftpsClientAdapterName;
        this.sftpServerAdapterName = sftpServerAdapterName;
        this.sftpClientAdapterName = sftpClientAdapterName;
        this.as2ServerAdapterName = as2ServerAdapterName;
        this.as2ClientAdapterName = as2ClientAdapterName;
        this.as2HttpClientAdapter = as2HttpClientAdapter;
        this.cdClientAdapterName = cdClientAdapterName;
        this.httpServerAdapterName = httpServerAdapterName;
        this.httpsServerAdapterName = httpsServerAdapterName;
        this.mqAdapterName = mqAdapterName;
        this.wsServerAdapterName = wsServerAdapterName;
        this.fsAdapter = fsAdapter;
        this.sfgSftpClientAdapterName = sfgSftpClientAdapterName;
        this.sfgSftpServerAdapterName = sfgSftpServerAdapterName;
        this.sfgFtpClientAdapterName = sfgFtpClientAdapterName;
        this.sfgFtpServerAdapterName = sfgFtpServerAdapterName;
        this.sfgFtpsClientAdapterName = sfgFtpsClientAdapterName;
        this.sfgFtpsServerAdapterName = sfgFtpsServerAdapterName;
    }

    public AdapterNamesModel getAdapterNames() {
        return loadAdapters();
    }

    public List<CommunityManagerKeyValueModel>  getAdapterNamesForPem() {
        return loadAdaptersForPem();
    }

    private AdapterNamesModel loadAdapters() {
        return new AdapterNamesModel()
                .setFtpServerAdapterName(ftpServerAdapterName)
                .setFtpClientAdapterName(ftpClientAdapterName)
                .setFtpsServerAdapterName(ftpsServerAdapterName)
                .setFtpsClientAdapterName(ftpsClientAdapterName)
                .setSftpServerAdapterName(sftpServerAdapterName)
                .setSftpClientAdapterName(sftpClientAdapterName)
                .setAs2ServerAdapterName(as2ServerAdapterName)
                .setAs2ClientAdapterName(as2ClientAdapterName)
                .setAs2HttpClientAdapter(as2HttpClientAdapter)
                .setCdClientAdapterName(cdClientAdapterName)
                .setHttpServerAdapterName(httpServerAdapterName)
                .setHttpsServerAdapterName(httpsServerAdapterName)
                .setMqAdapterName(mqAdapterName)
                .setWsServerAdapterName(wsServerAdapterName)
                .setFsAdapter(fsAdapter)
                .setSfgSftpClientAdapterName(sfgSftpClientAdapterName)
                .setSfgSftpServerAdapterName(sfgSftpServerAdapterName)
                .setSfgFtpClientAdapterName(sfgFtpClientAdapterName)
                .setSfgFtpServerAdapterName(sfgFtpServerAdapterName)
                .setSfgFtpsClientAdapterName(sfgFtpsClientAdapterName)
                .setSfgFtpsServerAdapterName(sfgFtpsServerAdapterName);
    }

    private List<CommunityManagerKeyValueModel> loadAdaptersForPem() {
        List<CommunityManagerKeyValueModel> adapterList = new ArrayList<>();
        adapterList.add(new CommunityManagerKeyValueModel("ftpServerAdapterName",ftpServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("ftpClientAdapterName",ftpClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("ftpsServerAdapterName",ftpsServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("ftpsClientAdapterName",ftpsClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sftpServerAdapterName",sftpServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sftpClientAdapterName",sftpClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("as2ServerAdapterName",as2ServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("as2ClientAdapterName",as2ClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("as2HttpClientAdapter",as2HttpClientAdapter));
        adapterList.add(new CommunityManagerKeyValueModel("cdClientAdapterName",cdClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("httpServerAdapterName",httpServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("httpsServerAdapterName",httpsServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("mqAdapterName",mqAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("wsServerAdapterName",wsServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("fsAdapter",fsAdapter));
        adapterList.add(new CommunityManagerKeyValueModel("sfgSftpClientAdapterName",sfgSftpClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sfgSftpServerAdapterName",sfgSftpServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpClientAdapterName",sfgFtpClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpServerAdapterName",sfgFtpServerAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpsClientAdapterName",sfgFtpsClientAdapterName));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpsServerAdapterName",sfgFtpsServerAdapterName));
        return adapterList;
    }
}
