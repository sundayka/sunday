package mypig.servlet;

import mypig.connector.Request;
import mypig.connector.Response;
import mypig.connector.http.HttpRequest;
import mypig.connector.http.HttpResponse;

import java.io.IOException;

public abstract class HttpServlet implements Servlet {

    public abstract void service(HttpRequest request, HttpResponse response);



    @Override
    public void init(ServletConfig var1) {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(Request request, Response response) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }


}
