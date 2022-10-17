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

import com.pe.pcm.exception.GlobalExceptionHandler;

import java.util.StringTokenizer;

public class PwdAlgData {
    private final String mSAlias;
    private final String mSJavaName;
    private final int mKeyLen;

    PwdAlgData(final String sAlias, final String sJavaName, final int iLen) {
        this.mSAlias = sAlias;
        this.mSJavaName = sJavaName;
        this.mKeyLen = iLen;
    }

    PwdAlgData(final String sDelimitedInputString) {
        final StringTokenizer st = new StringTokenizer(sDelimitedInputString, ",");
        if (st.countTokens() != 3) {
            throw GlobalExceptionHandler.internalServerError("Invalid input " + sDelimitedInputString + " comma-delimited string with 3 elements required");
        }
        this.mSAlias = st.nextToken();
        this.mSJavaName = st.nextToken();
        final String sTemp = st.nextToken();
        this.mKeyLen = Integer.parseInt(sTemp);
    }

    public final String getAlias() {
        return this.mSAlias;
    }

    public final String getJavaName() {
        return this.mSJavaName;
    }

    public final int getKeyLen() {
        return this.mKeyLen;
    }

    public final boolean compareAlias(final String sName) {
        return this.mSAlias.compareToIgnoreCase(sName) == 0;
    }
}
