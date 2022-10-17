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

import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.entity.As2Profile;
import com.pe.pcm.protocol.as2.si.As2ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

import static com.pe.pcm.utils.CommonFunctions.convertBooleanToString;

@Service
public class AS2ProfileService {

        private final As2ProfileRepository as2ProfileRepository;

    @Autowired
    public AS2ProfileService(As2ProfileRepository as2ProfileRepository) {
        this.as2ProfileRepository = as2ProfileRepository;
    }

    private final BiFunction<String, As2Model, As2Profile> serialize = (transportObjectId, as2Model) -> {
        As2Profile as2Profile = new As2Profile();
        as2Profile.setProfileId(transportObjectId + ":-3afe");
        as2Profile.setIsOrg(convertBooleanToString(as2Model.getHubInfo()));
        as2Profile.setHttpClientAdapter(as2Model.getHttpclientAdapter());
        return as2Profile;
    };

    public void save(String transportObjectId, As2Model as2Model) {
        As2Profile as2Profile = serialize.apply(transportObjectId, as2Model);
        as2ProfileRepository.save(as2Profile);
    }

    public void update(String transportObjectId, As2Model as2Model) {

        Optional<As2Profile> as2ProfileOptional = findById(transportObjectId);
        if (as2ProfileOptional.isPresent()) {
            As2Profile as2Profile = as2ProfileOptional.get();
            as2Profile.setIsOrg(convertBooleanToString(as2Model.getHubInfo()));
            as2Profile.setHttpClientAdapter(as2Model.getHttpclientAdapter());
            as2ProfileRepository.save(as2Profile);
        }
    }

    public Optional<As2Profile> get(String transportObjectId) {
        return as2ProfileRepository.findById(transportObjectId + ":-3afe");
    }

    private Optional<As2Profile> findById(String profileId){
     return  as2ProfileRepository.findById(profileId);
    }

    public void delete(String transportObjectId) {
        Optional<As2Profile> as2ProfileOptional = get(transportObjectId);
        as2ProfileOptional.ifPresent(as2Profile -> as2ProfileRepository.delete(as2Profile));
    }

}
