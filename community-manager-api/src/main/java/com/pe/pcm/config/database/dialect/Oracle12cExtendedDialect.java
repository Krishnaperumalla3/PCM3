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

package com.pe.pcm.config.database.dialect;

import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author Kiran Reddy.
 */
public class Oracle12cExtendedDialect extends Oracle12cDialect {

    public Oracle12cExtendedDialect() {
        super();
        registerFunction(
                "regexp_like", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN,
                        "(case when (regexp_like(?1, ?2)) then 1 else 0 end)")
        );
    }
}
