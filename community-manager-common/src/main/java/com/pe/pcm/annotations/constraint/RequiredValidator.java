/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.annotations.constraint;

import com.pe.pcm.utils.CommonFunctions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Kiran Reddy.
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {
    @Override
    public void initialize(Required required) {
        //Need this for initialising
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
         //return value != null && !value.isEmpty()
        return CommonFunctions.isNotNull(value);
    }

}

