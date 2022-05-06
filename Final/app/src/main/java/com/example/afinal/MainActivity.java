package com.example.afinal;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.afinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample";
    public EditText editTextPhoneNumber;
    public EditText editTextMessage;


    @Override //Creer par defaut jsp ce que ca fait
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.afinal.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    //Demmande la permission a lutilisateur
    public  void DemandePermissionSMS() {
        int sendSmsPermisson = ActivityCompat.checkSelfPermission(this ,Manifest.permission.SEND_SMS);
        if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
            // Si on a pas la permission.
            this.requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_CODE_SEND_SMS);
            return;
        }
        this.sendSMS();

    }

    private void sendSMS()  {
        this.editTextPhoneNumber = this.findViewById(R.id.Contact);
        this.editTextMessage = this.findViewById(R.id.Message);
        String phoneNumber = this.editTextPhoneNumber.getText().toString();
        String message = this.editTextMessage.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i( LOG_TAG,"SMS envoyer!");
            Toast.makeText(getApplicationContext(),"SMS envoyer!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...", ex);
            Toast.makeText(getApplicationContext(),"Your sms has failed... " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {// Note: If request is cancelled, the result arrays are empty.
            // Permissions granted (SEND_SMS).
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(LOG_TAG, "Permission granted!");
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                this.sendSMS();
            }
            // Cancelled or denied.
            else {
                Log.i(LOG_TAG, "Permission denied!");
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}