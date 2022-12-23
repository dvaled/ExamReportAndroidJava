package com.example.examreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;
public class KodeMahasiswaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<KodeMahasiswa> mahasiswaList = new
            ArrayList<>();
    public void setKodeMahasiswaList(ArrayList<KodeMahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }
    public KodeMahasiswaAdapter(Context context) {
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
                    .inflate(R.layout.item_kmahasiswa,
                            viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(itemView);
        KodeMahasiswa mahasiswa = (KodeMahasiswa) getItem(i);
        viewHolder.bind(mahasiswa);
        return itemView;
    }
    private class ViewHolder {
        private TextView txtNim, txtKode, txtNama, txtSmester;
        ViewHolder(View view) {
            txtNim = view.findViewById(R.id.txt_nim);
            txtNama = view.findViewById(R.id.txt_nama);
            txtSmester = view.findViewById(R.id.txt_smester);
            txtKode = view.findViewById(R.id.txt_kode);


        }
        void bind(KodeMahasiswa mahasiswa) {
            txtNim.setText(mahasiswa.getNim());
            txtNama.setText(mahasiswa.getNama());
            txtSmester.setText(mahasiswa.getSmester());
            txtKode.setText(mahasiswa.getKode());
        }
    }
}
