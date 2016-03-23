package cn.edu.rpc.client;

import cn.edu.rpc.common.JDKSerializa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 构造代理对象
 */
public class RpcProxy {

    public static <T> T getProxy(final Class<?> interfaceClass) {
        if (interfaceClass == null)
            throw new IllegalArgumentException("the interface is null");
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestID(UUID.randomUUID().toString())
                        .setClassName(interfaceClass.getName())
                        .setMethodName(method.getName())
                        .setParameterTypes(method.getParameterTypes())
                        .setParameters(args);
                RpcClient client = new RpcClient();

                RpcResponse response = client.send(request);
                return response.getResult();
            }
        });
    }
}
