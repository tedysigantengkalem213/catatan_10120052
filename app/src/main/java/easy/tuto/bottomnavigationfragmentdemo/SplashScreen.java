package easy.tuto.bottomnavigationfragmentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
//10120052_Eddy Rochman_If-2//

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                // Finish the splash activity
                finish();
            }
        }, SPLASH_DURATION);
    }
}