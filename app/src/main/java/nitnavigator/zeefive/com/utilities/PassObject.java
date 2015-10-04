package nitnavigator.zeefive.com.utilities;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PassObject implements Serializable{

    private String title;
    private String category;
    private String subtitle_2;
    private String about;
    private int tag_color;
    private String phone;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String date;
    private String email;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubtitle_2() {
        return subtitle_2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubtitle_2(String subtitle_2) {
        this.subtitle_2 = subtitle_2;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getTag_color() {
        return tag_color;
    }

    public void setTag_color(int tag_color) {
        this.tag_color = tag_color;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
