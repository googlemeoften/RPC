package cn.edu.base.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by HeYong on 2016/1/28.
 */
public interface TCPProtocol {

    //Accept I/O
    public void headleAccept(SelectionKey key) throws IOException;

    //Read I/O
    public void headleRead(SelectionKey key) throws IOException;

    //Write I/O
    public void headleWrite(SelectionKey key) throws IOException;
}
