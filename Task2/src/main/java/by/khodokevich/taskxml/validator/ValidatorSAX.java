package by.khodokevich.taskxml.validator;

import by.khodokevich.taskxml.exception.ProjectXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class ValidatorSAX {
    static final Logger LOGGER = LogManager.getLogger();

    public static boolean isXMLValid(String fileName, String schemaName) throws ProjectXMLException {
        LOGGER.info("Start isXMLValid() ");
        boolean result = false;

        Schema schema = null;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);

        try {
            schema = factory.newSchema(new File(schemaName));
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setSchema(schema);

            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(fileName, new PostcardsErrorHandler());

            result = true;
        } catch (ParserConfigurationException e) {
            LOGGER.error(fileName + " config error: " + e.getMessage());
            throw new ProjectXMLException(e);
        } catch (SAXException e) {
            LOGGER.error(fileName + " SAX error: " + e.getMessage());
            throw new ProjectXMLException(e);
        } catch (IOException e) {
            LOGGER.error("I/O error: " + e.getMessage());
            throw new ProjectXMLException(e);
        }
        LOGGER.info(fileName + "is valid");
        return result;
    }
}
