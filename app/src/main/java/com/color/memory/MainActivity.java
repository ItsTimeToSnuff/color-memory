package com.color.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainMenuFragment menuFragment = new MainMenuFragment();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        loadFragment(menuFragment);
    }

    void loadFragment(Fragment fragment) {
        fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.cont, fragment);
        fragmentTransaction.commit();
    }
}