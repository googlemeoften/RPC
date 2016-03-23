package cn.edu.rpc.test;

import cn.edu.rpc.client.RpcProxy;
import cn.edu.rpc.protocal.HelloService;

/**
 * Created by HeYong on 2016/3/22.
 */
public class Client {
    public static void main(String[] args) {
        HelloService service = RpcProxy.getProxy(HelloService.class);
        System.out.println(service.sayHello("dave"));
    }
}
