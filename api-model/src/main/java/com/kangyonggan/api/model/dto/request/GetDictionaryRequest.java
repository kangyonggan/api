package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class GetDictionaryRequest extends BaseObject {

    /**
     * 主键, 自增
     */
    @Valid(min = 1, max = Long.MAX_VALUE)
    private Long id;

}
