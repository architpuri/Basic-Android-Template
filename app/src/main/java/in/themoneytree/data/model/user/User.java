package in.themoneytree.data.model.user;

public class User {
    private Integer userId;
    private Integer portfolioId;
    private String userName;
    private String password;
    private int userType;
    private String fullName;
    private String userImageUrl;//if url=empty => no img uploaded till now.
    private String mobileNumber;
    private String userBackgroundImageUrl;
    private boolean userEnabled;//enable using email
    private boolean isMobileVerified;
    private String userAccessToken;
    private String permissionToken;

    public User() {
    }

    public User(Integer userId, Integer portfolioId, String userName, String password, int userType, String fullName, String userImageUrl, String mobileNumber, String userBackgroundImageUrl, boolean userEnabled, boolean isMobileVerified, String userAccessToken, String permissionToken) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.fullName = fullName;
        this.userImageUrl = userImageUrl;
        this.mobileNumber = mobileNumber;
        this.userBackgroundImageUrl = userBackgroundImageUrl;
        this.userEnabled = userEnabled;
        this.isMobileVerified = isMobileVerified;
        this.userAccessToken = userAccessToken;
        this.permissionToken = permissionToken;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserBackgroundImageUrl() {
        return userBackgroundImageUrl;
    }

    public void setUserBackgroundImageUrl(String userBackgroundImageUrl) {
        this.userBackgroundImageUrl = userBackgroundImageUrl;
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public boolean isMobileVerified() {
        return isMobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        isMobileVerified = mobileVerified;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getPermissionToken() {
        return permissionToken;
    }

    public void setPermissionToken(String permissionToken) {
        this.permissionToken = permissionToken;
    }
}