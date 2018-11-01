package com.example.risheek.apartmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ReceiptActivity extends AppCompatActivity {

    String[] spinnerValues = { "Blur", "NFS", "Burnout","GTA IV", "Racing", };
    private View btnSend;
    private View btnCancel;
    private EditText title;
    private EditText amount;
    private MultiSelectionSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        btnSend = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        title = findViewById(R.id.receiptTitle);
        amount = findViewById(R.id.receiptAmount);
        spinner = findViewById(R.id.members);
        spinner.setItems(spinnerValues);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytitle = title.getText().toString();
                String myamount = amount.getText().toString();
                String members= spinner.getSelectedItemsAsString();
                Toast.makeText(ReceiptActivity.this, mytitle + "\n" + myamount + "\n" + members, Toast.LENGTH_SHORT).show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReceiptActivity.this, "Cancel and redirect to the tabs activity in the group", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
