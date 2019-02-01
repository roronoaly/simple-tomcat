package com.roronoaly.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private String method;

    private String url;

    public Request(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> list = new ArrayList<>();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
                System.out.println(bufferedReader.toString() + "_____" + "recv : " + line);
                if (line.isEmpty()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] methodAndUrl = list.get(0).split(" ");
        this.method = methodAndUrl[0];
        this.url = methodAndUrl[1];
        System.out.println("请求类型：" + method);
        System.out.println("请求路径：" + url);
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

}
