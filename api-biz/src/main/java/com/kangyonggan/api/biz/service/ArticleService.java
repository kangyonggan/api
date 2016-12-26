package com.kangyonggan.api.biz.service;

import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveArticleRequest;
import com.kangyonggan.api.model.dto.request.UpdateArticleRequest;
import com.kangyonggan.api.model.vo.Article;

/**
 * @author kangyonggan
 * @since 2016/12/26
 */
public interface ArticleService {

    /**
     * 保存文章
     *
     * @param request
     * @param response
     */
    void saveArticleWithAttachments(SaveArticleRequest request, CommonResponse<Article> response);

    /**
     * 更新文章
     *
     * @param request
     * @param response
     */
    void updateArticleWithAttachments(UpdateArticleRequest request, CommonResponse<Article> response);
}
