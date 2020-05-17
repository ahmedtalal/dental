package com.example.dentalcart.Pojo;

public class ReviewsModel {
    private String name , feedBack , photo  ;
    private int rating ;

    public ReviewsModel(String name, String feedBack, String photo ,  int rating) {
        this.name = name;
        this.feedBack = feedBack;
        this.photo = photo;
        this.rating = rating;
    }

    public ReviewsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
