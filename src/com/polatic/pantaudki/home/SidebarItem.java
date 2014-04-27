/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package com.polatic.pantaudki.home;

/**
 * Objects for sidebar contains text and images
 * 
 * @author yodi
 */
public class SidebarItem {
    private int imageId;

    private String title;

    private String textCart;

    private boolean isCounterVisible = false;

    public SidebarItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public SidebarItem() {
        // TODO Auto-generated constructor stub
    }

    public SidebarItem(int imageId, String title, String textCart, boolean isCounterVisible) {
        super();
        this.imageId = imageId;
        this.title = title;
        this.textCart = textCart;
        this.isCounterVisible = isCounterVisible;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

    public SidebarItem(int imageId, String title, String textCart) {
        super();
        this.imageId = imageId;
        this.title = title;
        this.textCart = textCart;
    }

    public String getTextCart() {
        return textCart;
    }

    public void setTextCart(String textCart) {
        this.textCart = textCart;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + "\n" + imageId;
    }
}
