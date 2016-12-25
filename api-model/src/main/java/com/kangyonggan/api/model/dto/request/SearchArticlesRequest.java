package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class SearchArticlesRequest extends PageRequest {

    /**
     * 文章标题
     */
    @Valid(required = false, minLength = 1, maxLength = 64)
    private String title;

    /**
     * 创建人
     */
    @Valid(minLength = 1, maxLength = 20)
    private String createUsername;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Valid(required = false, pattern = "^[01]$", message = "是否删除的值只能是0或1")
    private Byte isDeleted;

}
