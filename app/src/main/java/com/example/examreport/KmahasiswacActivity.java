package com.example.examreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KmahasiswacActivity extends AppCompatActivity {
    private ListView listView;

    private MahasiswaAdapter adapter;
    private ArrayList<Mahasiswa> mahasiswaList;
    DatabaseReference dbMahasiswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kmahasiswac);

        listView = findViewById(R.id.tkmc_list);
        dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa");
        mahasiswaList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Intent intent = new Intent(KmahasiswacActivity.this,
                        CreateKodeM.class);
                intent.putExtra(CreateKodeM.EXTRA_KODEMAHASISWA,
                        mahasiswaList.get(i));
                startActivity(intent);
            }
        });
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
                        MahasiswaAdapter(KmahasiswacActivity.this);
                adapter.setMahasiswaList(mahasiswaList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(KmahasiswacActivity.this, "Terjadi kesalahan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}