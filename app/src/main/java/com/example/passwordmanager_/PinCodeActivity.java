package com.example.passwordmanager_;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
public class PinCodeActivity extends AppCompatActivity {

    private EditText editTextPin;
    private Button buttonSubmit;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_PIN = "pin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        editTextPin = findViewById(R.id.editTextPin);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = editTextPin.getText().toString();
                String savedPin = sharedPreferences.getString(KEY_PIN, null);

                if (savedPin == null) {
                    // Если пин-код не установлен, перенаправляем на экран установки пин-кода
                    Intent intent = new Intent(PinCodeActivity.this, SetPinActivity.class);
                    startActivity(intent);
                    finish();
                } else if (enteredPin.equals(savedPin)) {
                    // Если введенный пин-код верный
                    navigateToMainScreen();
                } else {
                    // Пин-код неверный
                    editTextPin.setError("Неверный PIN-код");
                }
            }
        });
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Закрываем текущую активность
    }
}
