package com.kangyonggan.api.biz.service;

import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveArticleRequest;
import com.kangyonggan.api.model.dto.request.UpdateArticleRequest;
import com.kangyonggan.api.model.dto.request.UpdateArticleWithAttachmentsRequest;
import com.kangyonggan.api.model.vo.Article;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/26
 */
public interface ArticleService {

    /**
     * 保存文章（带附件）
     *
     * @param request
     * @param response
     */
    void saveArticleWithAttachments(SaveArticleRequest request, CommonResponse<Article> response);

    /**
     * 更新文章（带附件）
     *
     * @param request
     * @param response
     */
    void updateArticleWithAttachments(UpdateArticleWithAttachmentsRequest request, CommonResponse<Article> response);

    /**
     * 更新文章
     *
     * @param request
     * @param response
     */
    void updateArticle(UpdateArticleRequest request, CommonResponse<Article> response);

    /**
     * 查找所有文章
     *
     * @return
     */
    List<Article> findAllArticles();

}
