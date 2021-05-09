package by.khodokevich.taskxml.builder.impl.sax;

import by.khodokevich.taskxml.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Year;
import java.util.*;

public class PostcardHandler extends DefaultHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Set<Postcard> postcards;
    private Postcard currentPostcard = null;
    private PostcardEnum currentEnum = null;
    private EnumSet<PostcardEnum> withText;

    public PostcardHandler() {
        postcards = new HashSet<>();
        withText = EnumSet.range(PostcardEnum.THEME, PostcardEnum.COLOR_TYPE);
    }

    public Set<Postcard> getPostcards() {
        Set<Postcard> newPostcards = new HashSet<>();
        newPostcards.addAll(postcards);
        return newPostcards;
    }

    @Override
    public void startDocument() throws SAXException {
        LOGGER.info("start start read document.");
        super.startDocument();
    }

    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        LOGGER.info("Start startElement().");
        LOGGER.info("String uri " + uri + " , String localName  " + localName + ", String qName " + qName + " , Number of Attributes attrs " + attrs.getLength());

        switch ((getValueForEnum(qName))) {
            case "PRINTED_POSTCARD": {
                PrintedPostcard printedPostcard = new PrintedPostcard();
                printedPostcard.setId(attrs.getValue(PostcardEnum.ID.toString()));
                printedPostcard.setAuthor(Optional.ofNullable(attrs.getValue(PostcardEnum.AUTHOR.toString())));
                printedPostcard.setSize(Optional.ofNullable(attrs.getValue(PostcardEnum.SIZE.toString())));
                currentPostcard = printedPostcard;
                LOGGER.info("Next postcard was created : " + printedPostcard);
                break;
            }
            case "DIGITAL_POSTCARD": {
                DigitalPostcard digitalPostcard = new DigitalPostcard();
                digitalPostcard.setId(attrs.getValue(PostcardEnum.ID.toString()));
                digitalPostcard.setAuthor(Optional.ofNullable(attrs.getValue(PostcardEnum.AUTHOR.toString())));
                String cardFileFormatString = attrs.getValue(PostcardEnum.FILE_FORMAT.toString());
                digitalPostcard.setFileFormat(CardFileFormat.valueOf(getValueForEnum(cardFileFormatString)));
                digitalPostcard.setSizeFile(attrs.getValue(PostcardEnum.SIZE_FILE.toString()));
                currentPostcard = digitalPostcard;
                LOGGER.info("Next postcard was created : " + digitalPostcard);
                break;
            }
            default: {
                PostcardEnum temp = PostcardEnum.valueOf(getValueForEnum(qName));
                if (withText.contains(temp)) {
                    currentEnum = temp;
                }
            }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        if (PostcardEnum.PRINTED_POSTCARD.toString().equals(qName) | PostcardEnum.DIGITAL_POSTCARD.toString().equals(qName)) {
            LOGGER.info("End of element " + currentPostcard.toString());
            postcards.add(currentPostcard);
        }
    }

    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
        LOGGER.info(" We work with element " + currentEnum + " = " + s);
        if (currentEnum != null) {
            switch (currentEnum) {
                case THEME:
                    currentPostcard.setTheme(s);
                    break;
                case TYPE:
                    currentPostcard.setType(TypePostcard.valueOf(getValueForEnum(s)));
                    break;
                case COUNTRY_OF_PRODUCTION:
                    currentPostcard.setCountryOfProduction(s);
                    break;
                case YEAR:
                    currentPostcard.setYear(Year.parse(s));
                    break;
                case SENT:
                    currentPostcard.setSent(Boolean.parseBoolean(s));
                    break;
                case COLOR_TYPE:
                    String colorTypeString = getValueForEnum(s);
                    ((PrintedPostcard) currentPostcard).setColorType(ColorTypeCard.valueOf(colorTypeString));
                    break;
                default:
                    LOGGER.info(" Unknown character ." + currentEnum.name());
            }
        }
        currentEnum = null;
    }


    private static String getValueForEnum(String text) {
        return text.toUpperCase().replace('-', '_');
    }

    @Override
    public void endDocument() throws SAXException {
        LOGGER.info("End of document. Has read next objects: ");
        for (Postcard postcard : postcards) {
            LOGGER.info((postcard.toString()));
        }
        super.endDocument();
    }
}
