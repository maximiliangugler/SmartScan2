package at.inthebox21.smartscan2.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import at.inthebox21.smartscan2.models.Product;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "smartscan.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductTable =
                "CREATE TABLE Product (" +
                "depoArtId INTEGER PRIMARY KEY, " +
                "name TEXT);";

        String createEANTable =
                "CREATE TABLE IF NOT EXISTS ProductEAN (" +
                "ean TEXT PRIMARY KEY, " +
                "product_id INTEGER, " +
                "FOREIGN KEY(product_id) REFERENCES Product(depoArtId) ON DELETE CASCADE);";

        db.execSQL(createProductTable);
        db.execSQL(createEANTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ArticleEAN");
        db.execSQL("DROP TABLE IF EXISTS Product");
        onCreate(db);
    }

    public void upsertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues productValues = new ContentValues();
        productValues.put("depoArtId", product.getDepoArtId());
        productValues.put("name", product.getName());
        db.insertWithOnConflict("Product", null, productValues, SQLiteDatabase.CONFLICT_REPLACE);

        for(String ean : product.getEans()) {
            ContentValues eanValues = new ContentValues();
            eanValues.put("ean", ean);
            eanValues.put("product_id", product.getDepoArtId());
            db.insertWithOnConflict("ProductEAN", null, eanValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    @SuppressLint("Range")
    public Product getProductByEAN(String ean) {
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;

        String productIdQuery = "SELECT product_id FROM ProductEAN WHERE ean = ?";
        Cursor cursor = db.rawQuery(productIdQuery, new String[]{ean});

        if(cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndex("product_id"));
            cursor.close();

            String productQuery = "SELECT * FROM Product WHERE depoArtId = ?";
            Cursor productCursor = db.rawQuery(productQuery, new String[]{String.valueOf(productId)});

            if (productCursor.moveToFirst()) {
                String name = productCursor.getString(productCursor.getColumnIndex("name"));
                String depoArtId = productCursor.getString(productCursor.getColumnIndex("id"));
                productCursor.close();

                String eanQuery = "SELECT ean FROM ProductEAN WHERE product_id = ?";
                Cursor eanCursor = db.rawQuery(eanQuery, new String[]{String.valueOf(productId)});

                List<String> eanList = new ArrayList<>();
                while (eanCursor.moveToNext()) {
                    eanList.add(eanCursor.getString(eanCursor.getColumnIndex("ean")));
                }
                eanCursor.close();

                product = new Product(name, depoArtId, eanList);
            }
        } else {
            cursor.close();
        }
        return product;
    }

}
