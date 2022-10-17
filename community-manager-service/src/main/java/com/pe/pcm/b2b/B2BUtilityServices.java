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

package com.pe.pcm.b2b;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.b2b.routing.channels.RoutingChannelModel;
import com.pe.pcm.b2b.routing.rules.RemoteRoutingRules;
import com.pe.pcm.sterling.FgPartGrpRepository;
import com.pe.pcm.sterling.RemoteMailBoxService;
import com.pe.pcm.sterling.entity.FgPartGrpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class B2BUtilityServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(B2BUtilityServices.class);

    private B2BApiService b2BApiService;
    private RemoteMailBoxService remoteMailBoxService;
    private FgPartGrpRepository fgPartGrpRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public B2BUtilityServices(B2BApiService b2BApiService, RemoteMailBoxService remoteMailBoxService, FgPartGrpRepository fgPartGrpRepository) {
        this.b2BApiService = b2BApiService;
        this.remoteMailBoxService = remoteMailBoxService;
        this.fgPartGrpRepository = fgPartGrpRepository;
    }

    public void updateRoutingRule(String name, String mailbox) {
        RemoteRoutingRules remoteRoutingRules = B2BFunctions.mapperStringToRemoteRoutingRules.apply(b2BApiService.getRoutingRules(name));
        remoteRoutingRules.getMailboxes().add(remoteMailBoxService.getIdByMailBoxName(mailbox));
        b2BApiService.updateRoutingRuleInSI(remoteRoutingRules, mailbox, TRUE, name);
    }

    public List<RoutingChannelModel> findRoutingChannelsByTemplateName(String templateName) {
        String routingChannels = b2BApiService.getRoutingChannelsFromSI(templateName, null, null);
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Arrays.asList(objectMapper.readValue(routingChannels, RoutingChannelModel[].class));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return Collections.emptyList();
    }

    public String getPartnerGroupDetails () {
        String outPut = "";
        String data = b2BApiService.getPartnerGroupDetails();
        List<FgPartGrpEntity> fgPartGrpEntities = fgPartGrpRepository.findAllByOrderByNameAsc();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(data)));
            NodeList nodeList = doc.getElementsByTagName("PartnerGroup");
            if(fgPartGrpEntities.size() == nodeList.getLength()) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    for (FgPartGrpEntity fgPartGrpEntity: fgPartGrpEntities) {
                        Element element = (Element) nodeList.item(i);
                        if (element.getAttribute("_id").equals(fgPartGrpEntity.getName())) {
                            element.setAttribute("key", fgPartGrpEntity.getPartGrpKey().trim());
                        }
                    }
                }
            }
            outPut = prettyPrint(doc);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return outPut;
    }

    private final String prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        return out.toString();
    }

    public List<RoutingChannelModel> findRoutingChannels(B2bRctSearchModel b2bRctSearchModel) {
        String routingChannels = b2BApiService.getRoutingChannelsFromSI(b2bRctSearchModel.getTemplateName(), b2bRctSearchModel.getConsumerName(), b2bRctSearchModel.getProducerName());
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Arrays.asList(objectMapper.readValue(routingChannels, RoutingChannelModel[].class));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return Collections.emptyList();
    }
}
