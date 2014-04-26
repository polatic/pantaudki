
package com.polatic.pantaudki.model;

public class APDBModel {
    private String id;
    private String year;
    private String unit;
    private String SKPDName;
    private String urusanID;
    private String urusanName;
    private String programID;
    private String programName;
    private String kegiatanNumber;
    private String price;
    private String priceRealization;
    private String percentRealization;
    private String kegiatanID;
    private String kegiatanName;
    private String realization;

    public APDBModel(String id, String year, String unit,
            String SKPDName, String urusanID, String urusanName,
            String programID, String programName, String kegiatanNumber,
            String price, String priceRealization, String percentRealization,
            String kegiatanID, String kegiatanName, String realization) {

        this.id = id;
        this.year = year;
        this.unit = unit;
        this.SKPDName = SKPDName;
        this.urusanID = urusanID;
        this.urusanName = urusanName;
        this.programID = programID;
        this.programName = programName;
        this.kegiatanNumber = kegiatanNumber;
        this.price = price;
        this.priceRealization = priceRealization;
        this.percentRealization = percentRealization;
        this.kegiatanID = kegiatanID;
        this.kegiatanName = kegiatanName;
        this.realization = realization;
    }

    public String getID() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getUnit() {
        return unit;
    }

    public String getSKPDName() {
        return SKPDName;
    }

    public String getUrusanID() {
        return urusanID;
    }

    public String getUrusanName() {
        return urusanName;
    }

    public String getProgramID() {
        return programID;
    }

    public String getProgramName() {
        return programName;
    }

    public String getKegiatanNumber() {
        return kegiatanNumber;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceRealization() {
        return priceRealization;
    }

    public String getPercentRealization() {
        return percentRealization;
    }

    public String getKegiatanID() {
        return kegiatanID;
    }

    public String getKegiatanName() {
        return kegiatanName;
    }

    public String getRealization() {
        return realization;
    }

}
