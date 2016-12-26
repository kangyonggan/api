package com.kangyonggan.api.service;

import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveDictionaryRequest;
import com.kangyonggan.api.model.dto.request.SearchDictionariesRequest;
import com.kangyonggan.api.model.dto.request.UpdateDictionaryByCodeRequest;
import com.kangyonggan.api.model.vo.Dictionary;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
public interface ApiDictionaryService {

    /**
     * 搜索字典, 一般是给管理员用
     *
     * @param request
     * @return
     */
    CommonResponse<Dictionary> searchDictionsries(SearchDictionariesRequest request);

    /**
     * 根据主键查找字典，可以查到未删除的，一般是给管理员用
     *
     * @param id
     * @return
     */
    CommonResponse<Dictionary> getDictionary(Long id);

    /**
     * 根据类型查找字典，只能查到未删除的
     *
     * @param type
     * @return
     */
    CommonResponse<Dictionary> findDictionariesByType(String type);

    /**
     * 保存字典
     *
     * @param request
     * @return
     */
    CommonResponse<Dictionary> saveDictionart(SaveDictionaryRequest request);

    /**
     * 根据代码查找字典，只能查到未删除的
     *
     * @param code
     * @return
     */
    CommonResponse<Dictionary> findDictionaryByCode(String code);

    /**
     * 根据代码更新字典
     *
     * @param request
     * @return
     */
    CommonResponse<Dictionary> updateDictionaryByCode(UpdateDictionaryByCodeRequest request);

    /**
     * 根据文章ID查找文章标签
     *
     * @param articleId
     * @return
     */
    CommonResponse<Dictionary> findDictionariesByArticleId(Long articleId);

}
