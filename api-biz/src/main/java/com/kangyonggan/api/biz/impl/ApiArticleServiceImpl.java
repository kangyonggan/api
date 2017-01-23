package com.kangyonggan.api.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kangyonggan.api.biz.service.ArticleService;
import com.kangyonggan.api.biz.service.AttachmentService;
import com.kangyonggan.api.biz.service.impl.BaseService;
import com.kangyonggan.api.common.annotation.CacheDelete;
import com.kangyonggan.api.common.annotation.CacheDeleteAll;
import com.kangyonggan.api.common.annotation.CacheGetOrSave;
import com.kangyonggan.api.common.util.StringUtil;
import com.kangyonggan.api.mapper.ArticleMapper;
import com.kangyonggan.api.model.constants.AppConstants;
import com.kangyonggan.api.model.constants.AttachmentType;
import com.kangyonggan.api.model.dto.reponse.AttachmentResponse;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.*;
import com.kangyonggan.api.model.vo.Article;
import com.kangyonggan.api.model.vo.Attachment;
import com.kangyonggan.api.service.ApiArticleService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Service("apiArticleService")
@Log4j2
public class ApiArticleServiceImpl extends BaseService<Article> implements ApiArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ArticleService articleService;

    @Override
    public CommonResponse<Article> searchArticles(SearchArticlesRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getTitle())) {
            criteria.andLike("title", StringUtil.toLikeString(request.getTitle()));
        }
        criteria.andEqualTo("createUsername", request.getCreateUsername());

        example.setOrderByClause("created_time desc");

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Article> articles = super.selectByExample(example);

        log.info("搜索文章结果记录数:size={}", articles.size());
        PageInfo<Article> page = new PageInfo(articles);

        response.setPage(page);
        return response;
    }

    @Override
//    @CacheGetOrSave("article:id:{0}") // 会有bug
    public AttachmentResponse<Article> getArticle(Long id) {
        AttachmentResponse<Article> response = AttachmentResponse.getSuccessResponse();

        Article article = super.selectByPrimaryKey(id);
        response.setData(article);

        if (article == null) {
            response.toNoResultResponse();
        } else {
            List<Attachment> attachments = attachmentService.findAttachmentsBySourceIdAndType(article.getId(), AttachmentType.ARTICLE.getType());
            response.setAttachments(attachments);
        }

        return response;
    }

    @Override
    @CacheGetOrSave("article:id:{0}")
    public AttachmentResponse<Article> findArticleById(Long id) {
        AttachmentResponse<Article> response = AttachmentResponse.getSuccessResponse();

        Article article = new Article();
        article.setId(id);
        article.setIsDeleted(AppConstants.IS_DELETED_NO);

        article = super.selectOne(article);
        response.setData(article);

        if (article == null) {
            response.toNoResultResponse();
        } else {
            List<Attachment> attachments = attachmentService.findAttachmentsBySourceIdAndType(article.getId(), AttachmentType.ARTICLE.getType());
            response.setAttachments(attachments);
        }

        return response;
    }

    @Override
    @CacheDeleteAll("article:tag")
    public CommonResponse<Article> saveArticleWithAttachments(SaveArticleRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        articleService.saveArticleWithAttachments(request, response);

        return response;
    }

    @Override
    @CacheDelete("article:id:{0:id}||dictionary:article:{0:id}")
    @CacheDeleteAll("article:tag")
    public CommonResponse<Article> updateArticleWithAttachments(UpdateArticleWithAttachmentsRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        articleService.updateArticleWithAttachments(request, response);

        return response;
    }

    @Override
    @CacheDelete("article:id:{0:id}||dictionary:article:{0:id}")
    @CacheDeleteAll("article:tag")
    public CommonResponse<Article> updateArticle(UpdateArticleRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        articleService.updateArticle(request, response);

        return response;
    }

    @Override
    @CacheGetOrSave("article:tag:{0:tag}:pageNum:{0:pageNum}:pageSize:{0:pageSize}")
    public CommonResponse<Article> findArticlesByTag(FindArticlesByTagRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Article> articles = articleMapper.selectArticlesByTag(request.getTag());

        log.info("根据标签查询文章结果记录数:size={}", articles.size());
        PageInfo<Article> page = new PageInfo(articles);
        response.setPage(page);

        if (articles.size() == 0) {
            response.toNoResultResponse();
        }

        return response;
    }

    @Override
    @CacheDelete("article:id:{0:id}")
    public CommonResponse<Article> deleteArticleAttachment(Long articleId, Long attachmentId) {
        attachmentService.deleteAttachment(articleId, attachmentId, AttachmentType.ARTICLE.getType());

        return CommonResponse.getSuccessResponse();
    }
}
