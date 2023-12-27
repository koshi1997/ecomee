package com.example.ecomee.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecomee.databinding.ActivitySideNavBinding;

public class sideNavActivity extends AppCompatActivity {

    ActivitySideNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySideNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}