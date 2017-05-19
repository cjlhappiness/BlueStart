package data;

public class UserData extends mData{

    private String user;
    private String password;
    private String firstDate;
    private String lastDate;
    private int count;
    private double longitude;
    private double latitude;

    public UserData(int id, int userId, String user, String password, String firstDate,
                    String lastDate, int count, double longitude, double latitude){
        super(id, userId);
        this.user = user;
        this.password = password;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.count = count;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public int getCount() {
        return count;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
