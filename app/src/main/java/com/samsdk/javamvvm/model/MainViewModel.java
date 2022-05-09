package com.samsdk.javamvvm.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.samsdk.javamvvm.database.AppDatabase;
import com.samsdk.javamvvm.database.Category;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Category>> listOfCategory;
    private AppDatabase appDatabase;

    public MainViewModel(Application application) {
        super(application);
        listOfCategory = new MutableLiveData<>();
        appDatabase = AppDatabase.getDbInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Category>> getListOfCategoryObserver() {
        return listOfCategory;
    }
    public void getAllCategoryList() {
        List<Category> categoryList = appDatabase.shoppingListDao().getAllCategoryList();
        if (categoryList.size() > 0) {
            listOfCategory.postValue(categoryList);
        } else {
            listOfCategory.postValue(null);
        }
    }
    public void insertCategory(String catName) {
        Category category = new Category();
        category.categoryName = catName;
        appDatabase.shoppingListDao().insertCategory(category);
        getAllCategoryList();
    }
    public void updateCategory(Category category) {
        appDatabase.shoppingListDao().updateCategory(category);
        getAllCategoryList();
    }
    public void deleteCategory(Category category) {
        appDatabase.shoppingListDao().deleteCategory(category);
        getAllCategoryList();
    }
}
