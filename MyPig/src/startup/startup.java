package startup;

import com.alibaba.fastjson.JSON;
import mypig.connector.Request;
import mypig.connector.http.HttpRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class startup {


    public static void main(String[] args)throws Exception{

        ServerSocket serverSocket = new ServerSocket(8080);

        while (true){
            Socket socket = serverSocket.accept();
            System.out.println(socket + ":linking ...");
            Processor processor = new Processor(socket);
            Thread thread = new Thread(processor);
            thread.start();
        }

    }
}


class Processor implements Runnable{

    /**
     * CR.
     */
    private static final byte CR = (byte) '\r';


    /**
     * LF.
     */
    private static final byte LF = (byte) '\n';

    private Socket socket;

    public Processor(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {
        OutputStream os;
        InputStream is;
        BufferedReader reader;
        PrintWriter writer;
        try {
            os = socket.getOutputStream();
            is = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            writer = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
//            byte[] buf = new byte[1024];
//            int len0 = is.read(buf,0,1024);
//            System.out.println(new String(buf,0,len0));

            if (socket!=null) {
                HttpRequest request = new HttpRequest(is);
                System.out.println("---------------------------------------------");
                String url = request.getUrl();
                String content_type = request.getAccept();
                System.out.println(url);
                System.out.println(content_type);

                File file = new File("webRoot" + url);
                if (file.exists()){
                    System.out.println("---------------------------------------------");
                    System.out.println("file output");
                    System.out.println("---------------------------------------------");
                    FileInputStream fis = new FileInputStream(file);
                    byte[] chars = new byte[1024];
                    int len = -1;
                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: " + content_type);
                    writer.println();
                    while ((len = fis.read(chars)) != -1) {
                        os.write(chars, 0, len);
                    }
                    os.flush();
                    System.out.println("over");
                }else {
                    if (url.equals("/cloud_note/user/login.do")){
                        System.out.println("---------------------------------------------");
                        System.out.println("servlet output");
                        System.out.println("---------------------------------------------");
                        String username = request.getParameter("name");
                        String password = request.getParameter("password");
                        System.out.println("username:"+username);
                        System.out.println("password:"+password);
                        UserService service = new UserServiceImpl();
                        System.out.println("service");
                        NoteResult<User> result = service.checkLogin(username,password);
                        System.out.println("result");
                        System.out.println("result:"+ result);
                        String json = JSON.toJSONString(result);
                        System.out.println("json:"+json);
                        writer.println("HTTP/1.1 200 OK");
                        writer.println("Content-Type: " + content_type);
                        writer.println();
                        writer.println(json);
                    }else {
                        System.out.println("error:"+url);
                    }
                }

                socket.close();

            }
        } catch (Exception e) {

        }

    }


}





