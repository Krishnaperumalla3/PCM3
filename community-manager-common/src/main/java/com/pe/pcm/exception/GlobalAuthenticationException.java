/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Kiran Reddy.
 */
public class GlobalAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7805847831067739624L;

	public GlobalAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }


	public GlobalAuthenticationException(String msg) {
		super(msg);
	}

}
