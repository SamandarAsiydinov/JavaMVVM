package com.samsdk.javamvvm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, Items.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ShoppingListDao shoppingListDao();

    public static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "app.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}