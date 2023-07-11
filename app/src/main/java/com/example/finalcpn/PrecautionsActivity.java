package com.example.finalcpn;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrecautionsActivity extends AppCompatActivity {

    private TextView tvPrecautions;
    private ImageView ivPrecautions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precautions);
        getSupportActionBar().hide();

        // Get reference to TextView and ImageView for displaying precautions and images

        ivPrecautions = findViewById(R.id.ivPrecautions);
        tvPrecautions=findViewById(R.id.tvPrecautions);

            // Retrieve predicted class from intent extra
            String predictedClass = getIntent().getStringExtra("predictedClass");




        // Check if predictedClass is not null
        if (predictedClass != null) {


            // Display precautions and set image based on predictedClass
            if (predictedClass.equalsIgnoreCase("COVID-19")) {
//                displayCovidPrecautions();
                ivPrecautions.setImageResource(R.drawable.covid); // Set COVID-19 image resource
            } else if (predictedClass.equalsIgnoreCase("PNEUMONIA")) {
                displayPneumoniaPrecautions();
                ivPrecautions.setImageResource(R.drawable.pneumonia); // Set Pneumonia image resource
            } else if  (predictedClass.equalsIgnoreCase("NORMAL")) {
                displaynormal();
                ivPrecautions.setImageResource(R.drawable.normal); // Set normal/healthy image resource
            }
        }
    }

    private void displaynormal() {
        tvPrecautions.setText("You are healthy");
    }

    private void displayCovidPrecautions() {
        // Set text in TextView to display COVID precautions
        tvPrecautions.setText("COVID Precautions:\n\n" +
                "- Wear a mask to cover your nose and mouth.\n" +
                "- Practice frequent handwashing with soap and water.\n" +
                "- Maintain social distancing of at least 6 feet from others.\n" +
                "- Avoid large gatherings and crowded places.\n" +
                "- Follow guidelines and recommendations from local health authorities.");
    }

    private void displayPneumoniaPrecautions() {
        // Set text in TextView to display PNEUMONIA precautions
        tvPrecautions.setText("Pneumonia Precautions:\n\n" +
                "- Take prescribed antibiotics as directed by your healthcare provider.\n" +
                "- Get plenty of rest and stay hydrated.\n" +
                "- Practice good respiratory hygiene, such as covering your mouth and nose when coughing or sneezing.\n" +
                "- Follow any additional treatment and care instructions provided by your healthcare provider.\n" +
                "- Contact your healthcare provider if symptoms worsen or do not improve.");
    }
}


