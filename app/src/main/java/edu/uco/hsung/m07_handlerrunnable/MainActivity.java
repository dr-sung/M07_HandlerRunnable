package edu.uco.hsung.m07_handlerrunnable;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Button button = (Button) findViewById(R.id.loadButton);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new Thread(new LoadIconTask(R.drawable.cs)).start();
            }
        });

        final Button buttonClear = (Button) findViewById(R.id.clearButton);
        buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
            }
        });

        final Button otherButton = (Button) findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Other button is pressed!!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class LoadIconTask implements Runnable {
        int resId;
        LoadIconTask(int resId) {
            this.resId = resId;
        }
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });
            bitmap = BitmapFactory.decodeResource(getResources(), resId);
            // Simulating long-running operation
            for (int i = 1; i < 11; i++) {
                sleep(); // 500ms
                final int step = i;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(step * 10);
                    }
                });
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            });
        }
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
