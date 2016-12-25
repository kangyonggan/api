package com.kangyonggan.api.model.dto.reponse;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.api.model.BaseObject;
import com.kangyonggan.api.model.constants.CommonErrors;
import com.kangyonggan.api.model.constants.ResponseState;
import lombok.Data;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/12/25
 */
@Data
public class CommonResponse<T> extends BaseObject {

    private static final Long serialVersionUID = -1L;

    /**
     * 响应状态
     */
    private ResponseState state;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误描述
     */
    private String errMsg;

    /**
     * 单条数据
     */
    private T data;

    /**
     * 数据集合
     */
    private List<T> list;

    /**
     * 分页数据
     */
    private PageInfo<T> page;

    /**
     * 获取一个响应成功的CommonResponse
     *
     * @param <T>
     * @return
     */
    public static <T> CommonResponse<T> getSuccessResponse() {
        CommonResponse<T> response = new CommonResponse();
        response.setState(ResponseState.Y);
        response.setErrCode(CommonErrors.SUCCESS.getErrCode());
        response.setErrMsg(CommonErrors.SUCCESS.getErrMsg());

        return response;
    }

    /**
     * 转换为请求成功，但没有结果集
     */
    public void toNoResultResponse() {
        setState(ResponseState.F);
        setErrCode(CommonErrors.NO_RESULT.getErrCode());
        setErrMsg(CommonErrors.NO_RESULT.getErrMsg());
    }

    /**
     * 转换为请求失败
     */
    public void toFailureResponse() {
        setState(ResponseState.F);
        setErrCode(CommonErrors.FAILURE.getErrCode());
        setErrMsg(CommonErrors.FAILURE.getErrMsg());
    }

    /**
     * 转换为未知异常
     */
    public void toUnknowExceptionResponse() {
        setState(ResponseState.E);
        setErrCode(CommonErrors.UNKNOW_EXCEPTION.getErrCode());
        setErrMsg(CommonErrors.UNKNOW_EXCEPTION.getErrMsg());
    }
}
