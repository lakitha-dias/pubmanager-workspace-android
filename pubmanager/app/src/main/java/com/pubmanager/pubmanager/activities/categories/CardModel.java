package com.pubmanager.pubmanager.activities.categories;


public class CardModel {
    private int imageId;
    private String title;
    private String subtitle;

    public CardModel(int imageId, String title, String subtitle) {
        this.imageId = imageId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getImage() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
