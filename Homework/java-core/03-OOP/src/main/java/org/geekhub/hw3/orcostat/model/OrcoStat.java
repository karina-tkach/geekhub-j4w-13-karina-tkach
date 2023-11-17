package org.geekhub.hw3.orcostat.model;

public class OrcoStat {
    private final Collection negativelyAliveOrcs;
    private final Collection destroyedTechnique;

    public OrcoStat() {
        this.negativelyAliveOrcs = new SimpleCollection();
        this.destroyedTechnique = new SimpleCollection();
    }

    public void smashOrc(Orc orc) {
        negativelyAliveOrcs.add(orc);
    }

    public int getNegativelyAliveOrcCount() {
        return negativelyAliveOrcs.size();
    }

    public void smashTechnique(Technique technique) {
        destroyedTechnique.add(technique);
        Object[] equipage = technique.getEquipage().getElements();
        for (Object object : equipage) {
            if (object instanceof Orc orc) {
                negativelyAliveOrcs.add(orc);
            }
        }
    }

    public int getLosesInDollars() {
        int orcsLosesInDollars = 0;
        for (Object object : negativelyAliveOrcs.getElements()) {
            if (object instanceof Orc orc) {
                orcsLosesInDollars += orc.getPrice();
            }
        }

        int techniqueLosesInDollars = 0;
        for (Object object : destroyedTechnique.getElements()) {
            if (object instanceof Technique technique) {
                techniqueLosesInDollars += technique.getPrice();
            }
        }

        return orcsLosesInDollars + techniqueLosesInDollars;
    }
}
