package com.pe.pcm.miscellaneous;

import com.pe.pcm.apiconnecct.ApiConnectClientService;
import com.pe.pcm.apiconnect.OutLookApiModel;
import com.pe.pcm.apiconnect.OutLookApiResponse;
import com.pe.pcm.apiconnect.OutLookEmailModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Shameer.v.
 */
@Service
public class OutLookApiService {


    private final ApiConnectClientService apiConnectClientService;

    @Autowired
    public OutLookApiService(ApiConnectClientService apiConnectClientService) {
        this.apiConnectClientService = apiConnectClientService;
    }

    public List<OutLookApiResponse> getOutLookAPI(OutLookApiModel outLookApiModel, HttpServletRequest httpServletRequest) {
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        JSONObject jsonObject = new JSONObject(apiConnectClientService.getOutlookResponse(outLookApiModel, params, headers));
        List<OutLookApiResponse> list = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("value");
        for (int index = 0; index < jsonArray.length(); index++) {
            JSONArray jsonArray1 = jsonArray.getJSONObject(index).getJSONArray("toRecipients");
            for (int index1 = 0; index1 < jsonArray1.length(); index1++) {
                list.add(new OutLookApiResponse(jsonArray1.getJSONObject(index1).getJSONObject("emailAddress")
                        .getString("name"),
                        jsonArray1.getJSONObject(index1).getJSONObject("emailAddress").getString("address")
                        , jsonArray.getJSONObject(index).getString("subject")));
            }
        }
        return list;
    }

    public String sendMailToOutlook(OutLookEmailModel outLookEmailModel) {
        apiConnectClientService.sendEmailToOutlook(outLookEmailModel);
        return "Mail Sent Successfully..!";
    }
}
