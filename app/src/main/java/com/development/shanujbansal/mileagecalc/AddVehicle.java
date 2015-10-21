package com.development.shanujbansal.mileagecalc;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

// import android.support.v4.app.Fragment;


public class AddVehicle extends Fragment {

    public AddVehicle() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_add_vehicle, container, false);
        //setContentView(R.layout.activity_add_vehicle);

        // check here if there is already a vehicle registered, then open the add entry page.
//        ArrayList<String> vehiclesList = DatabaseHelper.getInstance(getActivity()).getVehiclesList();
//        if (vehiclesList != null || vehiclesList.size() > 0) {
//            Intent addNewEntryIntent = new Intent(getActivity(), AddNewEntry.class);
//            // addNewEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            addNewEntryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(addNewEntryIntent);
//        }

//        ActionBar supportActionBar = getSupportActionBar();
//        if (supportActionBar != null) {
//            supportActionBar.setHomeButtonEnabled(true);
//            supportActionBar.setIcon(R.drawable.ic_launcher);
//        }

        // to set the transparent background.
        Drawable backgroundImage = getResources().getDrawable(R.drawable.background);
        backgroundImage.setAlpha(25);
        LinearLayout mainActivityLayout = (LinearLayout) rootView.findViewById(R.id.newVehicleAddLL);
        mainActivityLayout.setBackgroundDrawable(backgroundImage);

        // populate the fuel type spinner
        final Spinner fuelTypeSpinner = (Spinner) rootView.findViewById(R.id.vehicleFuelType);
        fuelTypeSpinner.setAdapter(new ArrayAdapter<FuelType>(getActivity(), android.R.layout.simple_list_item_1, FuelType.values()));

        // populate the region spinner.
        final Spinner regionSpinner = (Spinner) rootView.findViewById(R.id.vehicleRegion);
        ArrayList<String> regionsList = DatabaseHelper.getInstance(getActivity()).getRegionsList();
        regionSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, regionsList));

        final EditText vehicleNameTxtBox = (EditText) rootView.findViewById(R.id.vehicleName);
        final EditText vehicleBaseReadingTxtBox = (EditText) rootView.findViewById(R.id.vehicleBaseReading);
        final EditText vehicleRegNumberTxtBox = (EditText) rootView.findViewById(R.id.vehicleRegNumber);
        final EditText vehicleExpectedMileageTxtBox = (EditText) rootView.findViewById(R.id.vehicleExpectedMileage);

        // to hide keyboard in case of next or enter clicked from keyboard.
        vehicleNameTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception ex) {
                    }
                }
                return false;
            }
        });


        Button addVehicleButton = (Button) rootView.findViewById(R.id.addVehicleBtn);
        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to hide the keypad in case it's opened
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                    Toast.makeText(getActivity(), "Please enter the complete details", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (DatabaseHelper.getInstance(getActivity()).addVehicle(vehName, fuelType, vehBaseReading, vehRegionId, vehRegNumber, Double.valueOf(vehExpectedMileage))) {
                        Toast.makeText(getActivity(), "Vehicle registered successfully", Toast.LENGTH_SHORT).show();
                        vehicleNameTxtBox.setText("");
                        vehicleBaseReadingTxtBox.setText("");
                        vehicleRegNumberTxtBox.setText("");
                        vehicleExpectedMileageTxtBox.setText("");
                        regionSpinner.setSelection(0);
                        fuelTypeSpinner.setSelection(0);

                        Intent addEntryIntent = new Intent(getActivity(), AddNewEntry.class);
                        Bundle infoBundle = new Bundle();
                        infoBundle.putString("SelectedVehicle", vehName);
                        addEntryIntent.putExtras(infoBundle);
                        //addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        addEntryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(addEntryIntent);
                    } else
                        Toast.makeText(getActivity(), "Unable to register the vehicle.Please try again.", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Unable to register the vehicle.Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    /*
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
    */
}
