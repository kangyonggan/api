package com.kangyonggan.api.mapper;

import com.kangyonggan.api.model.vo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends MyMapper<Article> {

    /**
     * 根据标签查询文章
     *
     * @param tag
     * @return
     */
    List<Article> selectArticlesByTag(String tag);
}