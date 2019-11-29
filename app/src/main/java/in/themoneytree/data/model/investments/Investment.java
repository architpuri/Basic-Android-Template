package in.themoneytree.data.model.investments;

/**
 * Created By  Archit
 * on 11/27/2019
 * for TheMoneyTree
 */
public class Investment {
    private Integer investmentId;
    private String investmentName;
    private Double principalAmount;
    private String expiryDate;
    private Double interestRate;
    private int category;
    private String extraInfo;

    public Investment() {
    }

    public Investment(Integer investmentId, String investmentName, Double principalAmount, String expiryDate, Double interestRate, int category, String extraInfo) {
        this.investmentId = investmentId;
        this.investmentName = investmentName;
        this.principalAmount = principalAmount;
        this.expiryDate = expiryDate;
        this.interestRate = interestRate;
        this.category = category;
        this.extraInfo = extraInfo;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getInvestmentName() {
        return investmentName;
    }

    public void setInvestmentName(String investmentName) {
        this.investmentName = investmentName;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
