package com.example.chubby;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvGotoRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // (An toàn) đảm bảo FirebaseApp đã init
        try {
            FirebaseApp.getInstance();
        } catch (IllegalStateException e) {
            FirebaseApp.initializeApp(this);
        }

        // LOG cấu hình Firebase đang được nạp
        try {
            FirebaseOptions opt = FirebaseApp.getInstance().getOptions();
            Log.d("FB_OPT",
                    "projectId=" + opt.getProjectId()
                            + ", appId=" + opt.getApplicationId()
                            + ", apiKey=" + opt.getApiKey());
        } catch (Exception e) {
            Log.e("FB_OPT", "Không đọc được FirebaseOptions: " + e.getMessage());
        }

        // Nếu đã đăng nhập -> vào thẳng Main
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            gotoMain();
            return;
        }

        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGotoRegister = findViewById(R.id.tvGotoRegister);

        btnLogin.setOnClickListener(v -> doLogin());
        tvGotoRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private void doLogin() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) { edtEmail.setError("Nhập email"); return; }
        if (TextUtils.isEmpty(pass)) { edtPassword.setError("Nhập mật khẩu"); return; }

        btnLogin.setEnabled(false);

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(result -> {
                    FirebaseUser fu = result.getUser();
                    if (fu == null) { toast("Lỗi đăng nhập"); btnLogin.setEnabled(true); return; }
                    gotoMain();
                })
                .addOnFailureListener(e -> {
                    toast("Đăng nhập thất bại: " + e.getMessage());
                    btnLogin.setEnabled(true);
                });
    }

    private void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }
}
