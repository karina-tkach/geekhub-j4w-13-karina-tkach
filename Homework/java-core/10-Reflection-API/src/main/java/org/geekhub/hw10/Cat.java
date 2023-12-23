package org.geekhub.hw10;

public class Cat {
    private final int paws;
    private final int ears;
    private final String color;
    private int energy = 5;

    public Cat() {
        paws = 4;
        ears = 2;
        color = "Black";
    }
    public Cat(Cat secondCat){
        paws = secondCat.paws;
        ears = secondCat.ears;
        color = secondCat.color;
        energy = secondCat.energy;
    }
    public Cat(int paws, int ears, String color) {
        if(paws<0 || ears<0){
            throw new IllegalArgumentException();
        }
        this.paws = paws;
        this.ears = ears;
        this.color = color;
    }

    public int getPawsNumber(){
        return paws;
    }
    public int getEarsNumber(){
        return ears;
    }
    public String getColor(){
        return color;
    }
    public void sleep(){
        energy++;
    }
    public int getEnergy(){
        return energy;
    }
}
