package me.dalianmao.util.bean.protocol;

import java.util.List;

import org.springframework.util.Assert;

import me.dalianmao.util.Status;
import me.dalianmao.util.bean.BaseObject;
import me.dalianmao.util.collection.ListUtil;

/**
 * base response
 *
 * @author xiezhenzong
 *
 */
public class Response extends BaseObject {

    /**
     * 响应状态， 如果业务失败的话，则添加Error对象
     */
    public int status = Status.OK;
    public List<Error> errors = ListUtil.newList(3);
    public List<Object> data = ListUtil.newList(10);

    public Response() {
    }

    public Response(int status) {
        this.status = status;
    }

    /**
     * response is ok
     *
     * @return boolean
     */
    public boolean isOk() {
        return status == Status.OK && errors.isEmpty();
    }

    /**
     * has any error in response
     *
     * @return boolean
     */
    public boolean hasError() {
        return status != Status.OK || errors.size() > 0;
    }

    /**
     * add error
     *
     * @param error
     *            the error to add
     */
    public Response addError(Error error) {
        errors.add(error);
        return this;
    }

    public Response addError(String error) {
        return addError(new Error(error));
    }

    public Response addData(Object item) {
        data.add(item);
        return this;
    }

    public <T> Response wrap(List<T> target) {
        Assert.notNull(target);
        data.addAll(target);
        return this;
    }
}
