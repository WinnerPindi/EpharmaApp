package ca.example.ephramaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Rediriger vers la page de connexion après 3 secondes

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Remarriage de la page
                Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                startActivity(intent);
                finish();
            }
        };

        //Handler post delaye
        new Handler().postDelayed(runnable,3000);

    }
}