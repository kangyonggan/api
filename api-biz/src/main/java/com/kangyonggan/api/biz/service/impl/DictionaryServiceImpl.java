package com.kangyonggan.api.biz.service.impl;

import com.kangyonggan.api.biz.service.DictionaryService;
import com.kangyonggan.api.common.annotation.LogTime;
import com.kangyonggan.api.common.util.Collections3;
import com.kangyonggan.api.mapper.DictionaryMapper;
import com.kangyonggan.api.model.vo.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/26
 */
@Service
public class DictionaryServiceImpl extends BaseService<Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    @LogTime
    public List<Dictionary> findDictionariesByCodes(String codes) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andIn("code", Arrays.asList(codes.split(",")));

        return super.selectByExample(example);
    }

    @Override
    public void saveArticleDictionaries(Long articleId, List<Dictionary> dictionaries) {
        dictionaryMapper.insertArticleDictionaries(articleId, Collections3.extractToList(dictionaries, "code"));
    }

    @Override
    public void deleteArticleDictionaries(Long id) {
        dictionaryMapper.deleteArticleDictionaries(id);
    }
}
