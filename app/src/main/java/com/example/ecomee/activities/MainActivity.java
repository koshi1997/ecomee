package com.example.ecomee.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomee.R;
import com.example.ecomee.adapters.CategoryAdapter;
import com.example.ecomee.adapters.ProductAdapter;
import com.example.ecomee.databinding.ActivityMainBinding;
import com.example.ecomee.model.Category;
import com.example.ecomee.model.Product;
import com.example.ecomee.utility.Constants;

import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        categoryAdapter = new CategoryAdapter(this,categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    if (mainObj.getString("status").equals("success")){
                        JSONArray categoriesArray = mainObj.getJSONArray("categories");
                        for (int i = 0; i < categoriesArray.length(); i++){
                            JSONObject object = categoriesArray.getJSONObject(i);
                            Category category = new Category(
                                    object.getString("name"),
                                    Constants.CATEGORIES_IMAGE_URL + object.getString("icon"),
                                    object.getString("color"),
                                    object.getString("brief"),
                                    object.getInt("id")
                            );
                            categories.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }else {

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
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