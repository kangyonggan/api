package com.kangyonggan.api.model.dto.reponse;

import com.kangyonggan.api.model.constants.CommonErrors;
import com.kangyonggan.api.model.constants.ResponseState;
import com.kangyonggan.api.model.vo.Attachment;
import lombok.Data;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class AttachmentResponse<T> extends CommonResponse<T> {

    private static final Long serialVersionUID = -1L;

    private List<Attachment> attachments;

    /**
     * 获取一个响应成功的CommonResponse
     *
     * @param <T>
     * @return
     */
    public static <T> AttachmentResponse<T> getSuccessResponse() {
        AttachmentResponse<T> response = new AttachmentResponse();
        response.setState(ResponseState.Y);
        response.setErrCode(CommonErrors.SUCCESS.getErrCode());
        response.setErrMsg(CommonErrors.SUCCESS.getErrMsg());

        return response;
    }
}
