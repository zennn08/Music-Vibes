package com.example.musicvibes;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class PostDetailActivity extends AppCompatActivity {
    TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Aktifkan tombol back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView title = findViewById(R.id.tvTitle);
        contentText = findViewById(R.id.tvContent);
        ImageView image = findViewById(R.id.ivImage);
        TextView publishDate = findViewById(R.id.tvDate);

        String postTitle = getIntent().getStringExtra("title");
        String htmlContent = getIntent().getStringExtra("content");
        String imageUrl = getIntent().getStringExtra("image");
        String date = getIntent().getStringExtra("publishDate");

        title.setText(postTitle);
        publishDate.setText(date);
        Glide.with(this).load(imageUrl).into(image);

        contentText.setMovementMethod(LinkMovementMethod.getInstance());
        Spanned htmlSpanned = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY,
                new GlideImageGetter(contentText, this), null);
        contentText.setText(htmlSpanned);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}