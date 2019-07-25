package main.database;

public enum Category {
    FRUIT, VEGETABLE, MUSHROOM, MEAT, GRAIN;


    @Override
    public String toString() {
        return super.toString().substring(0,1) + super.toString().substring(1).toLowerCase();
    }
}
