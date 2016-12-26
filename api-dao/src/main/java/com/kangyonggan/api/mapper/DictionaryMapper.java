package com.kangyonggan.api.mapper;

import com.kangyonggan.api.model.vo.Dictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryMapper extends MyMapper<Dictionary> {

    /**
     * 保存文章标签
     *
     * @param articleId
     * @param codes
     */
    void insertArticleDictionaries(@Param("articleId") Long articleId, @Param("codes") List<String> codes);

    /**
     * 删除文章标签
     *
     * @param articleId
     */
    void deleteArticleDictionaries(Long articleId);

    /**
     * 根据文章ID查找文章标签
     *
     * @param articleId
     * @return
     */
    List<Dictionary> selectDictionariesByArticleId(Long articleId);
}