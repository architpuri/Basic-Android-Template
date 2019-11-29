package in.themoneytree.data.model.incomestreams;

/**
 * Created By  Archit
 * on 11/22/2019
 * for TheMoneyTree
 */
public class IncomeStream {
    private Integer streamId;
    private Integer userId;
    private String name;
    private double amount;
    private int frequency;//Monthly - 0, Yearly - 1,Inactive = -1
    private String streamInfo;
    private int streamType;//Passive ,Active

    public IncomeStream() {
    }

    public IncomeStream(Integer streamId, Integer userId, String name, double amount, int frequency, String streamInfo, int streamType) {
        this.streamId = streamId;
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.frequency = frequency;
        this.streamInfo = streamInfo;
        this.streamType = streamType;
    }

    public String getStreamInfo() {
        return streamInfo;
    }

    public void setStreamInfo(String streamInfo) {
        this.streamInfo = streamInfo;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId) {
        this.streamId = streamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
