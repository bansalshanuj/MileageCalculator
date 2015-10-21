package com.development.shanujbansal.mileagecalc.models;

/**
 * Created by shanuj.bansal on 10/20/2015.
 */
public class NavigationDrawerItem {

    private String title;
    private int icon;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
