package com.example.tomaszczubocha.cw_4_2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Przeglad extends Fragment {

    public static View kontener;
    public static View viewPrzeglad;
    public static Przegladanie przegladanie;
    public Przeglad przeglad = this;
    public ViewerFragment viewerFragment1;
    public static ViewerFragment viewerFragment2;
    public View view5;
    public Przeglad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.view, container, false);
        viewPrzeglad = view;
        view5 = view;

        viewerFragment1 = new ViewerFragment();
        viewerFragment2 = new ViewerFragment();

        View lista1 = view.findViewById(R.id.kontener_na_liste1);
        if (lista1 == null) {
            getChildFragmentManager().beginTransaction().add(R.id.kontener_na_liste2, new Przegladanie(), "lista2").commit();
            getChildFragmentManager().beginTransaction().add(R.id.kontener_na_szczegoly, viewerFragment1, "szczegoly").commit();
        } else {
            przegladanie = new Przegladanie();
            getChildFragmentManager().beginTransaction().add(R.id.kontener_na_liste1, przegladanie, "przegladanie").commit();
    }

        kontener = view.findViewById(R.id.kontener_na_liste1);

        return  view;
    }

}
