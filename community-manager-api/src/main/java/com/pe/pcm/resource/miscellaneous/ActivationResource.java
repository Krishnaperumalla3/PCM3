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

package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.user.UserService;
import com.pe.pcm.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
@Controller
@RequestMapping("activate")
public class ActivationResource {

    private final UserService userService;

    @Autowired
    public ActivationResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "account")
    public String activate(@RequestParam("key") String activateKey, HttpServletRequest request) {
        Optional<UserEntity> optionalUserEntity = userService.activateRegistration(activateKey);
        if (optionalUserEntity.isPresent()) {
            request.setAttribute("type", "success");
        } else {
            request.setAttribute("type", "fail");
        }
        request.setAttribute("url", request.getRequestURL().toString().replace("/activate/account", ""));
        return "activation.html";
    }

}


