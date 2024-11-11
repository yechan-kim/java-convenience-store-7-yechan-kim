package store.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShoppingCartProduct {

    private final String name;

    private int quantity;

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
        return mergeProducts(rawShoppingCartList.stream()
                .map(ShoppingCartProduct::of)
                .toList());
    }

    private static List<ShoppingCartProduct> mergeProducts(List<ShoppingCartProduct> shoppingCart) {
        return shoppingCart.stream()
                .collect(Collectors.toMap(ShoppingCartProduct::getName, product -> product,
                        (existing, replacement) -> {
                            existing.quantity += replacement.getQuantity();
                            return existing;
                        }))
                .values()
                .stream()
                .toList();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCartProduct that = (ShoppingCartProduct) o;
        return quantity == that.quantity &&
                Objects.equals(name, that.name);
    }
}
