package com.kangyonggan.api.biz.service;

import com.kangyonggan.api.model.vo.Dictionary;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/26
 */
public interface DictionaryService {

    /**
     * 查找标签
     *
     * @param codes
     * @return
     */
    List<Dictionary> findDictionariesByCodes(String codes);

    /**
     * 保存文章标签
     *
     * @param articleId
     * @param dictionaries
     */
    void saveArticleDictionaries(Long articleId, List<Dictionary> dictionaries);

    /**
     * 删除文章标签
     *
     * @param id
     */
    void deleteArticleDictionaries(Long id);
}
