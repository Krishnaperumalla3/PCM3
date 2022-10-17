package com.pe.pcm.mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeApiService {

    private final ModeApiClientService modeApiClientService;

    @Autowired
    public ModeApiService(ModeApiClientService modeApiClientService) {
        this.modeApiClientService = modeApiClientService;
    }

    public String documentSearch(CommonModel commonModel) {
        return modeApiClientService.modeDocumentSearchApi(commonModel);
    }

    public String modeActualPostAPIForIdentity(CommonModel commonModel) {
        return modeApiClientService.modeActualPostAPIForIdentity(commonModel);
    }

    public String modeActualPutAPIForIdentity(CommonModel commonModel, String id) {
        return modeApiClientService.modeActualPutAPIForIdentity(commonModel, id);
    }

    public String identitySearch(CommonModel commonModel, String search) {
        return modeApiClientService.modeActualGetCallForIdentitySearch(commonModel, search);
    }

    public String sendRuleSearch(CommonModel commonModel) {
        return modeApiClientService.modeActualGetCallForSendRuleSearch(commonModel);
    }

    public String addRule(CommonModel commonModel) {
        return modeApiClientService.modeActualPostApiForAddRule(commonModel);
    }

    public String modeActualPutAPIForAddRule(CommonModel commonModel, String id) {
        return modeApiClientService.modeActualPutAPIForAddRule(commonModel, id);
    }
}
