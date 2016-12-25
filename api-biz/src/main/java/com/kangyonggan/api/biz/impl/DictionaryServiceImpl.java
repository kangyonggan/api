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
import com.kangyonggan.api.model.dto.request.*;
import com.kangyonggan.api.model.vo.Dictionary;
import com.kangyonggan.api.service.DictionaryService;
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
@Service("dictionaryService")
@Log4j2
public class DictionaryServiceImpl extends BaseService<Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public CommonResponse<Dictionary> SearchDictionsries(SearchDictionariesRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getCode())) {
            criteria.andEqualTo("code", request.getCode());
        }
        if (StringUtils.isNotEmpty(request.getPcode())) {
            criteria.andEqualTo("pcode", request.getPcode());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            criteria.andEqualTo("type", request.getType());
        }
        if (StringUtils.isNotEmpty(request.getValue())) {
            criteria.andLike("value", StringUtil.toLikeString(request.getValue()));
        }
        if (request.getIsDeleted() != null) {
            criteria.andEqualTo("isDeleted", request.getIsDeleted());
        }
        example.setOrderByClause("pcode desc, sort asc");

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Dictionary> dictionaries = super.selectByExample(example);

        log.info("搜索字典结果记录数:size={}", dictionaries.size());
        PageInfo<Dictionary> page = new PageInfo(dictionaries);

        response.setPage(page);
        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:id:{0:id}")
    public CommonResponse<Dictionary> findDictionaryById(FindDictionaryByIdRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = super.selectByPrimaryKey(request.getId());
        response.setData(dictionary);

        if (dictionary == null) {
            response.toNoResultResponse();
        }

        return response;
    }

    @Override
    @CacheGetOrSave("dictionary:type:{0:type}")
    public CommonResponse<Dictionary> findDictionariesByType(FindDictionariesByTypeRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("type", request.getType());

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
            log.error("保存字典时， 属性拷贝异常");
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
    @CacheGetOrSave("dictionary:code:{0:code}")
    public CommonResponse<Dictionary> findDictionaryByCode(FindDictionaryByCodeRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = new Dictionary();
        dictionary.setIsDeleted(AppConstants.IS_DELETED_NO);
        dictionary.setCode(request.getCode());

        dictionary = super.selectOne(dictionary);
        response.setData(dictionary);

        if (dictionary == null) {
            response.toNoResultResponse();
        }
        return response;
    }

    @Override
    @CacheDeleteAll("dictionary:type||dictionary:code:{0:code}||dictionary:id")
    public CommonResponse<Dictionary> updateDictionaryByCode(UpdateDictionaryByCodeRequest request) {
        CommonResponse<Dictionary> response = CommonResponse.getSuccessResponse();

        Dictionary dictionary = new Dictionary();
        try {
            PropertyUtils.copyProperties(dictionary, request);
        } catch (Exception e) {
            log.error("根据代码更新字典时， 属性拷贝异常");
            response.toUnknowExceptionResponse();
            return response;
        }

        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("code", request.getCode());

        int row = dictionaryMapper.updateByExampleSelective(dictionary, example);
        log.info("根据代码更新字典影响行数:row={}", row);

        if (row == 0) {
            log.error("根据代码更新字典失败");
            response.toFailureResponse();
        }
        response.setData(dictionary);

        return response;
    }

}
