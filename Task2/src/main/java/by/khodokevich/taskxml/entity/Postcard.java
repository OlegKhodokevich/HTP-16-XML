package by.khodokevich.taskxml.entity;

import java.time.Year;
import java.util.Objects;
import java.util.Optional;

public abstract class Postcard {

    private String id;
    private String theme;
    private TypePostcard type;
    private String countryOfProduction;
    private Year year;
    private Optional<String> author;
    private boolean sent;

    public Postcard() {
        super();
    }

    public Postcard(String id, String theme, TypePostcard type, String countryOfProduction, Year year, Optional<String> author, boolean sent) {

        this.id = id;
        this.theme = theme;
        this.type = type;
        this.countryOfProduction = countryOfProduction;
        this.year = year;
        this.author = author;
        this.sent = sent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public TypePostcard getType() {
        return type;
    }

    public void setType(TypePostcard type) {
        this.type = type;
    }

    public String getCountryOfProduction() {
        return countryOfProduction;
    }

    public void setCountryOfProduction(String countryOfProduction) {
        this.countryOfProduction = countryOfProduction;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Optional<String> getAuthor() {
        return author;
    }

    public void setAuthor(Optional<String> author) {
        this.author = author;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Postcard)) return false;
        Postcard postcard = (Postcard) o;
        return sent == postcard.sent && Objects.equals(id, postcard.id) && Objects.equals(theme, postcard.theme) && type == postcard.type && Objects.equals(countryOfProduction, postcard.countryOfProduction) && Objects.equals(year, postcard.year) && Objects.equals(author, postcard.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, type, countryOfProduction, year, author, sent);
    }

}
