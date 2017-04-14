package com.example.tomaszczubocha.cw_4_2;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;

/**
 * Created by Tomasz Czubocha on 21.04.2016.
 */
public class CustomTabListener<T extends Fragment> implements ActionBar.TabListener{

    public Fragment _fragment;
    private final Activity _activity;
    private final String _tag;
    private final Class<T> _class;

    public CustomTabListener(Activity activity, String tag, Class<T> tClass) {

        _activity = activity;
        _tag = tag;
        _class = tClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        MainActivity.position = tab.getPosition();
        Log.v("lol", String.valueOf(MainActivity.position));

        if (_fragment == null) {
            _fragment = Fragment.instantiate(_activity, _class.getName());
            ft.add(android.R.id.content, _fragment, _tag);
        } else {
            ft.attach(_fragment);
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        if (_fragment!= null) {
            ft.detach(_fragment);
        }


    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {



    }
}
