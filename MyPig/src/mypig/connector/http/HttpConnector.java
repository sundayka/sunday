package mypig.connector.http;


import mypig.connector.Connector;
import mypig.connector.processor.Processor;
import mypig.container.Container;
import mypig.lifecycle.LifeCycle;
import mypig.lifecycle.LifecycleSupport;
import mypig.logger.FileLogger;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class HttpConnector extends Connector implements Runnable{
    private Stack<HttpProcessor> processors;
    private int minProcessors = 5;
    private int maxProcessors = 20;
    private int pos = 0;
    private LifecycleSupport support = new LifecycleSupport(this);
    private ServerSocket serverSocket = null;
    private int port = 8080;
    private boolean stop = false;
    private Container container;
    private Container parent;
    private FileLogger logger = new FileLogger(this);


    public HttpConnector(){

    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public void setContainer(Container container){
        this.container = container;
    }



    @Override
    public void init() {

    }

    @Override
    public void start() {
        Thread threadNow = new Thread(this);
        threadNow.start();

    }
    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        }catch (Exception e){
            logger.log("创建服务器套接字失败");
        }
        while (!stop){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("获取客户端套接字成功：socket is" + socket);
                HttpProcessor processor = createProcessor();
                processor.assign(socket);
            }catch (Exception e){
                logger.log("获取客户端套接字失败");
            }
        }

    }

    public HttpProcessor createProcessor(){
        HttpProcessor processor = null;
        while (pos<minProcessors){
            HttpProcessor processor0 = new HttpProcessor(this);
            processors.push(processor0);
        }
        if (processors.pop()==null && pos< maxProcessors){
            HttpProcessor processor0 = new HttpProcessor(this);
            processor = processor0;
        }
        return processor;
    }
}
