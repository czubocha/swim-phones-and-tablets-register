package com.example.tomaszczubocha.cw_4_2;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
public class Przegladanie extends Fragment {

    private ListView listView;
    private ArrayList<Urzadzenie> baza = new java.util.ArrayList<Urzadzenie>();
    //    DialogInterface.OnClickListener przegladanie = this;

    public Przegladanie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_przegladanie, container, false);

        baza.clear();

        FileInputStream is;
        ObjectInputStream ois;
        boolean loop = true;
        try {
            is = getActivity().openFileInput("baza.lol");
            ois = new ObjectInputStream(is);
            while (loop) {

                Urzadzenie urzadzenie = (Urzadzenie) ois.readObject();
                Log.v("urzadzenie", urzadzenie.toString());
                baza.add(urzadzenie);
            }
            is.close();
            ois.close();
        } catch (EOFException e) {
            loop = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        int ikona = 0;
//        int kolor;
        final ArrayList<RowBean> RowBean_data = new ArrayList<RowBean>();

        for (Urzadzenie urzadzenie : baza) {
            if (urzadzenie instanceof Telefon) {
                Telefon telefon = (Telefon) urzadzenie;
                ikona = R.drawable.nokia;
//                if (telefon.simlock.equals("simlock"))
//                    kolor = Color.RED;
//                else
//                    kolor = Color.WHITE;

                RowBean_data.add(new RowBean(ikona, telefon.model, telefon.ekran, telefon.pudelko, Color.TRANSPARENT));
            } else {
                Tablet tablet = (Tablet) urzadzenie;
                ikona = R.drawable.phablet;
//                kolor = Color.WHITE;
                RowBean_data.add(new RowBean(ikona, tablet.model, tablet.wyswietlacz, tablet.system_op, Color.TRANSPARENT));
            }
        }

//        RowBean_data.add(new RowBean(R.drawable.iphone5, "Test1", "5.5", "pudełko"));
//        RowBean_data.add(new RowBean(android.R.drawable.alert_dark_frame, "Test1", "2.5", "brak pudełka"));
//        RowBean_data.add(new RowBean(android.R.drawable.alert_light_frame, "Test1", "3.5", "pudełko"));

        RowAdapter adapter = new RowAdapter(getActivity(), R.layout.custom_row, RowBean_data);

        listView = (ListView) view.findViewById(R.id.listView2);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {

                view.setSelected(true);

                int screenOrientation = getResources().getConfiguration().orientation;

                if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {

                    String tekst;
                    Urzadzenie urzadzenie = baza.get(position);
                    if (urzadzenie instanceof Telefon) {
                        Telefon telefon = (Telefon) urzadzenie;
                        tekst = "model: " + telefon.model + "\n" + "ekran " + telefon.ekran + " cali\n" + telefon.kabel + "\n" + telefon.ladowarka + "\n" + telefon.latarka + "\n" + telefon.pudelko + "\n" + telefon.simlock + "\n" + telefon.sluchawki;
                    } else {
                        Tablet tablet = (Tablet) urzadzenie;
                        tekst= "model: " + tablet.model + "\n" + "ekran " + tablet.wyswietlacz + " cali\n" + tablet.kabel + "\n" + tablet.ladowarka + "\n" + tablet.modem + "\n" + tablet.system_op + "\n" + tablet.ram;
                    }

                    ViewerFragment vf = new ViewerFragment(tekst);
                    FragmentTransaction transaction = MainActivity.customTabListener._fragment.getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.kontener_na_szczegoly, vf, "VIEWER");
                    transaction.addToBackStack("TAG");
                    transaction.commit();

                } else {
//                        Log.v("lol",  String.valueOf(Przeglad.viewPrzeglad.findViewById(R.id.kontener_na_liste1).getId()));
                    String tekst;
                    Urzadzenie urzadzenie = baza.get(position);
                    if (urzadzenie instanceof Telefon) {
                        Telefon telefon = (Telefon) urzadzenie;
                        tekst = "model: " + telefon.model + "\n" + "ekran " + telefon.ekran + " cali\n" + telefon.kabel + "\n" + telefon.ladowarka + "\n" + telefon.latarka + "\n" + telefon.pudelko + "\n" + telefon.simlock + "\n" + telefon.sluchawki;
                    } else {
                        Tablet tablet = (Tablet) urzadzenie;
                        tekst= "model: " + tablet.model + "\n" + "ekran " + tablet.wyswietlacz + " cali\n" + tablet.kabel + "\n" + tablet.ladowarka + "\n" + tablet.modem + "\n" + tablet.system_op + "\n" + tablet.ram;
                    }

                    ViewerFragment vf = new ViewerFragment(tekst);
                    FragmentTransaction transaction = MainActivity.customTabListener._fragment.getChildFragmentManager().beginTransaction();
                    transaction.add(vf, "VIEWER");
                    transaction.replace(R.id.kontener_na_liste1, vf);
                    transaction.addToBackStack("VIEVER");
                    transaction.commit();

//                    TextView szczegoly = (TextView) view2.findViewById(R.id.szczegoly);

                }

            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    final int position, long id) {
//                Urzadzenie urzadzenie = baza.get(position);
//                OnUrzSelectedListener.onUrzSelected(urzadzenie);


//                if (urzadzenie instanceof Telefon) {
//                    Telefon telefon = (Telefon) urzadzenie;
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle(telefon.model)
//                            .setMessage("ekran " + telefon.ekran + " cali\n" + telefon.kabel + "\n" + telefon.ladowarka + "\n" + telefon.latarka + "\n" + telefon.pudelko + "\n" + telefon.simlock + "\n" + telefon.sluchawki)
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                }
//                            })
//                            .setNegativeButton("Usuń", przegladanie)
//                            .setIcon(RowBean_data.get(position).icon)
//                            .show();
//                } else {
//                    Tablet tablet = (Tablet) urzadzenie;
//                    new AlertDialog.Builder(getActivity())
//                            .setTitle(tablet.model)
//                            .setMessage("ekran " + tablet.wyswietlacz + " cali\n" + tablet.kabel + "\n" + tablet.ladowarka + "\n" + tablet.modem + "\n" + tablet.system_op + "\n" + tablet.ram)
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                }
//                            })
//                            .setNegativeButton("Usuń", przegladanie)
//                            .setIcon(RowBean_data.get(position).icon)
//                            .show();
//                }
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                baza.remove(position);

                getContext().deleteFile("baza.lol");
                try {
                    FileOutputStream os = getActivity().openFileOutput("baza.lol", Context.MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    os.close();
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FileOutputStream os = null;
                AppendingObjectOutputStream oos = null;

                try {
                    os = getActivity().openFileOutput("baza.lol", Context.MODE_APPEND);
                    oos = new AppendingObjectOutputStream(os);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Błąd przy inicjalizacji strumieni!", Toast.LENGTH_SHORT).show();
                }

                Log.v("rozmiar bazy po usun", String.valueOf(baza.size()));
                try {
                    for (Object urzadzenie : baza) {
                        oos.writeObject(urzadzenie);
                    }
                    os.close();
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Błąd przy wpisywaniu od nowa!", Toast.LENGTH_SHORT).show();
                }


                Fragment frg = null;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("przeglad");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();

                Toast.makeText(getContext(), "Usunięto!", Toast.LENGTH_SHORT).show();

                return true;

            }
        });

        return view;
    }

