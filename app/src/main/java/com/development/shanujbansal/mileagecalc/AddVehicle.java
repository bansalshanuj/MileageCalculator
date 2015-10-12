package com.development.shanujbansal.mileagecalc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddVehicle extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // to set the transparent background.
        Drawable backgroundImage = getResources().getDrawable(R.drawable.background);
        backgroundImage.setAlpha(25);
        LinearLayout mainActivityLayout = (LinearLayout) findViewById(R.id.newVehicleAddLL);
        mainActivityLayout.setBackgroundDrawable(backgroundImage);

        // populate the fuel type spinner
        final Spinner fuelTypeSpinner = (Spinner) findViewById(R.id.vehicleFuelType);
        fuelTypeSpinner.setAdapter(new ArrayAdapter<FuelType>(this, android.R.layout.simple_list_item_1, FuelType.values()));

        // populate the region spinner.
        final Spinner regionSpinner = (Spinner) findViewById(R.id.vehicleRegion);
        ArrayList<String> regionsList = DatabaseHelper.getInstance(this).getRegionsList();
        regionSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, regionsList));

        final EditText vehicleNameTxtBox = (EditText) findViewById(R.id.vehicleName);
        final EditText vehicleBaseReadingTxtBox = (EditText) findViewById(R.id.vehicleBaseReading);
        final EditText vehicleRegNumberTxtBox = (EditText) findViewById(R.id.vehicleRegNumber);
        final EditText vehicleExpectedMileageTxtBox = (EditText) findViewById(R.id.vehicleExpectedMileage);

        // to hide keyboard in case of next or enter clicked from keyboard.
        vehicleNameTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception ex) {
                    }
                }
                return false;
            }
        });
        vehicleBaseReadingTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception ex) {
                    }
                }
                return false;
            }
        });
        vehicleRegNumberTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception ex) {
                    }
                }
                return false;
            }
        });
        vehicleExpectedMileageTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception ex) {
                    }
                }
                return false;
            }
        });


        Button addVehicleButton = (Button) findViewById(R.id.addVehicleBtn);
        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to hide the keypad in case it's opened
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception ex) {
                }

                // now we have to insert the vehicle entry into the database.
                String vehName = vehicleNameTxtBox.getText().toString().trim();
                int vehRegionId = regionSpinner.getSelectedItemPosition() + 1; // regionSpinner.getSelectedItem().toString();
                String fuelType = fuelTypeSpinner.getSelectedItem().toString();
                String vehBaseReading = vehicleBaseReadingTxtBox.getText().toString().trim();
                String vehRegNumber = vehicleRegNumberTxtBox.getText().toString().trim();
                String vehExpectedMileage = vehicleExpectedMileageTxtBox.getText().toString().trim();

                if (vehName.isEmpty() || vehBaseReading.isEmpty() || vehRegNumber.isEmpty() || vehExpectedMileage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the complete details", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (DatabaseHelper.getInstance(getApplicationContext()).addVehicle(vehName, fuelType, vehBaseReading, vehRegionId, vehRegNumber, Double.valueOf(vehExpectedMileage))) {
                        Toast.makeText(getApplicationContext(), "Vehicle registered successfully", Toast.LENGTH_SHORT).show();
                        vehicleNameTxtBox.setText("");
                        vehicleBaseReadingTxtBox.setText("");
                        vehicleRegNumberTxtBox.setText("");
                        vehicleExpectedMileageTxtBox.setText("");
                        regionSpinner.setSelection(0);
                        fuelTypeSpinner.setSelection(0);

                        Intent addEntryIntent = new Intent(getApplicationContext(), AddNewEntry.class);
                        Bundle infoBundle = new Bundle();
                        infoBundle.putString("SelectedVehicle", vehName);
                        addEntryIntent.putExtras(infoBundle);
                        //addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(addEntryIntent);
                    } else
                        Toast.makeText(getApplicationContext(), "Unable to register the vehicle.Please try again.", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Unable to register the vehicle.Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_vehicle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_how_to_use:
                Intent usageIntent = new Intent(this, HowToUseActivity.class);
                //usageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                usageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(usageIntent);
                return true;
            case R.id.action_info:
                Intent infoIntent = new Intent(this, InfoActivity.class);
                // infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(infoIntent);
                return true;
            case R.id.action_share_app:
                return true;
            case R.id.action_add_entry:
                Intent addEntryIntent = new Intent(this, AddNewEntry.class);
                //addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(addEntryIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
