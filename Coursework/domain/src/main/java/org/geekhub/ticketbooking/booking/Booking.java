package org.geekhub.ticketbooking.booking;

import java.util.UUID;

public class Booking {
    private int id;
    private int showId;
    private int seatId;
    private int userId;
    private UUID uuid;

    public Booking() {
        this.id = -1;
        this.showId = -1;
        this.seatId = -1;
        this.userId = -1;
        this.uuid = UUID.randomUUID();
    }

    public Booking(int id, int showId, int seatId, int userId, UUID uuid) {
        this.id = id;
        this.showId = showId;
        this.seatId = seatId;
        this.userId = userId;
        this.uuid = uuid;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
