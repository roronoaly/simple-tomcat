package com.roronoaly.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class XmlUtils {
    private Document document;

    public XmlUtils(String path) {
        //获取解析器
        SAXReader saxReader = new SAXReader();
        try {
            //获取文档对象
            document = saxReader.read(path);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据节点名称获取所有的子节点信息
     *
     * @param name 节点名称
     * @return 子节点信息
     */
    public List getNodes(String name) {
        Element root = document.getRootElement();
        return root.elements(name);
    }
}
