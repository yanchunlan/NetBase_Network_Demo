package com.example.library.utils;

import com.example.library.annotation.Network;
import com.example.library.bean.NetBean;
import com.example.library.type.NetType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * author:  ycl
 * date:  2019/5/28 23:13
 * desc:
 */
public class NetAnnotationUtils {

    public static void post(NetType netType, Map<Object, List<NetBean>> networkList) {
        Set<Object> keySet = networkList.keySet();
        for (Object object : keySet) {
            List<NetBean> methodList = networkList.get(object);
            if (methodList != null) {
                for (NetBean netBean : methodList) {
                    if (netBean.getType().isAssignableFrom(netType.getClass())) {// equal
                        switch (netBean.getNetType()) {
                            case AUTO:
                                invoke(netBean, object, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(netBean, object, netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(netBean, object, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(netBean, object, netType);
                                }
                                break;
                            case NONE:
                                if (netType == NetType.NONE) {
                                    invoke(netBean, object, netType);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    private static void invoke(NetBean netBean, Object object, NetType netType) {
        Method method = netBean.getMethod();
        try {
            method.invoke(object, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static List<NetBean> findAnnotationMethod(Object object) {
        List<NetBean> methodList = new ArrayList<>();

        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }

            Type returnType = method.getGenericReturnType();
            if (!"void".equals(returnType.toString())) {
                throw new RuntimeException("method return type must be void");
            }

            Class<?>[] parameterTypes=method.getParameterTypes();
            if (1 != parameterTypes.length) {
                throw new RuntimeException("method parameter count must be one");
            }

            methodList.add(new NetBean(parameterTypes[0], network.netType(), method));
        }
        return methodList;
    }
}
