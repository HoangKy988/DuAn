package com.example.chubby;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.chubby.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.VH> {

    public interface OnDishClickListener {
        void onDishClick(Dish dish);
    }

    private final OnDishClickListener listener;
    private final @LayoutRes int itemLayoutRes;
    private final List<Dish> data;

    // Dùng mặc định layout của Home (item_dish_card)
    public DishAdapter(List<Dish> data, OnDishClickListener listener) {
        this(data, listener, R.layout.item_dish_card);
    }

    // Cho phép truyền layout tuỳ theo màn hình (Home hay Saved)
    public DishAdapter(List<Dish> data, OnDishClickListener listener, @LayoutRes int itemLayoutRes) {
        this.data = (data != null) ? new ArrayList<>(data) : new ArrayList<>();
        this.listener = listener;
        this.itemLayoutRes = itemLayoutRes;
        setHasStableIds(false);
    }

    static class VH extends RecyclerView.ViewHolder {
        final ImageView img;
        final TextView name;
        VH(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgDish);
            name = v.findViewById(R.id.tvName);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(itemLayoutRes, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        final Dish d = data.get(position);

        // Tên món ăn
        h.name.setText(d.getName());

        // ⚡ Hiển thị ảnh: ưu tiên imageData (BLOB), sau đó mới đến imageResId
        if (d.getImageData() != null && d.getImageData().length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(d.getImageData(), 0, d.getImageData().length);
            h.img.setImageBitmap(bmp);
        } else if (d.getImageResId() != 0) {
            Glide.with(h.img.getContext())
                    .load(d.getImageResId())
                    .transform(new RoundedCorners(24))
                    .into(h.img);
        } else {
            h.img.setImageResource(R.drawable.ic_avatar_placeholder); // fallback
        }

        // Sự kiện click item
        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDishClick(d);

            Intent intent = new Intent(v.getContext(), DishDetailActivity.class);
            intent.putExtra("dish_id", d.getId()); // truyền id để truy vấn SQLite sau này
            intent.putExtra("dish_name", d.getName());
            intent.putExtra("dish_description", d.getDescription());
            intent.putExtra("dish_ingredients", d.getIngredients());
            intent.putExtra("dish_recipe", d.getRecipe());
            intent.putExtra("dish_cook_time", d.getCookTime());

            // ⚡ Truyền thêm ảnh
            if (d.getImageData() != null && d.getImageData().length > 0) {
                intent.putExtra("dish_image_blob", d.getImageData());
            } else {
                intent.putExtra("dish_image_res", d.getImageResId());
            }

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // ⚡ Hàm này để dùng mỗi khi cần thay đổi danh sách
    public void updateData(List<Dish> newData) {
        this.data.clear();
        if (newData != null) {
            this.data.addAll(newData);
        }
        notifyDataSetChanged();
    }
}
