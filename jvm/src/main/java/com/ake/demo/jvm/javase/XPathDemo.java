package com.ake.demo.jvm.javase;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/17 15:47
 */
public class XPathDemo {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        // 解析文档，转换成doc
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("jvm/src/main/resources/xml/books.xml");
        XPath xPath = XPathFactory.newInstance().newXPath();
        // 编译表达式
        XPathExpression compile = xPath.compile("//book/title");
        // 用来解析和查询xml文档的，这个在数据的爬取方面可做优化点
        NodeList oList = (NodeList) compile.evaluate(doc, XPathConstants.NODESET);
        System.out.println(oList.getLength());

//        System.out.println(xPath.evaluate("//book/title", doc));
//        System.out.println(xPath.evaluate("//book", doc));
//        System.out.println(xPath.evaluate("//book", doc));
//        System.out.println(xPath.evaluate("//book", doc));
    }
}
