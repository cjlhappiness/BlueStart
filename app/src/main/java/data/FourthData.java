package data;

public class FourthData extends mData{

    private int year;
    private int month;
    private int day;
    private String content;

    public FourthData(int id, int userId) {
        super(id, userId);
    }

    public FourthData(int id, int userId, int year, int month, int day, String content) {
        super(id, userId);
        this.year = year;
        this.month = month;
        this.day = day;
        this.content = content;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void getDay(int day) {
        this.day = day;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
