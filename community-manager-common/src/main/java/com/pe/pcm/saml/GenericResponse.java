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

package com.pe.pcm.saml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Shameer.
 */
@JsonInclude(Include.NON_NULL)
public class GenericResponse {

    private boolean status;

    private String message;

    private Object payLoad;

    public GenericResponse(boolean status, String message, Object payLoad) {
        this.status = status;
        this.message = message;
        this.payLoad = payLoad;
    }

    public GenericResponse(boolean status, String string) {

        this.status = status;
        this.message = string;
    }

    public GenericResponse(boolean status, Object payLoad) {

        this.status = status;
        this.payLoad = payLoad;
    }

	public boolean isStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getPayLoad() {
		return payLoad;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPayLoad(Object payLoad) {
		this.payLoad = payLoad;
	}

    
	
}
