package store;

import camp.nextstep.edu.missionutils.Console;
import store.config.AppConfig;
import store.controller.StoreController;

public class Application {

    public static void main(String[] args) {
        AppConfig appConfig = AppConfig.getInstance();
        StoreController lottoController = appConfig.storeController();
        lottoController.run();

        Console.close();
    }
}
