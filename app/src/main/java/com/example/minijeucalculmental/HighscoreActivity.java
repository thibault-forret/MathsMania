package com.example.minijeucalculmental;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minijeucalculmental.database.ScoreBaseHelper;
import com.example.minijeucalculmental.database.ScoreDao;
import com.example.minijeucalculmental.entities.Score;

import java.util.ArrayList;
import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ScoreDao scoreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highscore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(HighscoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();  // Termine l'activité courante
            }
        };
        getOnBackPressedDispatcher().addCallback(   this, callback);

        scoreDao = new ScoreDao(new ScoreBaseHelper(this, "db-mathsmania-score", 1));

        List<Score> listScore = scoreDao.query(null, null, "score DESC");

        recyclerView = findViewById(R.id.recycler_view);

        ScoreAdapter adapter = new ScoreAdapter(listScore);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}