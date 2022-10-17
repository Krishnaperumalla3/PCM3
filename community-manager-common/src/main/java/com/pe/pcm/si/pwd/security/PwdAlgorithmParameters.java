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

package com.pe.pcm.si.pwd.security;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class PwdAlgorithmParameters {

    private PwdAlgorithmParameters() {
    }

    private static boolean fipsMode;
        private static String fipsProvider;

        public static AlgorithmParameters getInstance(final String algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
            return getInstance(algorithm, null);
        }

        public static AlgorithmParameters getInstance(final String algorithm, final String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
            if (!PwdAlgorithmParameters.fipsMode) {
                if (provider == null) {
                    return AlgorithmParameters.getInstance(algorithm);
                }
                return AlgorithmParameters.getInstance(algorithm, provider);
            }
            else {
                if (provider != null && !provider.equalsIgnoreCase(PwdAlgorithmParameters.fipsProvider)) {
                    throw new NoSuchProviderException("In FIPS mode cannot use requested provider " + provider);
                }
                return AlgorithmParameters.getInstance(algorithm, PwdAlgorithmParameters.fipsProvider);
            }
        }

        static {
            PwdAlgorithmParameters.fipsMode = PwdController.getFIPSMode();
            PwdAlgorithmParameters.fipsProvider = PwdController.getFIPSProvider();
        }
    }

