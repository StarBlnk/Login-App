package com.example.LoginApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.LoginApp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Welcome_page extends AppCompatActivity {

    TextView change;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weclome_layout);

        Intent intent = getIntent();
        TextView textview =findViewById(R.id.textView4);

        //getting username through intent
        if(intent.hasExtra("json")){
            try {
                JSONObject obj = new JSONObject(intent.getStringExtra("json"));
                String user = obj.getString("user");

                textview.setText(user);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.coding).into(imageView);

        change = (TextView) findViewById(R.id.textView10);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_page.this, Password_change.class);
                TextView text = findViewById(R.id.textView4);
                intent.putExtra("confirmuser", text.getText().toString());
                startActivity(intent);


            }
        });

    }
}
