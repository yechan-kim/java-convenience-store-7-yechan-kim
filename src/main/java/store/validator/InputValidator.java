package store.validator;

import java.util.regex.Pattern;
import store.controller.Command;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class InputValidator {

    private static final Pattern SHOPPING_CART_INPUT_PATTERN = Pattern.compile("^\\[[가-힣]+-\\d+](,\\[[가-힣]+-\\d+])*$");

    public void shoppingCartFormat(String input) {
        if (!SHOPPING_CART_INPUT_PATTERN.matcher(input).matches()) {
            throw StoreException.from(ErrorMessage.INVALID_FORMAT);
        }
    }

    public void command(String input) {
        input = input.toUpperCase();

        if (!input.equals(Command.YES.getValue()) && !input.equals(Command.NO.getValue())) {
            throw StoreException.from(ErrorMessage.INVALID_INPUT);
        }
    }
}
