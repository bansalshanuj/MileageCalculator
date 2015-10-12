package com.development.shanujbansal.mileagecalc;

/**
 * Created by shanuj.bansal on 4/20/2015.
 */
public class RegionFuel {
    private int _id;
    private String _region;
    private double _petrolPrice;
    private double _dieselPrice;
    private double _cngPrice;

    public RegionFuel(int id, String region, double petrolPrice, double dieselPrice, double cngPrice) {
        this._id = id;
        this._region = region;
        this._cngPrice = cngPrice;
        this._petrolPrice = petrolPrice;
        this._dieselPrice = dieselPrice;
    }

    public int getId() {
        return this._id;
    }

    public String getRegion() {
        return this._region;
    }

    public double getPetrolPrice() {
        return this._petrolPrice;
    }

    public double getDieselPrice() {
        return this._dieselPrice;
    }

    public double getCngPrice() {
        return this._cngPrice;
    }
}
