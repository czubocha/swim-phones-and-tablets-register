package com.example.tomaszczubocha.cw_4_2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tablety extends Fragment {

    public static String[] system = {"Android", "Windows", "iOS"};

    public static String ram;
    public static String ladowarka;
    public static String modem;
    public static String kabel;
    String model;
    public static String system_op;
    String wyswietlacz;

    FileOutputStream os;
    AppendingObjectOutputStream oos;
    FileInputStream is;
    ObjectInputStream ois;

    public Tablety() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tablety, container, false);

        ram = "";
        ladowarka = "brak ladowarki";
        modem = "brak modemu";
        kabel = "brak kabla";
        model = "";
        system_op = "żaden";
        wyswietlacz = "";

        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setProgress(70);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(120);
        final TextView seekBarValue = (TextView) view.findViewById(R.id.cale);
        seekBarValue.setText("7.0");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(progress);
                String value2 = "puste";
                if (value.length() == 1) {
                    value2 = "0." + value.charAt(0);
                    seekBarValue.setText(value2);
                } else if (value.length() == 3) {
                    value2 = value.charAt(0) + "" + value.charAt(1) + "." + value.charAt(2);
                    seekBarValue.setText(value2);
                } else {
                    value2 = value.charAt(0) + "." + value.charAt(1);
                    seekBarValue.setText(value2);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_tab);
        ArrayAdapter<String> adap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, system);
        spinner.setAdapter(adap);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                system_op = system[position];
                Log.v("system", system_op);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button zapisz = (Button) view.findViewById(R.id.zapisz_tab);
        zapisz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    os = getActivity().openFileOutput("baza.lol", Context.MODE_APPEND);
                    oos = new AppendingObjectOutputStream(os);
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Błąd inicjalizacji streamów", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                EditText model_edit = (EditText) view.findViewById(R.id.model_tab);
                model = model_edit.getText().toString();
                wyswietlacz = seekBarValue.getText().toString();

                Tablet tablet = new Tablet(ram, ladowarka, modem, kabel, model, system_op, wyswietlacz);
//                Toast.makeText(getActivity().getApplicationContext(), tablet.toString(), Toast.LENGTH_LONG).show();
                if (model.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Wprowadź nazwę modelu!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        oos.writeObject(tablet);
                        Toast.makeText(getActivity().getApplicationContext(), "Zapisano " + model + "!", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity().getApplicationContext(), "Wielkość pliku: " + os.getChannel().size(), Toast.LENGTH_SHORT).show();
                        os.close();
                        oos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Błąd", Toast.LENGTH_SHORT).show();
                    }

                    Tablety tablety = new Tablety();
                    Fragment frg = null;
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    MainActivity.tablety = tablety;
                    ft.replace(R.id.kontener, tablety);
                    ft.commit();

                }
            }
        });

        return view;
    }

    class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // do not write a header, but reset:
            // this line added after another question
            // showed a problem with the original
            reset();
        }

    }

}
