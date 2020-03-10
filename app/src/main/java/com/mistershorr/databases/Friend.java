package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
    private int clumsiness;
    private double gymFrequency;
    private boolean Awesome;
    private double moneyOwed;
    private String name;
    private int trustworthiness;

    // backendless specific fields
    // add the getters and setters

    private String objectId;
    private String ownerId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
// Must have a default, no parameter constructor
    // Must have getters & setters


    public Friend()
    {

    }

    public int getClumsiness()
    {
        return clumsiness;
    }

    public void setClumsiness(int clumsiness)
    {
        this.clumsiness = clumsiness;
    }

    public double getGymFrequency()
    {
        return gymFrequency;
    }

    public void setGymFrequency(double gymFrequency)
    {
        this.gymFrequency = gymFrequency;
    }

    public boolean isAwesome()
    {
        return Awesome;
    }

    public void setAwesome(boolean awesome)
    {
        Awesome = awesome;
    }

    public double getMoneyOwed()
    {
        return moneyOwed;
    }

    public void setMoneyOwed(double moneyOwed)
    {
        this.moneyOwed = moneyOwed;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getTrustworthiness()
    {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness)
    {
        this.trustworthiness = trustworthiness;
    }

    @Override
    public String toString()
    {
        return "Friend{" +
                "clumsiness=" + clumsiness +
                ", gymFrequency=" + gymFrequency +
                ", Awesome=" + Awesome +
                ", moneyOwed=" + moneyOwed +
                ", name='" + name + '\'' +
                ", trustworthiness=" + trustworthiness +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.gymFrequency);
        dest.writeByte(this.Awesome ? (byte) 1 : (byte) 0);
        dest.writeInt(this.clumsiness);
        dest.writeDouble(this.moneyOwed);
        dest.writeString(this.name);
        dest.writeInt(this.trustworthiness);
        dest.writeString(this.ownerId);
        dest.writeString(this.objectId);
    }

    protected Friend(Parcel in) {
        this.gymFrequency = in.readDouble();
        this.Awesome = in.readByte() != 0;
        this.clumsiness = in.readInt();
        this.moneyOwed = in.readDouble();
        this.name = in.readString();
        this.trustworthiness = in.readInt();
        this.ownerId = in.readString();
        this.objectId = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
