package me.dalianmao.util;

/**
 * 100-999: 业务异常错误码
 *
 * @author xiezhenzong
 *
 */
public interface BizError extends Status {

    int NOT_EXIST = 100;
    int EXIST = 101;
    int EMAIL_INVALID = 102;
    int PHONE_INVALID = 103;
    int ID_INVALID = 104;
    int NAME_TOO_LONG = 105;
    int NAME_TOO_SHORT = 106;
    int PASSWORD_TOO_WEAK = 107;
    int PASSWORD_INVALID = 108;

}
