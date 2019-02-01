package com.roronoaly.servlet;


import com.roronoaly.http.Request;
import com.roronoaly.http.Response;

public class FirstServlet extends AbstractServlet {

    @Override
    public void doGet(Request request, Response response) {
        response.setWrite("我的第一个Servlet");
    }

    @Override
    public void doPost(Request request, Response response) {
        this.doGet(request, response);
    }
}

