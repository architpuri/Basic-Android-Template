package in.themoneytree.data.model.portfolio;

import in.themoneytree.data.model.GeneralResponse;

/**
 * Created By  Archit
 * on 11/1/2019
 * for TheMoneyTree
 */
public class PortfolioResponse {
    private GeneralResponse generalResponse;
    private Portfolio portfolio;

    public PortfolioResponse() {
    }

    public PortfolioResponse(Portfolio portfolio, int statusCode, String message) {
        this.generalResponse = new GeneralResponse(statusCode, message);
        this.portfolio = portfolio;
    }

    public Portfolio getportfolio() {
        return portfolio;
    }

    public void setportfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }

    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}
