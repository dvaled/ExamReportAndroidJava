package com.example.examreport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NilaiMahasiswaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NilaiMahasiswa> mahasiswaList = new
            ArrayList<>();
    public void setNilaiMahasiswaList(ArrayList<NilaiMahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }
    public NilaiMahasiswaAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return mahasiswaList.size();
    }
    @Override
    public Object getItem(int i) {
        return mahasiswaList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_nmahasiswa,
                            viewGroup, false);
        }
        NilaiMahasiswaAdapter.ViewHolder viewHolder = new NilaiMahasiswaAdapter.ViewHolder(itemView);
        NilaiMahasiswa mahasiswa = (NilaiMahasiswa) getItem(i);
        viewHolder.bind(mahasiswa);
        return itemView;
    }
    private class ViewHolder {
        private TextView txtNim, txtNilai, txtNama, txtMateri;
        ViewHolder(View view) {
            txtNim = view.findViewById(R.id.txt_nim);
            txtNama = view.findViewById(R.id.txt_nama);
            txtMateri = view.findViewById(R.id.txt_materi);
            txtNilai = view.findViewById(R.id.txt_nilai);


        }
        void bind(NilaiMahasiswa mahasiswa) {
            txtNim.setText(mahasiswa.getNim_mahasiswa());
            txtNama.setText(mahasiswa.getNama_mahasiswa());
            txtMateri.setText(mahasiswa.getMateri());
            txtNilai.setText(mahasiswa.getNilai());
        }

    }
}

