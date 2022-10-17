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

package com.pe.pcm.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
public class SqlResponseModel implements Serializable {

    private ColumnModel columnModel;

    private List<SqlRequestModel> content = new ArrayList<>();

    public ColumnModel getColumnModel() {
        return columnModel;
    }

    public SqlResponseModel setColumnModel(ColumnModel columnModel) {
        this.columnModel = columnModel;
        return this;
    }

    public List<SqlRequestModel> getData() {
        return content;
    }

    public SqlResponseModel setData(List<SqlRequestModel> content) {
        this.content = content;
        return this;
    }
}
