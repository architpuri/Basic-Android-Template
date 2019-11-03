package in.themoneytree.data.model.portfolio;

/**
 * Created By  Archit
 * on 11/1/2019
 * for TheMoneyTree
 */
public class Portfolio {
    private Integer portfolioId;
    private Integer userId;
    private int riskScore;
    private int timeScore;
    private float retirementCorpusAmount;

    public Portfolio() {
    }

    public Portfolio(Integer portfolioId, Integer userId, int riskScore, int timeScore, float retirementCorpusAmount) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.riskScore = riskScore;
        this.timeScore = timeScore;
        this.retirementCorpusAmount = retirementCorpusAmount;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public int getTimeScore() {
        return timeScore;
    }

    public void setTimeScore(int timeScore) {
        this.timeScore = timeScore;
    }

    public float getRetirementCorpusAmount() {
        return retirementCorpusAmount;
    }

    public void setRetirementCorpusAmount(float retirementCorpusAmount) {
        this.retirementCorpusAmount = retirementCorpusAmount;
    }
}
