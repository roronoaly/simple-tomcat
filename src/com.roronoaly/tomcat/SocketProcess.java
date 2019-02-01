package com.roronoaly.tomcat;


import com.roronoaly.http.Request;
import com.roronoaly.http.Response;
import com.roronoaly.servlet.AbstractServlet;

import java.io.OutputStream;
import java.net.Socket;

public class SocketProcess extends Thread {
    private Socket socket;

    public SocketProcess(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            String servletName = Tomcat.SERVLET_MAPPING.get(request.getUrl());
            if (servletName != null) {
                //映射有的话找到对应的对象
                AbstractServlet servlet = (AbstractServlet) Tomcat.SERVLET.get(servletName);
                if (servlet != null) {
                    if ("GET".equals(request.getMethod())) {
                        servlet.doGet(request, response);
                    } else if ("POST".equals(request.getMethod())) {
                        servlet.doPost(request, response);
                    }
                } else {
                    System.out.println("找不到对应的servlet");
                }
            } else {
                System.out.println("找不到对应的servletMapping");
            }
            String res = Response.RESPONSE_HEADER + response.getWrite();
            OutputStream outputStream = socket.getOutputStream();

            outputStream.write(res.getBytes("GBK"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}