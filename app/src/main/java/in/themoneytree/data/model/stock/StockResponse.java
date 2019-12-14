package in.themoneytree.data.model.stock;

import in.themoneytree.data.model.GeneralResponse;

/**
 * Created By  Archit
 * on 10/17/2019
 * for TheMoneyTree
 */

public class StockResponse {
    private GeneralResponse generalResponse;
    private Stocks stock;

    public StockResponse() {
    }

    public StockResponse(Stocks stock, int statusCode, String message) {
        this.generalResponse = new GeneralResponse(statusCode, message);
        this.stock = stock;
    }

    public Stocks getstock() {
        return stock;
    }

    public void setstock(Stocks stock) {
        this.stock = stock;
    }

    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }

    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}

