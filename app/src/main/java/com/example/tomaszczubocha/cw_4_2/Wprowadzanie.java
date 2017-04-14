package com.example.tomaszczubocha.cw_4_2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wprowadzanie extends Fragment implements RadioGroup.OnCheckedChangeListener{

    AppCompatActivity A1;
    OnWyborOpcjiListener sluchaczF1;

    public Wprowadzanie() {
        // Required empty public constructor
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                sluchaczF1.onWyborOpcji(checkedId);
                break;
            case R.id.radioButton2:
                sluchaczF1.onWyborOpcji(checkedId);
                break;
        }
    }

    public interface OnWyborOpcjiListener {
        public void onWyborOpcji(int opcja);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            A1 = (AppCompatActivity) context;
            sluchaczF1 = (OnWyborOpcjiListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(A1.toString() + " musi implementować OnWyborOpcjiListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((RadioGroup) getActivity().findViewById(R.id.radioGroup)).setOnCheckedChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wprowadzanie, container, false);
    }



}
