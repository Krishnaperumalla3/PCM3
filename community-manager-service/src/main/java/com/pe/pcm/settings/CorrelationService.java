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

package com.pe.pcm.settings;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.correlation.CorrelationModel;
import com.pe.pcm.settings.entity.CorrelationEntity;
import com.pe.pcm.pem.StringConcatenationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class CorrelationService {

    private final CorrelationRepository correlationRepository;


    @Autowired
    public CorrelationService(CorrelationRepository correlationRepository) {
        this.correlationRepository = correlationRepository;
    }

    private final Function<CorrelationModel, CorrelationEntity> serialize = correlationModel -> new CorrelationEntity()
            .setPkId("1")
            .setCorrelationName1(correlationModel.getCorrelationName1())
            .setCorrelationName2(correlationModel.getCorrelationName2() == null ? null : correlationModel.getCorrelationName2())
            .setCorrelationName3(correlationModel.getCorrelationName3())
            .setCorrelationName4(correlationModel.getCorrelationName4())
            .setCorrelationName5(correlationModel.getCorrelationName5())
            .setCorrelationName6(correlationModel.getCorrelationName6())
            .setCorrelationName7(correlationModel.getCorrelationName7())
            .setCorrelationName8(correlationModel.getCorrelationName8())
            .setCorrelationName9(correlationModel.getCorrelationName9())
            .setCorrelationName10(correlationModel.getCorrelationName10())
            .setCorrelationName11(correlationModel.getCorrelationName11())
            .setCorrelationName12(correlationModel.getCorrelationName12())
            .setCorrelationName13(correlationModel.getCorrelationName13())
            .setCorrelationName14(correlationModel.getCorrelationName14())
            .setCorrelationName15(correlationModel.getCorrelationName15())
            .setCorrelationName16(correlationModel.getCorrelationName16())
            .setCorrelationName17(correlationModel.getCorrelationName17())
            .setCorrelationName18(correlationModel.getCorrelationName18())
            .setCorrelationName19(correlationModel.getCorrelationName19())
            .setCorrelationName20(correlationModel.getCorrelationName20())
            .setCorrelationName21(correlationModel.getCorrelationName21())
            .setCorrelationName22(correlationModel.getCorrelationName22())
            .setCorrelationName23(correlationModel.getCorrelationName23())
            .setCorrelationName24(correlationModel.getCorrelationName24())
            .setCorrelationName25(correlationModel.getCorrelationName25())
            .setCorrelationName26(correlationModel.getCorrelationName26())
            .setCorrelationName27(correlationModel.getCorrelationName27())
            .setCorrelationName28(correlationModel.getCorrelationName28())
            .setCorrelationName29(correlationModel.getCorrelationName29())
            .setCorrelationName30(correlationModel.getCorrelationName30())
            .setCorrelationName31(correlationModel.getCorrelationName31())
            .setCorrelationName32(correlationModel.getCorrelationName32())
            .setCorrelationName33(correlationModel.getCorrelationName33())
            .setCorrelationName34(correlationModel.getCorrelationName34())
            .setCorrelationName35(correlationModel.getCorrelationName35())
            .setCorrelationName36(correlationModel.getCorrelationName36())
            .setCorrelationName37(correlationModel.getCorrelationName37())
            .setCorrelationName38(correlationModel.getCorrelationName38())
            .setCorrelationName39(correlationModel.getCorrelationName39())
            .setCorrelationName40(correlationModel.getCorrelationName40())
            .setCorrelationName41(correlationModel.getCorrelationName41())
            .setCorrelationName42(correlationModel.getCorrelationName42())
            .setCorrelationName43(correlationModel.getCorrelationName43())
            .setCorrelationName44(correlationModel.getCorrelationName44())
            .setCorrelationName45(correlationModel.getCorrelationName45())
            .setCorrelationName46(correlationModel.getCorrelationName46())
            .setCorrelationName47(correlationModel.getCorrelationName47())
            .setCorrelationName48(correlationModel.getCorrelationName48())
            .setCorrelationName49(correlationModel.getCorrelationName49())
            .setCorrelationName50(correlationModel.getCorrelationName50());

    public CorrelationEntity getCorrelations() {
        return correlationRepository.findFirstByPkIdIsNotNull();
    }

    @Transactional
    public CorrelationEntity update(CorrelationModel crlModel) {
        CorrelationEntity crlEntity = serialize.apply(crlModel);
        return correlationRepository.save(crlEntity);
    }

    private void appendString(String value, StringBuilder stringBuilder) {
        if (!isEmpty(value)) {
            stringBuilder.append(value);
        }
    }

    public CommunityManagerKeyValueModel append(StringConcatenationModel stringConcatenationModel) {

        StringBuilder stringBuilder = new StringBuilder();

        appendString(stringConcatenationModel.getString1(), stringBuilder);
        appendString(stringConcatenationModel.getString2(), stringBuilder);
        appendString(stringConcatenationModel.getString3(), stringBuilder);
        appendString(stringConcatenationModel.getString4(), stringBuilder);
        appendString(stringConcatenationModel.getString5(), stringBuilder);
        appendString(stringConcatenationModel.getString6(), stringBuilder);
        appendString(stringConcatenationModel.getString7(), stringBuilder);
        appendString(stringConcatenationModel.getString8(), stringBuilder);
        appendString(stringConcatenationModel.getString9(), stringBuilder);
        appendString(stringConcatenationModel.getString10(), stringBuilder);
        appendString(stringConcatenationModel.getString11(), stringBuilder);
        appendString(stringConcatenationModel.getString12(), stringBuilder);
        appendString(stringConcatenationModel.getString13(), stringBuilder);
        appendString(stringConcatenationModel.getString14(), stringBuilder);
        appendString(stringConcatenationModel.getString15(), stringBuilder);
        appendString(stringConcatenationModel.getString16(), stringBuilder);
        appendString(stringConcatenationModel.getString17(), stringBuilder);
        appendString(stringConcatenationModel.getString18(), stringBuilder);
        appendString(stringConcatenationModel.getString19(), stringBuilder);
        appendString(stringConcatenationModel.getString20(), stringBuilder);

        return new CommunityManagerKeyValueModel("output", stringBuilder.toString());
    }

}
