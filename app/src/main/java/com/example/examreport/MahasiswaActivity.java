package com.example.examreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnaddmahasiswa;
    ListView listView;
    private MahasiswaAdapter adapter;
    private ArrayList<Mahasiswa> mahasiswaList;
    DatabaseReference dbMahasiswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        listView = findViewById(R.id.tmm_list);
        btnaddmahasiswa = findViewById(R.id.btn_addmahasiswa);
        btnaddmahasiswa.setOnClickListener(this);
        dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa");
        mahasiswaList = new ArrayList<>();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_addmahasiswa) {
            Intent intent = new Intent(MahasiswaActivity.this,
                    CreateMahasiswa.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        dbMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaList.clear();
                for (DataSnapshot mahasiswaSnapshot :
                        dataSnapshot.getChildren()) {
                    Mahasiswa mahasiswa =
                            mahasiswaSnapshot.getValue(Mahasiswa.class);
                    mahasiswaList.add(mahasiswa);
                }
                MahasiswaAdapter adapter = new
                        MahasiswaAdapter(MahasiswaActivity.this);
                adapter.setMahasiswaList(mahasiswaList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MahasiswaActivity.this, "Terjadi kesalahan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}