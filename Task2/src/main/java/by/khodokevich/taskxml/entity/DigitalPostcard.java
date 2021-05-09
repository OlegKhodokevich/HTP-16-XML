package by.khodokevich.taskxml.entity;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;
import java.util.Optional;

public class DigitalPostcard extends Postcard {

    private CardFileFormat fileFormat;
    private String sizeFile;

    public DigitalPostcard() {
    }

    public DigitalPostcard(String id, String theme, TypePostcard type, String countryOfProduction, Year year, Optional<String> author, boolean sent, CardFileFormat fileFormat, String sizeFile) {
        super(id, theme, type, countryOfProduction, year, author, sent);
        this.fileFormat = fileFormat;
        this.sizeFile = sizeFile;
    }

    public CardFileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(CardFileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getSizeFile() {
        return sizeFile;
    }

    public void setSizeFile(String sizeFile) {
        this.sizeFile = sizeFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DigitalPostcard)) return false;
        if (!super.equals(o)) return false;
        DigitalPostcard that = (DigitalPostcard) o;
        return fileFormat == that.fileFormat && Objects.equals(sizeFile, that.sizeFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileFormat, sizeFile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{id='").append(this.getId()).append('\'');
        sb.append(", theme='").append(this.getTheme()).append('\'');
        sb.append(", type=").append(this.getType()).append(", countryOfProduction='");
        sb.append(this.getCountryOfProduction()).append('\'');
        sb.append(", year=").append(this.getYear());
        sb.append(", author=").append(this.getAuthor());
        sb.append(", sent=").append(this.isSent());
        sb.append(", fileFormat=").append(fileFormat);
        sb.append(", sizeFile=").append(sizeFile).append('}');
        return sb.toString();
    }
}
