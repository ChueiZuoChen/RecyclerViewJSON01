package com.example.cz.recyclerviewexample;

public class ExampleItem {
    private String mImageUrl;
    private String mCreatorName;
    private int mLikes;

    public ExampleItem (String ImageUrl, String CreatorName, int Likes){
        this.mImageUrl = ImageUrl;
        this.mCreatorName = CreatorName;
        this.mLikes = Likes;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public String getCreator(){
        return mCreatorName;
    }

    public int getLikes(){
        return mLikes;
    }
}
