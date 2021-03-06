package com.development.shanujbansal.mileagecalc;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

// import android.support.v4.app.Fragment;


public class AddNewEntry extends Fragment {
    boolean test = false;
    String fuelPriceDisplay = "";

    public AddNewEntry() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.activity_add_new_enrty, container, false);

//        ActionBar supportActionBar = getActivity().getSupportActionBar();
//        if (supportActionBar != null) {
//            supportActionBar.setHomeButtonEnabled(true);
//            supportActionBar.setIcon(R.drawable.ic_launcher);
//        }

        String selectedVehicle = "";
        if (getArguments() != null) {
            selectedVehicle = getArguments().getString("SelectedVehicle");
        }

        // to set the transparent background.
        Drawable backgroundImage = getResources().getDrawable(R.drawable.background);
        backgroundImage.setAlpha(25);
        LinearLayout mainActivityLayout = (LinearLayout) rootView.findViewById(R.id.newEntryAddLL);
        mainActivityLayout.setBackgroundDrawable(backgroundImage);

        final EditText prevOdoReadingTxtBox = (EditText) rootView.findViewById(R.id.prevOdoReading);
        final EditText currOdoReadingTxtBox = (EditText) rootView.findViewById(R.id.currOdoReading);
        final EditText fuelAmountTxtBox = (EditText) rootView.findViewById(R.id.fuelAmount);
        final EditText fuelPriceInputTxtBox = (EditText) rootView.findViewById(R.id.fuelAmountPerUnit);
        final Spinner vehicleSpinner = (Spinner) rootView.findViewById(R.id.vehicleId);

        ArrayList<String> vehiclesList = DatabaseHelper.getInstance(getActivity()).getVehiclesList();
        if (vehiclesList != null && vehiclesList.size() == 0) {
            Toast.makeText(getActivity(), "There are no vehicles registered. Please register a vehicle first", Toast.LENGTH_LONG).show();
            // start add vehicle intent here.
            Intent addVehicleIntent = new Intent(getActivity(), AddVehicle.class);
            addVehicleIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(addVehicleIntent);
            // return;
        }

        ArrayAdapter<String> vehiclesListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, vehiclesList);
        vehicleSpinner.setAdapter(vehiclesListAdapter);
        if (!selectedVehicle.isEmpty()) {
            int spinnerPosition = vehiclesListAdapter.getPosition(selectedVehicle);
            vehicleSpinner.setSelection(spinnerPosition);
            spinnerPosition = 0;
        }

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    test = false;
                    String vehicleSelected = parent.getSelectedItem().toString().trim();
                    HashMap<String, String> vehicleDetails = DatabaseHelper.getInstance(view.getContext()).getVehicleDetails(vehicleSelected);

                    if (vehicleDetails != null && vehicleDetails.size() > 0) {
                        if (vehicleDetails.containsKey("prevReading"))
                            prevOdoReadingTxtBox.setText(vehicleDetails.get("prevReading"));

                        if (vehicleDetails.containsKey("fuelPrice"))
                            fuelPriceDisplay = vehicleDetails.get("fuelPrice");
                        fuelPriceInputTxtBox.setText(fuelPriceDisplay);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Button updateFuelPriceBtn = (Button) rootView.findViewById(R.id.updateFuelPriceBtn);
        final Button cancelFuelPriceBtn = (Button) rootView.findViewById(R.id.cancelFuelPriceBtn);

        updateFuelPriceBtn.setVisibility(View.GONE);
        cancelFuelPriceBtn.setVisibility(View.GONE);

        updateFuelPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // now we have to update the fuel price into the database.
                double updatedPrice = Double.parseDouble(fuelPriceInputTxtBox.getText().toString());
                String vehicleSelected = vehicleSpinner.getSelectedItem().toString().trim();

                int selectedVehicleId = DatabaseHelper.getInstance(getActivity()).getVehicleId(vehicleSelected);
                if (DatabaseHelper.getInstance(getActivity()).updateVehicleFuelPrice(updatedPrice, selectedVehicleId)) {
                    fuelPriceDisplay = fuelPriceInputTxtBox.getText().toString();
                    Toast.makeText(getActivity(), "Fuel price updated successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Unable to update the fuel prices.Please try again", Toast.LENGTH_SHORT).show();

                updateFuelPriceBtn.setVisibility(View.GONE);
                cancelFuelPriceBtn.setVisibility(View.GONE);
                // test = false;
            }
        });

        cancelFuelPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuelPriceInputTxtBox.setText(fuelPriceDisplay);
                updateFuelPriceBtn.setVisibility(View.GONE);
                cancelFuelPriceBtn.setVisibility(View.GONE);
            }
        });

        // updateFuelPriceBtn.setVisibility(View.VISIBLE);
        // TODO here set the functionality for making the update fuel prices button visible.
        fuelPriceInputTxtBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (test == true) {
                    updateFuelPriceBtn.setVisibility(View.VISIBLE);
                    cancelFuelPriceBtn.setVisibility(View.VISIBLE);
                }
                test = true;
            }
        });

        // to hide keyboard in case of next or enter clicked from keyboard.
        fuelPriceInputTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        currOdoReadingTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        prevOdoReadingTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        fuelAmountTxtBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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


        Button addEntryButton = (Button) rootView.findViewById(R.id.addEntryBtn);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to hide the keypad in case it's opened
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception ex) {
                }

                // now we have to insert the entry into the database.
                String prevOdoReadingTxt = prevOdoReadingTxtBox.getText().toString().trim();
                String currOdoReadingTxt = currOdoReadingTxtBox.getText().toString().trim();
                String fuelAmountTxt = fuelAmountTxtBox.getText().toString().trim();
                String fuelPriceInputTxt = fuelPriceInputTxtBox.getText().toString().trim();

                // check if all the details are present
                if (!prevOdoReadingTxt.isEmpty() && !currOdoReadingTxt.isEmpty() && !fuelAmountTxt.isEmpty()
                        && !fuelPriceInputTxt.isEmpty()) {
                    String vehicleSelected = vehicleSpinner.getSelectedItem().toString().trim();

                    int selectedVehicleId = DatabaseHelper.getInstance(getActivity()).getVehicleId(vehicleSelected);
                    int prevReading = Integer.parseInt(prevOdoReadingTxt);
                    int currReading = Integer.parseInt(currOdoReadingTxt);
                    double fuelAmt = Double.parseDouble(fuelAmountTxt);
                    double fuelPrice = Double.parseDouble(fuelPriceInputTxt);
                    double mileage = ((currReading - prevReading) / fuelAmt) * fuelPrice;
                    GregorianCalendar c = new GregorianCalendar();
                    String refillDate = c.getTime().toString();

                    if (currReading <= prevReading) {
                        Toast.makeText(getActivity(), "The current odometer reading should be greater than the previous odometer reading.",
                                Toast.LENGTH_SHORT).show();
                        currOdoReadingTxtBox.setText("");
                        return;
                    }

                    if (DatabaseHelper.getInstance(v.getContext()).addEntry(selectedVehicleId, prevReading, currReading, fuelAmt, refillDate, mileage, fuelPrice)) {
                        Toast.makeText(getActivity(), "Entry added", Toast.LENGTH_SHORT).show();
                        prevOdoReadingTxtBox.setText(String.valueOf(currReading));
                        currOdoReadingTxtBox.setText("");
                        fuelAmountTxtBox.setText("");

                        Intent viewMileageIntent = new Intent(getActivity(), ViewMileage.class);
                        Bundle infoBundle = new Bundle();
                        infoBundle.putString("SelectedVehicle", vehicleSelected);
                        viewMileageIntent.putExtras(infoBundle);
                        // viewMileageIntent.putExtra("SelectedVehicle", vehicleSelected);
                        //viewMileageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        viewMileageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(viewMileageIntent);

                    } else
                        Toast.makeText(getActivity(), "Unable to add entry.Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Enter the complete details and try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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
                //infoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                infoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(infoIntent);
                return true;
            case R.id.action_share_app:
                return true;
            case R.id.action_add_vehicle:
                Intent addVehicleIntent = new Intent(this, AddVehicle.class);
                //addVehicleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                addVehicleIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(addVehicleIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
