package nitnavigator.zeefive.com.data;

public class News {
    String id;
    String title;
    String link;
    String md5;
    String created_at;
    String type;

    public News(String id, String title, String link, String md5, String created_at, String type) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.md5 = md5;
        this.created_at = created_at;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
