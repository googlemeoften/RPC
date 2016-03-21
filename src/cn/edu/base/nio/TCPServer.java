package cn.edu.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by HeYong on 2016/1/28.
 */
public class TCPServer {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";

        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            channel.register(selector, SelectionKey.OP_ACCEPT);

            TCPProtocol protocol = new TCPProtocolImpl();

            while (true) {
                System.out.println(selector.keys().size());

                if (selector.select() != 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator it = keys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = (SelectionKey) it.next();

                        if (key.isAcceptable()) {
                            protocol.headleAccept(key);
                        } else if (key.isReadable()) {
                            protocol.headleRead(key);
                        } else if (key.isWritable()) {
                            protocol.headleWrite(key);
                        }

                        it.remove();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
