package in.themoneytree.data.model;

public class UserResponse {
    private GeneralResponse generalResponse;
    private User user;

    public UserResponse() {
    }

    public UserResponse(User user, int statusCode, String message) {
        this.generalResponse = new GeneralResponse(statusCode, message);
        this.user = user;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }

    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }
}
