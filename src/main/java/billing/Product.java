package billing;

public class Product {
    private String name;
    private double price;
    private int quantity; // This will represent the quantity purchased

    // Original constructor
    public Product(int id, String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // New constructor that only takes name and price, with default quantity
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 0; // Default quantity if not provided
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (Available: " + quantity + ")";
    }

    public void decreaseQuantity(int amount) {
        if (amount <= quantity) {
            this.quantity -= amount;
        } else {
            System.out.println("Insufficient quantity available to decrease.");
        }
    }
}
