package store.model;

import java.util.List;

public class Product {
    private final String name;
    private final int price;
    private final Promotion promotion;

    private final int promotionQuantity;
    private int quantity;

    private Product(String name, int price, int quantity, int promotionQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public static Product of(List<String> values, Promotion promotion) {
        return new Product(values.get(0), Integer.parseInt(values.get(1)), 0, Integer.parseInt(values.get(2)),
                promotion);
    }

    public static Product from(List<String> values) {
        return new Product(values.get(0), Integer.parseInt(values.get(1)), Integer.parseInt(values.get(2)), 0, null);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updatePromotionQuantity(int promotionQuantity) {
        this.quantity = promotionQuantity;
    }
}
