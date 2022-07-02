package com.example.h_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TasksActivity extends AppCompatActivity {
    private LinearLayout ll_task_view;
    private ImageButton btn_add, btn_save, btn_cancel;
    private FirebaseUser user;
    private FirebaseDatabase fdb;
    private DatabaseReference dbref;
    private static Integer task_id, edit_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ll_task_view = (LinearLayout) findViewById(R.id.id_ll_todo_view);
        btn_add = (ImageButton) findViewById(R.id.id_btn_todo_add);
        btn_save = (ImageButton) findViewById(R.id.id_btn_todo_save);
        btn_cancel = (ImageButton) findViewById(R.id.id_btn_todo_cancel);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fdb = FirebaseDatabase.getInstance();

        task_id = 1000000;
        edit_check = 0;
        Map<String, String> list = new HashMap();

        dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(snapshot.getKey());
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            int i = 0;
                            String temp = "";
                            for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                i++;
                                list.clear();
                                list.put(snapshot1.getKey(), snapshot1.getValue().toString());
                                if (i == 1) {
                                    temp = list.get("task");
                                }
                                if (i == 2) {
                                    task_id = Integer.valueOf(snapshot.getKey());
                                    LinearLayout new_ll_main = new LinearLayout(TasksActivity.this);
                                    new_ll_main.setTag(snapshot.getKey());
                                    new_ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
//                                    new_ll_main.setScaleY(0);
                                    new_ll_main.setBackground(ContextCompat.getDrawable(TasksActivity.this, R.drawable.border_black));
                                    new_ll_main.setOrientation(LinearLayout.HORIZONTAL);

                                    LinearLayout new_ll_cbx = new LinearLayout(TasksActivity.this);
                                    new_ll_cbx.setTag(snapshot.getKey());
                                    new_ll_cbx.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT, 0.1F));
                                    new_ll_cbx.setBackground(ContextCompat.getDrawable(TasksActivity.this, R.drawable.border_black));
                                    new_ll_cbx.setGravity(Gravity.CENTER);
                                    new_ll_main.addView(new_ll_cbx);

                                    LinearLayout new_ll_edt = new LinearLayout(TasksActivity.this);
                                    new_ll_edt.setTag(snapshot.getKey());
                                    new_ll_edt.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.9F));
                                    new_ll_edt.setPadding(10, 0, 0, 0);
                                    new_ll_edt.setBackground(ContextCompat.getDrawable(TasksActivity.this, R.drawable.border_black));
                                    new_ll_edt.setOrientation(LinearLayout.HORIZONTAL);
                                    new_ll_edt.setGravity(Gravity.CENTER_VERTICAL);
                                    new_ll_main.addView(new_ll_edt);

                                    CheckBox new_cbx = new CheckBox(TasksActivity.this);
                                    new_cbx.setTag(snapshot.getKey());
                                    new_cbx.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                                    new_cbx.setEnabled(true);
                                    new_cbx.setChecked(Boolean.valueOf(list.get("task_status")));
                                    new_ll_cbx.addView(new_cbx);

                                    EditText new_edt = new EditText(TasksActivity.this);
                                    new_edt.setTag(snapshot.getKey());
                                    new_edt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.7F));
                                    new_edt.setBackgroundResource(android.R.color.transparent);
                                    new_edt.setSingleLine(true);
                                    new_edt.setEnabled(false);
                                    new_edt.setTextSize(15F);
                                    new_edt.setText(temp);
                                    new_edt.setTextColor(Color.parseColor("#1B1E23"));
                                    new_edt.setTypeface(Typeface.DEFAULT_BOLD);
                                    new_ll_edt.addView(new_edt);

                                    ImageButton new_btn_e = new ImageButton(TasksActivity.this);
                                    new_btn_e.setTag(snapshot.getKey());
                                    new_btn_e.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
                                    new_btn_e.setScaleType(ImageView.ScaleType.FIT_XY);
                                    new_btn_e.setImageResource(R.drawable.edit);
                                    new_btn_e.setVisibility(View.VISIBLE);
                                    new_ll_edt.addView(new_btn_e);

                                    ImageButton new_btn_s = new ImageButton(TasksActivity.this);
                                    new_btn_s.setTag(snapshot.getKey());
                                    new_btn_s.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
                                    new_btn_s.setScaleType(ImageView.ScaleType.FIT_XY);
                                    new_btn_s.setImageResource(R.drawable.save);
                                    new_btn_s.setVisibility(View.GONE);
                                    new_ll_edt.addView(new_btn_s);

                                    ImageButton new_btn_d = new ImageButton(TasksActivity.this);
                                    new_btn_d.setTag(snapshot.getKey());
                                    new_btn_d.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
                                    new_btn_d.setScaleType(ImageView.ScaleType.FIT_XY);
                                    new_btn_d.setImageResource(R.drawable.delete);
                                    new_btn_d.setVisibility(View.VISIBLE);
                                    new_ll_edt.addView(new_btn_d);

                                    ll_task_view.addView(new_ll_main);
