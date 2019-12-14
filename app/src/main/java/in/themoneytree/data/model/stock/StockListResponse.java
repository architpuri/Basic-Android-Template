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
    private List<Stocks> stockss;

    protected StockListResponse() {
    }

    public StockListResponse(List<Stocks> stockss,int statusCode,String message) {
        this.stockss = stockss;
        this.generalResponse=new GeneralResponse(statusCode,message);
    }
    public List<Stocks> getStocks() {
        return stockss;
    }
    public void setStocks(List<Stocks> stockss) {
        this.stockss = stockss;
    }
    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }
    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }

    @Override
    public String toString() {
        return "StockListResponse{" +
                "generalResponse=" + generalResponse +
                ", stocks=" + stockss +
                '}';
    }
}
