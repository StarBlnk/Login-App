package com.example.LoginApp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.LoginApp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    DatabaseHelper myDb;
    Button signup, login;
    EditText user, pass;
    CheckBox checkBox1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        myDb = new DatabaseHelper(this);

        signup = (Button) findViewById(R.id.signup_button1);
        login = (Button) findViewById(R.id.login_button);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        checkBox1 = findViewById(R.id.checkBox);

        SharedPreferences sharedPref = getSharedPreferences("SharedPrefs", getBaseContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String name1 = sharedPref.getString("USERSHARE", "");
        String name2 = sharedPref.getString("PASSSHARE", "");

        if(sharedPref.getBoolean("saved", true)){
            user.setText(name1);
            checkBox1.setChecked(true);
        }
        else{
            user.setText("");
            checkBox1.setChecked(false);
        }
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0){
            checkBox1.setChecked(false);
        }





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    showMessage("Error", "No Account Found");
                    Toast.makeText(Login.this, "You have not made an account", Toast.LENGTH_SHORT).show();
                    return;
                }

                int num = 0;

                while (res.moveToNext()) {
                    //checking if username and password match
                    String USER = res.getString(1);
                    String PASS = res.getString(2);

                    if((user.getText().toString().equals(USER)) && pass.getText().toString().equals(PASS)){

                        //Using JSON to pass the username and password
                        JSONObject obj = new JSONObject();

                        try {
                            obj.put("user", user.getText().toString());
                            obj.put("pass", pass.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String out = obj.toString();
                        Intent intent = new Intent(Login.this, Welcome_page.class);
                        intent.putExtra("json", obj.toString());

                        //checkbox
                        SharedPreferences sharedPref = getSharedPreferences("SharedPrefs", getBaseContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        if(checkBox1.isChecked()){

                            editor.putString("USERSHARE", user.getText().toString());
                            editor.putString("PASSSHARE", pass.getText().toString());
                            editor.putBoolean("check", false);
                            editor.apply();


                            editor.putBoolean("saved", true);
                            editor.apply();
                        }
                        else{
                            editor.putBoolean("saved", false);
                            editor.apply();
                        }
                        num++;
                        startActivity(intent);
                    }

                }
                if(num == 0){
                    Toast.makeText(Login.this, "Username and Password not matching", Toast.LENGTH_SHORT).show();
                }


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });




    }



    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
    }

}
