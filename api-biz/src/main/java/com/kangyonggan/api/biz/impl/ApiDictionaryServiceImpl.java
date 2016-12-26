package com.kangyonggan.api.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kangyonggan.api.biz.service.impl.BaseService;
import com.kangyonggan.api.common.annotation.CacheDeleteAll;
import com.kangyonggan.api.common.annotation.CacheGetOrSave;
import com.kangyonggan.api.common.util.StringUtil;
import com.kangyonggan.api.mapper.DictionaryMapper;
import com.kangyonggan.api.model.constants.AppConstants;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import com.kangyonggan.api.model.dto.request.SaveDictionaryRequest;
import com.kangyonggan.api.model.dto.request.SearchDictionariesRequest;
import com.kangyonggan.api.model.dto.request.UpdateDictionaryByCodeRequest;
import com.kangyonggan.api.model.vo.Dictionary;
import com.kangyonggan.api.service.ApiDictionaryService;
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
@Service("apiDictionaryService")
@Log4j2
public class ApiDictionaryServiceImpl extends BaseService<Dictionary> implements ApiDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public CommonResponse<Dictionary> searchDictionsries(SearchDictionariesRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getType())) {
            criteria.andEqualTo("type", request.getType());
        }
        if (StringUtils.isNotEmpty(request.getValue())) {
            criteria.andLike("value", StringUtil.toLikeString(request.getValue()));
        }
        example.setOrderByClause("type desc, sort asc");

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Dictionary> dictionaries = super.selectByExample(example);

        log.info("搜索字典结果记录数:size={}", dictionaries.size());
        PageInfo<Dictionary> page = new PageInfo(dictionaries);
        response.setPage(page);

        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:id:{0}")
    public CommonResponse<Dictionary> getDictionary(Long id) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = super.selectByPrimaryKey(id);
        response.setData(dictionary);

        if (dictionary == null) {
            response.toNoResultResponse();
        }

        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:type:{0}")
    public CommonResponse<Dictionary> findDictionariesByType(String type) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("type", type);

        example.setOrderByClause("sort asc");
        List<Dictionary> dictionaries = super.selectByExample(example);
        log.info("根据类型查询字典结果记录数:size={}", dictionaries.size());
        response.setList(dictionaries);

        if (dictionaries.size() == 0) {
            response.toNoResultResponse();
        }

        return response;
    }

    @Override
    @CacheDeleteAll("dictionary:type")
    public CommonResponse<Dictionary> saveDictionart(SaveDictionaryRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = new Dictionary();
        try {
            PropertyUtils.copyProperties(dictionary, request);
        } catch (Exception e) {
            log.error("保存字典时， 属性拷贝异常", e);
            response.toUnknowExceptionResponse();
            return response;
        }

        log.info("copy request to dictionary, dictionary is:{}", dictionary);
        int row = super.insertSelective(dictionary);
        log.info("保存字典影响行数:row={}", row);
        if (row == 0) {
            log.error("保存字典失败，我也不知道为什么");
            response.toFailureResponse();
        }
        response.setData(dictionary);

        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:code:{0}")
    public CommonResponse<Dictionary> findDictionaryByCode(String code) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = new Dictionary();
        dictionary.setIsDeleted(AppConstants.IS_DELETED_NO);
        dictionary.setCode(code);

        dictionary = super.selectOne(dictionary);
        response.setData(dictionary);

        if (dictionary == null) {
            response.toNoResultResponse();
        }
        return response;
    }

    @Override
    @CacheDeleteAll("dictionary:type||dictionary:code:{0:code}||dictionary:id||dictionary:article")
    public CommonResponse<Dictionary> updateDictionaryByCode(UpdateDictionaryByCodeRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = new Dictionary();
        try {
            PropertyUtils.copyProperties(dictionary, request);
        } catch (Exception e) {
            log.error("根据代码更新字典时， 属性拷贝异常", e);
            response.toUnknowExceptionResponse();
            return response;
        }
        log.info("copy request to dictionary, dictionary is:{}", dictionary);

        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("code", request.getCode());

        int row = dictionaryMapper.updateByExampleSelective(dictionary, example);
        log.info("根据代码更新字典影响行数:row={}", row);

        if (row == 0) {
            log.error("根据代码更新字典失败, 我也不知道为什么");
            response.toFailureResponse();
        }
        dictionary = super.selectOne(dictionary);
        response.setData(dictionary);

        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:article:{0}")
    public CommonResponse<Dictionary> findDictionariesByArticleId(Long articleId) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        List<Dictionary> dictionaries = dictionaryMapper.selectDictionariesByArticleId(articleId);
        log.info("根据文章ID查询字典结果记录数:size={}", dictionaries.size());
        response.setList(dictionaries);

        if (dictionaries.size() == 0) {
            response.toNoResultResponse();
        }

        return response;
    }

}
