package com.roronoaly.tomcat;

import com.roronoaly.utils.XmlUtils;
import org.dom4j.Element;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tomcat {
    /**
     * 设置端口号
     */
    private static final int PORT = 8080;

    public static Map<String, String> SERVLET_MAPPING = new HashMap<>();

    public static Map<String, Object> SERVLET = new HashMap<>();

    /**
     * 控制服务器启动关闭
     */
    public boolean tomcatStarBool = true;

    private void init() {
        try {
            System.out.println("加载配置文件开始");
            //读取配置文件
            XmlUtils xml = new XmlUtils(XmlUtils.class.getResource("/") + "web.xml");
            //将所有的类都存储到容器中
            List list = xml.getNodes("servlet");
            for (Object object : list) {
                if (object instanceof Element) {
                    Element element = (Element) object;
                    SERVLET.put(element.element("servlet-name").getText(),
                            Class.forName(element.element("servlet-class").getText()).newInstance());
                }
            }
            //映射关系创建
            List list2 = xml.getNodes("servlet-mapping");
            for (Object object : list2) {
                if (object instanceof Element) {
                    Element element = (Element) object;
                    SERVLET_MAPPING.put(element.element("url-pattern").getText(), element.element("servlet-name").getText());
                }
            }
            System.out.println("加载配置文件结束");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Tomcat 服务已启动，地址：localhost ,端口：" + PORT);
            this.init();
            //持续监听
            do {
                Socket socket = serverSocket.accept();
                //处理任务
                Thread thread = new SocketProcess(socket);
                thread.start();
            } while (tomcatStarBool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.start();
    }
}