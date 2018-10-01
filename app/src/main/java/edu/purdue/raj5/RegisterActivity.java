package com.example.dell.apartmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    private void registerAccount(){
        firstName = (EditText)findViewById(R.id.et_reg_firstName);
        lastName = (EditText)findViewById(R.id.et_reg_lastName);
        email = (EditText)findViewById(R.id.et_reg_email);
        password = (EditText)findViewById(R.id.et_reg_pass);
        confirmPassword= (EditText)findViewById(R.id.et_reg_confirmPass);
        registration = (Button)findViewById(R.id.bt_register_action);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),firstName.getText()+" "+lastName.getText()+" "+email.getText()+" "+password.getText()+" "+confirmPassword.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
