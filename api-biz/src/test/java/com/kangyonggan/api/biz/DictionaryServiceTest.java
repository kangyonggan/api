package com.kangyonggan.api.biz;

import com.kangyonggan.api.model.constants.DictionaryType;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.FindDictionariesByTypeRequest;
import com.kangyonggan.api.model.dto.request.FindDictionaryByCodeRequest;
import com.kangyonggan.api.model.dto.request.SaveDictionaryRequest;
import com.kangyonggan.api.model.vo.Dictionary;
import com.kangyonggan.api.service.DictionaryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
public class DictionaryServiceTest extends AbstractServiceTest {

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void testSaveDictionary() {
        SaveDictionaryRequest request = new SaveDictionaryRequest();
        request.setCode("linux");
        request.setValue("Linux");
        request.setType(DictionaryType.ARTICLE_TAG.getType());

        CommonResponse response = dictionaryService.saveDictionart(request);
        log.info(response);
    }

    @Test
    public void testFindDictionaryByCode() {
        FindDictionaryByCodeRequest request = new FindDictionaryByCodeRequest();
        request.setCode("linux");

        CommonResponse<Dictionary> response = dictionaryService.findDictionaryByCode(request);
        log.info(response);
    }

    @Test
    public void testFindDictionaryByType() {
        FindDictionariesByTypeRequest request = new FindDictionariesByTypeRequest();
        request.setType(DictionaryType.ARTICLE_TAG.getType());

        CommonResponse<Dictionary> response = dictionaryService.findDictionariesByType(request);
        log.info(response);
    }

}
