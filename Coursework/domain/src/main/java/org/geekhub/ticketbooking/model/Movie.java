package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private int id;
    private String title;
    private String description;
    private int durationInMins;
    private OffsetDateTime releaseDate;
    private String country;
    private int ageLimit;
    private Genre genre;
    private byte[] image;

    public void setFormattedReleaseDate(String date) {
        if (!date.isEmpty()) {
            this.releaseDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).atOffset(ZoneOffset.UTC);
        } else {
            this.releaseDate = null;
        }
    }

    public String getFormattedReleaseDate() {
        if (releaseDate != null) {
            return releaseDate.format(DateTimeFormatter.ISO_DATE_TIME);
        } else {
            return "";
        }
    }

    public String getImageDataBase64() {
        return new String(Base64.encodeBase64(image));
    }
}
