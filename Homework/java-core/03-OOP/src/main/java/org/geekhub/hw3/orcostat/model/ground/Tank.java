package org.geekhub.hw3.orcostat.model.ground;

import org.geekhub.hw3.orcostat.model.Collection;
import org.geekhub.hw3.orcostat.model.Orc;
import org.geekhub.hw3.orcostat.model.SimpleCollection;
import org.geekhub.hw3.orcostat.model.Technique;

public class Tank implements Technique {
    private final Collection equipage;
    private static final int TANK_PRICE = 3_000_000;
    private final int maxEquipageSize;

    public Tank(int maxEquipageSize) {
        //check
        if (maxEquipageSize >= 0 && maxEquipageSize <= 6) {
            equipage = new SimpleCollection(maxEquipageSize);
            this.maxEquipageSize = maxEquipageSize;
        } else {
            equipage = new SimpleCollection(6);
            this.maxEquipageSize = 6;
        }
    }

    boolean putOrc(Orc orc) {
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
        return TANK_PRICE;
    }

    @Override
    public String shoot() {
        return "Bam!";
    }
}
