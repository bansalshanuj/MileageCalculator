package com.development.shanujbansal.mileagecalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by shanuj.bansal on 4/20/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Mileage Calculator";
    // table's name
    private static final String TABLE_VEHICLES = "Vehicles";
    private static final String TABLE_FUELPRICES = "Region_Fuel_Prices";
    private static final String TABLE_ENTRIES = "Records";
    // TABLE_VEHICLES columns
    // private static final String ID : would be used same as of fuel prices
    private static final String BASE_ODO_READING = "Base_Odometer_Reading";
    private static final String NAME = "Vehicle_Name";
    private static final String REGN_NO = "Registration_Number";
    private static final String REGION_ID = "Region_Id";
    private static final String FUEL_TYPE = "Fuel_Type";
    private static final String PREVIOUS_REFILL_ODO_READING = "Reading_At_Last_Refill";
    private static final String FUEL_PRICE = "Fuel_Price";
    private static final String EXPECTED_MILEAGE = "Expected_Mileage";

    // TABLE_FUELPRICES columns
    private static final String ID = "Id";
    private static final String REGION = "Region";
    private static final String PETROL_PRICE = "Petrol_Price";
    private static final String DIESEL_PRICE = "Diesel_Price";
    private static final String CNG_PRICE = "CNG_Price";
    // TABLE_ENTRIES columns
    private static final String VEHICLE_ID = "Vehicle_Id";
    private static final String PREV_ODO_READING = "Previous_Odometer_Reading";
    private static final String CURR_ODO_READING = "Current_Odometer_Reading";
    private static final String FUEL_AMOUNT = "Fuel_Amount";
    private static final String REFILL_DATE = "Refilling_Date";
    private static final String MILEAGE = "Mileage";
    private static DatabaseHelper instance;
    Random r = new Random();
    private Context _context;
    //private static final String FUEL_PRICE = "Fuel_Price";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this._context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseHelper(context);

        instance._context = context;
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFuelPricesTable = "CREATE TABLE " + TABLE_FUELPRICES + "("
                + ID + " INTEGER PRIMARY KEY," + REGION + " TEXT,"
                + PETROL_PRICE + " REAL," + DIESEL_PRICE + " REAL," + CNG_PRICE + " REAL" + ")";
        db.execSQL(createFuelPricesTable);
        enterFuelPricesForRegions(db);

        String createVehiclesTable = "CREATE TABLE " + TABLE_VEHICLES + "("
                + ID + " INTEGER PRIMARY KEY," + BASE_ODO_READING + " INTEGER,"
                + NAME + " TEXT," + REGN_NO + " TEXT," + REGION_ID + " INTEGER," + PREVIOUS_REFILL_ODO_READING + " INTEGER," + EXPECTED_MILEAGE + " REAL," + FUEL_PRICE + " REAL," + FUEL_TYPE + " TEXT" + ")";
        db.execSQL(createVehiclesTable);

        String createEntriesTable = "CREATE TABLE " + TABLE_ENTRIES + "("
                + VEHICLE_ID + " INTEGER," + PREV_ODO_READING + " INTEGER,"
                + CURR_ODO_READING + " INTEGER," + FUEL_AMOUNT + " REAL," + MILEAGE + " REAL," + FUEL_PRICE + " REAL," + REFILL_DATE + " TEXT" + ")";
        db.execSQL(createEntriesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void closeConnection() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != null)
            db.close();
    }

    private void enterFuelPricesForRegions(SQLiteDatabase db) {
        try {
            ArrayList<RegionFuel> regionFuelList = prepareRegionsList();

            for (RegionFuel regionFuel : regionFuelList) {
                ContentValues values = new ContentValues();
                values.put(ID, regionFuel.getId());
                values.put(REGION, regionFuel.getRegion());
                values.put(PETROL_PRICE, regionFuel.getPetrolPrice());
                values.put(DIESEL_PRICE, regionFuel.getDieselPrice());
                values.put(CNG_PRICE, regionFuel.getCngPrice());

                // Inserting Row
                db.insert(TABLE_FUELPRICES, null, values);
            }
            // db.close(); // Closing database connection
        } catch (Exception ex) {
            String err = ex.getMessage();
        }
    }

    private ArrayList<RegionFuel> prepareRegionsList() {
        ArrayList<RegionFuel> regionFuelArrayList = new ArrayList<RegionFuel>();

        regionFuelArrayList.add(new RegionFuel(1, "Andhra Pradesh", 71.37, 56.63, 48));
        regionFuelArrayList.add(new RegionFuel(2, "Arunachal Pradesh", 62.66, 49.22, 0)); //nu
        regionFuelArrayList.add(new RegionFuel(3, "Assam", 66.06, 52.15, 0));
        regionFuelArrayList.add(new RegionFuel(4, "Bihar", 70.94, 55.61, 0));
        regionFuelArrayList.add(new RegionFuel(5, "Chhattisgarh", 65.46, 55.22, 0));
        regionFuelArrayList.add(new RegionFuel(6, "Goa", 60.25, 53.75, 0));
        regionFuelArrayList.add(new RegionFuel(7, "Gujarat", 66.52, 54.85, 46.75));
        regionFuelArrayList.add(new RegionFuel(8, "Haryana", 67.24, 49.72, 0));
        regionFuelArrayList.add(new RegionFuel(9, "Himachal Pradesh", 68.07, 49.92, 0));
        regionFuelArrayList.add(new RegionFuel(10, "Jammu and Kashmir", 68.21, 52.6, 0));
        regionFuelArrayList.add(new RegionFuel(11, "Jharkhand", 64.94, 54.81, 0));
        regionFuelArrayList.add(new RegionFuel(12, "Karnataka", 69.59, 54.34, 0));
        regionFuelArrayList.add(new RegionFuel(13, "Kerala", 69.68, 55.57, 0));
        regionFuelArrayList.add(new RegionFuel(14, "Madhya Pradesh", 69.25, 56.27, 0));
        regionFuelArrayList.add(new RegionFuel(15, "Maharashtra", 70.89, 56.9, 43.45));
        regionFuelArrayList.add(new RegionFuel(16, "Manipur", 62.34, 49.53, 0));
        regionFuelArrayList.add(new RegionFuel(17, "Meghalaya", 64.05, 51.05, 0));
        regionFuelArrayList.add(new RegionFuel(18, "Mizoram", 62.34, 48.96, 0));
        regionFuelArrayList.add(new RegionFuel(19, "Nagaland", 64.62, 49.8, 0));
        regionFuelArrayList.add(new RegionFuel(20, "Odisha", 64.99, 54.89, 0));
        regionFuelArrayList.add(new RegionFuel(21, "Punjab", 70.73, 49.65, 0));
        regionFuelArrayList.add(new RegionFuel(22, "Rajasthan", 68.86, 54.65, 0));
        regionFuelArrayList.add(new RegionFuel(23, "Sikkim", 68.93, 53.99, 0));
        regionFuelArrayList.add(new RegionFuel(24, "Tamil Nadu", 66.13, 52.79, 0));
        regionFuelArrayList.add(new RegionFuel(25, "Telangana", 71.21, 56.04, 52));
        regionFuelArrayList.add(new RegionFuel(26, "Tripura", 62.15, 49.40, 0));
        regionFuelArrayList.add(new RegionFuel(27, "Uttar Pradesh", 70.21, 54.61, 40.5));
        regionFuelArrayList.add(new RegionFuel(28, "Uttarakhand", 66.84, 54.57, 0));
        regionFuelArrayList.add(new RegionFuel(29, "West Bengal", 70.49, 54.19, 0));
        regionFuelArrayList.add(new RegionFuel(30, "Andaman and Nicobar Islands", 56.40, 47.46, 0));
        regionFuelArrayList.add(new RegionFuel(31, "Chandigarh", 64.69, 49.23, 0));
        regionFuelArrayList.add(new RegionFuel(32, "Dadra and Nagar Haveli", 64.69, 52.14, 0));
        regionFuelArrayList.add(new RegionFuel(33, "Daman and Diu", 65.15, 52.14, 0));
        regionFuelArrayList.add(new RegionFuel(34, "Lakshadweep", 0, 0, 0));
        regionFuelArrayList.add(new RegionFuel(35, "National Capital Territory of Delhi", 63.21, 49.59, 38.15));
        regionFuelArrayList.add(new RegionFuel(36, "Puducherry", 62.05, 51.74, 0));

        return regionFuelArrayList;
    }

    public ArrayList<String> getRegionsList() {
        ArrayList<String> regionsList = new ArrayList<String>();
        try {
            // Select All Query
            String selectQuery = "SELECT " + REGION + " FROM " + TABLE_FUELPRICES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    String regionName = cursor.getString(0);
                    regionsList.add(regionName);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
        }

        return regionsList;
    }

    public double getExpectedMileage(int vehicleId) {
        double expMileage = 0.0;
        DecimalFormat valueFormat = new DecimalFormat("#.##");

        try {
            String selectQuery = "SELECT " + EXPECTED_MILEAGE + " FROM " + TABLE_VEHICLES + " WHERE " + ID + " =" + vehicleId;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    expMileage = cursor.getDouble(0);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
        }

        return expMileage;
    }

    public HashMap<String, String> getVehicleMileage(int vehicleId) {
        HashMap<String, String> mileageInfo = new HashMap<>();
        double mileageSum = 0.0;
        int numEntries = 0;
        double expensePerKmSum = 0.0;

        try {
            String selectQuery = "SELECT " + MILEAGE + "," + FUEL_PRICE + " FROM " + TABLE_ENTRIES + " WHERE " + VEHICLE_ID + " =" + vehicleId;
            DecimalFormat valueFormat = new DecimalFormat("#.##");

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    mileageSum += cursor.getDouble(0);
                    expensePerKmSum += cursor.getDouble(1) / cursor.getDouble(0);
                    numEntries++;
                } while (cursor.moveToNext());
            }

            if (numEntries != 0) {
                mileageInfo.put("Mileage", String.valueOf(valueFormat.format(mileageSum / numEntries)).toString());
                mileageInfo.put("ExpensePerKm", String.valueOf(valueFormat.format(expensePerKmSum / numEntries)).toString());
            }
        } catch (Exception ex) {
        }

        return mileageInfo;
    }

    public int getVehicleId(String vehicleName) {
        try {
            String selectQuery = "SELECT " + ID + " FROM " + TABLE_VEHICLES + " WHERE " + NAME + " ='" + vehicleName + "'";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } catch (Exception ex) {
        }
        return 0;
    }

    public HashMap<String, String> getVehicleDetails(String vehicleName) {
        HashMap<String, String> details = new HashMap<String, String>();
        try {
            String selectQuery = "SELECT " + PREVIOUS_REFILL_ODO_READING + "," + FUEL_PRICE + " FROM " + TABLE_VEHICLES + " WHERE " + NAME + " ='" + vehicleName + "'";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                details.put("prevReading", cursor.getString(0));
                details.put("fuelPrice", cursor.getString(1));
            }
        } catch (Exception ex) {
        }
        return details;
    }

    public HashMap<String, String> getFuelPrices(int regionId) {
        HashMap<String, String> fuelPricesDetails = new HashMap<String, String>();

        try {
            String selectQuery = "SELECT " + PETROL_PRICE + "," + DIESEL_PRICE + "," + CNG_PRICE + " FROM " + TABLE_FUELPRICES + " WHERE " + ID + " =" + regionId;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                String petrolPrice = cursor.getString(0);
                if (petrolPrice.equals("0"))
                    petrolPrice = "N.A";

                String dieselPrice = cursor.getString(1);
                if (dieselPrice.equals("0"))
                    dieselPrice = "N.A";

                String cngPrice = cursor.getString(2);
                if (cngPrice.equals("0"))
                    cngPrice = "N.A";

                fuelPricesDetails.put("petrolPrice", petrolPrice);
                fuelPricesDetails.put("dieselPrice", dieselPrice);
                fuelPricesDetails.put("cngPrice", cngPrice);
            }
        } catch (Exception ex) {
        }
        return fuelPricesDetails;
    }

    public ArrayList<String> getVehiclesList() {
        ArrayList<String> vehiclesList = new ArrayList<String>();
        try {
            // Select All Query
            String selectQuery = "SELECT " + NAME + " FROM " + TABLE_VEHICLES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    String vehicleName = cursor.getString(0);
                    // Adding vehicle to list
                    vehiclesList.add(vehicleName);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
        }

        return vehiclesList;
    }

    public boolean addVehicle(String name, String fuelType, String baseOdoReading, int regionId, String regNumber, double expecMileage) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            double fuelPrice = 0.0;
            String selectQuery = "SELECT * FROM " + TABLE_FUELPRICES + " WHERE " + ID + "=" + regionId;
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                if (fuelType.toLowerCase().equals("petrol"))
                    fuelPrice = cursor.getDouble(2);
                else if (fuelType.toLowerCase().equals("diesel"))
                    fuelPrice = cursor.getDouble(3);
                else if (fuelType.toLowerCase().equals("cng"))
                    fuelPrice = cursor.getDouble(4);
            }

            ContentValues values = new ContentValues();
            values.put(ID, r.nextInt());
            values.put(BASE_ODO_READING, baseOdoReading);
            values.put(NAME, name);
            values.put(REGN_NO, regNumber);
            values.put(REGION_ID, regionId);
            values.put(FUEL_TYPE, fuelType);
            values.put(PREVIOUS_REFILL_ODO_READING, baseOdoReading);
            values.put(FUEL_PRICE, fuelPrice);
            values.put(EXPECTED_MILEAGE, expecMileage);

            // Inserting Row
            db.insert(TABLE_VEHICLES, null, values);
            return true;
            // now the vehicle has been added in the Vehicle table, we need to make an entry to another table too.
        } catch (Exception ex) {
        }

        return false;
    }

    public boolean addEntry(int vehicleId, int prevReading, int currReading, double fuelAmt, String date, double mileage, double fuelPrice) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(VEHICLE_ID, vehicleId);
            values.put(PREV_ODO_READING, prevReading);
            values.put(CURR_ODO_READING, currReading);
            values.put(FUEL_AMOUNT, fuelAmt);
            values.put(REFILL_DATE, date);
            values.put(MILEAGE, mileage);
            values.put(FUEL_PRICE, fuelPrice);

            // Inserting Row
            db.insert(TABLE_ENTRIES, null, values);

            String updateQuery = "Update " + TABLE_VEHICLES + " Set " + PREVIOUS_REFILL_ODO_READING + "=" + currReading + " Where " + ID + "=" + vehicleId;
            db.execSQL(updateQuery);
            return true;

        } catch (Exception ex) {
        }

        return false;
    }

    public boolean updateVehicleFuelPrice(double updatedFuelPrice, int vehicleId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String updateQuery = "Update " + TABLE_VEHICLES + " Set " + FUEL_PRICE + "=" + updatedFuelPrice + " Where " + ID + "=" + vehicleId;
            db.execSQL(updateQuery);

            return true;
            // Now the vehicle fuel prices have been updated for the corresponding vehicle.
            // TODO : should we set this updated price for the whole state(region) or not?

        } catch (Exception ex) {
        }

        return false;
    }

    public boolean updateRegionFuelPrices(double petrolPrice, double dieselPrice, double cngPrice,
                                          int regionId) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            String updateQuery = "Update " + TABLE_FUELPRICES + " Set " + PETROL_PRICE + "=" + petrolPrice
                    + "," + DIESEL_PRICE + "=" + dieselPrice + "," + CNG_PRICE + "=" + cngPrice +
                    " Where " + ID + "=" + regionId;
            db.execSQL(updateQuery);

            return true;
        } catch (Exception ex) {
        }

        return false;
    }
}
