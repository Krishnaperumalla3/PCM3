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

package com.pe.pcm.protocol;

import com.pe.pcm.protocol.mailbox.MailboxRepository;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToMailboxEntity;

/**
 * @author Kiran Reddy.
 */
@Service
public class MailboxService {

    private final MailboxRepository mailboxRepository;

    @Autowired
    public MailboxService(MailboxRepository mailboxRepository) {
        this.mailboxRepository = mailboxRepository;
    }

    public MailboxEntity save(MailboxEntity mailboxEntity) {
        return mailboxRepository.save(mailboxEntity);
    }

    public MailboxEntity get(String pkId) {
        return mailboxRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        mailboxRepository.findBySubscriberId(pkId).ifPresent(mailboxRepository::delete);
    }

    public MailboxEntity saveProtocol(MailboxModel mailboxModel, String parentPrimaryKey, String childPrimaryKey,
                                      String subscriberType) {
        MailboxEntity mailboxEntity = mapperToMailboxEntity.apply(mailboxModel);
        mailboxEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(mailboxEntity);
    }

    public List<MailboxEntity> findAllBySubscriberId(List<String> subIds) {
        return mailboxRepository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }

    public MailboxEntity findFirstByInMailboxOrOutMailbox(String inMailbox, String outMailbox) {
        return mailboxRepository.findFirstByInMailboxOrOutMailbox(inMailbox, outMailbox).orElse(new MailboxEntity());
    }

}
