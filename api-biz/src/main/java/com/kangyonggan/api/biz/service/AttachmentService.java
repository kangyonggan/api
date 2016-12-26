package com.kangyonggan.api.biz.service;



import com.kangyonggan.api.model.vo.Attachment;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/14
 */
public interface AttachmentService {

    /**
     * 保存附件
     *
     * @param sourceId
     * @param attachments
     */
    void saveAttachments(Long sourceId, List<Attachment> attachments);

    /**
     * 查找附件
     *
     * @param sourceId
     * @param type
     * @return
     */
    List<Attachment> findAttachmentsBySourceIdAndType(Long sourceId, String type);
}
