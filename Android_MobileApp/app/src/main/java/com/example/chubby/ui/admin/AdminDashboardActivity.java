package com.example.chubby.ui.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chubby.R;
import com.example.chubby.model.DishDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView tvTotalDishes, tvTotalUsers, tvTotalPosts;
    private RecyclerView rvRecent;
    private RecentActivityAdapter adapter;
    private final List<String> activityList = new ArrayList<>();

    private DishDAO dishDAO;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    // Charts
    private BarChart barChartUsers;
    private LineChart lineChartPosts;
    private HorizontalBarChart barChartTopDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Bind views
        tvTotalDishes = findViewById(R.id.tvTotalDishes);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        tvTotalPosts = findViewById(R.id.tvTotalPosts);
        rvRecent = findViewById(R.id.rvRecentActivity);

        barChartUsers = findViewById(R.id.barChartUsers);
        lineChartPosts = findViewById(R.id.lineChartPosts);
        barChartTopDishes = findViewById(R.id.barChartTopDishes);

        rvRecent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecentActivityAdapter(activityList);
        rvRecent.setAdapter(adapter);

        dishDAO = new DishDAO(this);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadTotalCounts();
        loadRecentActivity();

        // Charts
        setupUserChartFromFirestore();
        setupPostChartFromFirestore();
        setupTopDishesChartFromFirestore();
    }
// lấy dữ liệu từ firebase và sqlite
    private void loadTotalCounts() {
        // Total Dishes (SQLite)
        int totalDishes = dishDAO.getTotalDishes();
        tvTotalDishes.setText("Total Dishes: " + totalDishes);

        // Total Users (Firestore)
        db.collection("users").get().addOnSuccessListener(snap ->
                tvTotalUsers.setText("Total Users: " + snap.size())
        );

        // Total Posts (Firestore)
        db.collection("posts").get().addOnSuccessListener(snap ->
                tvTotalPosts.setText("Total Posts: " + snap.size())
        );
    }
//lấy dữ liệu hoạt động gần nhất
    private void loadRecentActivity() {
        activityList.clear();

        db.collection("posts")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnSuccessListener(posts -> {
                    for (DocumentSnapshot doc : posts) {
                        activityList.add("New Post: " + doc.getString("content"));
                    }
                    adapter.notifyDataSetChanged();
                });

        db.collection("users")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnSuccessListener(users -> {
                    for (DocumentSnapshot doc : users) {
                        activityList.add("New User: " + doc.getString("email"));
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    // ================== Charts ================== //

    private void setupUserChartFromFirestore() {
        db.collection("users").get().addOnSuccessListener(snap -> {
            Map<Integer, Integer> monthCounts = new HashMap<>();

            for (DocumentSnapshot doc : snap) {
                Timestamp ts = doc.getTimestamp("createdAt");
                if (ts != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(ts.toDate());
                    int month = cal.get(Calendar.MONTH) + 1; // chỉ lấy số tháng
                    monthCounts.put(month, monthCounts.getOrDefault(month, 0) + 1);
                }
            }

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            int i = 0;
            for (Map.Entry<Integer, Integer> e : monthCounts.entrySet()) {
                entries.add(new BarEntry(i, e.getValue()));
                labels.add(String.valueOf(e.getKey())); // hiển thị tháng (8, 9, ...)
                i++;
            }

            BarDataSet dataSet = new BarDataSet(entries, "User mới theo tháng");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            BarData data = new BarData(dataSet);

            barChartUsers.setData(data);

            // X axis
            XAxis xAxis = barChartUsers.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setGranularity(1f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            // ✅ Y axis: chỉ hiển thị số nguyên
            YAxis yAxis = barChartUsers.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setGranularity(1f);
            yAxis.setGranularityEnabled(true);
            yAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return String.valueOf((int) value);
                }
            });
            barChartUsers.getAxisRight().setEnabled(false);

            barChartUsers.getDescription().setEnabled(false);
            barChartUsers.invalidate();
        });
    }

    private void setupPostChartFromFirestore() {
        db.collection("posts").get().addOnSuccessListener(snap -> {
            Map<String, Integer> dayCounts = new HashMap<>();

            for (DocumentSnapshot doc : snap) {
                Timestamp ts = doc.getTimestamp("createdAt");
                if (ts != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(ts.toDate());

                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH) + 1; // tháng (1-12)
                    String key = day + "/" + month; // ngày/tháng

                    dayCounts.put(key, dayCounts.getOrDefault(key, 0) + 1);
                }
            }

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            int i = 0;
            for (Map.Entry<String, Integer> e : dayCounts.entrySet()) {
                entries.add(new Entry(i, e.getValue()));
                labels.add(e.getKey()); // ví dụ "29/8"
                i++;
            }

            LineDataSet dataSet = new LineDataSet(entries, "Bài post theo ngày");
            dataSet.setColor(Color.BLUE);
            dataSet.setCircleColor(Color.RED);
            LineData data = new LineData(dataSet);

            lineChartPosts.setData(data);

            // X axis hiển thị ngày/tháng
            XAxis xAxis = lineChartPosts.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setGranularity(1f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            // Trục Y chỉ hiển thị số nguyên
            YAxis yAxis = lineChartPosts.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setGranularity(1f);
            yAxis.setGranularityEnabled(true);
            yAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return String.valueOf((int) value);
                }
            });
            lineChartPosts.getAxisRight().setEnabled(false);

            lineChartPosts.getDescription().setEnabled(false);
            lineChartPosts.invalidate();
        });
    }


    private void setupTopDishesChartFromFirestore() {
        db.collection("users").get().addOnSuccessListener(usersSnap -> {
            final Map<String, Integer> dishCounts = new HashMap<>();
            final int totalUsers = usersSnap.size();
            final int[] processedUsers = {0};

            for (DocumentSnapshot userDoc : usersSnap) {
                userDoc.getReference().collection("saved_dishes")
                        .get()
                        .addOnSuccessListener(savedSnap -> {
                            for (DocumentSnapshot dishDoc : savedSnap) {
                                String dishName = dishDoc.getString("name");
                                if (dishName != null) {
                                    dishCounts.put(dishName, dishCounts.getOrDefault(dishName, 0) + 1);
                                }
                            }

                            processedUsers[0]++;

                            if (processedUsers[0] == totalUsers) {
                                renderTopDishesChart(dishCounts);
                            }
                        });
            }
        });
    }

    private void renderTopDishesChart(Map<String, Integer> dishCounts) {
        if (dishCounts.isEmpty()) {
            barChartTopDishes.clear();
            barChartTopDishes.setNoDataText("Không có dữ liệu món ăn được lưu");
            return;
        }

        // Top 5
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(dishCounts.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        if (list.size() > 5) list = new ArrayList<>(list.subList(0, 5));

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Integer> e : list) {
            entries.add(new BarEntry(i, e.getValue()));
            labels.add(e.getKey());
            i++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Top món ăn được lưu");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataSet);

        barChartTopDishes.setData(data);

        // X axis (tên món)
        XAxis xAxis = barChartTopDishes.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // ✅ Y axis: chỉ hiển thị số nguyên
        YAxis yAxis = barChartTopDishes.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }
        });
        barChartTopDishes.getAxisRight().setEnabled(false);

        barChartTopDishes.getDescription().setEnabled(false);
        barChartTopDishes.invalidate();
    }
}
