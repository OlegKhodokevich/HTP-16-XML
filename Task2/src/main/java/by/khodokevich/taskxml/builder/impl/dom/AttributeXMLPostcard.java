package by.khodokevich.taskxml.builder.impl.dom;

public enum AttributeXMLPostcard {
    ID, AUTHOR, SIZE, SIZE_FILE, FILE_FORMAT;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
