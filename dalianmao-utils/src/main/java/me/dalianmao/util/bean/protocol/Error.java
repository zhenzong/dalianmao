package me.dalianmao.util.bean.protocol;

import me.dalianmao.util.bean.BaseObject;

/**
 * error object
 *
 * @author xiezhenzong
 *
 */
public class Error extends BaseObject {

    public int code;
    public String message;

    public Error() {
    }

    /**
     * @param code
     */
    public Error(int code) {
        this.code = code;
    }

    /**
     * @param message
     */
    public Error(String message) {
        this.message = message;
    }

    /**
     * @param code
     * @param message
     */
    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
