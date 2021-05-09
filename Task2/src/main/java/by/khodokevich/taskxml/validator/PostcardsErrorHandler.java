package by.khodokevich.taskxml.validator;

import by.khodokevich.taskxml.exception.ProjectXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class PostcardsErrorHandler extends DefaultHandler {
    private Logger logger = LogManager.getLogger(PostcardsErrorHandler.class);

    public PostcardsErrorHandler() {
    }

    public void warning(SAXParseException e) {
        logger.warn(getLineAddress(e) + "-" + e.getMessage());
    }

    public void error(SAXParseException e) {
        logger.error(getLineAddress(e) + "-" + e.getMessage());
    }

    public void fatalError(SAXParseException e) {
        logger.fatal(getLineAddress(e) + "-" + e.getMessage());
    }

    private String getLineAddress(SAXParseException e) {
        StringBuilder sb = new StringBuilder();
        sb.append("line: ").append(e.getLineNumber());
        sb.append(", column : ").append(e.getColumnNumber());
        logger.info(sb.toString());
        return  sb.toString();
    }
}
