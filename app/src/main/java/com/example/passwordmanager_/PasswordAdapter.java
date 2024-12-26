package com.example.passwordmanager_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;


public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private List<PasswordItem> passwordList;
    private OnPasswordClickListener listener;

    public interface OnPasswordClickListener {
        void onDeleteClick(PasswordItem passwordItem);
    }

    public PasswordAdapter(Context context, List<PasswordItem> passwordList, OnPasswordClickListener listener) {
        this.passwordList = passwordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        return new PasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        PasswordItem passwordItem = passwordList.get(position);
        holder.bind(passwordItem, listener);
    }

    @Override
    public int getItemCount() {
        return passwordList.size();
    }

    public static class PasswordViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewUsername;
        private TextView textViewPassword;
        private Button buttonDelete;

        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewPassword = itemView.findViewById(R.id.textViewPassword);
            buttonDelete = itemView.findViewById(R.id.buttonDelete); // Кнопка удаления
        }

        public void bind(final PasswordItem passwordItem, final OnPasswordClickListener listener) {
            textViewTitle.setText(passwordItem.getTitle());
            textViewUsername.setText(passwordItem.getUsername());
            textViewPassword.setText(passwordItem.getPassword());

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Создаем AlertDialog для подтверждения удаления
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Подтверждение удаления")
                            .setMessage("Вы уверены, что хотите удалить этот элемент?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listener.onDeleteClick(passwordItem); // Вызываем метод удаления
                                }
                            })
                            .setNegativeButton("Нет", null) // Закрываем диалог при нажатии "Нет"
                            .show();
                }
            });
        }


    }
}
