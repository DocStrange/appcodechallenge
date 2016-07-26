package com.shaunmccready.entity;

import org.apache.commons.lang3.StringUtils;


public enum ErrorCodes {

    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND"),
    MAX_USERS_REACHED("MAX_USERS_REACHED"),
    UNAUTHORIZED("UNAUTHORIZED"),
    OPERATION_CANCELED("OPERATION_CANCELED"),
    CONFIGURATION_ERROR("CONFIGURATION_ERROR"),
    INVALID_RESPONSE("INVALID_RESPONSE"),
    PENDING("PENDING"),
    FORBIDDEN("FORBIDDEN"),
    BINDING_NOT_FOUND("BINDING_NOT_FOUND"),
    TRANSPORT_ERROR("TRANSPORT_ERROR"),
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private String errorCode;

    ErrorCodes(final String errorCode){this.errorCode = errorCode; }

    public String getErrorCode(){ return errorCode;}

    public static ErrorCodes findByName(final String errorCode){
        if (StringUtils.isNotBlank(errorCode)){
            for(ErrorCodes accountStatus : values()){
                if(accountStatus.getErrorCode().equals(errorCode)){
                    return accountStatus;
                }
            }
        }
        return null;
    }


}
