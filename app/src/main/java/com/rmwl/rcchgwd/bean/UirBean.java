package com.rmwl.rcchgwd.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/28.
 */

public class UirBean implements Serializable{
    private String url;
    private String noteurl;
    private String imageurl;

    public String getNoteurl() {
        return noteurl;
    }

    public void setNoteurl(String noteurl) {
        this.noteurl = noteurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
