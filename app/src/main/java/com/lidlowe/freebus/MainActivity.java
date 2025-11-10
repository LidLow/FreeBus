package com.lidlowe.freebus;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

// This project simulates bus ticket messages for educational purposes only.
// Do not use this software to deceive or falsify transport systems.

public class MainActivity extends AppCompatActivity
{
    final int MY_PERMISSIONS_REQUEST_SMS_SEND = 20;
    final EditText busNumberPlain = (EditText) findViewById(R.id.busNum);
    final EditText phoneNumberPlain = (EditText) findViewById(R.id.phoneNum);
    final Switch transportMode = (Switch) findViewById(R.id.transportMode);
    Button generateButton = (Button) findViewById(R.id.generate);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        generateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String phoneNumber = phoneNumberPlain.getText().toString().trim();
                String currentTime = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(new Date());
                String busNumber = busNumberPlain.getText().toString().trim();
                String busSerial = getBusSerial(transportMode.isChecked());
                String ticketSerial = getTicketSerial();

                String message = String.format(
                        "ONAY! ALA\nBN %s\n%s,%s,120₸\nhttp://qr.tha.kz/%s\nTEST_DEMO_TEST",
                        currentTime,
                        busNumber,
                        busSerial,
                        ticketSerial
                );

                if (!busNumber.isEmpty())
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                }
            }
        });
    }

    public String getTicketSerial()
    {
        Random random = new Random();
        return String.valueOf(random.nextInt(10)) + (char) (random.nextInt(26) + 65) + random.nextInt(10) + random.nextInt(10) + (char) (random.nextInt(26) + 65);
    }

    public String getBusSerial(boolean isBus)
    {
        Random random = new Random();

        if (isBus)
        {
            return String.valueOf(random.nextInt(10)) + random.nextInt(10) + random.nextInt(10) + (char) (random.nextInt(26) + 65) + (char) (random.nextInt(26) + 65) + "02";
        }
        else
        {
            return "7" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SMS_SEND);
        }
    }
}

// This project simulates bus ticket messages for educational purposes only.
// Do not use this software to deceive or falsify transport systems.