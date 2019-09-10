package nitnavigator.zeefive.com.model;

/**
 * Created by Riyas V on 1/15/2016.
 */
public class Calendar {

    private String mId;
    private String mTitle;
    private String mDescription;
    private String mClassCount;
    private String mDate;
    private String mCreatedAt;

    public Calendar(String id, String title, String description, String classCount, String date, String createdAt) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mClassCount = classCount;
        mDate = date;
        mCreatedAt = createdAt;
    }


    public String getClassCount() {
        return mClassCount;
    }

    public void setClassCount(String classCount) {
        mClassCount = classCount;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
