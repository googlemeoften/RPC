package cn.edu.rpc.client;

import java.io.Serializable;

/**
 * 请求返回内容数据结构
 */
public class RpcResponse implements Serializable {
    private String requestId;
    private Throwable error;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public RpcResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Throwable getError() {
        return error;
    }

    public RpcResponse setError(Throwable error) {
        this.error = error;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
