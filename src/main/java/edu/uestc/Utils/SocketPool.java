package edu.uestc.Utils;


import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc socket链接池
 */
public class SocketPool {

    /**
     * socketMap
     */
    public static ConcurrentHashMap<Integer, SocketInfo> socketMap = new ConcurrentHashMap<Integer, SocketInfo>();

    private static SocketPool instance;

    private static String _ip;
    private static int _port;
    private SocketPool() {
    }

    public static SocketPool getInstance(String ip,int port) {
        if (instance == null) {
            synchronized (SocketPool.class) {
                if (instance == null) {
                    instance = new SocketPool();
                    _ip = ip;
                    _port = port;
                    instance.initSocket(true);
                }
            }
        }
        return instance;
    }


    /**
     * @Desc 初始化链接池
     */
    public void initSocket(boolean isAllReInit) {
        int defaultCount = 5;
        for (int i = 0; i < defaultCount; i++) {

            if (isAllReInit) {
                socketMap.put(i, setSocketInfo(i, true, false));
            } else {
                if (socketMap.get(i) == null || socketMap.get(i).isClosed()) {
                    socketMap.put(i, setSocketInfo(i, true, false));
                }
            }
        }

//        new CheckSocketThread().start();

    }

    /**
     * @Desc 设置socketInfo值
     */
    private static SocketInfo setSocketInfo(Integer key, boolean isFree, boolean isClosed) {
        SocketInfo socketInfo = new SocketInfo();
        Socket socket = createSocket();
        socketInfo.setFree(isFree);
        socketInfo.setSocket(socket);
        socketInfo.setSocketId(key);
        socketInfo.setClosed(isClosed);
        socketInfo.init();
        return socketInfo;
    }

    /**
     * @Desc 获取名字服务器链接
     */
    public SocketInfo getSocketInfo() {

        SocketInfo socketInfo = null;
        if (socketMap.size() > 0) {
            for (Map.Entry<Integer, SocketInfo> entry : socketMap.entrySet()) {
                socketInfo = entry.getValue();
                if (socketInfo.isFree() && !socketInfo.getSocket().isClosed()) {
                    socketInfo.setFree(false);
                    return socketInfo;
                }
            }
        } else {
            return null;
        }
        socketInfo = setSocketInfo(-1, true, true);
        return socketInfo;

    }

    /**
     * 释放socket
     */
    public static void distorySocket(Integer socketId) {

        SocketInfo socketInfo = socketMap.get(socketId);
        socketInfo.setFree(true);

    }

    /**
     * @Desc 释放socket
     * void
     */
    public static void distorySocket(SocketInfo socketInfo) {

        if (socketInfo == null) return;

        if (!socketInfo.isClosed()) {
            distorySocket(socketInfo.getSocketId());
            return;
        }

        try {
            if (socketInfo.getSocket() != null) {
                socketInfo.getSocket().close();
            }
        } catch (IOException e) {
        }
        socketInfo = null;

    }

    /**
     * @Desc 创建socket
     */
    public static Socket createSocket() {

        Socket socket = null;


        try {// 尝试通过ip1第一次建立连接
            socket = new Socket(_ip, _port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void main(String[] args) throws Exception{
        SocketPool socketPool = SocketPool.getInstance("121.48.165.136",8899);
        SocketInfo info = socketPool.getSocketInfo();
//        Socket socket = new Socket("121.48.165.136",8899);
    }
}
