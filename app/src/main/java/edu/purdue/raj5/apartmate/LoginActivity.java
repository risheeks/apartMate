package edu.purdue.raj5.apartmate;



import android.os.Bundle;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button exit;;

    final static Client sock = new Client("10.186.41.164", 9900);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

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
        Log.e("MESSAGE RECEIVED: ",message);
    }

    private void init() {
        /*
        * Disconnect button
        */
        //disconnect();

        /*
             Register button
         */
        register();
        /*
             Login button
         */
        login();

    }

    private void disconnect() {
        exit  = (Button)findViewById(R.id.close);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sock.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void register() {
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        register = (Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
                sock.send("REGISTER "+email.getText()+" "+password.getText());
            }
        });
    }

    private void login()
    {
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
                sock.send("LOGIN "+email.getText()+" "+password.getText());
            }

        });
    }

}

