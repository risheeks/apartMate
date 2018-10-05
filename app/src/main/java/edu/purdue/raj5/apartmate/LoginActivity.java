package edu.purdue.raj5.apartmate;



import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.net.Socket;

import static android.Manifest.permission.READ_CONTACTS;

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
     static Client sock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sock = new Client("10.186.47.140", 9910);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        password = (EditText)findViewById(R.id.et_login_password);
        password.setFocusable(false);
        password.setTag(password.getKeyListener());
        password.setKeyListener(null);
        init();

        sock.setClientCallback(new Client.ClientCallback () {
            @Override
            public void onMessage(String message) {
                processResponse(message);
            }

            @Override
            public void onConnect(Socket socket)  {
                sock.send("Connected");

                //sock.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message)  {


            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        sock.connect();

    }



    private void processResponse(String message) {
        Log.e("MESSAGE RECEIVED: ","'"+message+"'");
        Log.e("aaaa","Asasas");

        if(message.contains("LOGIN SUCCESS"))
        {
            Log.e("h","h");
            Intent i = new Intent(getBaseContext(), MenuActivity.class);
            startActivity(i);
        }else if(message.contains("LOGIN FAILURE")){
            Toast.makeText(LoginActivity.this,"Inalid Email or Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.e("dfdf","huireij");
        }



    }

    private void init() {


        /*
             Register button
         */
        register();
        /*
             Login button
         */
        login();
        forgotPassword();

    }




    private void register() {
        email = (EditText)findViewById(R.id.et_login_email);
        password = (EditText)findViewById(R.id.et_login_password);
        register = (Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("");
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login()
    {
        email = (EditText)findViewById(R.id.et_login_email);
        password = (EditText)findViewById(R.id.et_login_password);
        login = (Button)findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("");
                if((email.getText().toString().matches("")) || (password.getText().toString().matches(""))){
                    message.setText(R.string.some_field_empty);
                    return;
                }
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
                sock.send("LOGIN "+email.getText()+" "+password.getText());

            }

        });
    }

    private void forgotPassword(){
        forgotPass = (TextView)findViewById(R.id.tv_forgotPass);
        message = (TextView)findViewById(R.id.tv_login_message);
        password = (EditText)findViewById(R.id.et_login_password);
        password.setFocusableInTouchMode(true);
        password.setKeyListener((KeyListener)password.getTag());
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "Well, that is your problem",Toast.LENGTH_SHORT);
                message.setText(R.string.forgot_password);
            }
        });
    }

}

