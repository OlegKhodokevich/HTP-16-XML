package by.khodokevich.taskxml.builder.impl.sax;

public enum PostcardEnum {
    CARDS("cards"), PRINTED_POSTCARD("printed-postcard"), DIGITAL_POSTCARD("digital-postcard"), THEME("theme"), TYPE("type"), COUNTRY_OF_PRODUCTION("country-of-production"), YEAR("year"), SENT("sent"), COLOR_TYPE("color-type"), ID("id"), AUTHOR("author"), SIZE("size"), SIZE_FILE("size-file"), FILE_FORMAT("file-format");
    private String value;

    PostcardEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
