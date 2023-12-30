package com.example.eMobi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eMobi.databinding.ActivitySideNavBinding;

public class sideNavActivity extends AppCompatActivity {

    ActivitySideNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySideNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}