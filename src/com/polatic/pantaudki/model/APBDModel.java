
package com.polatic.pantaudki.model;


import android.os.Parcel;
import android.os.Parcelable;

public class APBDModel implements Parcelable  {
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
    private String isPhysic;
    
    public APBDModel(String id, String year, String unit,
            String SKPDName, String urusanID, String urusanName,
            String programID, String programName, String kegiatanID, String kegiatanName, 
            String price, String priceRealization, String percentRealization, String isPhysic) {

        this.id = id;
        this.year = year;
        this.unit = unit;
        this.SKPDName = SKPDName;
        this.urusanID = urusanID;
        this.urusanName = urusanName;
        this.programID = programID;
        this.programName = programName;
        this.price = price;
        this.priceRealization = priceRealization;
        this.percentRealization = percentRealization;
        this.kegiatanID = kegiatanID;
        this.kegiatanName = kegiatanName;
        this.isPhysic = kegiatanName;
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
    
    public String getIsPhysic() {
        return isPhysic;
    }
    
    /**
     * Write native data to parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(year);
        dest.writeString(unit);
        dest.writeString(SKPDName);
        dest.writeString(urusanID);
        dest.writeString(urusanName);
        dest.writeString(programID);
        dest.writeString(programName);
        dest.writeString(price);
        dest.writeString(priceRealization);
        dest.writeString(percentRealization);
        dest.writeString(kegiatanID);
        dest.writeString(kegiatanName);
        dest.writeString(kegiatanName);        
    }

    /** from Parcel, reads back fields IN THE ORDER they were written */
    public APBDModel(Parcel in) {
        id = in.readString();
        year = in.readString();
        unit = in.readString();
        SKPDName = in.readString();
        urusanID = in.readString();
        urusanName = in.readString();
        programID = in.readString();
        programName = in.readString();
        price = in.readString();
        priceRealization = in.readString();
        percentRealization = in.readString();
        kegiatanID = in.readString();
        kegiatanName = in.readString();
        isPhysic = in.readString();
    }

    public static final Parcelable.Creator<APBDModel> CREATOR = 
            new Parcelable.Creator<APBDModel>() {
        public APBDModel createFromParcel(Parcel in) {
            return new APBDModel(in); 
        }

        public APBDModel[] newArray(int size) {
            return new APBDModel[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

}
