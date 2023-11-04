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
        for (Object orc : equipage) {
            negativelyAliveOrcs.add(orc);
        }
    }

    public int getLosesInDollars() {
        int orcsLosesInDollars = 0;
        for (Object orc : negativelyAliveOrcs.getElements()) {
            orcsLosesInDollars += ((Orc) orc).getPrice();
        }

        int techniqueLosesInDollars = 0;
        for (Object technique : destroyedTechnique.getElements()) {
            techniqueLosesInDollars += ((Technique) technique).getPrice();
        }

        return orcsLosesInDollars + techniqueLosesInDollars;
    }
}
