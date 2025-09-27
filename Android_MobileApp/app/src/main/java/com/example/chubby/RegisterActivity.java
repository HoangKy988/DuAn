package com.example.chubby;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chubby.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtPassword2;
    private Button btnRegister;
    private TextView tvGotoLogin;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword2 = findViewById(R.id.edtPassword2);
        btnRegister = findViewById(R.id.btnRegister);
        tvGotoLogin = findViewById(R.id.tvGotoLogin);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(v -> doRegister());
        tvGotoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void doRegister() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString();
        String pass2 = edtPassword2.getText().toString();

        if (TextUtils.isEmpty(name)) { edtName.setError("Nhập họ tên"); return; }
        if (TextUtils.isEmpty(email)) { edtEmail.setError("Nhập email"); return; }
        if (pass.length() < 6) { edtPassword.setError("Mật khẩu >= 6 ký tự"); return; }
        if (!pass.equals(pass2)) { edtPassword2.setError("Mật khẩu không khớp"); return; }

        btnRegister.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(result -> {
                    FirebaseUser fu = result.getUser();
                    if (fu == null) { toast("Lỗi tạo tài khoản"); btnRegister.setEnabled(true); return; }

                    // Cập nhật displayName (tuỳ chọn)
                    UserProfileChangeRequest req = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    fu.updateProfile(req);

                    // Tạo document users/{uid} với role = "user"
                    String uid = fu.getUid();
                    Map<String, Object> doc = new HashMap<>();
                    doc.put("displayName", name);
                    doc.put("email", email);
                    doc.put("role", "user");
                    doc.put("createdAt", FieldValue.serverTimestamp());

                    db.collection("users").document(uid).set(doc)
                            .addOnSuccessListener(unused -> {
                                toast("Đăng ký thành công!");
                                FirebaseAuth.getInstance().signOut();              // ⬅️ thoát phiên
                                startActivity(new Intent(this, LoginActivity.class));
                                finish();
                            })

                            .addOnFailureListener(e -> {
                                toast("Lỗi lưu hồ sơ: " + e.getMessage());
                                btnRegister.setEnabled(true);
                            });
                })
                .addOnFailureListener(e -> {
                    toast("Lỗi đăng ký: " + e.getMessage());
                    btnRegister.setEnabled(true);
                });
    }

    private void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }
}
