package com.kangyonggan.api.biz.aop;

import com.kangyonggan.api.common.util.PropertiesUtil;
import com.kangyonggan.api.common.annotation.Valid;
import com.kangyonggan.api.common.util.AopUtil;
import com.kangyonggan.api.common.util.DateUtils;
import com.kangyonggan.api.common.util.Reflections;
import com.kangyonggan.api.model.constants.CommonErrors;
import com.kangyonggan.api.model.constants.ResponseState;
import com.kangyonggan.api.model.dto.reponse.CommonResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 切于所有对外接口的实现方法上，打印入参和出参，打印方法执行时间, 慢接口打印error日志，最外层try{}catch(){}，入参校验
 *
 * @author kangyonggan
 * @since 2016/11/30
 */
@Component
@Log4j2
public class ServiceAop {

    /**
     * 设定的接口最大执行时间
     */
    private Long slowInterfaceTime;

    public ServiceAop() {
        String val = PropertiesUtil.getPropertiesOrDefault("slow.interface.time", "10");
        slowInterfaceTime = Long.parseLong(val);
    }

    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object args[] = joinPoint.getArgs();
        Class clazz = joinPoint.getTarget().getClass();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = clazz.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        String targetName = "[" + clazz.getName() + "." + method.getName() + "]";
        Object result;

        try {
            log.info("进入接口:" + targetName + " - args:" + AopUtil.getStringFromRequest(args));

            String checkMessage = checkArgs(args);
            if (StringUtils.isNotEmpty(checkMessage)) {
                log.error("调用接口{}参数校验未通过, 失败信息:{}", targetName, checkMessage);
                CommonResponse response = new CommonResponse();
                response.setState(ResponseState.F);
                response.setErrCode(CommonErrors.BAD_ARGS.getErrCode());
                response.setErrMsg(CommonErrors.BAD_ARGS.getErrMsg() + "," + checkMessage);
                return response;
            }
            log.info("参数校验通过！");

            long beginTime = DateUtils.getNow().getTime();
            result = joinPoint.proceed(args);
            long endTime = DateUtils.getNow().getTime();
            long time = endTime - beginTime;

            log.info("离开接口:" + targetName + " - return:" + AopUtil.getStringFromResponse(result));
            log.info("接口耗时:" + time + "ms - " + targetName);

            if (time > slowInterfaceTime * 1000) {
                log.error("接口执行超过设定时间" + slowInterfaceTime + "s," + targetName);
            }
        } catch (Exception e) {
            log.error("调用接口" + targetName + "发送未知异常", e);
            CommonResponse response = new CommonResponse();
            response.setState(ResponseState.E);
            response.setErrCode(CommonErrors.UNKNOW_EXCEPTION.getErrCode());
            response.setErrMsg(CommonErrors.UNKNOW_EXCEPTION.getErrMsg());
            return response;
        }

        return result;
    }

    /**
     * 参数校验
     *
     * @param args
     * @return 校验成功返回null， 校验失败返回失败信息
     */
    private String checkArgs(Object args[]) {
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            Class clazz = arg.getClass();
            Field fields[] = Reflections.getFields(clazz);

            for (Field field : fields) {
                String checkMessage = checkField(arg, field);
                if (StringUtils.isNotEmpty(checkMessage)) {
                    return checkMessage;
                }
            }
        }
        return null;
    }

    /**
     * 校验属性
     *
     * @param arg
     * @param field
     * @return
     */
    private String checkField(Object arg, Field field) {
        Valid valid = field.getDeclaredAnnotation(Valid.class);
        if (valid != null) {
            boolean required = valid.required();
            long min = valid.min();
            long max = valid.max();
            int minlength = valid.minLength();
            int length = valid.length();
            int maxlength = valid.maxLength();
            String pattern = valid.pattern();
            String message = valid.message();

            Object value = Reflections.getFieldValue(arg, field.getName());

            // 不允许为空校验
            if (required && value == null) {
                return field.getName() + "不允许为空";
            }

            // int范围判断
            if (value instanceof Integer && (value != null || required)) {
                Integer val = (Integer) value;
                if (val < min) {
                    return field.getName() + "不能小于" + min + ",实际值为" + val;
                }
                if (val > max) {
                    return field.getName() + "不能大于" + max + ",实际值为" + val;
                }
            }

            // long范围判断
            if (value instanceof Long && (value != null || required)) {
                Long val = (Long) value;
                if (val < min) {
                    return field.getName() + "不能小于" + min + ",实际值为" + val;
                }
                if (val > max) {
                    return field.getName() + "不能大于" + max + ",实际值为" + val;
                }
            }

            // String长度判断
            if (value instanceof String && (value != null || required)) {
                String val = (String) value;
                if (val.length() < minlength) {
                    return field.getName() + "长度不能小于" + minlength + ",实际值为" + val;
                }
                if (length != -1 && val.length() != length) {
                    return field.getName() + "长度不等于" + length + ",实际值为" + val;
                }
                if (maxlength != -1 && val.length() > maxlength) {
                    return field.getName() + "长度不能大于" + maxlength + ",实际值为" + val;
                }
            }


            // 正则判断
            if (value != null || required) {
                String val = value.toString();
                if (StringUtils.isNotEmpty(pattern) && !val.matches(pattern)) {
                    if (StringUtils.isEmpty(message)) {
                        return field.getName() + "不合法" + ",实际值为" + val;
                    }
                    return message + ",实际值" + val;
                }
            }

        }
        return null;
    }
}
