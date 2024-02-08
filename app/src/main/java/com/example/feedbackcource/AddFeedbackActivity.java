package com.example.feedbackcource;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeedbackActivity extends AppCompatActivity {

    private EditText editTextCourseName;
    private EditText editTextFeedback;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);

        // Initialize views
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        Button buttonAddFeedback = findViewById(R.id.buttonAddFeedback);

        // Initialize Retrofit service
        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Set click listener for the button to add feedback
        buttonAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeedback();
            }
        });
    }


    private void addFeedback() {
        String courseName = editTextCourseName.getText().toString().trim();
        String feedback = editTextFeedback.getText().toString().trim();

        // Check if courseName and feedback are not empty
        if (!courseName.isEmpty() && !feedback.isEmpty()) {
            // Create a Retrofit call to  Flask server endpoint to add feedback
            Call<Void> call = apiService.addFeedback(courseName, feedback);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        finish();
                    } else {
                        Toast.makeText(AddFeedbackActivity.this, "Failed to add feedback", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AddFeedbackActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Course name and feedback cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
