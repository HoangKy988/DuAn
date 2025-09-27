package com.example.chubby.ui.feed;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.chubby.R;

public class BlogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.blog_container, new BlogFragment())
                    .commit();
        }
    }
}
