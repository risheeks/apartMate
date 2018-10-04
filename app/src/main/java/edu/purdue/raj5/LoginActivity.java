package com.example.dell.apartmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    Button register;
    TextView forgotPass;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();
        register();
        forgotPassword();
    }

    private void register() {
        email = (EditText)findViewById(R.id.et_login_email);
        password = (EditText)findViewById(R.id.et_login_password);
        register = (Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void login()
    {
        email = (EditText)findViewById(R.id.et_login_email);
        password = (EditText)findViewById(R.id.et_login_password);
        login = (Button)findViewById(R.id.bt_login);
        message = (TextView)findViewById(R.id.tv_login_message);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();

                if((email.getText().toString().matches("")) || (password.getText().toString().matches(""))){
                    message.setText(R.string.some_field_empty);
                    return;
                }
                Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }
    private void forgotPassword(){
        forgotPass = (TextView)findViewById(R.id.tv_forgotPass);
        message = (TextView)findViewById(R.id.tv_login_message);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Well, that is your problem",Toast.LENGTH_SHORT);
                message.setText(R.string.forgot_password);
            }
        });
    }

}
