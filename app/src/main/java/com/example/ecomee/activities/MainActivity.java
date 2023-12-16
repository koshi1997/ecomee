package com.example.ecomee.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.ecomee.R;
import com.example.ecomee.adapters.CategoryAdapter;
import com.example.ecomee.adapters.ProductAdapter;
import com.example.ecomee.databinding.ActivityMainBinding;
import com.example.ecomee.model.Category;
import com.example.ecomee.model.Product;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categories;

    ProductAdapter productAdapter;

    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategories();
        initProducts();
        initSlider();
    }

    private void initSlider() {
        // Assuming the image is in res/drawable and named baby_picture.jpg
        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.men2,"T-Shirt New"));
        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.sale_banner,"sale"));
        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.men1,"T shirt"));
    }


    void initCategories(){
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
    void  initProducts(){
        products = new ArrayList<>();
        products.add(new Product("baby","https://thumbs.dreamstime.com/b/top-view-fashion-trendy-look-kids-clothes-103930087.jpg","READY STOCK",52,54,20,1));
        products.add(new Product("baby","https://thumbs.dreamstime.com/b/top-view-fashion-trendy-look-kids-clothes-103930087.jpg","READY STOCK",52,20,20,2));
        products.add(new Product("baby","https://thumbs.dreamstime.com/b/top-view-fashion-trendy-look-kids-clothes-103930087.jpg","READY STOCK",52,30,20,3));
        products.add(new Product("baby","https://thumbs.dreamstime.com/b/top-view-fashion-trendy-look-kids-clothes-103930087.jpg","READY STOCK",52,10,20,4));
        products.add(new Product("baby","https://thumbs.dreamstime.com/b/top-view-fashion-trendy-look-kids-clothes-103930087.jpg","READY STOCK",52,15,20,5));
        productAdapter = new ProductAdapter(this,products);


        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

}