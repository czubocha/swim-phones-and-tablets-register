package com.example.tomaszczubocha.cw_4_2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Telefony extends Fragment {


    public Telefony() {
        // Required empty public constructor
    }

    public static String pudelko = "brak pudełka";
    public static String sluchawki = "brak słuchawek";
    public static String kabel = "brak kabla";
    public static String ladowarka = "brak ładowarki";
    String latarka = "brak latarki";
    String simlock = "brak simlocka";
    String model = "brak modelu";
    String ekran = "";
    SeekBar seekBar;
    Switch toggle;
    ToggleButton toggle2;

    FileOutputStream os;
    AppendingObjectOutputStream oos;
    FileInputStream is;
    ObjectInputStream ois;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_telefony, container, false);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        seekBar.setProgress(40);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(70);
        final TextView seekBarValue = (TextView) view.findViewById(R.id.cale);
        seekBarValue.setText("4.0");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(progress);
                if (value.length() == 1) {
                    String value2 = "0." + value.charAt(0);
                    seekBarValue.setText(value2);
                } else {
                    String value2 = value.charAt(0) + "." + value.charAt(1);
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

        toggle = (Switch) view.findViewById(R.id.latarka);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    latarka = "latarka";
                } else {
                    latarka = "brak latarki";
                }
            }
        });

        toggle2 = (ToggleButton) view.findViewById(R.id.simlock);
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    simlock = "simlock";
                } else {
                    simlock = "brak simlocka";
                }
            }
        });


        Button mButton = (Button) view.findViewById(R.id.zapisywanie);
        final EditText mEdit = (EditText) view.findViewById(R.id.numer);
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        try {
                            os = getActivity().openFileOutput("baza.lol", Context.MODE_APPEND);
                            oos = new AppendingObjectOutputStream(os);
                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Błąd inicjalizacji streamów", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        ekran = seekBarValue.getText().toString();
                        model = mEdit.getText().toString();
                        if (model.equals("")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Wprowadź nazwę modelu!", Toast.LENGTH_SHORT).show();
                        } else {
                            Telefon telefon = new Telefon(pudelko, sluchawki, kabel, ladowarka, latarka, simlock, model, ekran);
//                        Toast.makeText(getActivity().getApplicationContext(), telefon.toString(), Toast.LENGTH_LONG).show();
                            try {
                                oos.writeObject(telefon);
                                Toast.makeText(getActivity().getApplicationContext(), "Zapisano " + model + "!", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity().getApplicationContext(), "Wielkość pliku: " + os.getChannel().size(), Toast.LENGTH_SHORT).show();
                                os.close();
                                oos.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity().getApplicationContext(), "Błąd", Toast.LENGTH_SHORT).show();
                            }


//                        mEdit.clearComposingText();
//                        mEdit.setText("");
//                        seekBar.setProgress(40);
//                        toggle.setChecked(false);
//                        toggle2.setChecked(false);
//                        RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.pudelko);
//                        radioButton1.setChecked(false);
//                        RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.brakpudelka);
//                        radioButton2.setChecked(false);
//                        RadioButton radioButton3 = (RadioButton) view.findViewById(R.id.zamiennepudelko);
//                        radioButton3.setChecked(false);
                            Telefony telefony = new Telefony();
                            Fragment frg = null;
                            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            MainActivity.telefony = telefony;
                            ft.replace(R.id.kontener, telefony);
                            ft.commit();

                        }
                    }
                });

        return view;

    }


}
