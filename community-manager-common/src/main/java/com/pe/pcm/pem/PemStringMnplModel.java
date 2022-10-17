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

package com.pe.pcm.pem;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class PemStringMnplModel implements Serializable {

    private String data;
    private boolean ignoreSpace;
    private boolean ignoreLowerUpper;
    private boolean removeSpace;
    private boolean toLower;
    private boolean toUpper;

    public PemStringMnplModel(){
        //Dont remove this, this will be used at Json to Model Conversion
    }

    public PemStringMnplModel(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public PemStringMnplModel setData(String data) {
        this.data = data;
        return this;
    }

    public boolean isIgnoreSpace() {
        return ignoreSpace;
    }

    public PemStringMnplModel setIgnoreSpace(boolean ignoreSpace) {
        this.ignoreSpace = ignoreSpace;
        return this;
    }

    public boolean isIgnoreLowerUpper() {
        return ignoreLowerUpper;
    }

    public PemStringMnplModel setIgnoreLowerUpper(boolean ignoreLowerUpper) {
        this.ignoreLowerUpper = ignoreLowerUpper;
        return this;
    }

    public boolean isRemoveSpace() {
        return removeSpace;
    }

    public PemStringMnplModel setRemoveSpace(boolean removeSpace) {
        this.removeSpace = removeSpace;
        return this;
    }

    public boolean isToLower() {
        return toLower;
    }

    public PemStringMnplModel setToLower(boolean toLower) {
        this.toLower = toLower;
        return this;
    }

    public boolean isToUpper() {
        return toUpper;
    }

    public PemStringMnplModel setToUpper(boolean toUpper) {
        this.toUpper = toUpper;
        return this;
    }

}
