package mypig.connector.http;

import mypig.connector.Request;

import java.io.*;
import java.util.HashMap;

public class HttpRequest implements Request {


    /**
     * CR.
     */
    private static final byte CR = (byte) '\r';


    /**
     * LF.
     */
    private static final byte LF = (byte) '\n';

    private static final String LINE = "LINE";
    private static final String HEAD = "HEAD";
    private static final String BODY = "BODY";
    private static final String END ="END";
    private String parseState = LINE;

    private boolean isParseLine = false;
    private boolean isParseHeader =false;
    private boolean isParseBody = false;

    private int byteSize = 1024;
    private byte[] chars = new byte[byteSize];

    private int len = -1;
    private int pos = 0;

    private int start = 0;
    private int end = 0;



    private String method;
    private String url;
    private String protocol;
    private String Host;
    private String Connection;
    private String Cache_Control;
    private String Upgrade_Insecure_Requests;
    private String User_Agent;
    private String Accept;
    private String Accept_Encoding;
    private String Accept_Language;
    private int Content_Length;
    private String Content_Type;

    private String encoding;


    private HashMap<String,String> cookie = new HashMap<>();
    private HashMap<String,String> parameters = new HashMap<>();
    private HashMap<String,String> attribute = new HashMap<>();



    private InputStream inputStream;
    private HttpResponse httpResponse;


    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public HttpRequest(InputStream inputStream){
        this.inputStream = inputStream;
        parseLine();
        parseHeaders();
        parseBody();
    }

    private void parseLine(){
        if (!isParseLine){
            read();
            String line = new String(chars,0,end);
            System.out.println(line);
            String[] lines = line.split(" ");
            setMethod(lines[0]);
            setUrl(lines[1]);
            setProtocol(lines[2]);

            isParseLine = true;
        }else {
            throw new RuntimeException("请求行已解析！！！");
        }
    }

    private void parseHeaders(){
        if (!isParseLine){
            throw  new RuntimeException("请求行未解析！！！");
        }else if (isParseHeader){
            throw new RuntimeException("请求头已解析！！！");
        }else {
            try{
                System.out.println("parseHeaders");
                while (parseState.equals(HEAD)){
                    read();
                    String headLine = new String(chars,start,end-start);
                    System.out.println(headLine);
                    String[] header = headLine.split(": ");
                    if ("Host".equals(header[0])){
                        Host = header[1];
                    }else if ("Connection".equals(header[0])){
                        Connection = header[1];
                    }else if ("Cache-Control".equals(header[0])){
                        Cache_Control = header[1];
                    }else if ("Upgrade-Insecure-Requests".equals(header[0])){
                        Upgrade_Insecure_Requests = header[1];
                    }else if ("User-Agent".equals(header[0])){
                        User_Agent = header[1];
                    }else if ("Accept".equals(header[0])){
                        String[] accept = header[1].split(",");
                        Accept = accept[0];
                    }else if ("Accept-Encoding".equals(header[0])){
                        Accept_Encoding = header[1];
                    }else if ("Accept-Language".equals(header[0])){
                        Accept_Language = header[1];
                    }else if ("Content-Length".equals(header[0])){
                        Content_Length = Integer.parseInt(header[1]);
                    }else if("Content-Type".equals(header[0])){
                        String[] typeAndCoding = header[1].split("; ");
                        Content_Type = typeAndCoding[0];
                        if (typeAndCoding.length!=1){
                            encoding = typeAndCoding[1].split("=")[1];
                        }
                    }else if ("Cookie".equals(header[0])){
                        String[] cookies =header[1].split(";");
                        for (String cookieLine:cookies){
                            String[] cookieCouple = cookieLine.split("=");
                            cookie.put(cookieCouple[0],cookieCouple[1]);
                        }
                    }

                }
                isParseHeader = true;
                System.out.println("ended parseHeader");
            }catch (Exception e){

            }
        }

    }

    private void parseBody(){
        if (!getMethod().equals("POST")){
            return;
        }
        if (!isParseLine){
            throw new RuntimeException("nnn");
        }else if (!isParseHeader){
            throw new RuntimeException("nnn");
        }else {
            System.out.println("parseBody Line");
            while (parseState.equals(BODY)){
                read();
                String parameterLines = new String(chars,start,end-start);
                String[] parameterLine = parameterLines.split("&");
                System.out.println("parameterLines:"+parameterLines);
                for (String parameter:parameterLine){
                    System.out.println("parameter:"+parameter);
                    String[] entry = parameter.split("=");
                    String key = entry[0];
                    String value = entry[1];
                    synchronized (parameter){
                        parameters.put(key,value);
                    }
                }
            }
            isParseBody = true;
        }
    }


    public void initRead(){
        try {
            len = inputStream.read(chars,0,byteSize);
        }catch (Exception e){
            System.out.println("读取request请求失败");
        }
    }
    public void read(){
        if (pos>=len){
            initRead();
        }
        System.out.println("pos is "+pos +"; len is "+len);
        if (len==-1){
            throw new RuntimeException("请求空白");
        }
        if (chars[pos]==CR&&chars[pos+1]==LF){
            //System.out.println(pos +" is CR ;"+ (pos+1)+"is LF");
            pos = pos+2;
        }
        if (chars[pos]==CR&&chars[pos+1]==LF){
            parseState = BODY;
            return;
        }
        start = pos;
        while (chars[pos]!=CR&&chars[pos]!=LF){
            pos++;
            if (pos>=len){
                parseState = END;
                break;
            }
        }
        end = pos;
        while (parseState.equals(LINE)){
            parseState = HEAD;
        }
        System.out.println("start:"+start+";end:"+end);
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getConnection() {
        return Connection;
    }

    public void setConnection(String connection) {
        Connection = connection;
    }

    public String getCache_Control() {
        return Cache_Control;
    }

    public void setCache_Control(String cache_Control) {
        Cache_Control = cache_Control;
    }

    public String getUpgrade_Insecure_Requests() {
        return Upgrade_Insecure_Requests;
    }

    public void setUpgrade_Insecure_Requests(String upgrade_Insecure_Requests) {
        Upgrade_Insecure_Requests = upgrade_Insecure_Requests;
    }

    public String getUser_Agent() {
        return User_Agent;
    }

    public void setUser_Agent(String user_Agent) {
        User_Agent = user_Agent;
    }

    public String getAccept() {
        return Accept;
    }

    public void setAccept(String accept) {
        Accept = accept;
    }

    public String getAccept_Encoding() {
        return Accept_Encoding;
    }

    public void setAccept_Encoding(String accept_Encoding) {
        Accept_Encoding = accept_Encoding;
    }

    public String getAccept_Language() {
        return Accept_Language;
    }

    public void setAccept_Language(String accept_Language) {
        Accept_Language = accept_Language;
    }

    public HashMap<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(HashMap<String, String> cookie) {
        this.cookie = cookie;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Integer getContent_Length() {
        return Content_Length;
    }

    public String getContent_Type() {
        return Content_Type;
    }

    public void setContent_Length(Integer content_Length) {
        Content_Length = content_Length;
    }

    public void setContent_Type(String content_Type) {
        Content_Type = content_Type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
    public String getParameter(String key){
        return parameters.get(key);
    }
}

