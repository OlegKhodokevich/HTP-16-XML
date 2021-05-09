package by.khodokevich.taskxml.validator;

import by.khodokevich.taskxml.exception.ProjectXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorXML {
    static final Logger LOGGER = LogManager.getLogger();

    public static boolean isXMLValid(String fileNameXML, String fileNameSchema) throws ProjectXMLException {
        LOGGER.info("Start isXMLValid()");
        boolean result = false;
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = factory.newSchema(new File(fileNameSchema));
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileNameXML);
            validator.validate(source);
            result = true;
        } catch (SAXException | IOException e) {
            throw new ProjectXMLException(e);
        }
        LOGGER.info(fileNameXML + " is valid");
        return result;
    }
}
