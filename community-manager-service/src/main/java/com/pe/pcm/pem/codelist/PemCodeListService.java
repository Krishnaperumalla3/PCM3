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

package com.pe.pcm.pem.codelist;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.pem.codelist.entity.PemCodeListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.PEM_CODE_LIST_PRE_APPEND;
import static com.pe.pcm.utils.PCMConstants.PKEY_COUNT_TEN;

@Service
public class PemCodeListService {

    private PemCodeListRepository pemCodeListRepository;

    @Autowired
    public PemCodeListService(PemCodeListRepository pemCodeListRepository) {
        this.pemCodeListRepository = pemCodeListRepository;
    }

    private static PemCodeListModel apply(PemCodeListEntity pemCodeListEntity) {
        return new PemCodeListModel().setPkId(pemCodeListEntity.getPkId())
                .setProfileName(pemCodeListEntity.getProfileName())
                .setProtocol(pemCodeListEntity.getProtocol())
                .setCorrelationValue1(pemCodeListEntity.getCorrelationValue1())
                .setCorrelationValue2(pemCodeListEntity.getCorrelationValue2())
                .setCorrelationValue3(pemCodeListEntity.getCorrelationValue3())
                .setCorrelationValue4(pemCodeListEntity.getCorrelationValue4())
                .setCorrelationValue5(pemCodeListEntity.getCorrelationValue5())
                .setCorrelationValue6(pemCodeListEntity.getCorrelationValue6())
                .setCorrelationValue7(pemCodeListEntity.getCorrelationValue7())
                .setCorrelationValue8(pemCodeListEntity.getCorrelationValue8())
                .setCorrelationValue9(pemCodeListEntity.getCorrelationValue9())
                .setCorrelationValue10(pemCodeListEntity.getCorrelationValue10())
                .setCorrelationValue11(pemCodeListEntity.getCorrelationValue11())
                .setCorrelationValue12(pemCodeListEntity.getCorrelationValue12())
                .setCorrelationValue13(pemCodeListEntity.getCorrelationValue13())
                .setCorrelationValue14(pemCodeListEntity.getCorrelationValue14())
                .setCorrelationValue15(pemCodeListEntity.getCorrelationValue15())
                .setCorrelationValue16(pemCodeListEntity.getCorrelationValue16())
                .setCorrelationValue17(pemCodeListEntity.getCorrelationValue17())
                .setCorrelationValue18(pemCodeListEntity.getCorrelationValue18())
                .setCorrelationValue19(pemCodeListEntity.getCorrelationValue19())
                .setCorrelationValue20(pemCodeListEntity.getCorrelationValue20())
                .setCorrelationValue21(pemCodeListEntity.getCorrelationValue21())
                .setCorrelationValue22(pemCodeListEntity.getCorrelationValue22())
                .setCorrelationValue23(pemCodeListEntity.getCorrelationValue23())
                .setCorrelationValue24(pemCodeListEntity.getCorrelationValue24())
                .setCorrelationValue25(pemCodeListEntity.getCorrelationValue25());
    }

    @Transactional
    public void create(PemCodeListModel pemCodeListModel) {
        pemCodeListRepository.findByProfileName(pemCodeListModel.getProfileName()).ifPresent(pemCodeListEntity -> {
            throw notFound("PEM CodeList Profile");
        });
        pemCodeListModel.setPkId(getPrimaryKey.apply(PEM_CODE_LIST_PRE_APPEND, PKEY_COUNT_TEN));
        createCodeList(pemCodeListModel);
    }

    public PemCodeListModel get(String pkId) {
        return serialize.apply(pemCodeListRepository.findById(pkId).orElse(new PemCodeListEntity()));
    }

    public void delete(String pkId) {
        pemCodeListRepository.delete(pemCodeListRepository.findById(pkId).orElseThrow(() -> GlobalExceptionHandler.notFound("Code List")));
    }

    public List<PemCodeListEntity> findAll() {
        return pemCodeListRepository.findAllByOrderByProfileName().orElse(new ArrayList<>());
    }


    public List<PemCodeListEntity> findAllByProfilesNotIn(List<String> profiles) {
        return pemCodeListRepository.findAllByProfileNameNotInOrderByProfileName(profiles).orElse(new ArrayList<>());
    }

    private void createCodeList(PemCodeListModel pemCodeListModel) {
        pemCodeListRepository.save(mapperToEntity.apply(pemCodeListModel));
    }

    private final Function<PemCodeListEntity, PemCodeListModel> serialize = PemCodeListService::apply;

    private final Function<PemCodeListModel, PemCodeListEntity> mapperToEntity = pemCodeListModel ->
            new PemCodeListEntity().setPkId(pemCodeListModel.getPkId())
                    .setProfileName(pemCodeListModel.getProfileName())
                    .setProtocol(pemCodeListModel.getProtocol())
                    .setCorrelationValue1(pemCodeListModel.getCorrelationValue1())
                    .setCorrelationValue2(pemCodeListModel.getCorrelationValue2())
                    .setCorrelationValue3(pemCodeListModel.getCorrelationValue3())
                    .setCorrelationValue4(pemCodeListModel.getCorrelationValue4())
                    .setCorrelationValue5(pemCodeListModel.getCorrelationValue5())
                    .setCorrelationValue6(pemCodeListModel.getCorrelationValue6())
                    .setCorrelationValue7(pemCodeListModel.getCorrelationValue7())
                    .setCorrelationValue8(pemCodeListModel.getCorrelationValue8())
                    .setCorrelationValue9(pemCodeListModel.getCorrelationValue9())
                    .setCorrelationValue10(pemCodeListModel.getCorrelationValue10())
                    .setCorrelationValue11(pemCodeListModel.getCorrelationValue11())
                    .setCorrelationValue12(pemCodeListModel.getCorrelationValue12())
                    .setCorrelationValue13(pemCodeListModel.getCorrelationValue13())
                    .setCorrelationValue14(pemCodeListModel.getCorrelationValue14())
                    .setCorrelationValue15(pemCodeListModel.getCorrelationValue15())
                    .setCorrelationValue16(pemCodeListModel.getCorrelationValue16())
                    .setCorrelationValue17(pemCodeListModel.getCorrelationValue17())
                    .setCorrelationValue18(pemCodeListModel.getCorrelationValue18())
                    .setCorrelationValue19(pemCodeListModel.getCorrelationValue19())
                    .setCorrelationValue20(pemCodeListModel.getCorrelationValue20())
                    .setCorrelationValue21(pemCodeListModel.getCorrelationValue21())
                    .setCorrelationValue22(pemCodeListModel.getCorrelationValue22())
                    .setCorrelationValue23(pemCodeListModel.getCorrelationValue23())
                    .setCorrelationValue24(pemCodeListModel.getCorrelationValue24())
                    .setCorrelationValue25(pemCodeListModel.getCorrelationValue25());
}
