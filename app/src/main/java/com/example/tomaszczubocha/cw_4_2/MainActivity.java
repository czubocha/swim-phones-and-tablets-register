package com.example.tomaszczubocha.cw_4_2;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Wrapper;

public class MainActivity extends AppCompatActivity implements Wprowadzanie.OnWyborOpcjiListener {

    public static Telefony telefony;
    public static Tablety tablety;
    FragmentTransaction transakcja;
    public static int position;
    public static CustomTabListener customTabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            savedInstanceState.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        CustomTabListener customTabListener1 = new CustomTabListener<Informacje>(this, "informacje", Informacje.class);
        CustomTabListener customTabListener2 = new CustomTabListener<Wprowadzanie>(this, "wprowadzanie", Wprowadzanie.class);
        CustomTabListener customTabListener3 = new CustomTabListener<Przeglad>(this, "przeglad", Przeglad.class);

        customTabListener = new CustomTabListener<Przeglad>(this,"przeglad", Przeglad.class);

        ActionBar.Tab tab1 = actionBar.newTab()
                .setText("Informacje")
                .setTabListener(new CustomTabListener<Informacje>(this, "informacje", Informacje.class));
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab()
                .setText("Wprowadzanie")
                .setTabListener(new CustomTabListener<Wprowadzanie>(this,"wprowadzanie", Wprowadzanie.class));
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar.newTab()
                .setText("Przeglądanie")
                .setTabListener(customTabListener);
        actionBar.addTab(tab3);

        Log.v("trol", String.valueOf(position));
        switch (position) {
            case 0:
                customTabListener1.onTabSelected(tab1, getSupportFragmentManager().beginTransaction());
                break;
            case 1:
                customTabListener2.onTabSelected(tab2, getSupportFragmentManager().beginTransaction());
                break;
            case 2:
                customTabListener3.onTabSelected(tab3, getSupportFragmentManager().beginTransaction());
                break;
        }

        telefony = new Telefony();
        tablety = new Tablety();

        transakcja = getSupportFragmentManager().beginTransaction();
        transakcja.add(R.id.kontener, telefony, "telefony");
        transakcja.detach(telefony);
        transakcja.add(R.id.kontener, tablety, "tablety");
        transakcja.detach(tablety);
        transakcja.commit();
    }

    @Override
    public void onWyborOpcji(int opcja) {
        FragmentTransaction transakcja = getSupportFragmentManager().beginTransaction();
        transakcja.detach(telefony);
        transakcja.detach(tablety);
        switch (opcja) {
            case R.id.radioButton1:
                transakcja.attach(telefony);
                break;
            case R.id.radioButton2:
                transakcja.attach(tablety);
                break;
        }
        transakcja.commit();
    }

    @Override    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.pudelko:
                if (checked)
                    Telefony.pudelko = "pudełko oryginalne";
                break;
            case R.id.zamiennepudelko:
                if (checked)
                    Telefony.pudelko = "pudełko zamienne";
                break;
            case R.id.brakpudelka:
                if (checked)
                    Telefony.pudelko = "brak pudełka";
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.sluchawki:
                if (checked)
                    Telefony.sluchawki = "słuchawki";
                else
                    Telefony.sluchawki = "brak słuchawek";
                break;
            case R.id.kabel:
                if (checked)
                    Telefony.kabel = "kabel";
                else
                    Telefony.kabel = "brak kabla";
                break;
            case R.id.ladowarka:
                if (checked)
                    Telefony.ladowarka = "ładowarka";
                else
                    Telefony.ladowarka = "brak ładowarki";
        }
    }


    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        Tablety.ram = ((RadioButton) view).getText().toString();
        //Log.v("lol", Tablety.ram);
    }

    public void onCheckboxClicked2(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.modem:
                if (checked)
                    Tablety.modem = "modem 3G/LTE";
                else
                    Tablety.modem = "brak modemu";
                Log.v("modem", Tablety.modem);
                break;
            case R.id.kabel_tab:
                if (checked)
                    Tablety.kabel = "kabel";
                else
                    Tablety.kabel = "brak kabla";
                Log.v("kabel", Tablety.kabel);
                break;
            case R.id.ladowarka_tab:
                if (checked)
                    Tablety.ladowarka = "ładowarka";
                else
                    Tablety.ladowarka = "brak ładowarki";
                Log.v("ladowarka", Tablety.ladowarka);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            FragmentTransaction transaction = MainActivity.customTabListener._fragment.getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.kontener_na_liste1, new Przegladanie());
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            super.onBackPressed();
        }
    }

}
