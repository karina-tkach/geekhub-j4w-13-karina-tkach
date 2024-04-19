package org.geekhub.ticketbooking.movie;

import org.apache.commons.codec.binary.Base64;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("java:S107")
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

    public Movie() {
        this.id = -1;
        this.title = null;
        this.description = null;
        this.durationInMins = 0;
        this.releaseDate = null;
        this.country = null;
        this.ageLimit = 0;
        this.genre = null;
        this.image = null;
    }

    public Movie(int id, String title, String description, int durationInMins,
                 OffsetDateTime releaseDate, String country, int ageLimit, Genre genre, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationInMins = durationInMins;
        this.releaseDate = releaseDate;
        this.country = country;
        this.ageLimit = ageLimit;
        this.genre = genre;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(int durationInMins) {
        this.durationInMins = durationInMins;
    }

    public OffsetDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(OffsetDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setFormattedReleaseDate(String date) {
        if (!date.isEmpty()) {
            this.releaseDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).atOffset(ZoneOffset.UTC);
        } else {
            this.releaseDate = null;
        }
    }

    public String getFormattedReleaseDate() {
        if (releaseDate != null) {
            String str = releaseDate.format(DateTimeFormatter.ISO_DATE_TIME);
            return str.substring(0, str.length() - 1);
        } else {
            return "";
        }
    }

    public String getImageDataBase64() {
        return new String(Base64.encodeBase64(image));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return durationInMins == movie.durationInMins && ageLimit == movie.ageLimit &&
            Objects.equals(title, movie.title) && Objects.equals(description, movie.description) &&
            Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(country, movie.country) &&
            genre == movie.genre && Objects.deepEquals(image, movie.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, durationInMins, releaseDate, country,
            ageLimit, genre, Arrays.hashCode(image));
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", durationInMins=" + durationInMins +
            ", releaseDate=" + releaseDate +
            ", country='" + country + '\'' +
            ", ageLimit=" + ageLimit +
            ", genre=" + genre +
            '}';
    }
}
