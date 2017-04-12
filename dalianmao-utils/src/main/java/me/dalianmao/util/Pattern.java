package me.dalianmao.util;

/**
 * 格式
 *
 * @author xiezhenzong
 *
 */
public interface Pattern {

    // 时间格式
    String DATE = "yyyy-MM-dd";
    String DATE_SHORT = "yyyyMMdd";
    String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    String DATE_TIME_SHORT = "yyyyMMddHHmmss";

    // 手机号
    java.util.regex.Pattern MOBILE = java.util.regex.Pattern.compile("^((13[0-9])|(14[57])|(15[^4,\\D])|(17[0678])|(18[0,5-9]))\\d{8}$");

}
