package model;

public class Item {
    public String name;
    public int quantity;
    public int length;
    public int width;
    public int height;
    public int weight;

    public Item(String name, int quantity, int length, int width, int height, int weight) {
        this.name = name;
        this.quantity = quantity;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
    }
}