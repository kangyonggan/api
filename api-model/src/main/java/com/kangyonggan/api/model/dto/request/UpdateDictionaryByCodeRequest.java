package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class UpdateDictionaryByCodeRequest extends BaseObject {

    /**
     * 代码
     */
    @Valid(minLength = 1, maxLength = 32)
    private String code;

    /**
     * 值
     */
    @Valid(required = false, minLength = 1, maxLength = 128)
    private String value;

    /**
     * 类型
     */
    @Valid(required = false, minLength = 1, maxLength = 16)
    private String type;

    /**
     * 排序(从0开始)
     */
    @Valid(required = false, min = 0, max = Integer.MAX_VALUE)
    private Integer sort;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Valid(required = false, pattern = "^[01]$", message = "是否删除的值只能是0或1")
    private Byte isDeleted;

}
