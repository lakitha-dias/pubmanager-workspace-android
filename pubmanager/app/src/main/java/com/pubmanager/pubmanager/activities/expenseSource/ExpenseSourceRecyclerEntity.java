package com.pubmanager.pubmanager.activities.expenseSource;

public class ExpenseSourceRecyclerEntity {
    private String title;
    private boolean showMenu = false;
    private int image;

    public ExpenseSourceRecyclerEntity() {
    }

    public ExpenseSourceRecyclerEntity(String title, int image, boolean showMenu) {
        this.title = title;
        this.showMenu = showMenu;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isShowMenu() {
        return showMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
