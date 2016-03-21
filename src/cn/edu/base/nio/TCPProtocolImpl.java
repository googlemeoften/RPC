package cn.edu.base.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by HeYong on 2016/1/28.
 */
public class TCPProtocolImpl implements TCPProtocol {
    private static int BUFFER_SIZE = 1024;

    @Override
    public void headleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        channel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        channel.register(key.selector(), SelectionKey.OP_READ, buffer);
    }

    @Override
    public void headleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        int bytesRead = channel.read(buffer);
        if (bytesRead == -1) {
            System.out.println("======");
            channel.close();
            System.out.println("======");
            System.out.println(key.selector().keys().size());;
        } else if (bytesRead > 0) {
            //设置该通道可读可写
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void headleWrite(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();

        SocketChannel channel = (SocketChannel) key.channel();
        channel.write(buffer);

        while (!buffer.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        buffer.compact();
    }
}
