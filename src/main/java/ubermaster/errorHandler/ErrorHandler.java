package ubermaster.errorHandler;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ErrorHandler {
    private static final String FILEPATH = System.getProperty("user.dir")+
            "src\\main\\resources\\errorHandler\\errors.xml";

    private static Logger log = Logger.getLogger(ErrorHandler.class.getName());

    public static Exception createError(String errorCode) throws Exception {
        Exception exception = null;
        try {
            final File xmlFile = new File(FILEPATH);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("error");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    if(element.getAttribute("id").equals(errorCode)) {
                        exception = new Exception(element.getElementsByTagName("errormessage").item(0).getTextContent());
                    } else {
                        continue;
                    }
                }
            }
            return exception;
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new Exception(ex.getMessage());
        }
    }
}
