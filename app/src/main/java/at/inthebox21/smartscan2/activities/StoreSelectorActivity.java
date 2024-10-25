package at.inthebox21.smartscan2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import at.inthebox21.smartscan2.R;

public class StoreSelectorActivity extends AppCompatActivity {

    Button winlarn;
    Button krenstetten;
    Button greinsfurth;
    Button kematen;
    Button hausmening;
    Button krankenhaus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.store_selection_layout);

        winlarn = findViewById(R.id.winklarn);
        krenstetten = findViewById(R.id.krenstetten);
        greinsfurth = findViewById(R.id.greinsfurth);
        kematen = findViewById(R.id.kematen);
        hausmening = findViewById(R.id.hausmening);
        krankenhaus = findViewById(R.id.krankenhaus);

        winlarn.setOnClickListener(v -> returnToMainActivity(1));
        krenstetten.setOnClickListener(v -> returnToMainActivity(2));
        greinsfurth.setOnClickListener(v -> returnToMainActivity(3));
        kematen.setOnClickListener(v -> returnToMainActivity(4));
        hausmening.setOnClickListener(v -> returnToMainActivity(5));
        krankenhaus.setOnClickListener(v -> returnToMainActivity(6));

    }

    private void returnToMainActivity(int storeId) {
        Intent backToMainActivity = new Intent(this, MainActivity.class);
        backToMainActivity.putExtra("storeId", storeId);
        Log.d("StoreSelectorActivity", "Selected store= " + storeId + " - Returning to MainActivity...");
        startActivity(backToMainActivity);
    }
}
