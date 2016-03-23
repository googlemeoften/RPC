package cn.edu.rpc.test;

import cn.edu.rpc.service.RpcServer;

/**
 * Created by HeYong on 2016/3/22.
 */
public class Service {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        server.run();
    }
}
