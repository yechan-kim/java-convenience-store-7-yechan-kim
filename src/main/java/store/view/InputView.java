package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.dto.ShoppingCartInput;
import store.model.Product;
import store.model.Promotion;
import store.util.FileLoader;
import store.util.ProductPromotionParser;
import store.validator.InputValidator;

public class InputView {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    private final FileLoader fileLoader = new FileLoader();
    private final InputValidator validator = new InputValidator();
    private final ProductPromotionParser parser = new ProductPromotionParser();

    public List<Product> readProductList() {
        return parser.parseProducts(fileLoader.load(PRODUCT_FILE_PATH), readPromotionList());
    }

    private List<Promotion> readPromotionList() {
        return parser.parsePromotions(fileLoader.load(PROMOTION_FILE_PATH));
    }

    public ShoppingCartInput readShoppingCart() {
        while (true) {
            try {
                String rawShoppingCart = Console.readLine().trim();
                validator.shoppingCartFormat(rawShoppingCart);

                return ShoppingCartInput.from(rawShoppingCart);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
