package com.example.passwordmanager_;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSave;
    private RecyclerView recyclerView;
    private PasswordAdapter passwordAdapter;
    private List<PasswordItem> passwordList;
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_PIN = "pin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedPin = sharedPreferences.getString(KEY_PIN, null);

        if (savedPin == null) {
            Intent intent = new Intent(this, SetPinActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            editTextTitle = findViewById(R.id.editTextTitle);
            editTextUsername = findViewById(R.id.editTextUsername);
            editTextPassword = findViewById(R.id.editTextPassword);
            buttonSave = findViewById(R.id.buttonSave);
            recyclerView = findViewById(R.id.recyclerView);

            // Инициализация списка паролей и адаптера
            passwordList = new ArrayList<>();
            passwordAdapter = new PasswordAdapter(this, passwordList, new PasswordAdapter.OnPasswordClickListener() {
                @Override
                public void onDeleteClick(PasswordItem passwordItem) {
                    deletePassword(passwordItem);
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(passwordAdapter);

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePassword();
                }
            });

            loadPasswords();
        }



    }

    private void savePassword() {
        String title = editTextTitle.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!password.isEmpty() && !username.isEmpty() && !title.isEmpty())  {
            PasswordItem newItem = new PasswordItem(title, username, password);
            passwordList.add(newItem);
            passwordAdapter.notifyDataSetChanged();

            saveToSharedPreferences();

            Toast.makeText(this, "Пароль и логин сохранены!", Toast.LENGTH_SHORT).show();
            editTextTitle.setText("");
            editTextUsername.setText("");
            editTextPassword.setText("");
        } else {
            Toast.makeText(this, "Введите логин, пароль и заголовок", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPasswords() {
        SharedPreferences sharedPreferences = getSharedPreferences("PasswordManager", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("password_list", null);
        Type type = new TypeToken<List<PasswordItem>>() {}.getType();

        passwordList.clear();

        if (json != null) {
            List<PasswordItem> loadedList = gson.fromJson(json, type);
            if (loadedList != null) {
                passwordList.addAll(loadedList);
            }
        }

        passwordAdapter.notifyDataSetChanged();
    }

    private void saveToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("PasswordManager", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(passwordList);
        editor.putString("password_list", json);
        editor.apply();
    }

    private void deletePassword(PasswordItem passwordItem) {
        passwordList.remove(passwordItem);
        saveToSharedPreferences();
        passwordAdapter.notifyDataSetChanged();
    }
}
