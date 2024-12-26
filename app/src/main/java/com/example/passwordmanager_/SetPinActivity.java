package com.example.passwordmanager_;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class SetPinActivity extends AppCompatActivity {

    private EditText editTextSetPin;
    private Button buttonSetPin;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_PIN = "pin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);

        editTextSetPin = findViewById(R.id.editTextSetPin);
        buttonSetPin = findViewById(R.id.buttonSetPin);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonSetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPin = editTextSetPin.getText().toString();

                // Сохраняем новый пин-код в SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_PIN, newPin);
                editor.apply();

                // Перенаправляем на экран ввода пин-кода
                Intent intent = new Intent(SetPinActivity.this, PinCodeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
