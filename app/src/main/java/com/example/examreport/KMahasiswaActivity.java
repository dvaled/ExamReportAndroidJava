package com.example.examreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KMahasiswaActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private Button btn_tkm;

    private KodeMahasiswaAdapter adapter;
    private ArrayList<KodeMahasiswa> kmahasiswaList;
    DatabaseReference dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kmahasiswa);
        listView = findViewById(R.id.tkm_list);
        btn_tkm = findViewById(R.id.btn_TKMahasiswa);
        btn_tkm.setOnClickListener(this);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("kodemahasiswa");
        kmahasiswaList = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                Intent intent = new Intent(KMahasiswaActivity.this,
                        EditKmahasiswa.class);
                intent.putExtra(EditKmahasiswa.EXTRA_KODEMAHASISWA,
                        kmahasiswaList.get(i));
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
                kmahasiswaList.clear();
                for (DataSnapshot mahasiswaSnapshot :
                        dataSnapshot.getChildren()) {
                    KodeMahasiswa kodemahasiswa =
                            mahasiswaSnapshot.getValue(KodeMahasiswa.class);
                    kmahasiswaList.add(kodemahasiswa);
                }
                KodeMahasiswaAdapter adapter = new
                        KodeMahasiswaAdapter(KMahasiswaActivity.this);
                adapter.setKodeMahasiswaList(kmahasiswaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(KMahasiswaActivity.this, "Terjadi kesalahan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.btn_TKMahasiswa){
            Intent intent = new Intent(KMahasiswaActivity.this, KmahasiswacActivity.class);
            startActivity(intent);
        }
    }
}
