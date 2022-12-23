package com.example.examreport;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable {
    private String id_mahasiswa;
    private String nim_mahasiswa;
    private String nama_mahasiswa;

    public Mahasiswa() {

    }
        public String getId_mahasiswa() {
            return id_mahasiswa;
        }
        public void setId_mahasiswa (String id_mahasiswa){
            this.id_mahasiswa = id_mahasiswa;
        }
        public String getNim_mahasiswa () {
            return nim_mahasiswa;
        }
        public void setNim_mahasiswa (String nim_mahasiswa){
            this.nim_mahasiswa = nim_mahasiswa;
        }
        public String getNama_mahasiswa () {
            return nama_mahasiswa;
        }
        public void setNama_mahasiswa (String nama_mahasiswa){
            this.nama_mahasiswa = nama_mahasiswa;
        }
        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest,int flags){
            dest.writeString(this.id_mahasiswa);
            dest.writeString(this.nim_mahasiswa);
            dest.writeString(this.nama_mahasiswa);
        }
        protected Mahasiswa(Parcel in) {
            this.id_mahasiswa = in.readString();
            this.nim_mahasiswa = in.readString();
            this.nama_mahasiswa = in.readString();

        }

        public static final Parcelable.Creator<Mahasiswa> CREATOR
                = new Parcelable.Creator<Mahasiswa>() {
            @Override
            public Mahasiswa createFromParcel(Parcel source) {
                return new Mahasiswa(source);
            }
            @Override
            public Mahasiswa[] newArray(int size) {
                return new Mahasiswa[size];
            }
        };
}
