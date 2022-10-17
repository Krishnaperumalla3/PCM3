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

package com.pe.pcm.miscellaneous;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
public class CommonQueryPredicate {

    private CommonQueryPredicate() {
    }

    public static void getPredicate(Root<?> root, CriteriaBuilder cb, List<Predicate> predicates,
                                    String searchParam, String field, boolean isLike) {
        if (hasText(searchParam)) {
            if (isLike) {
                if (searchParam.trim().startsWith("*")) {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + searchParam.trim().toLowerCase().replace("*", "")));
                } else if (searchParam.trim().endsWith("*")) {
                    predicates.add(cb.like(cb.lower(root.get(field)), searchParam.trim().toLowerCase().replace("*", "") + "%"));
                } else {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + searchParam.trim().toLowerCase() + "%"));
                }
            } else {
                predicates.add(cb.equal(cb.lower(root.get(field)), searchParam.trim().toLowerCase()));
            }
        }
    }
}
