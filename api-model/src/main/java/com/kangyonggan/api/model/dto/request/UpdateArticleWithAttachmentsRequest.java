package com.kangyonggan.api.model.dto.request;

import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.model.BaseObject;
import com.kangyonggan.api.model.vo.Attachment;
import lombok.Data;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class UpdateArticleWithAttachmentsRequest extends BaseObject {

    /**
     * 主键, 自增
     */
    @Valid(min = 1, max = Long.MAX_VALUE)
    private Long id;

    /**
     * 文章标题
     */
    @Valid(required = false, minLength = 1, maxLength = 64)
    private String title;

    /**
     * 标签
     */
    @Valid(required = false, minLength = 1, maxLength = 512)
    private String tags;

    /**
     * 创建人
     */
    @Valid(required = false, minLength = 1, maxLength = 20)
    private String createUsername;

    /**
     * 创建人姓名
     */
    @Valid(required = false, minLength = 1, maxLength = 32)
    private String createFullname;

    /**
     * 文章内容
     */
    @Valid(required = false, minLength = 1)
    private String content;

    private List<Attachment> attachments;

}
