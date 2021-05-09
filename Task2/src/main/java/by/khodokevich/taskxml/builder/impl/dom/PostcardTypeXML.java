package by.khodokevich.taskxml.builder.impl.dom;

public enum PostcardTypeXML {
    PRINTED_POSTCARD, DIGITAL_POSTCARD;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
