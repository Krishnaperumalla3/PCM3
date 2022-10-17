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

package com.pe.pcm.login;


import com.pe.pcm.annotations.constraint.Required;

/**
 * @author Chenchu Kiran.
 */
public class CommunityManagerLoginModel {

    @Required(message = "userName")
    private String userName;

    @Required(message = "password")
    private String password;


    public CommunityManagerLoginModel() {
    }

    public CommunityManagerLoginModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public CommunityManagerLoginModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CommunityManagerLoginModel setPassword(String password) {
        this.password = password;
        return this;
    }

}
