package com.kangyonggan.api.biz.service.impl;

import com.kangyonggan.api.biz.service.AttachmentService;
import com.kangyonggan.api.mapper.AttachmentMapper;
import com.kangyonggan.api.model.constants.AppConstants;
import com.kangyonggan.api.model.vo.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/14
 */
@Service
public class AttachmentServiceImpl extends BaseService<Attachment> implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public void saveAttachments(Long sourceId, List<Attachment> attachments) {
        attachmentMapper.insertAttachments(sourceId, attachments);
    }

    @Override
    public List<Attachment> findAttachmentsBySourceId(Long sourceId) {
        Attachment attachment = new Attachment();
        attachment.setIsDeleted(AppConstants.IS_DELETED_NO);
        attachment.setSourceId(sourceId);

        return super.select(attachment);
    }
}
