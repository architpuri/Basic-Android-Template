package in.themoneytree.data.model.stock;

/**
 * Created By  Archit
 * on 10/17/2019
 * for TheMoneyTree
 */
import java.util.List;

import in.themoneytree.data.model.GeneralResponse;

public class StockListResponse {
    private GeneralResponse generalResponse;
    private List<Stock> stocks;

    protected StockListResponse() {
    }

    public StockListResponse(List<Stock> stocks, int statusCode, String message) {
        this.stocks = stocks;
        this.generalResponse=new GeneralResponse(statusCode,message);
    }
    public List<Stock> getStocks() {
        return stocks;
    }
    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }
    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}
