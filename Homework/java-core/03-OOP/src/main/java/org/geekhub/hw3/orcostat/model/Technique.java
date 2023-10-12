package org.geekhub.hw3.orcostat.model;

public interface Technique {
    int getPrice();

    Collection getEquipage();

    String shoot();

    default String destroy() {
        StringBuilder resultedString = new StringBuilder();
        resultedString.append("Destroyed!");
        Object[] equipage = getEquipage().getElements();
        for (Object orc : equipage) {
            String orcScream = ((Orc) orc).scream();
            resultedString.append(orcScream);
        }
        return resultedString.toString();
    }
}