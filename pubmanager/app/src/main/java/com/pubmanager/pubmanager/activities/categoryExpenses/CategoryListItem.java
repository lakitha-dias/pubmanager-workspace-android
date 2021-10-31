package com.pubmanager.pubmanager.activities.categoryExpenses;

public class CategoryListItem {

    private String title;
    private int image;
    private String description;
    private String categoryId;

    public CategoryListItem(String title, String description, String categoryId) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
