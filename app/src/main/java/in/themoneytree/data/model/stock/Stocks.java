package in.themoneytree.data.model.stock;

public class Stocks {
    private Integer stockId;
    private String stockName;
    private Double stockPrice;
    private Double priceChange;
    private Double changePercent;
    private String stockImgUrl;

    public Stocks() {
    }

    public Stocks (Integer stockId,String stockName,Double stockPrice,String stockImgUrl){
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.priceChange = 5.0;
        this.changePercent = 0.02;
        this.stockImgUrl = stockImgUrl;
    }

    /*public Stock(Integer stockId, String stockName, Double stockPrice, Double priceChange, Double changePercent, String stockImgUrl) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.priceChange = priceChange;
        this.changePercent = changePercent;
        this.stockImgUrl = stockImgUrl;
    }*/

    public String getStockImgUrl() {
        return stockImgUrl;
    }

    public void setStockImgUrl(String stockImgUrl) {
        this.stockImgUrl = stockImgUrl;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(Double priceChange) {
        this.priceChange = priceChange;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
    }
}
