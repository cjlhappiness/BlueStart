package data;

public class ListData extends mData{

    private String title;
    private String content;

    public ListData(int id, int userId, String title, String content){
        super(id, userId);
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
