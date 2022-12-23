package com.example.examreport;

import android.os.Parcel;
import android.os.Parcelable;

public class KodeMahasiswa implements Parcelable {
    private String id;
    private String nama;
    private String nim;
    private String smester;
    private String kode;
    public KodeMahasiswa(){

    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    public String getNim(){
        return nim;
    }
    public void setNim(String nim){
        this.nim = nim;
    }
    public String getSmester(){
        return smester;
    }
    public void setSmester(String smester){
        this.smester = smester;
    }
    public String getKode(){
        return kode;
    }
    public void setKode(String kode){
        this.kode = kode;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.nim);
        dest.writeString(this.smester);
        dest.writeString(this.kode);
    }
    protected KodeMahasiswa(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.nim = in.readString();
        this.smester = in.readString();
        this.kode = in.readString();
    }
    public static final Parcelable.Creator<KodeMahasiswa> CREATOR
            = new Parcelable.Creator<KodeMahasiswa>(){
        @Override
        public KodeMahasiswa createFromParcel(Parcel source){
            return new KodeMahasiswa(source);
        }
        @Override
        public KodeMahasiswa[] newArray(int size){
            return new KodeMahasiswa[size];
        }
    };
}