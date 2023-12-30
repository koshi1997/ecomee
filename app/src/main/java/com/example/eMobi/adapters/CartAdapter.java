package com.example.eMobi.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eMobi.R;
import com.example.eMobi.databinding.ItemCartBinding;
import com.example.eMobi.databinding.QuantityDialogBinding;
import com.example.eMobi.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartviewHolder> {

    Context context;
    ArrayList<Product>products;
    CartListener cartListener;
    Cart cart;

    public interface CartListener{
        public void onQuantityChanged();
    }

    public CartAdapter(Context context,ArrayList<Product> products,CartListener cartListener){
        this.context = context;
        this.products = products;
        this.cartListener=cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartviewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartviewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.image);
        holder.binding.name.setText(product.getName());
        holder.binding.price.setText("LkR "+product.getPrice());
        holder.binding.quantity.setText(product.getQuantity()+ "item");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                QuantityDialogBinding quantityDialogBinding = QuantityDialogBinding.inflate(LayoutInflater.from(context));
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(quantityDialogBinding.getRoot())
                        .create();




                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                quantityDialogBinding.productName.setText(product.getName());
                quantityDialogBinding.stock.setText("Stock" + product.getStock());
                quantityDialogBinding.quantity.setText(String.valueOf(product.getQuantity()));
                int stock = product.getStock();

                quantityDialogBinding.minBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity = product.getQuantity();
                        if (quantity>1)
                            quantity--;
                        product.setQuantity(quantity);
                        quantityDialogBinding.quantity.setText(String.valueOf(quantity));

                        notifyDataSetChanged();
                        cart.updateItem(product,product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });
                quantityDialogBinding.plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity = product.getQuantity();
                        quantity++;

                        if (quantity>product.getStock()){
                            Toast.makeText(context,"Max stock available" +product.getStock(),Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            product.setQuantity(quantity);
                            quantityDialogBinding.quantity.setText(String.valueOf(quantity));
                        }
                        notifyDataSetChanged();
                        cart.updateItem(product,product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });

                quantityDialogBinding.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

//                        notifyDataSetChanged();
//                        cart.updateItem(product,product.getQuantity());
//                        cartListener.onQuantityChanged();
                    }
                });
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartviewHolder extends RecyclerView.ViewHolder{

        ItemCartBinding binding;

        public CartviewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCartBinding.bind(itemView);
        }
    }
}
