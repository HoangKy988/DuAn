package com.example.chubby.ui.saved;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chubby.DishAdapter;
import com.example.chubby.R;
import com.example.chubby.data.SavedRepository;
import com.example.chubby.model.Dish;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    private RecyclerView rv;
    private DishAdapter adapter;
    private SavedRepository repo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved, container, false);

        rv = v.findViewById(R.id.recycler);

        // ✅ Grid 3 cột
        GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        rv.setLayoutManager(glm);

        // ✅ Spacing giữa các item (12dp mỗi cạnh)
        int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        rv.addItemDecoration(new GridSpacingItemDecoration(3, spacing, true));

        // Adapter ban đầu rỗng, dùng layout grid
        adapter = new DishAdapter(new ArrayList<>(),
                dish -> {
                    // TODO: xử lý khi click món ăn (mở detail chẳng hạn)
                },
                R.layout.item_dish_card_grid); // ⚡ dùng layout grid
        rv.setAdapter(adapter);

        // Repository (Firestore + SQLite)
        repo = new SavedRepository(requireContext());

        // Load dữ liệu saved
        repo.getSavedDishes(dishes -> adapter.updateData(dishes));

        return v;
    }

    // ==================== Helper class ====================
    // Tạo khoảng cách đều trong GridLayout
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect,
                                   @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // cột của item

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) { // hàng đầu tiên
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // luôn có bottom
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
}
