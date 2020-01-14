package edu.uestc.Utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 名字服务器连接信息
 */
public class SocketInfo {
    /**
     * socket
     */
    private Socket socket;
    /**
     * 是否空闲 （是：true  否：false）
     */
    private boolean isFree;
    /**
     * socket id
     */
    private Integer socketId;

    /**
     * 是否为可关闭链接 （是：true  否：false）
     */
    private boolean isClosed;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public Integer getSocketId() {
        return socketId;
    }

    public void setSocketId(Integer socketId) {
        this.socketId = socketId;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }


    public void init(){
        try{
            if(socket!=null){
                writer = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public SocketInfo(){
    }
    public SocketInfo(String ip,int port){
        try{
            socket = new Socket(ip,port);
            init();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private PrintWriter writer;
    private BufferedReader in;
    public String read(){
        StringBuffer res = new StringBuffer() ;
        try{
            char[] buf = new char[1024];
            int size = 0;
            while (res.toString().isEmpty() || !res.toString().contains("eof")) {
                size = in.read(buf,0,1024);
                res.append(new String(buf,0,size));
                System.out.println(res);
            }
        }catch (Exception e){
            return "";
        }
        String tmp = res.toString();
        tmp = tmp.replaceAll("eof\r\n","").replaceAll("eof","");
        return tmp;
    }

    public void write(String data){
        try{
            writer.println(data+"eof");
            // 将从系统标准输入读入的字符串输出到Server
            writer.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}