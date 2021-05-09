package by.khodokevich.taskxml.builder.impl.stax;

import by.khodokevich.taskxml.builder.impl.sax.PostcardEnum;
import by.khodokevich.taskxml.entity.*;
import by.khodokevich.taskxml.exception.ProjectXMLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PostcardStAXBuilder {
    private static final Logger LOGGER = LogManager.getLogger(PostcardStAXBuilder.class);

    private HashSet<Postcard> postcards = new HashSet<>();
    private XMLInputFactory inputFactory;

    public PostcardStAXBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public Set<Postcard> getPostcards() {
        HashSet<Postcard> newPostcards = new HashSet<>();
        newPostcards.addAll(postcards);
        return newPostcards;
    }

    public void buildSetPostcards(String fileName) throws ProjectXMLException {
        LOGGER.info("Start buildSetPostcards() with file " + fileName);
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name = null;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                int type = reader.next();

                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();

                    if (PostcardEnum.valueOf(getValueForEnum(name)) == PostcardEnum.PRINTED_POSTCARD | PostcardEnum.valueOf(getValueForEnum(name)) == PostcardEnum.DIGITAL_POSTCARD) {
                        Postcard postcard = buildPostcard(reader, name);
                        postcards.add(postcard);
                        LOGGER.info("Next postcard was add " + postcard);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            throw new ProjectXMLException(e);
        }
    }

    private Postcard buildPostcard(XMLStreamReader reader, String name) throws XMLStreamException, ProjectXMLException {
        LOGGER.info("Start buildPostcard() with element " + name);
        Postcard postcard;

        switch (PostcardEnum.valueOf(getValueForEnum(name))) {
            case PRINTED_POSTCARD:
                PrintedPostcard printedPostcard = new PrintedPostcard();
                printedPostcard.setSize(Optional.ofNullable(reader.getAttributeValue(null, PostcardEnum.SIZE.getValue())));
                postcard = choiceElement(reader, printedPostcard);
                break;
            case DIGITAL_POSTCARD:
                DigitalPostcard digitalPostcard = new DigitalPostcard();
                String fileFormatPostcardString = getValueForEnum(reader.getAttributeValue(null, PostcardEnum.FILE_FORMAT.getValue()));
                digitalPostcard.setFileFormat(CardFileFormat.valueOf(fileFormatPostcardString));
                digitalPostcard.setSizeFile(reader.getAttributeValue(null, PostcardEnum.SIZE_FILE.getValue()));
                postcard = choiceElement(reader, digitalPostcard);
                break;
            default:
                throw new ProjectXMLException("Unexpected value: " + PostcardEnum.valueOf(getValueForEnum(name)));
        }
        return postcard;
    }

    private Postcard choiceElement(XMLStreamReader reader, Postcard postcard) throws XMLStreamException, ProjectXMLException {
        LOGGER.info("Start choiceElement() .");
        postcard.setId(reader.getAttributeValue(null, PostcardEnum.ID.getValue()));
        postcard.setAuthor(Optional.ofNullable(reader.getAttributeValue(null, PostcardEnum.AUTHOR.getValue())));
        String name;

        while (reader.hasNext()) {
            int type = reader.next();

            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    String valueElement = getXMLText(reader);

                    switch (PostcardEnum.valueOf(getValueForEnum(name))) {
                        case THEME:
                            postcard.setTheme(valueElement);
                            break;
                        case TYPE:
                            String typePostcardString = getValueForEnum(valueElement);
                            postcard.setType(TypePostcard.valueOf(typePostcardString));
                            break;
                        case COUNTRY_OF_PRODUCTION:
                            postcard.setCountryOfProduction(valueElement);
                            break;
                        case YEAR:
                            String yearPostcardString = getValueForEnum(valueElement);
                            postcard.setYear(Year.parse(yearPostcardString));
                            break;
                        case SENT:
                            String sentPostcardString = getValueForEnum(valueElement);
                            postcard.setSent(Boolean.parseBoolean(sentPostcardString));
                            break;
                        case COLOR_TYPE:
                            String ColorTypePostcardString = getValueForEnum(valueElement);
                            ((PrintedPostcard) postcard).setColorType(ColorTypeCard.valueOf(ColorTypePostcardString));
                            break;
                    }
                    LOGGER.info("Read element = " + name + " , value = " + valueElement);
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();

                    if (PostcardEnum.valueOf(getValueForEnum(name)) == PostcardEnum.PRINTED_POSTCARD | PostcardEnum.valueOf(getValueForEnum(name)) == PostcardEnum.DIGITAL_POSTCARD) {
                        return postcard;
                    }
                    break;
            }
        }

        throw new ProjectXMLException("Unknown element in tag printed-card or digital-card.");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if ((reader.hasNext())) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private static String getValueForEnum(String text) {
        return text.toUpperCase().replace('-', '_');
    }
}
