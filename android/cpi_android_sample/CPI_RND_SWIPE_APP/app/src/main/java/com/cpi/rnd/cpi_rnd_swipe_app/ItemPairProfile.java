package com.cpi.rnd.cpi_rnd_swipe_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPairProfile {

    @SerializedName("row_id")
    @Expose
    private int row_id;

    // For Search Item
    @SerializedName("s_title")
    @Expose
    private String s_title;

    @SerializedName("s_image_url")
    @Expose
    private String s_image_url;

    @SerializedName("s_price")
    @Expose
    private String s_price;


    // For Result Item
    @SerializedName("r_title")
    @Expose
    private String r_title;

    @SerializedName("r_image_url")
    @Expose
    private String r_image_url;

    @SerializedName("r_price")
    @Expose
    private String r_price;

    @SerializedName("s_prod_url")
    @Expose
    private String s_prod_url;

    public String getS_prod_url() {
        return s_prod_url;
    }

    public void setS_prod_url(String s_prod_url) {
        this.s_prod_url = s_prod_url;
    }

    public String getR_prod_url() {
        return r_prod_url;
    }

    public void setR_prod_url(String r_prod_url) {
        this.r_prod_url = r_prod_url;
    }

    @SerializedName("r_prod_url")
    @Expose

    private String r_prod_url;


    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public String getS_image_url() {
        return s_image_url;
    }

    public void setS_image_url(String s_image_url) {
        this.s_image_url = s_image_url;
    }

    public String getS_price() {
        return s_price;
    }

    public void setS_price(String s_price) {
        this.s_price = s_price;
    }

    public String getR_title() {
        return r_title;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

    public String getR_image_url() {
        return r_image_url;
    }

    public void setR_image_url(String r_image_url) {
        this.r_image_url = r_image_url;
    }

    public String getR_price() {
        return r_price;
    }

    public void setR_price(String r_price) {
        this.r_price = r_price;
    }
}
