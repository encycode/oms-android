package com.encycode.sheetalfoods;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.encycode.sheetalfoods.dao.CategoriesDao;
import com.encycode.sheetalfoods.dao.NotificationsDao;
import com.encycode.sheetalfoods.dao.OrderDetailsDao;
import com.encycode.sheetalfoods.dao.OrdersDao;
import com.encycode.sheetalfoods.dao.ProductTypesDao;
import com.encycode.sheetalfoods.dao.ProductsDao;
import com.encycode.sheetalfoods.dao.UsersDao;
import com.encycode.sheetalfoods.entity.Categories;
import com.encycode.sheetalfoods.entity.Notifications;
import com.encycode.sheetalfoods.entity.OrderDetails;
import com.encycode.sheetalfoods.entity.Orders;
import com.encycode.sheetalfoods.entity.ProductTypes;
import com.encycode.sheetalfoods.entity.Products;
import com.encycode.sheetalfoods.entity.Users;

@Database(entities = {
        Categories.class,
        Notifications.class,
        OrderDetails.class,
        Orders.class,
        Products.class,
        ProductTypes.class,
        Users.class
}, version = 1)
public abstract class OMSDatabase extends RoomDatabase {

    private static OMSDatabase instance;

    public abstract CategoriesDao categoriesDao();
    public abstract NotificationsDao notificationsDao();
    public abstract OrderDetailsDao orderDetailsDao();
    public abstract OrdersDao ordersDao();
    public abstract ProductsDao productsDao();
    public abstract ProductTypesDao productTypesDao();
    public abstract UsersDao usersDao();

    public static synchronized OMSDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),OMSDatabase.class,"OMSDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
