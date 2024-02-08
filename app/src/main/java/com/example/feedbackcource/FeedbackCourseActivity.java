package com.example.feedbackcource;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackCourseActivity extends AppCompatActivity {

    private TextView textViewFeedback;
    private ListView listViewFeedback;
    private ArrayAdapter<String> adapter;
    private List<String> courseList = new ArrayList<>();
    private ImageButton imageButtonAddFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_course);
        imageButtonAddFeedback = findViewById(R.id.imageButtonAddFeedback);
        imageButtonAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddFeedbackActivity when the + icon is clicked
                Intent intent = new Intent(FeedbackCourseActivity.this, AddFeedbackActivity.class);
                startActivity(intent);
            }
        });
        textViewFeedback = findViewById(R.id.textViewFeedback);
        listViewFeedback = findViewById(R.id.listViewFeedback);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        listViewFeedback.setAdapter(adapter);

        ImageButton addButton = findViewById(R.id.imageButtonAddFeedback);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewFeedback.setVisibility(View.VISIBLE);
            }
        });

        ImageButton searchButton = findViewById(R.id.imageButtonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = ((TextView) findViewById(R.id.editTextCourseCode)).getText().toString().trim();
                searchCourse(query);
            }
        });
    }


    private void searchCourse(String query) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<Course>> call = apiService.searchCourse(query);
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    courseList.clear();
                    for (Course course : response.body()) {
                        courseList.add(course.getCourseName() + " - " + course.getFeedback());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FeedbackCourseActivity.this, "Failed to search courses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(FeedbackCourseActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
