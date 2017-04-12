package me.dalianmao.util;

/**
 * 状态码<br/>
 * 0-9: 表示正常的响应<br/>
 * 10-99: 表示异常的响应<br/>
 * 通过继承本接口来拓展新的状态码
 *
 * @author xiezhenzong
 *
 */
public interface Status {

    /**
     * 调用成功
     */
    int OK = 0;

    /**
     * 部分成功
     */
    int PARTLY_OK = 1;

    /**
     * 处理中，不一定是异常，也不一定是正常的，，所以请自行判断在哪儿使用
     */
    int PROCESSING = 2;

    /**
     * 参数异常
     */
    int PARAM_ERROR = 10;

    /**
     * 对方返回异常，比如，null
     */
    int RESPONSE_ERROR = 11;

    /**
     * 超时异常
     */
    int TIMEOUT_ERROR = 12;

    /**
     * 网络异常，不包含上面的超时异常，比如： reset by peer等
     */
    int NETWORK_ERROR = 13;

    /**
     * 系统异常
     */
    int SYSTEM_ERROR = 14;

    /**
     * 权限异常，自行定义
     */
    int AUTH_ERROR = 15;

    /**
     * 超限异常，比如，超出配额，但是，如果参数过长，请放在参数异常那里，
     */
    int EXCEED_ERROR = 16;

    /**
     * 数据库异常
     */
    int DB_ERROR = 17;

    /**
     * 业务异常
     */
    int BIZ_ERROR = 18;

    /**
     * 外部服务异常
     */
    int INVOKE_ERROR = 19;

    /**
     * 未知的异常
     */
    int ERROR = 20;

}
