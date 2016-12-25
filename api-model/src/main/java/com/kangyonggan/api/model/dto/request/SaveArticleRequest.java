package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class SaveArticleRequest extends BaseObject {

    /**
     * 文章标题
     */
    @Valid(minLength = 1, maxLength = 64)
    private String title;

    /**
     * 标签
     */
    @Valid(minLength = 1, maxLength = 512)
    private String tags;

    /**
     * 创建人
     */
    @Valid(minLength = 1, maxLength = 20)
    private String createUsername;

    /**
     * 创建人姓名
     */
    @Valid(minLength = 1, maxLength = 32)
    private String createFullname;

    /**
     * 文章内容
     */
    @Valid(minLength = 1)
    private String content;

}
