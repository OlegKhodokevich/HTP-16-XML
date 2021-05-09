package by.khodokevich.taskxml.builder.impl.dom;

import by.khodokevich.taskxml.entity.*;
import by.khodokevich.taskxml.exception.ProjectXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Year;
import java.util.*;

public class PostcardSetDOMBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DocumentBuilder docBuilder;
    private final Set<Postcard> postcards;

    public PostcardSetDOMBuilder() throws ProjectXMLException {
        this.postcards = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ProjectXMLException(e);
        }
    }

    public Set<Postcard> getPostcards() {
        Set<Postcard> newPostcards = new HashSet<>();
        newPostcards.addAll(postcards);
        return newPostcards;
    }

    public void buildSetPostcards(String fileNameXML) throws ProjectXMLException {
        LOGGER.info("Start buildSetPostcards method. With file = " + fileNameXML);
        Document doc = null;

        try {
            doc = docBuilder.parse(fileNameXML);
            Element root = doc.getDocumentElement();
            for (PostcardTypeXML postcardType : PostcardTypeXML.values()) {
                NodeList postcardList = root.getElementsByTagName(postcardType.toString());
                LOGGER.debug("Enter in for() postcardType = " + postcardType + " length = " + postcardList.getLength());

                for (int i = 0; i < postcardList.getLength(); i++) {
                    Element postcardElement = (Element) postcardList.item(i);
                    Postcard postcard = buildPostcard(postcardElement);
                    LOGGER.debug("Add next postcard : " + postcard);
                    postcards.add(postcard);
                }
            }
        } catch (SAXException | IOException e) {
            throw new ProjectXMLException(e);
        }
    }

    private Postcard buildPostcard(Element postcardElement) {
        LOGGER.info("Start buildPostcard method. With element = " + postcardElement.getTagName());
        Postcard postcard = null;

        if (getValueForEnum(postcardElement.getTagName()).equals(SubClassesPostcard.PRINTED_POSTCARD.name())) {

            PrintedPostcard printedPostcard = new PrintedPostcard();

            printedPostcard.setId(postcardElement.getAttribute(AttributeXMLPostcard.ID.toString()));
            printedPostcard.setAuthor(Optional.ofNullable(postcardElement.getAttribute(AttributeXMLPostcard.AUTHOR.toString())));
            printedPostcard.setTheme(getElementTextContent(postcardElement, ElementPostcardXML.THEME.toString()));
            String nameTypePostcard = getElementTextContent(postcardElement, ElementPostcardXML.TYPE.toString());
            printedPostcard.setType(TypePostcard.valueOf(getValueForEnum(nameTypePostcard)));
            printedPostcard.setCountryOfProduction(getElementTextContent(postcardElement, ElementPostcardXML.COUNTRY_OF_PRODUCTION.toString()));
            printedPostcard.setYear(Year.parse(getElementTextContent(postcardElement, ElementPostcardXML.YEAR.toString())));
            printedPostcard.setSent(Boolean.parseBoolean(getElementTextContent(postcardElement, ElementPostcardXML.SENT.toString())));
            String colorTypeName = getElementTextContent(postcardElement, ElementPostcardXML.COLOR_TYPE.toString());
            printedPostcard.setColorType(ColorTypeCard.valueOf(getValueForEnum(colorTypeName)));
            printedPostcard.setSize(Optional.ofNullable(postcardElement.getAttribute(AttributeXMLPostcard.SIZE.toString())));

            LOGGER.info(printedPostcard.toString());

            postcard = printedPostcard;
        } else {
            DigitalPostcard digitalPostcard = new DigitalPostcard();

            digitalPostcard.setId(postcardElement.getAttribute(AttributeXMLPostcard.ID.toString()));
            digitalPostcard.setAuthor(Optional.ofNullable(postcardElement.getAttribute(AttributeXMLPostcard.AUTHOR.toString())));
            digitalPostcard.setTheme(getElementTextContent(postcardElement, ElementPostcardXML.THEME.toString()));
            String nameTypePostcard = getElementTextContent(postcardElement, ElementPostcardXML.TYPE.toString());
            digitalPostcard.setType(TypePostcard.valueOf(getValueForEnum(nameTypePostcard)));
            digitalPostcard.setCountryOfProduction(getElementTextContent(postcardElement, ElementPostcardXML.COUNTRY_OF_PRODUCTION.toString()));
            digitalPostcard.setYear(Year.parse(getElementTextContent(postcardElement, ElementPostcardXML.YEAR.toString())));
            digitalPostcard.setSent(Boolean.parseBoolean(getElementTextContent(postcardElement, ElementPostcardXML.SENT.toString())));
            String fileFormatName = postcardElement.getAttribute(AttributeXMLPostcard.FILE_FORMAT.toString());
            digitalPostcard.setFileFormat(CardFileFormat.valueOf(getValueForEnum(fileFormatName)));
            digitalPostcard.setSizeFile(postcardElement.getAttribute(AttributeXMLPostcard.SIZE_FILE.toString()));

            LOGGER.info(digitalPostcard.toString());

            postcard = digitalPostcard;
        }

        return postcard;
    }

    private static String getElementTextContent(Element element, String elementName) {
        LOGGER.info("Start getElementTextContent with node " + element.getTagName() + " element = " + elementName);
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        String text = node.getTextContent();
        LOGGER.info("element = " + text);
        return text;
    }

    private static String getValueForEnum(String text) {
        return text.toUpperCase().replace('-', '_');
    }


}
