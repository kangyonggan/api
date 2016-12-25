package com.kangyonggan.api.service;

import com.kangyonggan.api.model.dto.reponse.AttachmentResponse;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.*;
import com.kangyonggan.api.model.vo.Article;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
public interface ArticleService {

    /**
     * 搜索文章, 一般是在工作台中使用
     *
     * @param request
     * @return
     */
    CommonResponse<Article> searchArticles(SearchArticlesRequest request);

    /**
     * 根据主键查找文章，可以查到未删除的，一般是在工作台中用
     *
     * @param request
     * @return
     */
    AttachmentResponse<Article> getArticle(GetArticleRequest request);

    /**
     * 根据主键查找文章(只能查到未删除的)
     *
     * @param request
     * @return
     */
    AttachmentResponse<Article> findArticleById(FindArticleByIdRequest request);

    /**
     * 保存文章
     *
     * @param request
     * @return
     */
    CommonResponse<Article> saveArticleWithAttachments(SaveArticleRequest request);

    /**
     * 更新文章
     *
     * @param request
     * @return
     */
    CommonResponse<Article> updateArticleWithAttachments(UpdateArticleRequest request);

    /**
     * 根据标签查找文章
     *
     * @param request
     * @return
     */
    CommonResponse<Article> findArticlesByTag(FindArticlesByTagRequest request);

}
