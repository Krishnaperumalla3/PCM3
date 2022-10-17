/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.user;

import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.user.entity.UserAuditEventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Kiran Reddy.
 */
@Service
public class UserAuditEventService {

    private final UserAuditEventRepository userAuditEventRepository;

    @Autowired
    public UserAuditEventService(UserAuditEventRepository userAuditEventRepository) {
        this.userAuditEventRepository = userAuditEventRepository;
    }


    @Transactional
    public void userAudit(String principle, String eventType, String eventData) {
        AtomicBoolean status = new AtomicBoolean(false);
        findFirstNRecords(principle).forEach(userAuditEventEntity -> {
            if (userAuditEventEntity.getEventType().equals(AuthoritiesConstants.AUTHENTICATION_SUCCESS)) {
                status.set(true);
            }
        });
    }


    public void add(String principle, String eventType, String eventData) {
        userAuditEventRepository.save(
                new UserAuditEventEntity().setPrinciple(principle)
                        .setEventType(eventType)
                        .setEventData(eventData)
                        .setEventDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
        );
    }

    private List<UserAuditEventEntity> findFirstNRecords(String principle) {
        return userAuditEventRepository.findTopByPrincipleOrderByEventDate(principle).orElse(new ArrayList<>());
    }
}
