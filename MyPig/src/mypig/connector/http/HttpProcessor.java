package mypig.connector.http;


import mypig.connector.Connector;
import mypig.connector.processor.Processor;

import java.net.Socket;
import java.util.Scanner;

public class HttpProcessor extends Processor implements Runnable{

    private Socket socket = null;
    private boolean available = false;
    private Connector parent;


    public HttpProcessor(Connector parent){
        this.parent = parent;
    }


    public void assign(Socket socket) throws Exception{
        while (available){
            this.wait();
        }
        this.socket = socket;
    }

    public void await()throws Exception{
        while (!available){
            this.wait();
        }
    }


    @Override
    public void run() {
        try {

        }catch (Exception e){

        }

    }
}