//                                    new_ll_main.animate().scaleY(1F).setDuration(500);

                                    new_btn_e.setOnClickListener(vebtn -> {
                                        btn_add.setVisibility(View.INVISIBLE);
                                        new_btn_e.setVisibility(View.GONE);
                                        new_btn_s.setVisibility(View.VISIBLE);
                                        new_edt.setEnabled(true);
                                        new_edt.setTypeface(Typeface.DEFAULT);
                                        edit_check++;
                                    });

                                    new_btn_s.setOnClickListener(vsbtn -> {
                                        dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Boolean cbx_value = new_cbx.isChecked();
                                                String edt_value = new_edt.getText().toString();
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("task_status", cbx_value);
                                                map.put("task", edt_value);
                                                dbref.updateChildren(map);
                                                Toast.makeText(TasksActivity.this, "Task successfully saved!", Toast.LENGTH_SHORT).show();
                                                new_edt.setEnabled(false);
                                                new_edt.setTypeface(Typeface.DEFAULT_BOLD);
                                                new_btn_s.setVisibility(View.GONE);
                                                new_btn_e.setVisibility(View.VISIBLE);
                                                edit_check--;
                                                if (edit_check == 0) {
                                                    btn_add.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(TasksActivity.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    });

                                    new_btn_d.setOnClickListener(vdbtn -> {
                                        dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                                        dbref.removeValue();
                                        ll_task_view.removeView(new_ll_main);
                                        Toast.makeText(TasksActivity.this, "Task deleted!", Toast.LENGTH_SHORT).show();
                                    });

                                    new_cbx.setOnClickListener(vcbx -> {
                                        dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Boolean cbx_value = new_cbx.isChecked();
                                                String edt_value = new_edt.getText().toString();
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("task_status", cbx_value);
                                                map.put("task", edt_value);
                                                dbref.updateChildren(map);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(TasksActivity.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    });
                                    i = 0;
                                    temp = "";
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_add.setOnClickListener(v -> {
            dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(String.valueOf(++task_id));

            LinearLayout new_ll_main = new LinearLayout(this);
            new_ll_main.setTag(String.valueOf(task_id));
            new_ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
            new_ll_main.setScaleX(0);
            new_ll_main.setBackground(ContextCompat.getDrawable(this, R.drawable.border_black));
            new_ll_main.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout new_ll_cbx = new LinearLayout(this);
            new_ll_cbx.setTag(String.valueOf(task_id));
            new_ll_cbx.setLayoutParams(new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.MATCH_PARENT, 0.1F));
            new_ll_cbx.setBackground(ContextCompat.getDrawable(this, R.drawable.border_black));
            new_ll_cbx.setGravity(Gravity.CENTER);
            new_ll_main.addView(new_ll_cbx);

            LinearLayout new_ll_edt = new LinearLayout(this);
            new_ll_edt.setTag(String.valueOf(task_id));
            new_ll_edt.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.9F));
            new_ll_edt.setPadding(10, 0, 0, 0);
            new_ll_edt.setBackground(ContextCompat.getDrawable(this, R.drawable.border_black));
            new_ll_edt.setOrientation(LinearLayout.HORIZONTAL);
            new_ll_edt.setGravity(Gravity.CENTER_VERTICAL);
            new_ll_main.addView(new_ll_edt);

            CheckBox new_cbx = new CheckBox(this);
            new_cbx.setTag(String.valueOf(task_id));
            new_cbx.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            new_cbx.setEnabled(false);
            new_ll_cbx.addView(new_cbx);

            EditText new_edt = new EditText(this);
            new_edt.setTag(String.valueOf(task_id));
            new_edt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.7F));
            new_edt.setBackgroundResource(android.R.color.transparent);
            new_edt.setSingleLine(true);
            new_edt.setTextSize(15F);
            new_edt.setTextColor(Color.parseColor("#1B1E23"));
            new_edt.setTypeface(Typeface.DEFAULT);
            new_ll_edt.addView(new_edt);

            ImageButton new_btn_e = new ImageButton(this);
            new_btn_e.setTag(String.valueOf(task_id));
            new_btn_e.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            new_btn_e.setScaleType(ImageView.ScaleType.FIT_XY);
            new_btn_e.setImageResource(R.drawable.edit);
            new_btn_e.setVisibility(View.INVISIBLE);
            new_ll_edt.addView(new_btn_e);

            ImageButton new_btn_s = new ImageButton(this);
            new_btn_s.setTag(String.valueOf(task_id));
            new_btn_s.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            new_btn_s.setScaleType(ImageView.ScaleType.FIT_XY);
            new_btn_s.setImageResource(R.drawable.save);
            new_btn_s.setVisibility(View.GONE);
            new_ll_edt.addView(new_btn_s);

            ImageButton new_btn_d = new ImageButton(this);
            new_btn_d.setTag(String.valueOf(task_id));
            new_btn_d.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            new_btn_d.setScaleType(ImageView.ScaleType.FIT_XY);
            new_btn_d.setImageResource(R.drawable.delete);
            new_btn_d.setVisibility(View.INVISIBLE);
            new_ll_edt.addView(new_btn_d);

            ll_task_view.addView(new_ll_main);
            new_ll_main.animate().scaleX(1F).setDuration(250);

            btn_add.setVisibility(View.INVISIBLE);
            btn_save.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);

            btn_save.setOnClickListener(vsbtn -> {
                Boolean cbx_value = new_cbx.isChecked();
                String edt_value = new_edt.getText().toString();
                if (edt_value.isEmpty()) {
                    Toast.makeText(TasksActivity.this, "Please enter a task!", Toast.LENGTH_SHORT).show();
                } else if (!edt_value.isEmpty()) {
                    TodoModal todoModal = new TodoModal(cbx_value, edt_value);
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dbref.setValue(todoModal);
                            Toast.makeText(TasksActivity.this, "Task successfully added!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(TasksActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    new_cbx.setEnabled(true);
                    new_edt.setEnabled(false);
                    new_btn_e.setVisibility(View.VISIBLE);
                    new_btn_d.setVisibility(View.VISIBLE);
                    btn_add.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.INVISIBLE);
                    btn_cancel.setVisibility(View.INVISIBLE);
                }
            });

            btn_cancel.setOnClickListener(vcbtn -> {
                ll_task_view.removeView(new_ll_main);
                btn_add.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.INVISIBLE);
            });

            new_btn_e.setOnClickListener(vebtn -> {
                btn_add.setVisibility(View.INVISIBLE);
                new_btn_e.setVisibility(View.GONE);
                new_btn_s.setVisibility(View.VISIBLE);
                new_edt.setEnabled(true);
                new_edt.setTypeface(Typeface.DEFAULT);
                edit_check++;
            });

            new_btn_s.setOnClickListener(vsbtn -> {
                dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean cbx_value = new_cbx.isChecked();
                        String edt_value = new_edt.getText().toString();
                        Map<String, Object> map = new HashMap<>();
                        map.put("task_status", cbx_value);
                        map.put("task", edt_value);
                        dbref.updateChildren(map);
                        Toast.makeText(TasksActivity.this, "Task successfully saved!", Toast.LENGTH_SHORT).show();
                        new_edt.setEnabled(false);
                        new_edt.setTypeface(Typeface.DEFAULT_BOLD);
                        new_btn_s.setVisibility(View.GONE);
                        new_btn_e.setVisibility(View.VISIBLE);
                        edit_check--;
                        if (edit_check == 0) {
                            btn_add.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TasksActivity.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            });

            new_btn_d.setOnClickListener(vdbtn -> {
                dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                dbref.removeValue();
                ll_task_view.removeView(new_ll_main);
                Toast.makeText(TasksActivity.this, "Task deleted!", Toast.LENGTH_SHORT).show();
            });

            new_cbx.setOnClickListener(vcbx -> {
                dbref = fdb.getReference(""+user.getUid().toString()).child("Tasks").child(new_btn_d.getTag().toString());
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Boolean cbx_value = new_cbx.isChecked();
                        String edt_value = new_edt.getText().toString();
                        Map<String, Object> map = new HashMap<>();
                        map.put("task_status", cbx_value);
                        map.put("task", edt_value);
                        dbref.updateChildren(map);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TasksActivity.this, ""+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }
}