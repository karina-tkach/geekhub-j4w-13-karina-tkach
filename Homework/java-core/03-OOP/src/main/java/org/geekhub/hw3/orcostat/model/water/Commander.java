package org.geekhub.hw3.orcostat.model.water;

import org.geekhub.hw3.orcostat.model.Orc;

public class Commander extends Orc {
    private final int seaTimeInHours;

    public Commander() {
        this(0);
    }

    public Commander(int seaTimeInHours) {
        super(25_000);
        this.seaTimeInHours = seaTimeInHours;
    }

    public int getSeaTimeInHours() {
        return seaTimeInHours;
    }

    @Override
    public String scream() {
        return "Damn!";
    }
}
