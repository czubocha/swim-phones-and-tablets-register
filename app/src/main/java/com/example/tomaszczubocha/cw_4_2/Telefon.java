package com.example.tomaszczubocha.cw_4_2;

import java.io.Serializable;

/**
 * Created by Tomasz Czubocha on 11.04.2016.
 */
public class Telefon extends Urzadzenie implements Serializable{

    String pudelko = "puste";
    String sluchawki = "puste";
    String kabel = "puste";
    String ladowarka = "puste";
    String latarka = "puste";
    String simlock = "puste";
    String model = "puste";
    String ekran = "puste";

    public Telefon(String pudelko, String sluchawki, String kabel, String ladowarka, String latarka, String simlock, String model, String ekran) {
        this.pudelko = pudelko;
        this.sluchawki = sluchawki;
        this.kabel = kabel;
        this.ladowarka = ladowarka;
        this.latarka = latarka;
        this.simlock = simlock;
        this.model = model;
        this.ekran = ekran;
    }

    @Override
    public String toString() {
        return  model + "\n" +pudelko + "\n" + sluchawki + "\n" + kabel + "\n" + ladowarka + "\n" + latarka + "\n" + simlock + "\n" + ekran;
    }
}
