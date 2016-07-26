package com.shaunmccready.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

public class ResponseDTO extends GenericDTO{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountIdentifier;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    public static ResponseDTO buildSuccessResponse(String accountId, String message){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(true);

        if(StringUtils.isNotBlank(accountId)) {
            responseDTO.setAccountIdentifier(accountId);
        }

        if(StringUtils.isNotBlank(message)) {
            responseDTO.setMessage(message);
        }

        return responseDTO;
    }

    public static ResponseDTO buildFailedResponse(String errorCode, String errorMessage){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(false);
        responseDTO.setErrorCode(errorCode);
        responseDTO.setMessage(errorMessage);

        return responseDTO;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
