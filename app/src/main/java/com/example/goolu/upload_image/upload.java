package com.example.goolu.upload_image;

import android.net.Uri;

public class upload {
    private String mName;
    private String mImageUrl;
    public upload(){

    }
    public upload(String name,String ImageUrl){
        if(name.trim().equals("")){
            name="No name";
        }
        mName=name;
        mImageUrl=ImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
