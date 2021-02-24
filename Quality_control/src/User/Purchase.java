package User;

import Admin.Product;

public class Purchase {
    private String Product;
    private double Price;
    private String Status;
    private String Date;
    Purchase(){};
    Purchase(String Product, double Price, String Status, String Date)
    {
        this.Product = Product;
        this.Price = Price;
        this.Status = Status;
        this.Date = Date;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
