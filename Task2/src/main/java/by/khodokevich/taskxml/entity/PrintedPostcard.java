package by.khodokevich.taskxml.entity;

import java.time.Year;
import java.util.Objects;
import java.util.Optional;


public class PrintedPostcard extends Postcard {

    private ColorTypeCard colorType;
    private Optional<String> size;

    public PrintedPostcard() {
        super();
    }

    public PrintedPostcard(String id, String theme, TypePostcard type, String countryOfProduction, Year year, Optional<String> author, boolean sent, ColorTypeCard colorType, Optional<String> size) {
        super(id, theme, type, countryOfProduction, year, author, sent);
        this.colorType = colorType;
        this.size = size;
    }

    public ColorTypeCard getColorType() {
        return colorType;
    }

    public void setColorType(ColorTypeCard colorType) {
        this.colorType = colorType;
    }

    public Optional<String> getSize() {
        return size;
    }

    public void setSize(Optional<String> size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrintedPostcard)) return false;
        if (!super.equals(o)) return false;
        PrintedPostcard that = (PrintedPostcard) o;
        return colorType == that.colorType && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), colorType, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{id='").append(this.getId()).append('\'');
        sb.append(", theme='").append(this.getTheme()).append('\'');
        sb.append(", type=").append(this.getType()).append(", countryOfProduction='");
        sb.append(this.getCountryOfProduction()).append('\'');
        sb.append(", year=").append(this.getYear());
        sb.append(", author=").append(this.getAuthor());
        sb.append(", sent=").append(this.isSent());
        sb.append(", colorType=").append(colorType);
        sb.append(", size='").append(size).append('\'').append('}');
        return sb.toString();
    }
}
