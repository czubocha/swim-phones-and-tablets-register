package com.example.tomaszczubocha.cw_4_2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Informacje extends Fragment {

    private ArrayList<Urzadzenie> baza;

    public Informacje() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_informacje, container, false);

        baza = new java.util.ArrayList<Urzadzenie>();

        FileInputStream is;
        ObjectInputStream ois;

        boolean loop = true;
        try {
            is = getActivity().openFileInput("baza.lol");
            ois = new ObjectInputStream(is);
            while (loop) {

                Urzadzenie urzadzenie = (Urzadzenie) ois.readObject();
//                Log.v("urzadzenie", urzadzenie.toString());
                baza.add(urzadzenie);
            }
            is.close();
            ois.close();
        } catch (EOFException e) {
            loop = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        int telefony = 0;
        int tablety = 0;
        double suma_ekranow = 0;
        int androidy = 0;
        int ladowarki = 0;
        int simlocki = 0;
        double udzial_androida;
        double sredni_ekran;
        double procent_ladowarek;
        double procent_simlockow;

        for (Urzadzenie urzadzenie : baza)
            if (urzadzenie instanceof Telefon) {
                telefony++;
                suma_ekranow += Double.parseDouble(((Telefon) urzadzenie).ekran);
                if (((Telefon) urzadzenie).ladowarka.equals("ładowarka"))
                    ladowarki++;
                if (((Telefon) urzadzenie).simlock.equals("simlock"))
                    simlocki++;
            }
            else {
                tablety++;
                suma_ekranow += Double.parseDouble(((Tablet) urzadzenie).wyswietlacz);
                if (((Tablet) urzadzenie).system_op.equals("Android"))
                    androidy++;
                if (((Tablet) urzadzenie).ladowarka.equals("ładowarka"))
                    ladowarki++;
            }

        TextView tv_telefony = (TextView) view.findViewById(R.id.telefony);
        TextView tv_tablety = (TextView) view.findViewById(R.id.tablety);
        TextView tv_srednia = (TextView) view.findViewById(R.id.wielkosc_ekranu);
        TextView tv_androidy = (TextView) view.findViewById(R.id.android_w_tab);
        TextView tv_ladowarki = (TextView) view.findViewById(R.id.ladowarki);
        TextView tv_simlocki = (TextView) view.findViewById(R.id.simlocki);

        sredni_ekran = (suma_ekranow / baza.size());
        String result1 = String.format("%.2f", sredni_ekran);

        udzial_androida = (androidy / (double) tablety) * 100;
        String result2 = String.format("%.2f", udzial_androida);

        procent_ladowarek = (ladowarki / (double) baza.size()) * 100;
        String result3 = String.format("%.2f", procent_ladowarek);

        procent_simlockow = (simlocki / (double) telefony) * 100;
        String result4 = String.format("%.2f", procent_simlockow);

        if (baza.isEmpty()) {
            result1 = "0";
            result2 = "0";;
            result3 = "0";;
            result4 = "0";
        } else if (tablety == 0)
            result2 = "0";
        else if (telefony == 0)
            result4 = "0";

        tv_telefony.setText("Liczba telefonów w bazie: " + telefony);
        tv_tablety.setText("Liczba tabletów w bazie: " + tablety);
        tv_srednia.setText("Srednia wielkość ekranu urządzenia: " + result1 + " cali");
        tv_androidy.setText("Udział systemu Android w tabletach: " + result2 + "%");
        tv_ladowarki.setText("Procent urządzeń z ładowarkami: " + result3 + "%");
        tv_simlocki.setText("Procent simlocków w telefonach: " + result4 + "%");

                Button kasuj = (Button) view.findViewById(R.id.kasuj);
        kasuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getApplicationContext().deleteFile("baza.lol");
                try {
                    FileOutputStream os = getActivity().openFileOutput("baza.lol", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    os.close();
                    oos.close();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Błąd kasowania!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("informacje");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();

            }

        });

        return view;

    }

}
