package com.example.examreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnKMahasiswa, btnNMahasiswa, btnMahasiswa, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnKMahasiswa = findViewById(R.id.btn_KMahasiswa);
        btnKMahasiswa.setOnClickListener(this);
        btnNMahasiswa = findViewById(R.id.btn_NMahasiswa);
        btnNMahasiswa.setOnClickListener(this);
        btnMahasiswa = findViewById(R.id.btn_Mahasiswa);
        btnMahasiswa.setOnClickListener(this);
        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }
    @Override
    public void onClick(View view){
        if (view.getId() == R.id.btn_KMahasiswa){
            Intent intent = new Intent(MainActivity.this, KMahasiswaActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_NMahasiswa){
            Intent intent = new Intent(MainActivity.this, NMahasiswaActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_Mahasiswa){
            Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
            startActivity(intent);
        }

    }

}