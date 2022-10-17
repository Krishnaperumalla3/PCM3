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

package com.pe.pcm.protocol.as2;

import com.pe.pcm.b2b.B2BRemoteAS2Service;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.CertsAndPriKeyService;
import com.pe.pcm.certificate.TrustedCertInfoService;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.entity.As2Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.b2b.B2BFunctions.mapperJsonRemoteAs2OrgToAs2Model;
import static com.pe.pcm.b2b.B2BFunctions.mapperJsonRemoteAs2PartnerToAs2Model;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToAs2Entity;
import static com.pe.pcm.utils.CommonFunctions.*;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class As2Service {

    private final As2Repository as2Repository;
    private final B2BRemoteAS2Service b2BRemoteAS2Service;
    private final CertsAndPriKeyService certsAndPriKeyService;
    private final TrustedCertInfoService trustedCertInfoService;
    private final CaCertInfoService caCertInfoService;

    @Autowired
    public As2Service(As2Repository as2Repository, B2BRemoteAS2Service b2BRemoteAS2Service, CertsAndPriKeyService certsAndPriKeyService, TrustedCertInfoService trustedCertInfoService, CaCertInfoService caCertInfoService) {
        this.as2Repository = as2Repository;
        this.b2BRemoteAS2Service = b2BRemoteAS2Service;
        this.certsAndPriKeyService = certsAndPriKeyService;
        this.trustedCertInfoService = trustedCertInfoService;
        this.caCertInfoService = caCertInfoService;
    }

    public As2Entity save(String parentPrimaryKey, String childPrimaryKey, String subscriberType, As2Model as2Model, String sciProfileId) {
        As2Entity as2Entity = mapperToAs2Entity.apply(as2Model);
        as2Entity.setSciProfileId(sciProfileId);
        as2Entity.setPkId(childPrimaryKey);
        as2Entity.setSubscriberId(parentPrimaryKey);
        as2Entity.setSubscriberType(subscriberType);
        as2Entity.setCaCertId(isNotNull(as2Model.getCaCertificate()) ? (as2Model.getHubInfo() ? "" : caCertInfoService.findByNameNotThrow(as2Model.getCaCertificate()).getObjectId()) : "");
        as2Entity.setExchgCertName(isNotNull(as2Model.getExchangeCertificate()) ? as2Model.getHubInfo() ? certsAndPriKeyService.findByIdNoThrow(as2Model.getExchangeCertificate()).getName() : trustedCertInfoService.findByIdNoThrow(as2Model.getExchangeCertificate()).getName() : "");
        as2Entity.setSigningCertName(isNotNull(as2Model.getSigningCertification()) ? as2Model.getHubInfo() ? certsAndPriKeyService.findByIdNoThrow(as2Model.getSigningCertification()).getName() : trustedCertInfoService.findByIdNoThrow(as2Model.getSigningCertification()).getName() : "");
        return as2Repository.save(as2Entity);
    }

    public void save(As2Entity as2Entity) {
        as2Repository.save(as2Entity);
    }

    public void update(As2Model as2Model) {
        As2Entity as2EntityOrg = get(as2Model.getProfileId());
        As2Entity as2Entity = mapperToAs2Entity.apply(as2Model);
        as2Entity.setPkId(as2EntityOrg.getPkId());
        as2Entity.setSubscriberId(as2EntityOrg.getSubscriberId());
        as2Entity.setSubscriberType(as2EntityOrg.getSubscriberType());
        as2Repository.save(as2Entity);
    }

    public As2Entity get(String subscriberId) {
        return as2Repository.findBySubscriberId(subscriberId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String subscriberId) {
        as2Repository.findBySubscriberId(subscriberId).ifPresent(as2Repository::delete);
    }

    public As2Entity saveFromB2bApi(As2Model as2Model, String parentPrimaryKey, String childPrimaryKey, Boolean isUpdate, String subscriberType) {
        b2BRemoteAS2Service.saveRemoteAs2Profile(as2Model, isUpdate);
        return as2Repository.save(mapperToAs2Entity.apply(as2Model)
                .setPkId(childPrimaryKey)
                .setSubscriberId(parentPrimaryKey)
                .setSubscriberType(subscriberType));
    }

    public void deleteThroughB2bApi(Boolean hubInfo, String profileId) {
        if (hubInfo) {
            b2BRemoteAS2Service.deleteRemoteAs2OrgProfile(profileId);
        } else {
            b2BRemoteAS2Service.deleteRemoteAs2PartnerProfile(profileId);
        }
    }

    public void getUsingB2bApi(As2Model as2Model) {
        if (as2Model.getHubInfo()) {
            mapperJsonRemoteAs2OrgToAs2Model.apply(as2Model, b2BRemoteAS2Service.getRemoteAs2OrgProfile(as2Model.getProfileId()));
        } else {
            mapperJsonRemoteAs2PartnerToAs2Model.apply(as2Model, b2BRemoteAS2Service.getRemoteAs2PartnerProfile(as2Model.getProfileId()));
        }
    }

    public List<As2Entity> findAllByCaCertNotNull() {
        return as2Repository.findAllByCaCertNotNull().orElse(new ArrayList<>());
    }

    public List<As2Entity> findAllByExchgCertNameNotNull() {
        return as2Repository.findAllByExchgCertNameNotNull().orElse(new ArrayList<>());
    }

    public List<As2Entity> findAllBySigningCertNameNotNull() {
        return as2Repository.findAllBySigningCertNameNotNull().orElse(new ArrayList<>());
    }
}
