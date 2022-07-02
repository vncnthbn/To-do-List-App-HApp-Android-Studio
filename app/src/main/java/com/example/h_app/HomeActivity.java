package com.example.h_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout ll_todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ll_todo = (LinearLayout) findViewById(R.id.id_ll_todo);

        ll_todo.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TasksActivity.class);
            startActivity(intent);
            finish();
        });
    }
}