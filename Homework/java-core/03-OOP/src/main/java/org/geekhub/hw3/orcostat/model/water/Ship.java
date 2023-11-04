package org.geekhub.hw3.orcostat.model.water;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.Orc;
import org.geekhub.hw3.orcostat.model.SimpleCollection;
import org.geekhub.hw3.orcostat.model.Technique;

public class Ship implements Technique {
    private final Collection equipage;
    private static final int SHIP_PRICE = 50_000_000;
    private final int maxEquipageSize;

    public Ship(int maxEquipageSize) {
        equipage = new SimpleCollection(maxEquipageSize);
        this.maxEquipageSize = maxEquipageSize;
    }

    public boolean putOrc(Orc orc) {
        if (equipage.size() == maxEquipageSize) {
            return false;
        }
        boolean isPresent = false;
        for (Object presentedOrc : equipage.getElements()) {
            if (presentedOrc == orc) {
                isPresent = true;
                break;
            }
        }
        if (isPresent) {
            return false;
        }
        equipage.add(orc);
        return true;
    }

    @Override
    public Collection getEquipage() {
        return equipage;
    }

    @Override
    public int getPrice() {
        return SHIP_PRICE;
    }

    @Override
    public String shoot() {
        return "Bang!";
    }
}
