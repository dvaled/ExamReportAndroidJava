package com.example.examreport;

import android.os.Parcel;
import android.os.Parcelable;

public class NilaiMahasiswa implements Parcelable {
    private String Id;
    private String nama_mahasiswa;
    private String nim_mahasiswa;
    private String materi;
    private String nilai;
    public NilaiMahasiswa(){

    }
    public String getId(){
        return Id;
    }
    public void setId(String Id){
        this.Id = Id;
    }
    public String getNama_mahasiswa(){
        return nama_mahasiswa;
    }
    public void setNama_mahasiswa(String nama_mahasiswa){
        this.nama_mahasiswa = nama_mahasiswa;
    }
    public String getNim_mahasiswa(){
        return nim_mahasiswa;
    }
    public void setNim_mahasiswa(String nim_mahasiswa){
        this.nim_mahasiswa = nim_mahasiswa;
    }
    public String getMateri(){
        return materi;
    }
    public void setMateri(String materi){
        this.materi = materi;
    }
    public String getNilai(){
        return nilai;
    }
    public void setNilai(String nilai){
        this.nilai = nilai;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.nama_mahasiswa);
        dest.writeString(this.nim_mahasiswa);
        dest.writeString(this.materi);
        dest.writeString(this.nilai);
    }
    protected NilaiMahasiswa(Parcel in) {
        this.Id = in.readString();
        this.nama_mahasiswa = in.readString();
        this.nim_mahasiswa = in.readString();
        this.materi = in.readString();
        this.nilai = in.readString();
    }
    public static final Parcelable.Creator<NilaiMahasiswa> CREATOR
            = new Parcelable.Creator<NilaiMahasiswa>(){
        @Override
        public NilaiMahasiswa createFromParcel(Parcel source){
            return new NilaiMahasiswa(source);
        }
        @Override
        public NilaiMahasiswa[] newArray(int size){
            return new NilaiMahasiswa[size];
        }
    };
}
