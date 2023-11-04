package org.geekhub.hw3.orcostat.model;

public class Orc {
    private static final int LADA_VESTA_SPORT_PRICE = 10_000;
    private int price;

    public Orc() {
        price = LADA_VESTA_SPORT_PRICE;
    }

    public Orc(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }

    public String scream() {
        return "Aaaaaa!";
    }
}
