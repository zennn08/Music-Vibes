package com.example.musicvibes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PostModel> postList = new ArrayList<>();
    PostAdapter adapter;
    final String API_URL = "https://www.musicvibes.web.id/wp-json/wp/v2/posts?per_page=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PostAdapter(this, postList, post -> {
            Intent intent = new Intent(this, PostDetailActivity.class);
            intent.putExtra("title", post.title);
            intent.putExtra("content", post.content);
            intent.putExtra("image", post.imageUrl);
            intent.putExtra("publishDate", post.publishDate);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fetchPosts();
    }

    void fetchPosts() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String title = obj.getJSONObject("title").getString("rendered");
                            String content = obj.getJSONObject("content").getString("rendered");
                            String excerpt  = obj.getJSONObject("excerpt").getString("rendered");
                            String imageUrl = obj.optString("jetpack_featured_media_url");
                            String dateString = obj.getString("date");
                            String formattedDate = formatDate(dateString);

                            excerpt = excerpt.replaceAll("<.*?>", "").trim();
                            if (excerpt.endsWith("[&hellip;]")) {
                                excerpt = excerpt.replace("[&hellip;]", "...");
                            }

                            postList.add(new PostModel(title, content, excerpt, imageUrl, formattedDate));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Fetch error", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return dateString;
        }
    }
}