package data;

public class ThirdData extends mData{

    private int state;
    private String day;
    private String content;

    public ThirdData(int id, int userId, int state, String day, String content) {
        super(id, userId);
        this.state = state;
        this.day = day;
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
