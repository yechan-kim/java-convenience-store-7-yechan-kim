package store.dto;

public record ShoppingCartInput(String rawShoppingCart) {

    public static ShoppingCartInput from(String rawShoppingCart) {
        return new ShoppingCartInput(rawShoppingCart);
    }
}
