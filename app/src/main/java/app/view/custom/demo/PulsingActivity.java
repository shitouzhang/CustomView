package app.view.custom.demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.view.custom.R;
import app.view.custom.widget.PulsingLayout;

public class PulsingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsing);

        PulsingLayout pulsingLayout = findViewById(R.id.pulsingLayout);

        pulsingLayout.setColor(Color.parseColor("#448AFF"));
        pulsingLayout.setBackgroundColorOfLayout(Color.parseColor("#00000000"));

    }



}
