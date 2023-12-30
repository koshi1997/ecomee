package com.example.eMobi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eMobi.databinding.ActivityHelpBinding;

public class helpActivity extends AppCompatActivity {

    ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}