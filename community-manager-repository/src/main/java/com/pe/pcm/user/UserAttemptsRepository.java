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

import com.pe.pcm.user.entity.UserAttemptsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface UserAttemptsRepository extends CrudRepository<UserAttemptsEntity, Long> {

    Optional<UserAttemptsEntity> findByUsername(String username);
}
