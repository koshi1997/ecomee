package com.example.eMobi.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eMobi.adapters.CartAdapter;
import com.example.eMobi.databinding.ActivityCheckoutBinding;
import com.example.eMobi.model.Product;
import com.example.eMobi.utility.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    double totalPrice = 0;
    final int tax = 11;
    ProgressDialog progressDialog;
    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing....");


        products = new ArrayList<>();

        cart = TinyCartHelper.getCart();

        for(Map.Entry<Item,Integer> item : cart.getAllItemsWithQty().entrySet()){
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }

        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subTotal.setText(String.format("LKR %.2f",cart.getTotalPrice()));

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);

        binding.subTotal.setText(String.format("LKR %.2f",cart.getTotalPrice()));
        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.total.setText("LKR"+totalPrice);

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder();

            }
        });

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    void processOrder() {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject productOrder = new JSONObject();
        JSONObject dataObject = new JSONObject();
        try {
            productOrder.put("address", binding.addressBox.getText().toString());
            productOrder.put("buyer", binding.nameBox.getText().toString());
            productOrder.put("comment", binding.commentBox.getText().toString());
            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
            productOrder.put("last_update", Calendar.getInstance().getTimeInMillis());
            productOrder.put("date_ship", Calendar.getInstance().getTimeInMillis());
            productOrder.put("email", binding.emailBox.getText().toString());
            productOrder.put("phone", binding.phoneBox.getText().toString());
            productOrder.put("serial", "cab1a4e22ddd");
            productOrder.put("shipping", "");
            productOrder.put("shipping_location", "");
            productOrder.put("shipping_rate", "0.0");
            productOrder.put("status", "WAITING");
            productOrder.put("tax", tax);
            productOrder.put("total_fees", totalPrice);
            productOrder.put("payment_status", "WAITING");

            JSONArray product_order_detail = new JSONArray();
            for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
                Product product = (Product) item.getKey();
                int quantity = item.getValue();
                product.setQuantity(quantity);

                JSONObject productObj = new JSONObject();
                productObj.put("amount", quantity);
                productObj.put("price_item", product.getPrice());
                productObj.put("product_id", product.getId());
                productObj.put("product_name", product.getName());
                product_order_detail.put(productObj);
            }

            dataObject.put("product_order", productOrder);
            dataObject.put("product_order_detail", product_order_detail);
            Log.e("err",dataObject.toString());


        } catch (JSONException e) {}
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_ORDER_URL, dataObject, response -> {
            try {
                if (response.getString("status").equals("success")){
                    Toast.makeText(CheckoutActivity.this, "Success Order", Toast.LENGTH_SHORT).show();
                    String orderNumber = response.getJSONObject("data").getString("code");
                    new AlertDialog.Builder(CheckoutActivity.this)
                            .setTitle("Order Successful")
                            .setCancelable(false)
                            .setMessage("Your Order number is :"+ orderNumber)
                            .setPositiveButton("pay Now", (dialogInterface, i) -> {
                                Intent intent = new Intent(CheckoutActivity.this,PaymentActivity.class);
                                intent.putExtra("orderCode",orderNumber);
                                startActivity(intent);
                            }).show();
                }else {
                    new AlertDialog.Builder(CheckoutActivity.this)
                            .setTitle("Order Failed")
                            .setCancelable(false)
                            .setMessage("Something went wrong,please try")
                            .setPositiveButton("close", (dialogInterface, i) -> {

                            }).show();

                    Toast.makeText(CheckoutActivity.this, "Fail Order", Toast.LENGTH_SHORT).show();
                    Log.e("res",response.toString());
                }
                progressDialog.dismiss();
                Log.e("res",response.toString());

            } catch (JSONException e) {}

        }, error -> {

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String ,String> headers = new HashMap<>();
                headers.put("Security","secure_code");
                return headers;
            }
        };
        queue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}


//        try {
//            productOrder.put("address",binding.addressBox.getText().toString());
//            productOrder.put("buyer",binding.nameBox.getText().toString());
//            productOrder.put("comment",binding.commentBox.getText().toString());
//            productOrder.put("created_at", Calendar.getInstance().getTimeInMillis());
//            productOrder.put("date_ship",Calendar.getInstance().getTimeInMillis());
//            productOrder.put("email",binding.emailBox.getText().toString());
//            productOrder.put("phone",binding.phoneBox.getText().toString());
//            productOrder.put("serial","cab1a4e22ddd");
//            productOrder.put("shipping","");
//            productOrder.put("shipping_location","");
//            productOrder.put("shipping_rate","0.0");
//            productOrder.put("status","WAITING");
//            productOrder.put("tax",tax);
//            productOrder.put("total_fees",totalPrice);
//            productOrder.put("payment_status","WAITING");


//            JSONArray product_order_detail = new JSONArray();
//            for(Map.Entry<Item,Integer> item : cart.getAllItemsWithQty().entrySet()){
//                Product product = (Product) item.getKey();
//                int quantity = item.getValue();
//                product.setQuantity(quantity);
//
//                JSONObject productObj = new JSONObject();
//                productObj.put("amount",quantity);
//                productObj.put("product_item",product.getPrice());
//                productObj.put("product_id",product.getId());
//                productObj.put("product_name",product.getName());
//                product_order_detail.put(productObj);
//            }
//
//            dataObject.put("product_order",productOrder);
//            dataObject.put("product_order_detail",product_order_detail);
//
//        }catch (JSONException e){
//            throw new RuntimeException(e);
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.POST_ORDER_URL, dataObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if (response.getString("success").equals("success")){
//                        Toast.makeText(CheckoutActivity.this, "Success Order", Toast.LENGTH_SHORT).show();
//                        Log.e("res",response.toString());
//                    }else {
//                        Toast.makeText(CheckoutActivity.this, "Fail Order", Toast.LENGTH_SHORT).show();
//                        Log.e("res",response.toString());
//                    }
//                } catch (JSONException e) {}
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(request);
//    }

