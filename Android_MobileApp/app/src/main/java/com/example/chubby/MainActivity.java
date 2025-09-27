package com.example.chubby;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chubby.auth.RoleManager;
import com.example.chubby.data.DishRepository;
import com.example.chubby.model.Dish;
import com.example.chubby.ui.saved.SavedFragment;
import com.example.chubby.ui.admin.AdminDashboardActivity; // ✅ thêm dòng này
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvFeatured, rvNear, rvClean;
    private ChipGroup chipGroup;
    private TextInputEditText edtSearch;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private View homeRoot;
    private BottomNavigationView bottomNav;

    private TextView tvFeatured, tvNear, tvClean;

    private DishRepository dishRepo;
    private DishAdapter featuredAdapter, nearAdapter, cleanAdapter;
    private List<Dish> featuredList, nearList, cleanList;

    private OnBackPressedCallback backCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        initViews();
        setupToolbarAndDrawer();
        setupNavigationDrawerMenu();
        setupSearchAndChips();
        setupRecyclerViews();
        setupBottomNavigation();

        backCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                    if (fm.getBackStackEntryCount() == 0) {
                        if (homeRoot != null) homeRoot.setVisibility(View.VISIBLE);
                        if (bottomNav != null) bottomNav.setSelectedItemId(R.id.nav_home);
                    }
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backCallback);

        if (savedInstanceState == null) {
            showHome();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
        if (current == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        populateDrawerHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadDishLists();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backCallback != null) backCallback.remove();
    }

    // ================== MENU APPBAR ================== //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        RoleManager.getCurrentUserRole(role -> {
            boolean isAdmin = "admin".equals(role);
            menu.findItem(R.id.action_add).setVisible(isAdmin);
            menu.findItem(R.id.action_dashboard).setVisible(isAdmin);
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            // ✅ Nút Thêm món
            startActivity(new Intent(this, CreateDishActivity.class));
            return true;
        } else if (id == R.id.action_dashboard) {
            // ✅ Nút Data
            startActivity(new Intent(this, AdminDashboardActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ================== INIT ================== //
    private void initViews() {
        drawerLayout  = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNav     = findViewById(R.id.bottom_nav);
        homeRoot      = findViewById(R.id.home_root);

        edtSearch = findViewById(R.id.edtSearch);
        chipGroup = findViewById(R.id.chipGroup);
        rvFeatured = findViewById(R.id.rvFeatured);
        rvNear = findViewById(R.id.rvNear);
        rvClean = findViewById(R.id.rvClean);

        tvFeatured = findViewById(R.id.tvFeatured);
        tvNear     = findViewById(R.id.tvNear);
        tvClean    = findViewById(R.id.tvClean);
    }

    private void setupToolbarAndDrawer() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setupNavigationDrawerMenu() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showToast("Home được chọn");
                showHome();
            } else if (id == R.id.nav_settings) {
                drawerLayout.closeDrawers();
                drawerLayout.postDelayed(this::showSettingsDialog, 250);
                return true;
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void showSettingsDialog() {
        final String[] options = {"Chỉnh sửa hồ sơ", "Chế độ sáng/tối"};

        new AlertDialog.Builder(this)
                .setTitle("Cài đặt")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        startActivity(new Intent(this, EditProfileActivity.class));
                    } else if (which == 1) {
                        int nightMode = AppCompatDelegate.getDefaultNightMode();
                        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ================== Drawer Header ================== //
    private void populateDrawerHeader() {
        if (navigationView == null) return;
        View header = navigationView.getHeaderView(0);
        if (header == null) return;

        TextView tvName  = header.findViewById(R.id.tvHeaderName);
        TextView tvEmail = header.findViewById(R.id.tvHeaderEmail);
        ImageView imgAvt = header.findViewById(R.id.imgHeaderAvatar);

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u == null) return;

        tvEmail.setText(u.getEmail() != null ? u.getEmail() : "");

        FirebaseFirestore.getInstance()
                .collection("users").document(u.getUid())
                .get()
                .addOnSuccessListener((DocumentSnapshot doc) -> {
                    String nameInUsers = (doc != null) ? doc.getString("name") : null;
                    String avatarUrl   = (doc != null) ? doc.getString("avatarUrl") : null;

                    String displayName =
                            !TextUtils.isEmpty(nameInUsers) ? nameInUsers :
                                    !TextUtils.isEmpty(u.getDisplayName()) ? u.getDisplayName() :
                                            (u.getEmail() != null && u.getEmail().contains("@")
                                                    ? u.getEmail().substring(0, u.getEmail().indexOf('@'))
                                                    : "Người dùng");

                    tvName.setText(displayName);

                    if (!TextUtils.isEmpty(avatarUrl)) {
                        Glide.with(this)
                                .load(avatarUrl)
                                .placeholder(R.drawable.ic_avatar_placeholder)
                                .error(R.drawable.ic_avatar_placeholder)
                                .circleCrop()
                                .into(imgAvt);
                    } else if (u.getPhotoUrl() != null) {
                        Glide.with(this)
                                .load(u.getPhotoUrl())
                                .placeholder(R.drawable.ic_avatar_placeholder)
                                .error(R.drawable.ic_avatar_placeholder)
                                .circleCrop()
                                .into(imgAvt);
                    } else {
                        imgAvt.setImageResource(R.drawable.ic_avatar_placeholder);
                    }
                });
    }

    // ================== Search & Chips ================== //
    private void setupSearchAndChips() {
        String[] chips = {"thịt bò", "cơm", "trứng", "cá", "gà"};
        for (String c : chips) {
            Chip chip = new Chip(this, null, com.google.android.material.R.style.Widget_Material3_Chip_Assist);
            chip.setText(c);
            chip.setOnClickListener(v -> {
                edtSearch.setText("ing:" + c);
                edtSearch.setSelection(edtSearch.getText() != null ? edtSearch.getText().length() : 0);
                applyFilter("ing:" + c);
            });
            chipGroup.addView(chip);
        }

        findViewById(R.id.btnSuggest).setOnClickListener(v -> {
            String q = Objects.toString(edtSearch.getText(), "").trim();
            if (q.isEmpty()) {
                showToast("Nhập nguyên liệu trước đã!");
            } else {
                if (!q.toLowerCase(Locale.ROOT).startsWith("ing:")) {
                    q = "ing:" + q;
                    edtSearch.setText(q);
                    edtSearch.setSelection(edtSearch.getText() != null ? edtSearch.getText().length() : 0);
                }
                applyFilter(q);
                showToast("Gợi ý theo nguyên liệu: " + q);
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                applyFilter(query);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // ================== RecyclerView ================== //
    private void setupRecyclerViews() {
        dishRepo = new DishRepository(this);

        rvFeatured.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvNear.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvClean.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        DishAdapter.OnDishClickListener clickListener = dish -> {
            Intent i = new Intent(MainActivity.this, DishDetailActivity.class);
            i.putExtra("dish_id", dish.getId());
            startActivity(i);
        };

        featuredAdapter = new DishAdapter(new ArrayList<>(), clickListener);
        nearAdapter     = new DishAdapter(new ArrayList<>(), clickListener);
        cleanAdapter    = new DishAdapter(new ArrayList<>(), clickListener);

        rvFeatured.setAdapter(featuredAdapter);
        rvNear.setAdapter(nearAdapter);
        rvClean.setAdapter(cleanAdapter);
    }

    // ================== Reload ================== //
    private void reloadDishLists() {
        dishRepo.getAllDishes(all -> {
            featuredList = new ArrayList<>();
            nearList     = new ArrayList<>();
            cleanList    = new ArrayList<>();

            for (Dish d : all) {
                if ("Featured".equalsIgnoreCase(d.getType())) {
                    featuredList.add(d);
                } else if ("Near".equalsIgnoreCase(d.getType())) {
                    nearList.add(d);
                } else if ("Clean".equalsIgnoreCase(d.getType())) {
                    cleanList.add(d);
                }
            }

            updateSection(featuredAdapter, featuredList, tvFeatured, rvFeatured);
            updateSection(nearAdapter,     nearList,     tvNear,     rvNear);
            updateSection(cleanAdapter,    cleanList,    tvClean,    rvClean);
        });
    }

    // ================== Filter ================== //
    private void applyFilter(String raw) {
        if (TextUtils.isEmpty(raw)) {
            reloadDishLists();
            return;
        }

        String kw = raw.toLowerCase(Locale.ROOT);

        if (kw.startsWith("ing:")) {
            String ingQuery = kw.substring(4).trim();
            dishRepo.searchByIngredients(ingQuery, matches -> {
                updateSection(featuredAdapter, filterByType(matches, "Featured"), tvFeatured, rvFeatured);
                updateSection(nearAdapter,     filterByType(matches, "Near"),     tvNear,     rvNear);
                updateSection(cleanAdapter,    filterByType(matches, "Clean"),    tvClean,    rvClean);

                if (matches.isEmpty()) {
                    showToast("Không tìm thấy món phù hợp nguyên liệu.");
                }
            });
        } else {
            dishRepo.searchByName(kw, matches -> {
                updateSection(featuredAdapter, filterByType(matches, "Featured"), tvFeatured, rvFeatured);
                updateSection(nearAdapter,     filterByType(matches, "Near"),     tvNear,     rvNear);
                updateSection(cleanAdapter,    filterByType(matches, "Clean"),    tvClean,    rvClean);

                if (matches.isEmpty()) {
                    showToast("Không tìm thấy món phù hợp tên tìm kiếm.");
                }
            });
        }

    }

    private List<Dish> filterByType(List<Dish> src, String type) {
        List<Dish> out = new ArrayList<>();
        if (src == null) return out;
        for (Dish d : src) {
            if (type.equalsIgnoreCase(d.getType())) {
                out.add(d);
            }
        }
        return out;
    }

    private void updateSection(DishAdapter adapter, List<Dish> data, TextView title, RecyclerView listRv) {
        adapter.updateData(data);
        boolean visible = data != null && !data.isEmpty();
        setSectionVisibility(title, listRv, visible);
    }

    private void setSectionVisibility(TextView title, RecyclerView listRv, boolean visible) {
        int v = visible ? View.VISIBLE : View.GONE;
        if (title  != null)  title.setVisibility(v);
        if (listRv != null) listRv.setVisibility(v);
    }

    // ================== BottomNav ================== //
    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                showHome();
                return true;
            } else if (id == R.id.nav_saved) {
                showSaved();
                return true;
            } else if (id == R.id.nav_list) {
                startActivity(new Intent(this, com.example.chubby.ui.feed.BlogActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, BmiActivity.class));
                return true;
            }
            return false;
        });
    }

    private void showHome() {
        if (homeRoot != null) homeRoot.setVisibility(View.VISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void showSaved() {
        if (homeRoot != null) homeRoot.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new SavedFragment())
                .addToBackStack("saved")
                .commit();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
