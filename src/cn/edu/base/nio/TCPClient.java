package cn.edu.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by HeYong on 2016/1/28.
 */
public class TCPClient {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";
        String content = "nihao";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);

            if (!channel.connect(new InetSocketAddress(host, port))) {
                while (!channel.finishConnect()) {
                    System.out.println("No Connection...");
                }
            }

            ByteBuffer writer = ByteBuffer.wrap(content.getBytes());
            ByteBuffer reader = ByteBuffer.allocate(content.length());

            int totalBytesRcvd = 0;
            int bytesRcvd;
            while (totalBytesRcvd < content.length()) {
                if (writer.hasRemaining()) {
                    channel.write(writer);
                }

                if ((bytesRcvd = channel.read(reader)) == -1) {
                    throw new SocketException("Connection closed prematurely");
                }
                //计算接收到的总字节数
                totalBytesRcvd += bytesRcvd;
            }

            //打印出接收到的数据
            System.out.println("Received: " + new String(reader.array(), 0, totalBytesRcvd));
            //关闭信道
            channel.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
