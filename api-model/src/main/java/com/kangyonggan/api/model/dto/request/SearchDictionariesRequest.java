package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class SearchDictionariesRequest extends PageRequest {

    /**
     * 值
     */
    @Valid(required = false, maxLength = 128)
    private String value;

    /**
     * 类型
     */
    @Valid(required = false, maxLength = 16)
    private String type;

}
