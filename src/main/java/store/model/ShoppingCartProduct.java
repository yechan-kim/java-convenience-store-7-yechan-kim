package store.model;

import java.util.List;

public class ShoppingCartProduct {

    private final String name;
    private final int quantity;

    private ShoppingCartProduct(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static ShoppingCartProduct of(String rawShoppingCartProduct) {
        List<String> rawShoppingCartProductList = List.of(rawShoppingCartProduct.split("-"));

        return new ShoppingCartProduct(rawShoppingCartProductList.get(0).substring(1),
                Integer.parseInt(rawShoppingCartProductList.get(1)
                        .substring(0, rawShoppingCartProductList.get(1).length() - 1)));
    }

    public static List<ShoppingCartProduct> createList(String rawShoppingCart) {
        List<String> rawShoppingCartList = List.of(rawShoppingCart.split(","));

        return rawShoppingCartList.stream()
                .map(ShoppingCartProduct::of)
                .toList();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
