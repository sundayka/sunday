package mypig.servlet;


import mypig.connector.Request;
import mypig.connector.Response;

import java.io.IOException;

public interface Servlet {
    void init(ServletConfig var1) throws ServletException;

    ServletConfig getServletConfig();

    void service(Request request, Response response) throws ServletException, IOException;

    String getServletInfo();

    void destroy();

}
