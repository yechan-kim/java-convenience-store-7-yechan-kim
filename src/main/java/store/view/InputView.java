package store.view;

import java.util.List;
import store.model.Product;
import store.model.Promotion;
import store.util.FileLoader;
import store.util.ProductPromotionParser;

public class InputView {

    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";

    private final ProductPromotionParser parser = new ProductPromotionParser();
    private final FileLoader fileLoader = new FileLoader();

    public List<Product> readProductList() {
        return parser.parseProducts(fileLoader.load(PRODUCT_FILE_PATH), readPromotionList());
    }

    private List<Promotion> readPromotionList() {
        return parser.parsePromotions(fileLoader.load(PROMOTION_FILE_PATH));
    }
}
