package data;

public class FirstData extends ListData{

    private String imgUrl;

    public FirstData(int id, int userId, String title, String content, String imgUrl){
        super(id, userId, title, content);
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
