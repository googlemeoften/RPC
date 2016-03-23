package cn.edu.rpc.common;

import java.io.*;

/**
 * Created by HeYong on 2016/3/22.
 */
public class JDKSerializa {
    public static byte[] encode(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(oos);
            close(bos);
        }

        return bos.toByteArray();
    }

    public static Object decode(byte[] bytes) {
        ByteArrayInputStream bio = null;
        ObjectInputStream ois = null;

        Object obj = null;
        try {
            bio = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bio);
            obj = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(ois);
            close(bio);
        }
        return obj;
    }

    public static void close(InputStream in) {
        if (in != null)
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
