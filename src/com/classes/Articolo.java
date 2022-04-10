package com.classes;

import org.jetbrains.annotations.NotNull;

public class Articolo {
    private String uniqueCode;
    private String productName;
    private double cost;
    private int quantity;

    public Articolo(String productName, int quantity, double cost) throws Exception{
        setProductName(productName);
        setQuantity(quantity);
        setCost(cost);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(@NotNull String productName) throws Exception {
        if(!productName.equals("")) this.productName = productName;
        else
            throw new Exception();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    /*public double costMultiplier(int quantity){
        if(quantity > 1){
            return cost*quantity;
        }
        else
            return cost;
    }*/

    public void setCost(double cost) throws Exception{
        if(cost<=0) throw new Exception();
        else
            this.cost = cost;
    }

    @Override
    public String toString() {
        return "Articolo{" +
                "uniqueCode= " + uniqueCode + '\'' +
                ", productName= " + productName + '\'' +
                ", cost= " + cost +
                ", quantity= " + quantity +
                '}';
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}
