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

package com.pe.pcm.sterling;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.sterling.mailbox.entity.MbxMailBoxEntity;
import com.pe.pcm.sterling.mailbox.MbxMailBoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Kiran Reddy.
 */
@Service
public class RemoteMailBoxService {

    private final MbxMailBoxRepository mbxMailBoxRepository;

    @Autowired
    public RemoteMailBoxService(MbxMailBoxRepository mbxMailBoxRepository) {
        this.mbxMailBoxRepository = mbxMailBoxRepository;
    }

    public String getIdByMailBoxName(String path) {

        List<Long> ids = findAllByPath(path).stream().map(MbxMailBoxEntity::getMailboxId).collect(Collectors.toList());
        if (ids.isEmpty()) {
            throw notFound("MailBox");
        }
        if (ids.size() > 1) {
            throw new CommunityManagerServiceException(HttpStatus.CONFLICT.value(), "The given mailbox has multiple IDs, Mailbox = " + path);
        }
        return String.valueOf(ids.get(0));
    }

    private List<MbxMailBoxEntity> findAllByPath(String path) {
        return mbxMailBoxRepository.findAllByPath(path).orElse(Collections.emptyList());
    }

    public Optional<MbxMailBoxEntity> findByMailboxPath(String path) {
        return mbxMailBoxRepository.findFirstByPathUp(path.toUpperCase());
    }

}
