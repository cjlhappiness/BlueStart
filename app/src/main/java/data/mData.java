package data;

public class mData {

    public int id;
    public int userId;
    private String spare;

    public mData(int id, int userId){
        this.id = id;
        this.userId = userId;
    }

    public mData(int id, int userId, String spare){
        this.id = id;
        this.userId = userId;
        this.spare = spare;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }


    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }
}
