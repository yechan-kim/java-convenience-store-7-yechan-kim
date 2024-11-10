package store.controller;

import java.util.List;
import store.model.Product;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final List<Product> productList = inputView.readProductList();

    public void start() {
        outputView.intro();
        outputView.products(productList);
    }
}
