package com.roronoaly.servlet;


import com.roronoaly.http.Request;
import com.roronoaly.http.Response;

public abstract class AbstractServlet {

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);
}
