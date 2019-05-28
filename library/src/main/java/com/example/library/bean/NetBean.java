package com.example.library.bean;

import com.example.library.type.NetType;

import java.lang.reflect.Method;


/**
 * author:  ycl
 * date:  2019/5/28 22:53
 * desc:
 */
public class NetBean {

    private Class<?> type;
    private NetType netType;
    private Method method;

    public NetBean(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
