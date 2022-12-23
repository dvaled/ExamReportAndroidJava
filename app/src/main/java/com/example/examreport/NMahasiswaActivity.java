package com.example.examreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NMahasiswaActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private Button btn_tnm;

    private NilaiMahasiswaAdapter adapter;
    private ArrayList<NilaiMahasiswa> NmahasiswaList;
    DatabaseReference dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmahasiswa);
        listView = findViewById(R.id.tnm_list);
        btn_tnm = findViewById(R.id.btn_TNMahasiswa);
        btn_tnm.setOnClickListener(this);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("nilaimahasiswa");
        NmahasiswaList = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Intent intent = new Intent(NMahasiswaActivity.this,
                        EditNmahasiswa.class);
                intent.putExtra(EditNmahasiswa.EXTRA_NILAIMAHASISWA,
                        NmahasiswaList.get(i));
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
                NmahasiswaList.clear();
                for (DataSnapshot mahasiswaSnapshot :
                        dataSnapshot.getChildren()) {
                    NilaiMahasiswa nilaimahasiswa =
                            mahasiswaSnapshot.getValue(NilaiMahasiswa.class);
                    NmahasiswaList.add(nilaimahasiswa);
                }
                NilaiMahasiswaAdapter adapter = new
                        NilaiMahasiswaAdapter(NMahasiswaActivity.this);
                adapter.setNilaiMahasiswaList(NmahasiswaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NMahasiswaActivity.this, "Terjadi kesalahan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_TNMahasiswa){
            Intent intent = new Intent(NMahasiswaActivity.this, NmahasiswacActivity.class);
            startActivity(intent);
        }
    }
}