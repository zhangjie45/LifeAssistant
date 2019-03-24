package com.example.pc.lifeassistant.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;


/**
 * Created by pc on 2019/3/24.
 */
@AVClassName("Remind")
public class RemindInfo extends AVObject {
    private String addedpersonaccount;
    private String content;
    private Date date;

    public String getAddedpersonaccount() {
        return addedpersonaccount;
    }

    public void setAddedpersonaccount(String addedpersonaccount) {
        this.addedpersonaccount = addedpersonaccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
