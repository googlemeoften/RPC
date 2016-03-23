package cn.edu.rpc.test;

import cn.edu.rpc.protocal.HelloService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by HeYong on 2016/3/22.
 */
public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String name) {
        return name + " hello world";
    }

}
