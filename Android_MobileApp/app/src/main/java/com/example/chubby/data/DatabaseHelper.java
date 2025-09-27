package com.example.chubby.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "chubby.db";   // tên database
    private static final int DB_VERSION = 4;             // ⚡ tăng version để onUpgrade chạy

    // Tên bảng & cột
    public static final String TABLE_DISH = "Dish";
    public static final String COL_ID = "id";               // id duy nhất (UUID, TEXT)
    public static final String COL_NAME = "name";

    // 2 loại cột ảnh: gallery (BLOB) hoặc resource id (INTEGER)
    public static final String COL_IMAGE = "imageData";      // ảnh lưu từ gallery (byte[])
    public static final String COL_IMAGE_RES = "imageResId"; // ảnh mặc định từ drawable

    public static final String COL_TYPE = "type";           // Featured / Near / Clean
    public static final String COL_DESC = "description";
    public static final String COL_INGREDIENTS = "ingredients";
    public static final String COL_RECIPE = "recipe";
    public static final String COL_TIME = "cookTime";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Dish với id là TEXT PRIMARY KEY (UUID)
        db.execSQL("CREATE TABLE " + TABLE_DISH + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +    // ⚡ UUID
                COL_NAME + " TEXT, " +
                COL_IMAGE + " BLOB, " +             // ảnh từ gallery (byte[])
                COL_IMAGE_RES + " INTEGER, " +      // ảnh resource id
                COL_TYPE + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_INGREDIENTS + " TEXT, " +
                COL_RECIPE + " TEXT, " +
                COL_TIME + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Khi nâng cấp, xóa bảng cũ và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH);
        onCreate(db);
    }
}
