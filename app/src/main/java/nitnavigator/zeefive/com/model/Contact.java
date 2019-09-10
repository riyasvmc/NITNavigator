package nitnavigator.zeefive.com.model;

import android.util.Log;

import java.io.Serializable;

import nitnavigator.zeefive.com.activities.MainActivity;

/**
 * Created by Riyas V on 1/15/2016.
 */
public class Contact implements Serializable{

    public static final String CONTACT = "contact";
    private String mId;
    private String mTitle;
    private String mCategory;
    private String mPhone;
    private String mMobile;
    private String mCreatedAt;

    public Contact(){
    }

    public Contact(String id, String title, String category, String phone, String mobile, String createdAt) {
        mId = id;
        mTitle = title;
        mCategory = category;
        mPhone = phone;
        mMobile = mobile;
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
