package by.khodokevich.taskxml.builder.impl.sax;

import by.khodokevich.taskxml.entity.Postcard;
import by.khodokevich.taskxml.exception.ProjectXMLException;
import by.khodokevich.taskxml.validator.PostcardsErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PostcardsSaxBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private Set<Postcard> postcards;
    private XMLReader reader;
    private PostcardHandler handler;
    private SAXParserFactory factory = SAXParserFactory.newInstance();

    public PostcardsSaxBuilder() throws ProjectXMLException {
        postcards = new HashSet<>();
        handler = new PostcardHandler();
        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
            reader.setErrorHandler(new PostcardsErrorHandler());
            reader.setContentHandler(handler);
        } catch (ParserConfigurationException | SAXException e) {
            throw new ProjectXMLException(e);
        }
    }

    public Set<Postcard> getPostcards() {
        Set<Postcard> newPostcards = new HashSet<>();
        newPostcards.addAll(postcards);
        return newPostcards;
    }

    public void buildSetPostcards(String fileName) throws ProjectXMLException {
        LOGGER.info("Start buildSetPostcards. File = " + fileName);
        try {
            reader.parse(fileName);

        } catch (IOException e) {
            throw new ProjectXMLException(e);
        } catch (SAXException e) {
            throw new ProjectXMLException(e);
        }
        postcards = handler.getPostcards();
        LOGGER.info("End buildSetPostcards.");
    }
}
