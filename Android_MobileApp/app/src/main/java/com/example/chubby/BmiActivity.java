package com.example.chubby;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chubby.model.DishDAO;
import com.example.chubby.model.FoodItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BmiActivity extends AppCompatActivity {

    private EditText edtWeight, edtHeight;
    private Button btnCalculate;
    private TextView tvBmiResult;
    private Spinner spinnerFood;
    private EditText edtFoodWeight;
    private Button btnCalculateKcal;
    private TextView tvResultKcal;

    private List<FoodItem> foodList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvBmiResult = findViewById(R.id.tvBmiResult);
        spinnerFood = findViewById(R.id.spinnerFood);
        edtFoodWeight = findViewById(R.id.edtFoodWeight);
        btnCalculateKcal = findViewById(R.id.btnCalculateKcal);
        tvResultKcal = findViewById(R.id.tvResultKcal);

        btnCalculate.setOnClickListener(v -> calculateBmi());

        foodList = loadFoodsFromJson();

        List<String> foodNames = new ArrayList<>();
        for (FoodItem food : foodList) {
            foodNames.add(food.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                foodNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(adapter);

        btnCalculateKcal.setOnClickListener(v -> calculateKcal());
    }

    private void calculateBmi() {
        String strWeight = edtWeight.getText().toString().trim();
        String strHeight = edtHeight.getText().toString().trim();

        if (TextUtils.isEmpty(strWeight) || TextUtils.isEmpty(strHeight)) {
            Toast.makeText(this, "Vui lòng nhập đủ cân nặng và chiều cao", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float weight = Float.parseFloat(strWeight);
            float heightCm = Float.parseFloat(strHeight);
            if (weight <= 0 || heightCm <= 0) {
                Toast.makeText(this, "Cân nặng và chiều cao phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }

            float heightM = heightCm / 100f;
            float bmi = weight / (heightM * heightM);

            String category = categorizeBmi(bmi);

            String result = String.format("BMI của bạn là: %.2f\nBạn thuộc nhóm: %s", bmi, category);
            tvBmiResult.setText(result);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private String categorizeBmi(float bmi) {
        if (bmi < 18.5) {
            return "Gầy";
        } else if (bmi < 24.9) {
            return "Bình thường";
        } else if (bmi < 29.9) {
            return "Thừa cân";
        } else {
            return "Béo phì";
        }
    }
    private List<FoodItem> loadFoodsFromJson() {
        List<FoodItem> foods = new ArrayList<>();
        try {
            InputStream is = getAssets().open("foods.json"); // đọc file từ thư mục assets
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                int kcal = obj.getInt("kcal");
                foods.add(new FoodItem(name, kcal));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foods;
    }

    private void calculateKcal() {
        String strWeight = edtFoodWeight.getText().toString().trim();
        if (TextUtils.isEmpty(strWeight)) {
            Toast.makeText(this, "Vui lòng nhập khối lượng thực phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(strWeight);
            if (weight <= 0) {
                Toast.makeText(this, "Khối lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }

            int pos = spinnerFood.getSelectedItemPosition();
            if (pos < 0 || pos >= foodList.size()) {
                Toast.makeText(this, "Vui lòng chọn thực phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            FoodItem selectedFood = foodList.get(pos);
            double kcal = selectedFood.getKcalPer100g() * weight / 100.0;

            tvResultKcal.setText(String.format("Lượng kcal: %.2f kcal", kcal));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị khối lượng không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
