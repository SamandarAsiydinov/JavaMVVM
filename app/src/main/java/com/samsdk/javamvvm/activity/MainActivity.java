package com.samsdk.javamvvm.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.samsdk.javamvvm.R;
import com.samsdk.javamvvm.adapter.CategoryListAdapter;
import com.samsdk.javamvvm.database.Category;
import com.samsdk.javamvvm.databinding.ActivityMainBinding;
import com.samsdk.javamvvm.databinding.AddCategoryLayoutBinding;
import com.samsdk.javamvvm.model.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryListAdapter.HandleCategoryClick {

    private MainViewModel viewModel;

    private CategoryListAdapter categoryListAdapter;
    private ActivityMainBinding binding;
    private Category categoryForEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Shopping List");

        initViews();

    }

    private void initViews() {
        binding.addNewCategoryFloat.setOnClickListener(view -> {
            showAddCategoryDialog(false);
        });
        setupRv();
        initViewModel();
    }

    private void setupRv() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter = new CategoryListAdapter(this, this);
        binding.recyclerView.setAdapter(categoryListAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getListOfCategoryObserver().observe(this, categories -> {
            if (categories == null) {
                binding.noResult.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                categoryListAdapter.setCategoryList(categories);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.noResult.setVisibility(View.GONE);
            }
        });
    }

    private void showAddCategoryDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.add_category_layout, null);
        AppCompatEditText enterCategoryInput = dialogView.findViewById(R.id.enterCategoryInput);
        MaterialButton createBtn = dialogView.findViewById(R.id.createButton);
        MaterialButton cancelBtn = dialogView.findViewById(R.id.cancelButton);

        if (isForEdit) {
            createBtn.setText("Update");
            enterCategoryInput.setText(categoryForEdit.categoryName);
        }

        cancelBtn.setOnClickListener(view -> {
            dialogBuilder.dismiss();
        });
        createBtn.setOnClickListener(v -> {
            String name = enterCategoryInput.getText().toString();
            if (TextUtils.isEmpty(name)) {
                toast("Enter category name");
                return;
            }

            if (isForEdit) {
                categoryForEdit.categoryName = name;
                viewModel.updateCategory(categoryForEdit);
            } else {
                viewModel.insertCategory(name);
            }
            dialogBuilder.dismiss();
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @Override
    public void itemClick(Category category) {

    }

    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }

    @Override
    public void editItem(Category category) {
        categoryForEdit = category;
        showAddCategoryDialog(true);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}