package com.kangyonggan.api.biz.impl;

import com.kangyonggan.api.biz.service.impl.BaseService;
import com.kangyonggan.api.model.vo.Article;
import com.kangyonggan.api.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Service("articleService")
@Log4j2
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {



}
