package com.example.tomaszczubocha.cw_4_2;

import java.io.Serializable;

/**
 * Created by Tomasz Czubocha on 23.04.2016.
 */
public class Tablet extends Urzadzenie implements Serializable{

    String ram = "puste";
    String ladowarka = "puste";
    String modem = "puste";
    String kabel = "puste";
    String model = "puste";
    String system_op = "puste";
    String wyswietlacz = "puste";

    public Tablet(String ram, String ladowarka, String modem, String kabel, String model, String system_op, String wyswietlacz) {
        this.ram = ram;
        this.ladowarka = ladowarka;
        this.modem = modem;
        this.kabel = kabel;
        this.model = model;
        this.system_op = system_op;
        this.wyswietlacz = wyswietlacz;
    }

    @Override
    public String toString() {
        return  model + "\n" + modem + "\n" + kabel + "\n" + ladowarka + "\n"  + system_op + "\n" + ram + "\n" + wyswietlacz;
    }
}
