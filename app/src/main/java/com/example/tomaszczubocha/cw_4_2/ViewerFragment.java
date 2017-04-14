package com.example.tomaszczubocha.cw_4_2;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewerFragment extends Fragment {

    public TextView szczegoly;
    public View vieww;
    String mTekst = "Wybierz pozycję";

    public ViewerFragment() {
        // Required empty public constructor
    }

    public ViewerFragment(String tekst) {
        mTekst = tekst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewer, container, false);
        vieww = view;
        szczegoly = (TextView) view.findViewById(R.id.szczegoly);

        szczegoly.setText(mTekst);

        Button powrot = (Button) view.findViewById(R.id.powrot);
        powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = MainActivity.customTabListener._fragment.getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.kontener_na_liste1, new Przegladanie(), "TAG");
                transaction.addToBackStack("VIEVER");
                transaction.commit();
            }
        });

        int screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            powrot.setVisibility(View.GONE);
        }

        return view;

    }

}
