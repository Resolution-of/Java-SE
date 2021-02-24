package Usages;

import java.io.Serializable;

public class Product{
    private String productName;
    private double price;
    private double weight;
    private double protein;
    private double fats;
    private double carbohydrates;
    private double nutritionalValue;
    private String composition;
    public Product() {}
    public Product(String productName, double price, double weight, double protein, double fats, double carbohydrates, double nutritionalValue, String composition)
    {
        this.productName = productName;
        this.price = price;
        this.weight = weight;
        this.protein = protein;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.nutritionalValue = nutritionalValue;
        this.composition = composition;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(double nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
