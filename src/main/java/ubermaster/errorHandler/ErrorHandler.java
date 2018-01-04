package ubermaster.errorHandler;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorHandler {
    private static final String FILEPATH = System.getProperty("user.dir")+
            "src\\main\\resources\\errorHandler\\errors.xml";

    private static Logger log = Logger.getLogger(ErrorHandler.class.getName());

    public Exception createError(String errorCode) {
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
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            return exception;
        }
    }
}
