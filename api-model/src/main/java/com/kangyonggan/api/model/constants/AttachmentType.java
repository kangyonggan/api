package com.kangyonggan.api.model.constants;

import lombok.Getter;

/**
 * 附件类型
 *
 * @author kangyonggan
 * @since 2016/12/3
 */
public enum AttachmentType {

    ARTICLE("article", "文章附件");

    /**
     * 类型
     */
    @Getter
    private final String type;

    /**
     * 类型名称
     */
    @Getter
    private final String name;

    AttachmentType(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
