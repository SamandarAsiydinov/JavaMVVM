package com.samsdk.javamvvm.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "categoryName")
    public String categoryName;


}
