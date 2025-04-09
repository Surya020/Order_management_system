package entity;

public class Clothing extends Product {
    private String size;
    private String color;

    // Simple constructor
    public Clothing() {
        super();
    }

    // Constructor with all fields
    public Clothing(int productId, String productName, String description, double price, 
                   int quantityInStock, String size, String color) {
        super(productId, productName, description, price, quantityInStock, "Clothing");
        this.size = size;
        this.color = color;
    }

    // Simple getters and setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
} 