//    @Override
//    public void onClick(DialogInterface dialog, int which) {
//
//        baza.remove(position_to_delete);
//
//        getContext().deleteFile("baza.lol");
//        try {
//            FileOutputStream os = getActivity().openFileOutput("baza.lol", Context.MODE_PRIVATE);
//            ObjectOutputStream oos = new ObjectOutputStream(os);
//            os.close();
//            oos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FileOutputStream os = null;
//        AppendingObjectOutputStream oos = null;
//
//        try {
//            os = getActivity().openFileOutput("baza.lol", Context.MODE_APPEND);
//            oos = new AppendingObjectOutputStream(os);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity().getApplicationContext(), "Błąd przy inicjalizacji strumieni!", Toast.LENGTH_SHORT).show();
//        }
//
//        Log.v("rozmiar bazy po usun", String.valueOf(baza.size()));
//        try {
//            for (Object urzadzenie : baza) {
//                oos.writeObject(urzadzenie);
//            }
//            os.close();
//            oos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity().getApplicationContext(), "Błąd przy wpisywaniu od nowa!", Toast.LENGTH_SHORT).show();
//        }
//
//
//        Fragment frg = null;
//        frg = getActivity().getSupportFragmentManager().findFragmentByTag("przegladanie");
//        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
//    }

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
