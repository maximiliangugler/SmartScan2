package at.inthebox21.smartscan2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;

import at.inthebox21.smartscan2.R;
import at.inthebox21.smartscan2.database.DatabaseHelper;
import at.inthebox21.smartscan2.models.Product;

public class MainActivity extends AppCompatActivity {

    private final static int SUPPLIER_ID = 0;
    private static boolean isStoreSelected = false;
    private static int storeId;
    private static HashMap<Integer, String> storeNames;
    private DatabaseHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize database
        database = new DatabaseHelper(this);
        database.getWritableDatabase();

        // Check for incoming intent from store selection activity
        Intent returnFromStoreSelection = getIntent();
        storeId = returnFromStoreSelection.getIntExtra("storeId", -1);
        if(storeId != -1) {
            isStoreSelected = true;
            Log.d("MainActivity", "Store selected: " + storeId);
        }

        // Navigate to store selection activity if no store is selected
        if (!isStoreSelected) {
            Log.d("MainActivity", "No store selected, switching to store selection activity...");
            Intent showStoreSelection = new Intent(this, StoreSelectorActivity.class);
            startActivity(showStoreSelection);
            finish();
        } else {
            setContentView(R.layout.activity_main);
        }

        // Populate store names HashMap
        storeNames = new HashMap<>();
        storeNames.put(1, "Winklarn");
        storeNames.put(2, "Krenstetten");
        storeNames.put(3, "Greinsfurth");
        storeNames.put(4, "Kematen");
        storeNames.put(5, "Hausmening");
        storeNames.put(6, "Krankenhaus");

    }
}