package com.example.ecomee.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.GridLayout;

import com.example.ecomee.R;
import com.example.ecomee.adapters.CategoryAdapter;
import com.example.ecomee.databinding.ActivityMainBinding;
import com.example.ecomee.model.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categories = new ArrayList<>();
        categories.add(new Category("baby","https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/How_to_use_icon.svg/2214px-How_to_use_icon.svg.png","#fe438e","Groomer Baby Clothing | Babies 0-24 Months | Preemie Baby Clothing",1));
        categories.add(new Category("baby","https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/How_to_use_icon.svg/2214px-How_to_use_icon.svg.png","#18ab4e","Groomer Baby Clothing | Babies 0-24 Months | Preemie Baby Clothing",1));
        categories.add(new Category("baby","https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/How_to_use_icon.svg/2214px-How_to_use_icon.svg.png","#fb0504","Groomer Baby Clothing | Babies 0-24 Months | Preemie Baby Clothing",1));
        categories.add(new Category("baby","https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/How_to_use_icon.svg/2214px-How_to_use_icon.svg.png","#4186ff","Groomer Baby Clothing | Babies 0-24 Months | Preemie Baby Clothing",1));
        categories.add(new Category("baby","https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/How_to_use_icon.svg/2214px-How_to_use_icon.svg.png","#BF360C","Groomer Baby Clothing | Babies 0-24 Months | Preemie Baby Clothing",1));
        categoryAdapter = new CategoryAdapter(this,categories);


        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }
}