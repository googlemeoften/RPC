package cn.edu.rpc.service;

import cn.edu.rpc.client.RpcRequest;
import cn.edu.rpc.client.RpcResponse;
import cn.edu.rpc.common.JDKSerializa;
import cn.edu.rpc.test.HelloServiceImpl;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

public class RpcHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("============");

        RpcRequest request = null;
        RpcResponse response = null;
        //if (msg instanceof RpcRequest) {
        request = (RpcRequest) msg;

        response = new RpcResponse();
        try {
            //==========–¥À¿==========
            Class clazz = HelloServiceImpl.class;

            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
            Object resulet = method.invoke(obj, request.getParameters());
            response.setResult(resulet).setRequestId(request.getRequestID());
        } catch (Exception e) {
            response.setError(e);
        } finally {
            ctx.writeAndFlush(response);
        }
        // }
    }
}
