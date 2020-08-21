package com.example.LoginApp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class Password_change extends AppCompatActivity {

    DatabaseHelper myDb = new DatabaseHelper(this);
    EditText old, new1, confirm;
    Button change;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordchange_layout);

        old = findViewById(R.id.pass3);
        new1 = findViewById(R.id.pass6);
        confirm = findViewById(R.id.pass7);
        change = findViewById(R.id.change_button);





        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                Intent intent = getIntent();
                String username = intent.getStringExtra("confirmuser");
                int wrongcurrent = 0;
                int wrongmatch = 0;

                while (res.moveToNext()){
                    String USER = res.getString(1);
                    String PASS = res.getString(2);

                    if(username.equals(USER) && (old.getText().toString()).equals(PASS) && (new1.getText().toString()).equals(confirm.getText().toString())){

                        myDb.updateData(username, new1.getText().toString());
                        boolean isUpdated = myDb.updateData(username, new1.getText().toString());

                        if(isUpdated){
                            Toast.makeText(Password_change.this, "Password is updated", Toast.LENGTH_SHORT).show();

                        }
                    }

                    if(username.equals(USER) && (old.getText().toString()).equals(PASS)){
                        wrongcurrent++;
                    }
                    if((new1.getText().toString()).equals(confirm.getText().toString())){
                        wrongmatch++;
                    }
                }

                if(wrongcurrent == 0){
                    Toast.makeText(Password_change.this, "Wrong current password", Toast.LENGTH_SHORT).show();
                }
                if(wrongmatch == 0){
                    Toast.makeText(Password_change.this, "New password does not match", Toast.LENGTH_SHORT).show();
                }


                Intent intent1 = new Intent(Password_change.this, Welcome_page.class);

                JSONObject obj = new JSONObject();

                try{
                    obj.put("user", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent1.putExtra("json", obj.toString());
                startActivity(intent1);
            }
        });


    }
}
