package com.samsdk.javamvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samsdk.javamvvm.R;
import com.samsdk.javamvvm.database.Category;
import com.samsdk.javamvvm.databinding.ItemLayoutBinding;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private HandleCategoryClick clickListener;

    public CategoryListAdapter(Context context, HandleCategoryClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
        Category category = this.categoryList.get(position);
        holder.binding.tvCategoryName.setText(category.categoryName);

        holder.itemView.setOnClickListener(view -> {
            clickListener.itemClick(category);
        });

        holder.binding.editCategory.setOnClickListener(view -> {
            clickListener.editItem(category);
        });
        holder.binding.removeCategory.setOnClickListener(view -> {
            clickListener.removeItem(category);
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList == null || categoryList.size() == 0)
            return 0;
        else
            return categoryList.size();
    }

    public static class CategoryListViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding binding;

        public CategoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLayoutBinding.bind(itemView);
        }
    }

    public interface HandleCategoryClick {
        void itemClick(Category category);

        void removeItem(Category category);

        void editItem(Category category);
    }
}