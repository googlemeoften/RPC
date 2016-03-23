package cn.edu.rpc.client;

import cn.edu.rpc.common.RpcDecoder;
import cn.edu.rpc.common.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by HeYong on 2016/3/22.
 */
public class RpcClient extends ChannelHandlerAdapter {
    private String host = "localhost";
    private int port = 8080;
    private RpcResponse response;
    private RpcRequest request;
    private ReentrantLock lock = new ReentrantLock();
    private Condition isResponse = lock.newCondition();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //if (msg instanceof RpcResponse) {
        lock.lock();
        try {
            response = (RpcResponse) msg;
            isResponse.signalAll();
        } finally {
            lock.unlock();
        }
        //}
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ChannelFuture f = null;
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new RpcEncoder())
                            .addLast(new RpcDecoder())
                            .addLast(RpcClient.this);
                }
            });

            // Start the client.
            f = b.connect(host, port).sync(); // (5)
            f.channel().writeAndFlush(request).sync();
            lock.lock();
            try {
                while (response == null) {
                    isResponse.await();
                }
            } finally {
                lock.unlock();
            }
            // Wait until the connection is closed.
            // f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
        return response;
    }

}
