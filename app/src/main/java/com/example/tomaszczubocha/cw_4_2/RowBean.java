package com.example.tomaszczubocha.cw_4_2;

/**
 * Created by Tomasz Czubocha on 12.04.2016.
 */
public class RowBean {

    public int icon;
    public String title;
    public String ekran;
    public String pudelko;
    public int kolor;

    public RowBean(){

    }

    public RowBean(int icon, String title, String ekran, String pudelko, int kolor) {

        this.icon = icon;
        this.title = title;
        this.ekran = ekran;
        this.pudelko = pudelko;
        this.kolor = kolor;
    }
}
