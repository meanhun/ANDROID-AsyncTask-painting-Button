package com.share4happy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txt_percent;
    EditText txt_sobutton;
    Button btn_vebutton;
    ProgressBar progressBar;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvent();
    }

    private void addEvent() {
        //Sự kiện vẽ button
        btn_vebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(txt_sobutton.getText().toString());
                ButtonTask buttonTask = new ButtonTask();
                buttonTask.execute(number);
            }
        });
    }

    private void addControls() {
        txt_percent = findViewById(R.id.txt_percent);
        txt_sobutton = findViewById(R.id.txt_sobutton);
        btn_vebutton = findViewById(R.id.btn_vebutton);
        progressBar = findViewById(R.id.progressBar);
        linearLayout = findViewById(R.id.linearLayout);
    }
    class ButtonTask extends AsyncTask<Integer, Integer, Void>{
        //Bắt đầu tiến trình
        @Override
        protected void onPreExecute() {
            linearLayout.removeAllViews();
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            //Đây là sô được truyền vào từ txtSoButton thông qua hàm execute
            int n = integers[0];
            Random r = new Random();
            for (int i=0;i<n;i++){
                int percent = i*100/n;
                int value = r.nextInt(500);
                // Đẩy giá trị percent từ doInBackground sang onProgressUpdate
                publishProgress(percent,value);
                //Trì hoãn từng bước vẽ
                SystemClock.sleep(100);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Lấy giá trị truyền vào từ doInBackground
            int value = values[1];
            int percent = values[0];
            txt_percent.setText(percent+"%");
            progressBar.setProgress(percent);
            //Vẽ giao diện
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
            );
            //Vẽ 1 button lên giao diện
            Button button = new Button(MainActivity.this);
            button.setLayoutParams(params);
            button.setText(value+"");
            linearLayout.addView(button);
        }
        //Kết thúc tiến trình

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setProgress(100);
        }
    }
}