package com.example.chubby.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.chubby.R;
import com.example.chubby.data.DatabaseHelper;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class DishDAO {
    private final DatabaseHelper dbHelper;

    public DishDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // ================= INSERT (Admin thêm món mới) ================= //
    public void insertDish(Dish dish) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Nếu chưa có id thì tự sinh UUID
        if (dish.getId() == null || dish.getId().trim().isEmpty()) {
            dish.setId(UUID.randomUUID().toString());
        }

        ContentValues values = new ContentValues();
        values.put("id", dish.getId());
        values.put(DatabaseHelper.COL_NAME, dish.getName());

        if (dish.getImageData() != null) {
            values.put("imageData", dish.getImageData()); // lưu ảnh gallery (BLOB)
        } else {
            values.put("imageResId", dish.getImageResId()); // lưu id ảnh drawable
        }

        values.put(DatabaseHelper.COL_TYPE, dish.getType());
        values.put(DatabaseHelper.COL_DESC, dish.getDescription());
        values.put(DatabaseHelper.COL_INGREDIENTS, dish.getIngredients());
        values.put(DatabaseHelper.COL_RECIPE, dish.getRecipe());
        values.put(DatabaseHelper.COL_TIME, dish.getCookTime());

        long rowId = db.insert(DatabaseHelper.TABLE_DISH, null, values);
        if (rowId == -1) {
            Log.e("DishDAO", "Insert thất bại cho dish: " + dish.getName());
        } else {
            Log.d("DishDAO", "Insert thành công dish: " + dish.getName() + " (id=" + dish.getId() + ")");
        }
        db.close();
    }

    // ================= INSERT SAMPLE ================= //
    public void insertSampleData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Risotto
        if (!existsInDB(db, "sample_risotto")) {
            insertDish(db, "sample_risotto", "Risotto nấm", R.drawable.risotto, "Featured",
                    "Risotto nấm kem béo, mềm mịn, hương vị đậm đà.",
                    "Gạo Arborio, nấm, rượu vang trắng, phô mai Parmesan, bơ",
                    "Xào nấm, cho gạo, thêm rượu vang + nước dùng, khuấy đến khi mềm, thêm phô mai.",
                    350);
        }

        // Phở bò
        if (!existsInDB(db, "sample_phobo")) {
            insertDish(db, "sample_phobo", "Phở bò", R.drawable.phobo, "Near",
                    "Phở bò Việt Nam thơm ngon, nước dùng trong, ngọt thanh.",
                    "Bánh phở, thịt bò, hành, gừng, quế, hồi",
                    "Ninh xương, trụng phở, thêm thịt bò, chan nước dùng.",
                    480);
        }

        // Salad Hy Lạp
        if (!existsInDB(db, "sample_salad")) {
            insertDish(db, "sample_salad", "Salad Hy Lạp", R.drawable.currygreen, "Clean",
                    "Salad rau củ với phô mai feta, dầu olive, thanh mát.",
                    "Dưa chuột, cà chua, hành tây, phô mai feta, olive",
                    "Thái rau, trộn dầu olive, rắc phô mai feta.",
                    180);
        }
        // Mì xào bò
        if (!existsInDB(db, "sample_mixaobo")) {
            insertDish(db, "sample_mixaobo", "Mì xào bò", R.drawable.mixaobo, "Featured",
                    "Mì xào bò mềm dai, đậm đà vị thịt bò và rau củ.",
                    "Mì, thịt bò, cà rốt, hành tây, nước tương, tiêu, dầu hào",
                    "1. Luộc mì.\n2. Xào thịt bò với gia vị.\n3. Thêm rau củ, mì vào xào chung.\n4. Nêm nếm và tắt bếp.",
                    30);
        }

        // Trứng chiên
        if (!existsInDB(db, "sample_trungchien")) {
            insertDish(db, "sample_trungchien", "Trứng chiên", R.drawable.trungchien, "Featured",
                    "Trứng chiên vàng ươm, mềm mịn, có thể thêm hành hoặc cà chua.",
                    "Trứng, hành lá, muối, tiêu, dầu ăn",
                    "1. Đánh trứng đều với gia vị.\n2. Chiên trên chảo nóng đến khi vàng.",
                    10);
        }

        // Sườn chua ngọt
        if (!existsInDB(db, "sample_suonchuangot")) {
            insertDish(db, "sample_suonchuangot", "Sườn chua ngọt", R.drawable.suonchuangot, "Near",
                    "Sườn heo được kho mềm, sốt chua ngọt hấp dẫn.",
                    "Sườn heo, đường, giấm, nước mắm, tỏi, ớt",
                    "1. Ướp sườn với gia vị.\n2. Kho sườn với đường và giấm đến khi mềm và nước sốt sệt lại.",
                    50);
        }

        // Cơm chiên trứng
        if (!existsInDB(db, "sample_comchientrung")) {
            insertDish(db, "sample_comchientrung", "Cơm chiên trứng", R.drawable.comchientrung, "Featured",
                    "Món cơm chiên thơm ngon với trứng và hành lá.",
                    "Cơm, trứng, hành lá, dầu ăn, muối, tiêu",
                    "1. Đánh trứng.\n2. Phi hành.\n3. Cho cơm vào xào cùng trứng.\n4. Nêm gia vị vừa ăn.",
                    15);
        }

        // Thịt kho trứng
        if (!existsInDB(db, "sample_thitkhotrung")) {
            insertDish(db, "sample_thitkhotrung", "Thịt kho trứng", R.drawable.thikhotrung, "Near",
                    "Thịt ba chỉ kho mềm, thấm đậm đà, ăn kèm trứng cút béo ngậy.",
                    "Thịt ba chỉ, trứng cút, nước dừa, hành tím, đường, nước mắm, tiêu",
                    "1. Luộc trứng cút.\n2. Ướp thịt với gia vị.\n3. Kho thịt với nước dừa, đường đến khi thịt mềm.\n4. Thêm trứng vào kho cùng, nêm nếm lại.",
                    60);
        }

        // Cá chiên giòn
        if (!existsInDB(db, "sample_cachien")) {
            insertDish(db, "sample_cachien", "Cá chiên giòn", R.drawable.cachien, "Near",
                    "Cá chiên vàng giòn bên ngoài, thịt bên trong mềm ngọt.",
                    "Cá, bột chiên giòn, muối, tiêu, dầu ăn",
                    "1. Rửa sạch cá, ướp gia vị.\n2. Lăn cá qua bột chiên giòn.\n3. Chiên ngập dầu đến khi vàng giòn.",
                    25);
        }

        // Gà kho gừng
        if (!existsInDB(db, "sample_gakhogung")) {
            insertDish(db, "sample_gakhogung", "Gà kho gừng", R.drawable.gakhogunf, "Near",
                    "Gà kho gừng thơm nồng, cay nhẹ, rất hấp dẫn.",
                    "Gà, gừng, nước mắm, đường, tiêu, hành tím",
                    "1. Ướp gà với gia vị và gừng.\n2. Kho gà với lửa nhỏ đến khi nước cạn sệt.\n3. Nêm nếm lại cho vừa ăn.",
                    45);
        }

        // Sushi
        if (!existsInDB(db, "sample_sushi")) {
            insertDish(db, "sample_sushi", "Sushi", R.drawable.sushi, "Near",
                    "Món cơm cuộn rong biển nổi tiếng.",
                    "Cơm, rong biển, cá hồi, rau củ",
                    "1. Trải rong biển.\n2. Dàn cơm.\n3. Thêm cá hồi, rau.\n4. Cuộn chặt và cắt miếng.",
                    200);
        }

        // Pizza Margherita
        if (!existsInDB(db, "sample_pizza")) {
            insertDish(db, "sample_pizza", "Pizza Margherita", R.drawable.pizza, "Near",
                    "Pizza truyền thống với phô mai và cà chua.",
                    "Bột mì, cà chua, phô mai mozzarella, húng quế",
                    "1. Làm đế pizza.\n2. Thêm sốt cà chua, phô mai.\n3. Nướng ở 220°C trong 15 phút.",
                    266);
        }

        // Tacos
        if (!existsInDB(db, "sample_tacos")) {
            insertDish(db, "sample_tacos", "Tacos", R.drawable.tacos, "Near",
                    "Món bánh ngô kẹp thịt và rau.",
                    "Vỏ bánh ngô, thịt bò, rau, phô mai, salsa",
                    "1. Làm nóng vỏ bánh.\n2. Thêm nhân thịt, rau.\n3. Rưới salsa và ăn kèm.",
                    230);
        }

        // Curry Gà
        if (!existsInDB(db, "sample_curryga")) {
            insertDish(db, "sample_curryga", "Curry Gà", R.drawable.curryga, "Near",
                    "Món cà ri cay thơm đặc trưng.",
                    "Thịt gà, khoai tây, bột cà ri, nước cốt dừa",
                    "1. Xào gà với gia vị.\n2. Thêm khoai và nước cốt dừa.\n3. Nấu đến khi chín mềm.",
                    320);
        }

        // Hamburger
        if (!existsInDB(db, "sample_hamburger")) {
            insertDish(db, "sample_hamburger", "Hamburger", R.drawable.hamberger, "Near",
                    "Bánh mì kẹp thịt bò và rau củ.",
                    "Bánh mì, thịt bò, phô mai, rau xà lách, cà chua",
                    "1. Nướng thịt bò.\n2. Kẹp cùng bánh mì, rau và phô mai.\n3. Ăn kèm sốt tuỳ thích.",
                    295);
        }

        // Risotto nấm
        if (!existsInDB(db, "sample_risotto")) {
            insertDish(db, "sample_risotto", "Risotto nấm", R.drawable.risotto, "Near",
                    "Risotto nấm kem béo, mềm mịn, hương vị đậm đà.",
                    "Gạo Arborio, nấm, rượu vang trắng, phô mai Parmesan, bơ",
                    "1. Xào nấm với bơ.\n2. Cho gạo vào đảo đều.\n3. Thêm rượu vang và nước dùng từ từ.\n4. Khuấy đến khi gạo chín mềm.\n5. Thêm phô mai và bơ, trộn đều.",
                    350);
        }

        // Burrito
        if (!existsInDB(db, "sample_burrito")) {
            insertDish(db, "sample_burrito", "Burrito", R.drawable.burrito, "Near",
                    "Burrito Mexico cuộn nhân thịt, cơm, đậu, rau củ.",
                    "Bánh tortilla, thịt bò, cơm, đậu, rau củ",
                    "1. Làm nóng bánh tortilla.\n2. Thêm nhân thịt, cơm, đậu.\n3. Cuộn chặt.",
                    500);
        }

        // Cơm gà Hải Nam
        if (!existsInDB(db, "sample_comga")) {
            insertDish(db, "sample_comga", "Cơm gà Hải Nam", R.drawable.comga, "Near",
                    "Cơm gà Hải Nam thơm mềm, ăn kèm sốt gừng.",
                    "Gà, cơm gạo thơm, gừng, hành lá",
                    "1. Luộc gà.\n2. Nấu cơm bằng nước luộc gà.\n3. Thái gà, chan cơm, thêm sốt.",
                    320);
        }

        // Vịt quay Bắc Kinh
        if (!existsInDB(db, "sample_vitquay")) {
            insertDish(db, "sample_vitquay", "Vịt quay Bắc Kinh", R.drawable.vitquay, "Near",
                    "Vịt quay Bắc Kinh da giòn, thịt mềm, ăn kèm bánh tráng mỏng.",
                    "Vịt, gia vị ướp, bánh tráng mỏng, dưa leo",
                    "1. Ướp vịt.\n2. Quay giòn.\n3. Thái lát, ăn kèm bánh tráng và dưa leo.",
                    600);
        }
        // Canh chua cá lóc
        if (!existsInDB(db, "sample_canhca")) {
            insertDish(db, "sample_canhca", "Canh chua cá lóc", R.drawable.canhca, "Clean",
                    "Canh chua cá lóc đậm đà vị chua thanh, ngon cơm.",
                    "Cá lóc, me, cà chua, bạc hà, giá, rau thơm, gia vị",
                    "1. Nấu nước dùng với me và gia vị.\n2. Cho cá lóc vào nấu.\n3. Thêm rau và rau thơm trước khi tắt bếp.",
                    40);
        }

        // Canh rong biển
        if (!existsInDB(db, "sample_canhrong")) {
            insertDish(db, "sample_canhrong", "Canh rong biển", R.drawable.canhrong, "Clean",
                    "Canh rong biển thanh mát, giàu dinh dưỡng.",
                    "Rong biển khô, đậu hũ, hành lá, tỏi, nước dùng, gia vị",
                    "1. Ngâm rong biển.\n2. Nấu nước dùng.\n3. Cho rong biển và đậu hũ vào nấu.\n4. Nêm nếm gia vị, cho hành lá vào.",
                    25);
        }

        // Canh ngao chua
        if (!existsInDB(db, "sample_canhngao")) {
            insertDish(db, "sample_canhngao", "Canh ngao chua", R.drawable.canhngao, "Clean",
                    "Canh ngao chua thanh mát, dễ ăn.",
                    "Ngao, cà chua, me, hành lá, ngò rí, gia vị",
                    "1. Sơ chế ngao.\n2. Nấu nước dùng với me.\n3. Cho ngao và cà chua vào nấu.\n4. Nêm nếm gia vị, thêm hành lá, ngò rí.",
                    30);
        }

        // Salad rau củ
        if (!existsInDB(db, "sample_salad")) {
            insertDish(db, "sample_salad", "Salad rau củ", R.drawable.salad, "Clean",
                    "Salad rau củ tươi ngon, giàu vitamin.",
                    "Xà lách, cà chua, dưa leo, dầu ôliu, giấm",
                    "1. Rửa rau củ.\n2. Trộn dầu ôliu, giấm.\n3. Trộn đều và dùng ngay.",
                    10);
        }

        // Ratatouille
        if (!existsInDB(db, "sample_ratatouille")) {
            insertDish(db, "sample_ratatouille", "Ratatouille", R.drawable.ratatouille, "Clean",
                    "Món hầm rau củ nổi tiếng của Pháp, thanh đạm và đầy màu sắc.",
                    "Cà tím, bí ngòi, ớt chuông, cà chua, hành tây, tỏi, dầu ô liu, gia vị",
                    "1. Thái lát mỏng các loại rau củ.\n2. Xếp xen kẽ vào khay nướng.\n3. Rưới sốt cà chua, nêm gia vị.\n4. Nướng chín mềm, thơm.",
                    150);
        }

        // Đậu hũ hấp
        if (!existsInDB(db, "sample_dauhu")) {
            insertDish(db, "sample_dauhu", "Đậu hũ hấp", R.drawable.dauhu, "Clean",
                    "Đậu hũ hấp mềm, thanh đạm.",
                    "Đậu hũ non, hành lá, nước tương",
                    "1. Cắt đậu hũ.\n2. Hấp chín.\n3. Rưới nước tương, rắc hành.",
                    20);
        }

        // Pad Thai
        if (!existsInDB(db, "sample_padthai")) {
            insertDish(db, "sample_padthai", "Pad Thai", R.drawable.padthai, "Clean",
                    "Mì xào chua ngọt với tôm.",
                    "Bánh phở, tôm, giá đỗ, trứng, sốt me",
                    "1. Xào trứng, tôm.\n2. Cho phở, giá đỗ và sốt me.\n3. Trộn đều và thưởng thức.",
                    340);
        }

        // Cà ri xanh
        if (!existsInDB(db, "sample_currygreen")) {
            insertDish(db, "sample_currygreen", "Cà ri xanh", R.drawable.currygreen, "Clean",
                    "Cà ri xanh cay béo, nấu với nước cốt dừa và thịt gà.",
                    "Thịt gà, cà ri xanh, nước cốt dừa, lá chanh",
                    "1. Xào cà ri xanh.\n2. Cho thịt gà.\n3. Thêm nước cốt dừa, lá chanh.",
                    450);
        }

        // Súp bí đỏ
        if (!existsInDB(db, "sample_supbido")) {
            insertDish(db, "sample_supbido", "Súp bí đỏ", R.drawable.supbido, "Clean",
                    "Súp bí đỏ ngọt dịu, giàu vitamin A.",
                    "Bí đỏ, sữa tươi không đường, hành tây",
                    "1. Hấp bí đỏ.\n2. Xay nhuyễn cùng sữa.\n3. Nêm nếm vừa ăn.",
                    70);
        }

        // Rau muống xào tỏi
        if (!existsInDB(db, "sample_raumuong")) {
            insertDish(db, "sample_raumuong", "Rau muống xào tỏi", R.drawable.raumuongxaotoi, "Clean",
                    "Rau muống giòn xanh, thơm mùi tỏi.",
                    "Rau muống, tỏi, dầu ăn ít",
                    "1. Luộc sơ rau.\n2. Xào nhanh với tỏi.\n3. Nêm nếm vừa ăn.",
                    45);
        }

        // Bí xanh luộc
        if (!existsInDB(db, "sample_bixanh")) {
            insertDish(db, "sample_bixanh", "Bí xanh luộc", R.drawable.bixanhluoc, "Clean",
                    "Bí xanh luộc ngọt mát, dễ tiêu.",
                    "Bí xanh, muối",
                    "1. Gọt vỏ bí.\n2. Luộc chín.\n3. Dùng kèm nước chấm.",
                    25);
        }

        // Gỏi cuốn chay
        if (!existsInDB(db, "sample_goicuon")) {
            insertDish(db, "sample_goicuon", "Gỏi cuốn chay", R.drawable.goicuonchay, "Clean",
                    "Gỏi cuốn thanh đạm, ăn không ngán.",
                    "Bánh tráng, rau sống, bún, đậu hũ chiên",
                    "1. Chuẩn bị nguyên liệu.\n2. Cuốn lại.\n3. Chấm nước tương pha.",
                    90);
        }

        // Cháo yến mạch rau củ
        if (!existsInDB(db, "sample_chaoyenmach")) {
            insertDish(db, "sample_chaoyenmach", "Cháo yến mạch rau củ", R.drawable.chaoyenmach, "Clean",
                    "Cháo yến mạch bổ dưỡng, nhiều chất xơ.",
                    "Yến mạch, cà rốt, bí đỏ, muối",
                    "1. Nấu yến mạch.\n2. Thêm rau củ.\n3. Nêm nhạt vừa ăn.",
                    100);
        }

        // Miến xào rau củ
        if (!existsInDB(db, "sample_mienxao")) {
            insertDish(db, "sample_mienxao", "Miến xào rau củ", R.drawable.mienxaoraucu, "Clean",
                    "Miến dai mềm, nhiều rau củ tươi ngon.",
                    "Miến, cà rốt, cải ngọt, nấm",
                    "1. Ngâm miến.\n2. Xào rau.\n3. Trộn miến vào, nêm nếm.",
                    120);
        }

        // Súp lơ hấp
        if (!existsInDB(db, "sample_suplohap")) {
            insertDish(db, "sample_suplohap", "Súp lơ hấp", R.drawable.suplohap, "Clean",
                    "Súp lơ hấp giữ nguyên vị ngọt tự nhiên.",
                    "Súp lơ xanh, muối, dầu oliu",
                    "1. Rửa súp lơ.\n2. Hấp chín.\n3. Rưới chút dầu oliu.",
                    35);
        }

        // Đậu que xào dầu hào chay
        if (!existsInDB(db, "sample_dauque")) {
            insertDish(db, "sample_dauque", "Đậu que xào dầu hào chay", R.drawable.dauquexao, "Clean",
                    "Đậu que giòn ngọt, thấm gia vị.",
                    "Đậu que, dầu hào chay, tỏi",
                    "1. Rửa đậu que.\n2. Xào nhanh.\n3. Nêm dầu hào chay.",
                    55);
        }

        // Khoai lang luộc
        if (!existsInDB(db, "sample_khoailang")) {
            insertDish(db, "sample_khoailang", "Khoai lang luộc", R.drawable.khoailangluoc, "Clean",
                    "Khoai lang ngọt bùi, no lâu.",
                    "Khoai lang",
                    "1. Rửa sạch.\n2. Luộc chín.\n3. Ăn nóng.",
                    80);
        }

        // Cà tím nướng mỡ hành
        if (!existsInDB(db, "sample_catim")) {
            insertDish(db, "sample_catim", "Cà tím nướng mỡ hành", R.drawable.catim, "Clean",
                    "Cà tím mềm ngọt, thơm hành lá.",
                    "Cà tím, hành lá, nước tương",
                    "1. Nướng cà tím.\n2. Rưới mỡ hành chay.\n3. Ăn kèm nước chấm.",
                    60);
        }

        // Canh mướp nấu nấm
        if (!existsInDB(db, "sample_canhmuop")) {
            insertDish(db, "sample_canhmuop", "Canh mướp nấu nấm", R.drawable.canhmuop, "Clean",
                    "Canh mướp ngọt mát, nấm dai giòn.",
                    "Mướp, nấm rơm, hành lá",
                    "1. Gọt mướp.\n2. Nấu với nấm.\n3. Nêm nếm.",
                    50);
        }

        // Salad đậu gà
        if (!existsInDB(db, "sample_saladdauga")) {
            insertDish(db, "sample_saladdauga", "Salad đậu gà", R.drawable.saladdauga, "Clean",
                    "Salad đậu gà giàu đạm thực vật.",
                    "Đậu gà, rau xà lách, chanh, dầu oliu",
                    "1. Luộc đậu gà.\n2. Trộn với rau.\n3. Thêm nước sốt chanh.",
                    110);
        }

        // Súp cà rốt
        if (!existsInDB(db, "sample_supcarot")) {
            insertDish(db, "sample_supcarot", "Súp cà rốt", R.drawable.supcarot, "Clean",
                    "Súp cà rốt ngọt dịu, đẹp da.",
                    "Cà rốt, sữa hạt, muối",
                    "1. Hấp cà rốt.\n2. Xay nhuyễn.\n3. Nêm nhạt vừa ăn.",
                    65);
        }
        // Canh ngao chua
        if (!existsInDB(db, "sample_canhngao")) {
            insertDish(db, "sample_canhngao", "Canh ngao chua", R.drawable.canhngao, "Clean",
                    "Canh ngao thanh mát, vị chua dịu nhẹ, rất thích hợp cho mùa hè.",
                    "Ngao, cà chua, dứa, me chua, hành lá, ngò rí, gia vị",
                    "1. Sơ chế ngao, rửa sạch.\n2. Xào cà chua, dứa.\n3. Đun nước dùng cùng me chua.\n4. Cho ngao vào nấu đến khi ngao mở miệng.\n5. Nêm nếm gia vị và thêm hành lá, ngò rí.",
                    30);
        }

        db.close();
    }

    // Hàm kiểm tra id có trong DB chưa
    private boolean existsInDB(SQLiteDatabase db, String id) {
        Cursor c = db.query(DatabaseHelper.TABLE_DISH,
                new String[]{"id"}, "id=?", new String[]{id},
                null, null, null);
        boolean exists = (c != null && c.moveToFirst());
        if (c != null) c.close();
        return exists;
    }




    private void insertDish(SQLiteDatabase db, String id, String name, int imageRes, String type,
                            String desc, String ingredients, String recipe, int time) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put(DatabaseHelper.COL_NAME, name);
        cv.put("imageResId", imageRes);   // ảnh resource
        cv.put(DatabaseHelper.COL_TYPE, type);
        cv.put(DatabaseHelper.COL_DESC, desc);
        cv.put(DatabaseHelper.COL_INGREDIENTS, ingredients);
        cv.put(DatabaseHelper.COL_RECIPE, recipe);
        cv.put(DatabaseHelper.COL_TIME, time);

        long rowId = db.insert(DatabaseHelper.TABLE_DISH, null, cv);
        Log.d("DishDAO", "Insert mẫu " + name + " rowId=" + rowId + " id=" + id);
    }

    // ================= GET ALL ================= //
    public List<Dish> getAllDishes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Dish> list = new ArrayList<>();
        Cursor c = db.query(DatabaseHelper.TABLE_DISH, null, null, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                list.add(cursorToDish(c));
            }
            c.close();
        }
        db.close();
        return list;
    }

    public List<Dish> getFeaturedDishes() {
        return getDishesByCategory("Featured");
    }

    public List<Dish> getNearDishes() {
        return getDishesByCategory("Near");
    }

    public List<Dish> getCleanDishes() {
        return getDishesByCategory("Clean");
    }

    private List<Dish> getDishesByCategory(String cat) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Dish> list = new ArrayList<>();
        Cursor c = db.query(DatabaseHelper.TABLE_DISH, null,
                DatabaseHelper.COL_TYPE + "=?", new String[]{cat}, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                list.add(cursorToDish(c));
            }
            c.close();
        }
        db.close();
        return list;
    }

    // ================= GET ONE ================= //
    public Dish getDishById(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Dish dish = null;
        Cursor c = db.query(DatabaseHelper.TABLE_DISH, null,
                "id=?", new String[]{id}, null, null, null);
        if (c != null && c.moveToFirst()) {
            dish = cursorToDish(c);
            c.close();
        }
        db.close();
        return dish;
    }
    // ================= COUNT ================= //
    public int getTotalDishes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_DISH, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    public Dish getDishByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Dish dish = null;
        Cursor c = db.query(DatabaseHelper.TABLE_DISH, null,
                DatabaseHelper.COL_NAME + "=?", new String[]{name}, null, null, null);
        if (c != null && c.moveToFirst()) {
            dish = cursorToDish(c);
            c.close();
        }
        db.close();
        return dish;
    }

    // ================= Convert Cursor -> Dish ================= //
    private Dish cursorToDish(Cursor c) {
        String id   = c.getString(c.getColumnIndexOrThrow("id"));
        String name = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_NAME));
        String type = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_TYPE));
        String desc = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_DESC));
        String ing  = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_INGREDIENTS));
        String recipe = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE));
        int time   = c.getInt(c.getColumnIndexOrThrow(DatabaseHelper.COL_TIME));

        int imageResId = -1;
        byte[] imageBytes = null;

        int idxData = c.getColumnIndex("imageData");
        if (idxData != -1) {
            imageBytes = c.getBlob(idxData);
        }

        int idxRes = c.getColumnIndex("imageResId");
        if (idxRes != -1) {
            imageResId = c.getInt(idxRes);
        }

        if (imageBytes != null && imageBytes.length > 0) {
            return new Dish(id, name, imageBytes, type, desc, ing, recipe, time);
        } else {
            return new Dish(id, name, imageResId, type, desc, ing, recipe, time);
        }
    }

    // ================= SEARCH ================= //
    public List<Dish> searchByIngredient(String query) {
        if (query == null || query.trim().isEmpty()) return getAllDishes();

        String q = normalizeVN(query);
        List<Dish> all = getAllDishes();
        List<Dish> result = new ArrayList<>();

        for (Dish d : all) {
            List<String> tokens = splitIngredientPhrases(d.getIngredients());
            for (String t : tokens) {
                if (t.equals(q)) {
                    result.add(d);
                    break;
                }
            }
        }
        return result;
    }

    public List<Dish> searchByIngredients(String queryCsv) {
        if (queryCsv == null || queryCsv.trim().isEmpty()) return getAllDishes();

        String[] parts = queryCsv.split(",");
        List<String> needs = new ArrayList<>();
        for (String p : parts) {
            String n = normalizeVN(p);
            if (!n.isEmpty()) needs.add(n);
        }
        if (needs.isEmpty()) return getAllDishes();

        List<Dish> all = getAllDishes();
        List<Dish> out = new ArrayList<>();

        for (Dish d : all) {
            List<String> tokens = splitIngredientPhrases(d.getIngredients());
            boolean allMatch = true;
            for (String need : needs) {
                if (!tokens.contains(need)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) out.add(d);
        }
        return out;
    }

    // ================= UTILS ================= //
    private String normalizeVN(String s) {
        if (s == null) return "";
        String lower = s.toLowerCase(Locale.ROOT).trim();
        String norm = Normalizer.normalize(lower, Normalizer.Form.NFD);
        return norm.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private List<String> splitIngredientPhrases(String ingredients) {
        List<String> out = new ArrayList<>();
        if (ingredients == null || ingredients.trim().isEmpty()) return out;
        String[] phrases = ingredients.split("[,;]+");
        for (String ph : phrases) {
            String n = normalizeVN(ph);
            if (!n.isEmpty()) out.add(n);
        }
        return out;
    }
    // ================== CẬP NHẬT MÓN ĂN ================== //
    public int updateDish(Dish dish) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NAME, dish.getName());
        values.put(DatabaseHelper.COL_IMAGE, dish.getImageData()); // BLOB
        values.put(DatabaseHelper.COL_TYPE, dish.getType());
        values.put(DatabaseHelper.COL_DESC, dish.getDescription());
        values.put(DatabaseHelper.COL_INGREDIENTS, dish.getIngredients());
        values.put(DatabaseHelper.COL_RECIPE, dish.getRecipe());
        values.put(DatabaseHelper.COL_TIME, dish.getCookTime());

        // WHERE id = ?
        return db.update(DatabaseHelper.TABLE_DISH,
                values,
                DatabaseHelper.COL_ID + "=?",
                new String[]{dish.getId()});
    }
    public void deleteDish(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_DISH,
                DatabaseHelper.COL_ID + "=?",
                new String[]{id});
        db.close();
    }

}
