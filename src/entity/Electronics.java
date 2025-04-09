package entity;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    // Simple constructor
    public Electronics() {
        super();
    }

    // Constructor with all fields
    public Electronics(int productId, String productName, String description, double price, 
                      int quantityInStock, String brand, int warrantyPeriod) {
        super(productId, productName, description, price, quantityInStock, "Electronics");
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Simple getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
} 