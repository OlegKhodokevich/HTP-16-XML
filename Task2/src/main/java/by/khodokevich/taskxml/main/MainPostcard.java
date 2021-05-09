package by.khodokevich.taskxml.main;

import by.khodokevich.taskxml.builder.impl.dom.PostcardSetDOMBuilder;
import by.khodokevich.taskxml.builder.impl.sax.PostcardsSaxBuilder;
import by.khodokevich.taskxml.builder.impl.stax.PostcardStAXBuilder;
import by.khodokevich.taskxml.entity.Postcard;
import by.khodokevich.taskxml.exception.ProjectXMLException;
import by.khodokevich.taskxml.validator.ValidatorXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MainPostcard {
    private static final Logger LOGGER = LogManager.getLogger();
    static final String FILE_NAME = "data/cards.xml";
    static final String SCHEMA_NAME = "data/schema.xsd";

    public static void main(String[] args) {
        System.out.println(String.join(File.separator,"data", "cards.xml"));
        LOGGER.info("\n");

        Set<Postcard> postcards = new HashSet<>();

        try {
            System.out.println("XML file " + FILE_NAME + " if valid: " + ValidatorXML.isXMLValid(FILE_NAME, SCHEMA_NAME));
        } catch (ProjectXMLException e) {
            e.printStackTrace();
        }
        System.out.println(" ");
        System.out.println("Parse XML file " + FILE_NAME + " with DOM.");
        try {
            PostcardSetDOMBuilder postcardSetDOMBuilder = new PostcardSetDOMBuilder();
            postcardSetDOMBuilder.buildSetPostcards(FILE_NAME);
            postcards = postcardSetDOMBuilder.getPostcards();

        } catch (ProjectXMLException e) {
            LOGGER.error(e);
        }

        for (Postcard postcard : postcards) {
            System.out.println(postcard.toString());
        }

        System.out.println(" ");
        System.out.println("Parse XML file " + FILE_NAME + " with SAX.");
        try {
            PostcardsSaxBuilder saxBuilder = new PostcardsSaxBuilder();
            saxBuilder.buildSetPostcards(FILE_NAME);//
            postcards = saxBuilder.getPostcards();
        } catch (ProjectXMLException e) {
            LOGGER.error(e);
        }

        for (Postcard postcard : postcards) {
            System.out.println(postcard.toString());
        }

        System.out.println(" ");
        System.out.println("Parse XML file " + FILE_NAME + " with StAX.");
        try {
            PostcardStAXBuilder postcardStAXBuilder = new PostcardStAXBuilder();
            postcardStAXBuilder.buildSetPostcards(FILE_NAME);
            postcards = postcardStAXBuilder.getPostcards();

        } catch (ProjectXMLException e) {
            LOGGER.error(e);
        }

        for (Postcard postcard : postcards) {
            System.out.println(postcard.toString());
        }

    }
}
