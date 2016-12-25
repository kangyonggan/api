package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class FindDictionariesByTypeRequest extends BaseObject {

    /**
     * 类型
     */
    @Valid(minLength = 1, maxLength = 16)
    private String type;

}
