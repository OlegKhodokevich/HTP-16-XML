package by.khodokevich.taskxml.builder.impl.dom;

public enum ElementPostcardXML {
    THEME, TYPE, COUNTRY_OF_PRODUCTION, YEAR, SENT, COLOR_TYPE;

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', '-');
    }
}
