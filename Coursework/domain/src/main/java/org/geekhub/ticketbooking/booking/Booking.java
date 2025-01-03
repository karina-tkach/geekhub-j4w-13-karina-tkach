package org.geekhub.ticketbooking.booking;

import org.geekhub.ticketbooking.user.User;

import java.util.UUID;

public class Booking {
    private int id;
    private int showId;
    private int seatId;
    private User user;
    private UUID uuid;
    private String showDetails;

    public Booking() {
        this.id = -1;
        this.showId = -1;
        this.seatId = -1;
        this.user = null;
        this.uuid = null;
        this.showDetails = null;
    }

    public Booking(int id, int showId, int seatId, User user, UUID uuid, String showDetails) {
        this.id = id;
        this.showId = showId;
        this.seatId = seatId;
        this.user = user;
        this.uuid = uuid;
        this.showDetails = showDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getShowDetails() {
        return showDetails;
    }

    public void setShowDetails(String showDetails) {
        this.showDetails = showDetails;
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + id +
            ", showId=" + showId +
            ", seatId=" + seatId +
            ", user=" + user +
            ", uuid=" + uuid +
            ", showDetails='" + showDetails + '\'' +
            '}';
    }
}
