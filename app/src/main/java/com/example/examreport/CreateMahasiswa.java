package com.example.examreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateMahasiswa extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNim, edtNama;
    private Button btnSave;
    private Mahasiswa mahasiswa;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mahasiswa);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtNama = findViewById(R.id.edt_nama_mahasiswa);
        edtNim = findViewById(R.id.edt_nim_mahasiswa);
        btnSave = findViewById(R.id.btn_saveM);
        btnSave.setOnClickListener(this);
        mahasiswa = new Mahasiswa();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_saveM) {
            saveMahasiswa();
        }
    }
    private void saveMahasiswa()
    {
        String nama = edtNama.getText().toString().trim();
        String nim = edtNim.getText().toString().trim();
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            edtNim.setError("Field ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(CreateMahasiswa.this, "Saving Data...",
                    Toast.LENGTH_SHORT).show();
            DatabaseReference dbMahasiswa =
                    mDatabase.child("mahasiswa");
            String id = dbMahasiswa.push().getKey();
            mahasiswa.setId_mahasiswa(id);
            mahasiswa.setNim_mahasiswa(nim);
            mahasiswa.setNama_mahasiswa(nama);
            //insert data
            dbMahasiswa.child(id).setValue(mahasiswa);
            finish();
        }
    }
}