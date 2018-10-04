package com.example.dell.apartmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registration;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerAccount();
    }
    private void registerAccount(){
        firstName = (EditText)findViewById(R.id.et_reg_firstName);
        lastName = (EditText)findViewById(R.id.et_reg_lastName);
        email = (EditText)findViewById(R.id.et_reg_email);
        password = (EditText)findViewById(R.id.et_reg_pass);
        confirmPassword= (EditText)findViewById(R.id.et_reg_confirmPass);
        registration = (Button)findViewById(R.id.bt_register_action);
        message = (TextView) findViewById(R.id.tv_reg_message);


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),firstName.getText()+" "+lastName.getText()+" "+email.getText()+" "+password.getText()+" "+confirmPassword.getText(),Toast.LENGTH_SHORT).show();
                if (!isValidEmailAddress(String.valueOf(email.getText()))){
                    message.setText(R.string.error_invalid_email);
                    //Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.getText().equals(confirmPassword.getText())){
                    message.setText((R.string.passwords_do_not_match));
                    //Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                    return;
                }
                if((firstName.getText().toString().matches("")) || (lastName.getText().toString().matches("")) || (password.getText().toString().matches("")) ){
                    message.setText(R.string.some_field_empty);
                    return;
                }
                Intent i = new Intent(RegisterActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
    public final static boolean isValidEmailAddress(String emailAddress){
        if((emailAddress != null)){
            return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        }
        return false;
    }

}
