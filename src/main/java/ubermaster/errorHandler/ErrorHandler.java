package ubermaster.errorHandler;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ErrorHandler {
    private static final String FILEPATH = System.getProperty("user.dir")+
            "\\src\\main\\resources\\errorHandler\\";

    private static Logger log = Logger.getLogger(ErrorHandler.class.getName());

    public static NodeList parseXML(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        try {
            final File xmlFile = new File(FILEPATH
                    + errorCode.toString() + ".xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            return doc.getElementsByTagName("error");
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            log.error(ex.getMessage(), ex);
            Class clazz = ex.getClass();
            if (clazz.getSimpleName() == "ParserConfigurationException") {
                throw new ParserConfigurationException(ex.getMessage());
            } else if (clazz.getSimpleName() == "SAXException") {
                throw new SAXException(ex.getMessage());
            } else {
                throw new IOException(ex.getMessage());
            }
        }
    }

    public static SQLException createSQLException(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        SQLException exception = null;
        NodeList nodeList = parseXML(errorCode);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(errorCode.getFullName())) {
                    exception = new SQLException(element.getElementsByTagName("errormessage").item(0).getTextContent());
                } else {
                    continue;
                }
            }
        }
        return exception;
    }

    public static UsernameNotFoundException createUsernameNotFoundException(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        UsernameNotFoundException exception = null;
        NodeList nodeList = parseXML(errorCode);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(errorCode.getFullName())) {
                    exception = new UsernameNotFoundException(element.getElementsByTagName("errormessage").item(0).getTextContent());
                } else {
                    continue;
                }
            }
        }
        return exception;
    }

    public static ClassNotFoundException createClassNotFoundException(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        ClassNotFoundException exception = null;
        NodeList nodeList = parseXML(errorCode);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(errorCode.getFullName())) {
                    exception = new ClassNotFoundException(element.getElementsByTagName("errormessage").item(0).getTextContent());
                } else {
                    continue;
                }
            }
        }
        return exception;
    }

    public static ServletException createServletException(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        ServletException exception = null;
        NodeList nodeList = parseXML(errorCode);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(errorCode.getFullName())) {
                    exception = new ServletException(element.getElementsByTagName("errormessage").item(0).getTextContent());
                } else {
                    continue;
                }
            }
        }
        return exception;
    }

    public static Exception createException(Errors errorCode)
            throws ParserConfigurationException, SAXException, IOException {
        Exception exception = null;
        NodeList nodeList = parseXML(errorCode);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(errorCode.getFullName())) {
                    exception = new Exception(element.getElementsByTagName("errormessage").item(0).getTextContent());
                } else {
                    continue;
                }
            }
        }
        return exception;
    }
}
