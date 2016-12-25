package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class FindDictionaryByCodeRequest extends BaseObject {

    /**
     * 代码
     */
    @Valid(minLength = 1, maxLength = 32)
    private String code;

}
