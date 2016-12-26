package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class SaveDictionaryRequest extends BaseObject {

    /**
     * 代码
     */
    @Valid(minLength = 1, maxLength = 32)
    private String code;

    /**
     * 值
     */
    @Valid(minLength = 1, maxLength = 128)
    private String value;

    /**
     * 类型
     */
    @Valid(minLength = 1, maxLength = 16)
    private String type;

    /**
     * 排序(从0开始)
     */
    @Valid(required = false, min = 0, max = Integer.MAX_VALUE)
    private Integer sort;

}
