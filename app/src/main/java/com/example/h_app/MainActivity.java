package com.example.h_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageView img_h_logo;
    private Button btn_sign_in, btn_sign_up;
    private LinearLayout ll_sign_in, ll_sign_up;
    private Boolean sign_in_form, sign_up_form;
    private EditText edt_sign_in_user, edt_sign_in_pass, edt_sign_up_user, edt_sign_up_pass1, edt_sign_up_pass2;
    private String username_str, password_str, reg_username_str, reg_password1_str, reg_password2_str;
    private FirebaseAuth f_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_h_logo = (ImageView) findViewById(R.id.id_img_h_logo);
        btn_sign_in = (Button) findViewById(R.id.id_btn_sign_in);
        btn_sign_up = (Button) findViewById(R.id.id_btn_sign_up);
        ll_sign_in = (LinearLayout) findViewById(R.id.id_ll_sign_in);
        ll_sign_up = (LinearLayout) findViewById(R.id.id_ll_sign_up);

        edt_sign_in_user = (EditText) findViewById(R.id.id_edt_username);
        edt_sign_in_pass = (EditText) findViewById(R.id.id_edt_password);
        edt_sign_up_user = (EditText) findViewById(R.id.id_edt_reg_username);
        edt_sign_up_pass1 = (EditText) findViewById(R.id.id_edt_reg_password1);
        edt_sign_up_pass2 = (EditText) findViewById(R.id.id_edt_reg_password2);

        f_auth = FirebaseAuth.getInstance();

        sign_in_form = false;
        sign_up_form = false;

        btn_sign_in.setOnClickListener(v -> {
            if (!sign_in_form && !sign_up_form) {
                img_h_logo.animate().translationY(-300F).setDuration(500);
                img_h_logo.animate().scaleY(0.4F).setDuration(500);
                img_h_logo.animate().scaleX(0.4F).setDuration(500);
                img_h_logo.animate().rotation(360F).setDuration(500);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img_h_logo.getLayoutParams();
                params.bottomMargin = 0;
                img_h_logo.setLayoutParams(params);

                ll_sign_in.animate().scaleY(1F).setDuration(500);
                ll_sign_in.animate().scaleX(1F).setDuration(500);
                ll_sign_in.animate().alphaBy(1F).setDuration(500);
                ll_sign_in.setVisibility(v.VISIBLE);

                btn_sign_in.animate().translationY(-300F).setDuration(500);
                btn_sign_in.animate().scaleY(1F).setDuration(500);
                btn_sign_in.animate().scaleX(1F).setDuration(500);
                btn_sign_in.setBackgroundColor(getResources().getColor(R.color.purple_500));
                btn_sign_in.setTextColor(getResources().getColor(R.color.white));

                btn_sign_up.animate().translationY(-300F).setDuration(500);
                btn_sign_up.animate().scaleY(0.8F).setDuration(500);
                btn_sign_up.animate().scaleX(0.6F).setDuration(500);
                btn_sign_up.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sign_up.setTextColor(getResources().getColor(R.color.black));

                ll_sign_up.animate().scaleY(0F).setDuration(500);
                ll_sign_up.animate().scaleX(0F).setDuration(500);
                ll_sign_up.animate().alphaBy(0F).setDuration(500);
                ll_sign_up.setVisibility(v.INVISIBLE);

                sign_in_form = true;
                sign_up_form = false;
            } else if (sign_up_form) {
                img_h_logo.animate().rotation(360F).setDuration(500);

                ll_sign_in.animate().scaleY(1F).setDuration(500);
                ll_sign_in.animate().scaleX(1F).setDuration(500);
                ll_sign_in.animate().alphaBy(1F).setDuration(500);
                ll_sign_in.setVisibility(v.VISIBLE);

                btn_sign_in.animate().translationY(-300F).setDuration(500);
                btn_sign_in.animate().scaleY(1F).setDuration(500);
                btn_sign_in.animate().scaleX(1F).setDuration(500);
                btn_sign_in.setBackgroundColor(getResources().getColor(R.color.purple_500));
                btn_sign_in.setTextColor(getResources().getColor(R.color.white));

                btn_sign_up.animate().translationY(-300F).setDuration(500);
                btn_sign_up.animate().scaleY(0.8F).setDuration(500);
                btn_sign_up.animate().scaleX(0.6F).setDuration(500);
                btn_sign_up.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sign_up.setTextColor(getResources().getColor(R.color.black));

                ll_sign_up.animate().scaleY(0F).setDuration(500);
                ll_sign_up.animate().scaleX(0F).setDuration(500);
                ll_sign_up.animate().alphaBy(0F).setDuration(500);
                ll_sign_up.setVisibility(v.INVISIBLE);

                sign_in_form = true;
                sign_up_form = false;
            } else if (sign_in_form) {
                username_str = edt_sign_in_user.getText().toString();
                password_str = edt_sign_in_pass.getText().toString();

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                if (username_str.isEmpty()) {
                    Toast.makeText(this, "Enter Email Address!", Toast.LENGTH_LONG).show();
                } else if (password_str.isEmpty()) {
                    Toast.makeText(this, "Enter Password!", Toast.LENGTH_LONG).show();
                } else if (!username_str.isEmpty() || !password_str.isEmpty()) {
                    f_auth.signInWithEmailAndPassword(username_str, password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                img_h_logo.animate().translationY(500F).setDuration(50);
                                img_h_logo.animate().scaleY(50F).setDuration(5000);
                                img_h_logo.animate().scaleX(50F).setDuration(5000);
                                img_h_logo.animate().rotation(1080F).setDuration(1000);
                                ll_sign_in.setVisibility(View.GONE);
                                btn_sign_in.setVisibility(View.GONE);
                                btn_sign_up.setVisibility(View.GONE);
                                ll_sign_up.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 550);
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong Email Address or Password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btn_sign_up.setOnClickListener(v -> {
            if (!sign_up_form && !sign_in_form) {
                img_h_logo.animate().translationY(-300F).setDuration(500);
                img_h_logo.animate().scaleY(0.4F).setDuration(500);
                img_h_logo.animate().scaleX(0.4F).setDuration(500);
                img_h_logo.animate().rotation(-360F).setDuration(500);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img_h_logo.getLayoutParams();
                params.bottomMargin = 0;
                img_h_logo.setLayoutParams(params);

                ll_sign_in.animate().scaleY(0F).setDuration(500);
                ll_sign_in.animate().scaleX(0F).setDuration(500);
                ll_sign_in.animate().alpha(0F).setDuration(500);
                ll_sign_in.setVisibility(v.INVISIBLE);

                btn_sign_in.animate().translationY(-1200F).setDuration(500);
                btn_sign_in.animate().scaleY(0.8F).setDuration(500);
                btn_sign_in.animate().scaleX(0.6F).setDuration(500);
                btn_sign_in.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sign_in.setTextColor(getResources().getColor(R.color.black));

                btn_sign_up.animate().translationY(-200F).setDuration(500);
                btn_sign_up.animate().scaleY(1F).setDuration(500);
                btn_sign_up.animate().scaleX(1F).setDuration(500);
                btn_sign_up.setBackgroundColor(getResources().getColor(R.color.purple_500));
                btn_sign_up.setTextColor(getResources().getColor(R.color.white));

                ll_sign_up.animate().scaleY(1F).setDuration(500);
                ll_sign_up.animate().scaleX(1F).setDuration(500);
                ll_sign_up.animate().alphaBy(1F).setDuration(500);
                ll_sign_up.setVisibility(v.VISIBLE);

                sign_in_form = false;
                sign_up_form = true;
            } else if (sign_in_form) {
                img_h_logo.animate().rotation(-360F).setDuration(500);

                ll_sign_in.animate().scaleY(0F).setDuration(500);
                ll_sign_in.animate().scaleX(0F).setDuration(500);
                ll_sign_in.animate().alpha(0F).setDuration(500);
                ll_sign_in.setVisibility(v.INVISIBLE);

                btn_sign_in.animate().translationY(-1200F).setDuration(500);
                btn_sign_in.animate().scaleY(0.8F).setDuration(500);
                btn_sign_in.animate().scaleX(0.6F).setDuration(500);
                btn_sign_in.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sign_in.setTextColor(getResources().getColor(R.color.black));

                btn_sign_up.animate().translationY(-200F).setDuration(500);
                btn_sign_up.animate().scaleY(1F).setDuration(500);
                btn_sign_up.animate().scaleX(1F).setDuration(500);
                btn_sign_up.setBackgroundColor(getResources().getColor(R.color.purple_500));
                btn_sign_up.setTextColor(getResources().getColor(R.color.white));

                ll_sign_up.animate().scaleY(1F).setDuration(500);
                ll_sign_up.animate().scaleX(1F).setDuration(500);
                ll_sign_up.animate().alphaBy(1F).setDuration(500);
                ll_sign_up.setVisibility(v.VISIBLE);

                sign_in_form = false;
                sign_up_form = true;
            } else if (sign_up_form) {
                reg_username_str = edt_sign_up_user.getText().toString();
                reg_password1_str = edt_sign_up_pass1.getText().toString();
                reg_password2_str = edt_sign_up_pass2.getText().toString();

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                if (reg_username_str.isEmpty()) {
                    Toast.makeText(this, "Enter Email Address!", Toast.LENGTH_LONG).show();
                } else if (reg_username_str.length() < 10) {
                    Toast.makeText(this, "Enter valid Email Address!", Toast.LENGTH_LONG).show();
                } else if (reg_password1_str.isEmpty() || reg_password2_str.isEmpty()) {
                    Toast.makeText(this, "Enter a password!", Toast.LENGTH_LONG).show();
                } else if (!reg_password1_str.equals(reg_password2_str)) {
                    Toast.makeText(this, "Password don't match!", Toast.LENGTH_LONG).show();
                } else if (reg_password1_str.length() < 6) {
                    Toast.makeText(this, "Password must be 6 characters or more!", Toast.LENGTH_SHORT).show();
                } else if (!reg_username_str.isEmpty() && reg_password1_str.equals(reg_password2_str)) {
                    f_auth.createUserWithEmailAndPassword(reg_username_str, reg_password1_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                img_h_logo.animate().translationY(500F).setDuration(50);
                                img_h_logo.animate().scaleY(50F).setDuration(5000);
                                img_h_logo.animate().scaleX(50F).setDuration(5000);
                                img_h_logo.animate().rotation(1080F).setDuration(1000);
                                ll_sign_in.setVisibility(View.GONE);
                                btn_sign_in.setVisibility(View.GONE);
                                btn_sign_up.setVisibility(View.GONE);
                                ll_sign_up.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Signed Up Successfully!", Toast.LENGTH_SHORT).show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 550);
                            } else {
                                Toast.makeText(MainActivity.this, "Email already exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



}