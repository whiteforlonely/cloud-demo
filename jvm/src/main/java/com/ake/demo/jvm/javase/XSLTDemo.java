package com.ake.demo.jvm.javase;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/10/17 10:12
 */
public class XSLTDemo {

    public static void main(String[] args) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        String sourcePath = "jvm/src/main/resources";
        Transformer transformer = factory.newTransformer(new StreamSource(sourcePath + "/xslt/test.xsl"));
        StreamSource source = new StreamSource(sourcePath + "/xml/books.xml");
        transformer.transform(source, new StreamResult(sourcePath + "/html/books.html"));
    }
}
