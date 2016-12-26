package com.kangyonggan.api.biz;

import com.kangyonggan.api.model.constants.DictionaryType;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveDictionaryRequest;
import com.kangyonggan.api.service.ApiDictionaryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
public class DictionaryServiceTest extends AbstractServiceTest {

    @Autowired
    private ApiDictionaryService dictionaryService;

    @Test
    public void testSaveDictionary() {
        SaveDictionaryRequest request = new SaveDictionaryRequest();
        request.setCode("linux");
        request.setValue("Linux");
        request.setType(DictionaryType.ARTICLE_TAG.getType());

        CommonResponse response = dictionaryService.saveDictionart(request);
        log.info(response);
    }

}
