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

package com.pe.pcm.si.pwd;

import com.pe.pcm.exception.CommunityManagerServiceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;


public class PwdMessageDigestFactory {

    private static final Method secureInstanceMethod;

    static {
        Method m;
        try {
            Class<?> c = Class.forName("com.pe.pcm.si.pwd.security.PwdMessageDigest");
            m = c.getMethod("getInstance", new Class[]{String.class});
        } catch (ClassNotFoundException e) {
            m = null;
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
        secureInstanceMethod = m;
    }

    public static PwdMessageDigest getInstance(String algorithm) throws NoSuchAlgorithmException {
        if (secureInstanceMethod == null) {
            return new PwdBasicMessageDigest(algorithm);
        }
        try {
            return (PwdMessageDigest) secureInstanceMethod.invoke(null, new Object[]{algorithm});
        } catch (IllegalAccessException e) {
            throw new CommunityManagerServiceException(e);
        } catch (InvocationTargetException e) {
            if ((e.getCause() instanceof NoSuchAlgorithmException)) {
                throw ((NoSuchAlgorithmException) e.getCause());
            }
            throw new CommunityManagerServiceException(e);
        }
    }

}
