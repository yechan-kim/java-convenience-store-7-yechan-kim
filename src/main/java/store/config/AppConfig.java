package store.config;

import store.controller.StoreController;
import store.model.MemberShipDiscountHandler;
import store.model.Receipt;
import store.model.StockUpdater;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return SingleTonHelper.INSTANCE;
    }

    public StoreController storeController() {
        return new StoreController(createReceipt(), createInputView(), createOutputView(), createStockUpdater(),
                createMemberShipDiscountHandler());
    }

    private Receipt createReceipt() {
        return new Receipt();
    }

    private InputView createInputView() {
        return new InputView();
    }

    private OutputView createOutputView() {
        return new OutputView();
    }

    private StockUpdater createStockUpdater() {
        return new StockUpdater();
    }

    private MemberShipDiscountHandler createMemberShipDiscountHandler() {
        return new MemberShipDiscountHandler();
    }

    private static class SingleTonHelper {
        private static final AppConfig INSTANCE = new AppConfig();
    }
}
