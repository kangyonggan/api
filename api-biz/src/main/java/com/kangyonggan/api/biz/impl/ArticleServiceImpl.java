package com.kangyonggan.api.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kangyonggan.api.biz.service.AttachmentService;
import com.kangyonggan.api.biz.service.impl.BaseService;
import com.kangyonggan.api.common.annotation.CacheDelete;
import com.kangyonggan.api.common.annotation.CacheDeleteAll;
import com.kangyonggan.api.common.annotation.CacheGetOrSave;
import com.kangyonggan.api.common.util.StringUtil;
import com.kangyonggan.api.mapper.ArticleMapper;
import com.kangyonggan.api.model.constants.AppConstants;
import com.kangyonggan.api.model.dto.reponse.AttachmentResponse;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.*;
import com.kangyonggan.api.model.vo.Article;
import com.kangyonggan.api.model.vo.Attachment;
import com.kangyonggan.api.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Service("articleService")
@Log4j2
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public CommonResponse<Article> searchArticles(SearchArticlesRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getTitle())) {
            criteria.andLike("title", StringUtil.toLikeString(request.getTitle()));
        }
        if (request.getIsDeleted() != null) {
            criteria.andEqualTo("isDeleted", request.getIsDeleted());
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
    @CacheGetOrSave("article:id:{0:id}")
    public AttachmentResponse<Article> getArticle(GetArticleRequest request) {
        AttachmentResponse<Article> response = AttachmentResponse.getSuccessResponse();

        Article article = super.selectByPrimaryKey(request.getId());
        response.setData(article);

        if (article == null) {
            response.toNoResultResponse();
        } else {
            List<Attachment> attachments = attachmentService.findAttachmentsBySourceId(article.getId());
            response.setAttachments(attachments);
        }

        return response;
    }

    @Override
    @CacheGetOrSave("article:id:{0:id}")
    public AttachmentResponse<Article> findArticleById(FindArticleByIdRequest request) {
        AttachmentResponse<Article> response = AttachmentResponse.getSuccessResponse();

        Article article = new Article();
        article.setId(request.getId());
        article.setIsDeleted(AppConstants.IS_DELETED_NO);

        article = super.selectOne(article);
        response.setData(article);

        if (article == null) {
            response.toNoResultResponse();
        } else {
            List<Attachment> attachments = attachmentService.findAttachmentsBySourceId(article.getId());
            response.setAttachments(attachments);
        }

        return response;
    }

    @Override
    @CacheDeleteAll("article:tag")
    public CommonResponse<Article> saveArticleWithAttachments(SaveArticleRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        Article article = new Article();
        try {
            PropertyUtils.copyProperties(article, request);
        } catch (Exception e) {
            log.error("保存文章时， 属性拷贝异常");
            response.toUnknowExceptionResponse();
            return response;
        }

        log.info("copy request to article, article is:{}", article);
        int row = super.insertSelective(article);
        log.info("保存文章影响行数:row={}", row);
        if (row == 0) {
            log.error("保存文章失败，我也不知道为什么");
            response.toFailureResponse();
        }
        response.setData(article);

        // 保存附件
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            attachmentService.saveAttachments(article.getId(), request.getAttachments());
        }

        return response;
    }

    @Override
    @CacheDelete("article:id:{0:id}")
    @CacheDeleteAll("article:tag")
    public CommonResponse<Article> updateArticle(UpdateArticleRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        Article article = new Article();
        try {
            PropertyUtils.copyProperties(article, request);
        } catch (Exception e) {
            log.error("根据主键更新文章时， 属性拷贝异常");
            response.toUnknowExceptionResponse();
            return response;
        }
        log.info("copy request to article, article is:{}", article);

        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("id", request.getId());

        int row = articleMapper.updateByExampleSelective(article, example);
        log.info("根据主键更新文章影响行数:row={}", row);

        if (row == 0) {
            log.error("根据主键更新文章失败");
            response.toFailureResponse();
        }
        response.setData(article);

        return response;
    }

    @Override
    @CacheGetOrSave("article:tag:{0:tag}:pageNum:{0:pageNum}:pageSize:{0:pageSize}")
    public CommonResponse<Article> findArticlesByTag(FindArticlesByTagRequest request) {
        CommonResponse<Article> response = CommonResponse.getSuccessResponse();

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Article> articles = articleMapper.selectArticlesByTag(request.getTag());

        log.info("根据标签查询文章结果记录数:size={}", articles.size());
        response.setList(articles);

        if (articles.size() == 0) {
            response.toNoResultResponse();
        }

        return response;
    }
}
