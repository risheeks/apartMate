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
        registerAccount();
    }
    private void registerAccount(){
        firstName = (EditText)findViewById(R.id.et_reg_firstName);
        lastName = (EditText)findViewById(R.id.et_reg_lastName);
        email = (EditText)findViewById(R.id.et_reg_email);
        password = (EditText)findViewById(R.id.et_reg_pass);
        confirmPassword= (EditText)findViewById(R.id.et_reg_confirmPass);
        registration = (Button)findViewById(R.id.bt_register_action);
        registration.setOnClickListener(new View.OnClickListener() {
              registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),firstName.getText()+" "+lastName.getText()+" "+email.getText()+" "+password.getText()+" "+confirmPassword.getText(),Toast.LENGTH_SHORT).show();
                if (!isValidEmailAddress(String.valueOf(email.getText()))){
                    Toast.makeText(getApplicationContext(),"Please enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.getText().equals(confirmPassword.getText())){
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
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
