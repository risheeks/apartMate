package edu.purdue.raj5.apartmate;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    final Client sock = new Client("10.186.38.238", 9980);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
       // register();
        sock.setClientCallback(new Client.ClientCallback () {
            @Override
            public void onMessage(String message) {
                sock.send(message);
            }

            @Override
            public void onConnect(Socket socket)  {
                sock.send("Logging in");
                //sock.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message)  {
                sock.send("Disconnecting");
                try {
                    socket.getOutputStream().write("k".getBytes());
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{socket.close();}catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        sock.connect();
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.bt_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
                sock.send("YOLO");
            }

        });

//        login();
    }

    private void register() {
        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
        register = (Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),email.getText()+" "+password.getText(),Toast.LENGTH_SHORT).show();
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
                sock.send("YOLO");
            }

        });
    }

}

