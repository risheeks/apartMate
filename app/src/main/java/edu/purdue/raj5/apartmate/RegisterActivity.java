package edu.purdue.raj5.apartmate;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    Button registration;
    TextView message;

	
    final static Client socket = LoginActivity.sock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerAccount();
    }

	/*Collects the text for registering an account. Checks validity 
		contraints such as matching password and valid email. 
		Sends the message to the server to add a new account and collects
		the response.*/
    private void registerAccount() {
        firstName = (EditText) findViewById(R.id.et_reg_firstName);
        lastName = (EditText) findViewById(R.id.et_reg_lastName);
        email = (EditText) findViewById(R.id.et_reg_email);
        password = (EditText) findViewById(R.id.et_reg_pass);
        confirmPassword = (EditText) findViewById(R.id.et_reg_confirmPass);
        registration = (Button) findViewById(R.id.bt_register_action);
        message = (TextView) findViewById(R.id.tv_reg_message);

        socket.setClientCallback(new Client.ClientCallback (){

            @Override
            public void onMessage(String mess) {
                Log.e("MESSAGE RECEIVED: ",mess);

                if(mess.equals("REGISTER SUCCESS"))
                {
                    Log.e("h","h");
                    Intent i = new Intent(getBaseContext(), MenuActivity.class);
                    startActivity(i);
                }else if(mess.equals("REGISTER ACCOUNT_EXISTS")){
                  //  Toast.makeText(getBaseContext(),"Account Exists", Toast.LENGTH_SHORT).show();
				  message.setText("Account is already registered.");
                }else if(mess.contains("LOGIN SUCCESS"))
                {
                    Log.e("h","h");
                    Intent i = new Intent(getBaseContext(), MenuActivity.class);
                    startActivity(i);
                }else if(mess.contains("LOGIN FAILURE")){
                   // Toast.makeText(getBaseContext(),"Inalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onConnect(Socket socket) throws IOException {
                LoginActivity.sock.send("Yooo");
            }

            @Override
            public void onDisconnect(Socket socket, String message) throws IOException {

            }

            @Override
            public void onConnectError(Socket socket, String message) {

            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),firstName.getText()+" "+lastName.getText()+" "+email.getText()+" "+password.getText()+" "+confirmPassword.getText(),Toast.LENGTH_SHORT).show();
                if (!isValidEmailAddress(String.valueOf(email.getText()))) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!String.valueOf(password.getText()).equals(String.valueOf(confirmPassword.getText()))) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((firstName.getText().toString().matches("")) || (lastName.getText().toString().matches("")) || (password.getText().toString().matches("")) ){
                    message.setText(R.string.some_field_empty);
                    return;
                }
                socket.send("REGISTER "+email.getText()+" "+password.getText()+" "+firstName.getText().toString()+" "+lastName.getText().toString());

            }
        });
    }
	/*Pattern matcher for valid email addresses. Returns
		True if the email is valid, false if not valid, and false 
		if there is no email provided.*/
    public final static boolean isValidEmailAddress(String emailAddress){
        if((emailAddress != null)){
            return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
        }
        return false;
    }
}