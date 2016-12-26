package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class FindArticlesByTagRequest extends PageRequest {

    /**
     * 标签
     */
    @Valid(required = false, maxLength = 32)
    private String tag;

}
