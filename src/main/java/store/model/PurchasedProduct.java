package store.model;

import java.util.Objects;

public class PurchasedProduct {
    private final String name;
    private final int price;
    private final int promotionQuantity;
    private final int quantity;
    private final Promotion promotion;

    private PurchasedProduct(String name, int price, int promotionQuantity, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static PurchasedProduct of(String name, int price, int promotionQuantity, int quantity,
                                      Promotion promotion) {
        return new PurchasedProduct(name, price, promotionQuantity, quantity, promotion);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PurchasedProduct that)) {
            return false;
        }

        return isEqualTo(that);
    }

    private boolean isEqualTo(PurchasedProduct that) {
        return price == that.price &&
                promotionQuantity == that.promotionQuantity &&
                quantity == that.quantity &&
                Objects.equals(name, that.name) &&
                Objects.equals(promotion, that.promotion);
    }
}
