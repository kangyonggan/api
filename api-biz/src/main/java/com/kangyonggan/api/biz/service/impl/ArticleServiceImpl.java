package com.kangyonggan.api.biz.service.impl;

import com.kangyonggan.api.biz.service.ArticleService;
import com.kangyonggan.api.biz.service.AttachmentService;
import com.kangyonggan.api.biz.service.DictionaryService;
import com.kangyonggan.api.common.annotation.LogTime;
import com.kangyonggan.api.common.util.Collections3;
import com.kangyonggan.api.mapper.ArticleMapper;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveArticleRequest;
import com.kangyonggan.api.model.dto.request.UpdateArticleRequest;
import com.kangyonggan.api.model.dto.request.UpdateArticleWithAttachmentsRequest;
import com.kangyonggan.api.model.vo.Article;
import com.kangyonggan.api.model.vo.Dictionary;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/26
 */
@Service
@Log4j2
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @LogTime
    public void saveArticleWithAttachments(SaveArticleRequest request, CommonResponse<Article> response) {
        List<Dictionary> dictionaries = dictionaryService.findDictionariesByCodes(request.getTags());
        request.setTags(Collections3.convertToString(Collections3.extractToList(dictionaries, "value"), " "));

        Article article = new Article();
        try {
            PropertyUtils.copyProperties(article, request);
        } catch (Exception e) {
            log.error("保存文章时， 属性拷贝异常", e);
            response.toUnknowExceptionResponse();
            return;
        }

        log.info("copy request to article, article is:{}", article);
        int row = super.insertSelective(article);
        log.info("保存文章影响行数:row={}", row);
        if (row == 0) {
            log.error("保存文章失败，我也不知道为什么");
            response.toFailureResponse();
        }
        response.setData(article);

        dictionaryService.saveArticleDictionaries(article.getId(), dictionaries);

        // 保存附件
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            attachmentService.saveAttachments(article.getId(), request.getAttachments());
        }
    }

    @Override
    @LogTime
    public void updateArticleWithAttachments(UpdateArticleWithAttachmentsRequest request, CommonResponse<Article> response) {

        List<Dictionary> dictionaries = null;
        if (StringUtils.isNotEmpty(request.getTags())) {
            dictionaries = dictionaryService.findDictionariesByCodes(request.getTags());
            request.setTags(Collections3.convertToString(Collections3.extractToList(dictionaries, "value"), " "));
        }

        Article article = new Article();
        try {
            PropertyUtils.copyProperties(article, request);
        } catch (Exception e) {
            log.error("根据主键更新文章时， 属性拷贝异常", e);
            response.toUnknowExceptionResponse();
            return;
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
        article = super.selectByPrimaryKey(article.getId());
        response.setData(article);

        if (dictionaries != null && !dictionaries.isEmpty()) {

            // 删除原本的标签
            dictionaryService.deleteArticleDictionaries(article.getId());

            dictionaryService.saveArticleDictionaries(article.getId(), dictionaries);
        }

        // 保存附件
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            attachmentService.saveAttachments(article.getId(), request.getAttachments());
        }
    }

    @Override
    @LogTime
    public void updateArticle(UpdateArticleRequest request, CommonResponse<Article> response) {
        List<Dictionary> dictionaries = null;
        if (StringUtils.isNotEmpty(request.getTags())) {
            dictionaries = dictionaryService.findDictionariesByCodes(request.getTags());
            request.setTags(Collections3.convertToString(Collections3.extractToList(dictionaries, "value"), " "));
        }

        Article article = new Article();
        try {
            PropertyUtils.copyProperties(article, request);
        } catch (Exception e) {
            log.error("根据主键更新文章时， 属性拷贝异常", e);
            response.toUnknowExceptionResponse();
            return;
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
        article = super.selectByPrimaryKey(article.getId());
        response.setData(article);

        if (dictionaries != null && !dictionaries.isEmpty()) {

            // 删除原本的标签
            dictionaryService.deleteArticleDictionaries(article.getId());

            dictionaryService.saveArticleDictionaries(article.getId(), dictionaries);
        }
    }
}
