package mypig.logger;

import mypig.connector.http.HttpConnector;
import mypig.lifecycle.LifeCycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class FileLogger {

    private LifeCycle parent;
    private PrintWriter printWriter;
    private String suffix = ".log";
    private String prefix = System.getProperty("user.dir")+File.separator+"log"+File.separator;

    public FileLogger(LifeCycle parent){
        this.parent = parent;
        init();
    }

    private void init(){
        String name = parent.getClass().getName().substring(parent.getClass().getName().lastIndexOf(".")+1);
        File file = new File(prefix + name + suffix);
        System.out.println(file.toString());
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file,true);
            printWriter = new PrintWriter(fos,true);
        }catch (Exception e){
            System.out.println("创建文件输出流失败");
        }
    }

    public void log(String message){
        printWriter.println();
        printWriter.println("**********************************************");
        printWriter.println(new Date());
        printWriter.println("message:");
        printWriter.println(message);
        printWriter.println("**********************************************");
        printWriter.println();
    }


    public static void main(String[] args){
        FileLogger logger = new FileLogger(new HttpConnector());
        logger.log("hello everybody");
    }
}
