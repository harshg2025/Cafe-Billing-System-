package src;

public class MenuItem {
    private String name;
    private double price;
    private int quantity;
    private boolean isSelected;
    private String imagePath;

    public MenuItem(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.isSelected = false;
        this.imagePath = imagePath;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
    
    public String getImagePath() {
        return imagePath;
    }
}
