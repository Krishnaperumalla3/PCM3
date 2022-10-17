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

package com.pe.pcm.utils;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class MemoryStatsModel implements Serializable {

    private String heapSize;
    private String heapMaxSize;
    private String heapFreeSize;

    public String getHeapSize() {
        return heapSize;
    }

    public MemoryStatsModel setHeapSize(String heapSize) {
        this.heapSize = heapSize;
        return this;
    }

    public String getHeapMaxSize() {
        return heapMaxSize;
    }

    public MemoryStatsModel setHeapMaxSize(String heapMaxSize) {
        this.heapMaxSize = heapMaxSize;
        return this;
    }

    public String getHeapFreeSize() {
        return heapFreeSize;
    }

    public MemoryStatsModel setHeapFreeSize(String heapFreeSize) {
        this.heapFreeSize = heapFreeSize;
        return this;
    }
}
