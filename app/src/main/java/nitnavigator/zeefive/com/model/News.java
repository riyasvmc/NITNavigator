package nitnavigator.zeefive.com.model;

/**
 * Created by Riyas V on 1/15/2016.
 */
public class News {
    private String mId;
    private String mTitle;
    private String mLink;
    private String mType;
    private String mCreatedAt;

    public News(String id, String title, String link, String type, String createdAt) {
        mId = id;
        mTitle = title;
        mLink = link;
        mType = type;
        mCreatedAt = createdAt;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
