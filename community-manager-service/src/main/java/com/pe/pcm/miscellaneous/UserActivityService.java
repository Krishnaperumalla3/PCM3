package com.pe.pcm.miscellaneous;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.user.UserAuditEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shameer.v.
 */
@Service
public class UserActivityService {

    private final UserAuditEventRepository userAuditEventRepository;


    @Autowired
    public UserActivityService(UserAuditEventRepository userAuditEventRepository) {
        this.userAuditEventRepository = userAuditEventRepository;
    }

    public CommunityManagerKeyValueModel getUserActivity(String userId) {
        CommunityManagerKeyValueModel communityManagerKeyValueModel = new CommunityManagerKeyValueModel();
        userAuditEventRepository.findFirstByPrincipleOrderByEventDateDesc(userId)
                .ifPresent(userActivityEntity -> communityManagerKeyValueModel.setKey(userActivityEntity.getPrinciple())
                        .setValue(userActivityEntity.getEventData()));
        return communityManagerKeyValueModel;
    }
}
