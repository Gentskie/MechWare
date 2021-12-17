package com.example.mechware.Helper;

import com.example.mechware.Helper.AircraftSubHelper.EngineCIHelper;
import com.example.mechware.Helper.AircraftSubHelper.PropellerCIHelper;

public class AircraftRecordHelper {
    public String manufacturer, model, serial, registration_number, date_of_manufacture, uid;

    public EngineCIHelper engineCIHelper;
    public PropellerCIHelper propellerCIHelper;

    public AircraftRecordHelper() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDate_of_manufacture() {
        return date_of_manufacture;
    }

    public void setDate_of_manufacture(String date_of_manufacture) {
        this.date_of_manufacture = date_of_manufacture;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public EngineCIHelper getEngineCIHelper() {
        return engineCIHelper;
    }

    public void setEngineCIHelper(EngineCIHelper engineCIHelper) {
        this.engineCIHelper = engineCIHelper;
    }

    public PropellerCIHelper getPropellerCIHelper() {
        return propellerCIHelper;
    }

    public void setPropellerCIHelper(PropellerCIHelper propellerCIHelper) {
        this.propellerCIHelper = propellerCIHelper;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
