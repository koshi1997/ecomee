package com.example.ecomee.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomee.adapters.CategoryAdapter;
import com.example.ecomee.adapters.ProductAdapter;
import com.example.ecomee.databinding.ActivityMainBinding;
import com.example.ecomee.model.Category;
import com.example.ecomee.model.Product;
import com.example.ecomee.utility.Constants;
import com.mancj.materialsearchbar.MaterialSearchBar;

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

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query",text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        initCategories();
        initProducts();
        initSlider();
    }

    private void initSlider() {
        // Assuming the image is in res/drawable and named baby_picture.jpg
//        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.men2, "T-Shirt New"));
//        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.sale_banner, "sale"));
//        binding.carousel.addData(new CarouselItem("android.resource://" + getPackageName() + "/" + R.drawable.men1, "T shirt"));
        getRecentOffers();
    }


    void initCategories() {
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);

        getCategories();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoryAdapter);
    }

    void getCategories() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_CATEGORIES_URL, response -> {
            try {
                JSONObject mainObj = new JSONObject(response);
                if (mainObj.getString("status").equals("success")) {
                    JSONArray categoriesArray = mainObj.getJSONArray("categories");
                    for (int i = 0; i < categoriesArray.length(); i++) {
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
                } else {

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }, error -> {});
        queue.add(request);
    }

    void initProducts() {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);

        getRecentProducts();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setAdapter(productAdapter);
    }

    void getRecentProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCTS_URL + "?count=8";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getString("status").equals("success")) {
                    JSONArray productsArray = object.getJSONArray("products");
                    for (int i = 0; i < productsArray.length(); i++) {
                        JSONObject childObj = productsArray.getJSONObject(i);

                        // Check if the "discount" key exists
                        double discount = childObj.has("discount") ? childObj.getDouble("discount") : 0.0;

                        Product product = new Product(
                                childObj.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL + childObj.getString("image"),
                                childObj.getString("status"),
                                childObj.getDouble("price"),
                                discount,  // Use the value or a default (e.g., 0.0) if the key is not present
                                childObj.getInt("stock"),
                                childObj.getInt("id")
                        );

                        products.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                } else {
                    // Handle the case when the status is not "success"
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // Handle JSON parsing error
            }
        }, error -> {
            error.printStackTrace();
            // Handle Volley error
        });

        queue.add(request);
    }

//    void getProducts() {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_PRODUCTS_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject mainObj = new JSONObject(response);
//                    if (mainObj.getString("status").equals("success")) {
//                        JSONArray productsArray = mainObj.getJSONArray("products");
//                        for (int i = 0; i < productsArray.length(); i++) {
//                            JSONObject object = productsArray.getJSONObject(i);
//                            Product product = new Product(
//                                    object.getString("name"),
//                                    object.getString("image"),
//                                    object.getString("status"),
//                                    object.getDouble("price"),
//                                    object.getDouble("discount"),
//                                    object.getInt("stock"),
//                                    object.getInt("id")
//                            );
//                            products.add(product);
//                        }
//                        categoryAdapter.notifyDataSetChanged();
//                    } else {
//
//                    }
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//
//            }
//        }, error -> {
//
//        });
//        queue.add(request);
//    }

    void getRecentOffers(){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.GET_OFFERS_URL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getString("status").equals("success")) {
                    JSONArray offerArray = object.getJSONArray("news_infos");
                    for (int i = 0; i < offerArray.length(); i++) {
                        JSONObject childObj = offerArray.getJSONObject(i);
                        binding.carousel.addData(
                                new CarouselItem(
                                      Constants.NEWS_IMAGE_URL + childObj.getString("image"),
                                        childObj.getString("title")
                                )
                        );
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {});
        queue.add(request);
    }

}