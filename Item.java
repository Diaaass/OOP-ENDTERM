public class Item {
    private String name;
    private int quantity;
    private Price price;
    private Price sale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getSale() {
        return sale;
    }

    public void setSale(Price sale) {
        this.sale = sale;
    }

    public Item(String name, int quantity, Price price, Price sale) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.sale = sale;
    }

    public Item() {
    }
}