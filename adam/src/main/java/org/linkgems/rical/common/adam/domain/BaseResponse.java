package org.linkgems.rical.common.adam.domain;

import org.linkgems.rical.common.adam.enums.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:13 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T> extends BaseObject{

    private boolean success;

    private T data;

    private BaseError error;

    public static <T> BaseResponse<T> success(T data){
        BaseResponse<T> response = new BaseResponse<>();
        response.setSuccess(Boolean.TRUE);
        response.setData(data);
        return response;
    }

    public static BaseResponse failure(){
        BaseResponse response = new BaseResponse();
        response.setSuccess(Boolean.FALSE);
        response.setError(new BaseError(ErrorEnum.UNKNOWN_ERROR));
        return response;
    }

    public static BaseResponse failure(BaseException ex){
        BaseResponse response = new BaseResponse();
        response.setSuccess(Boolean.FALSE);
        response.setError(new BaseError(ex));
        return response;
    }

    public static BaseResponse failure(String code, String message){
        BaseResponse response = new BaseResponse();
        response.setSuccess(Boolean.FALSE);
        response.setError(new BaseError(code, message));
        return response;
    }
}
