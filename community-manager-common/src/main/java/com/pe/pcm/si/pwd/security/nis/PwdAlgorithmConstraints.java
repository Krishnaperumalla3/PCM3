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

package com.pe.pcm.si.pwd.security.nis;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.util.Set;

public interface PwdAlgorithmConstraints {
    int permits(Set<PwdCryptoPrimitive> var1, String var2, AlgorithmParameters var3);

    int permits(Set<PwdCryptoPrimitive> var1, Key var2);

    int permits(Set<PwdCryptoPrimitive> var1, String var2, Key var3, AlgorithmParameters var4);
}
