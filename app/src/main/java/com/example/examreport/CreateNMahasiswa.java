package com.example.examreport;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNMahasiswa extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMateri, edtNilai;
    private TextView shwNama, shwNim;
    private Button btnSaveN;

    public static final String EXTRA_NILAIMAHASISWA = "extra_nilaimahasiswa";
    private Mahasiswa mahasiswa;
    private NilaiMahasiswa nilaiMahasiswa;
    private String mahasiswaId;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_nmahasiswa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        shwNama =findViewById(R.id.edt_nama);
        shwNim = findViewById(R.id.edt_nim);
        edtMateri = findViewById(R.id.edt_materi);
        edtNilai = findViewById(R.id.edt_nilai);
        btnSaveN = findViewById(R.id.btn_saveN);
        btnSaveN.setOnClickListener(this);

        nilaiMahasiswa = new NilaiMahasiswa();

        mahasiswa = getIntent().getParcelableExtra(EXTRA_NILAIMAHASISWA);
        if (mahasiswa != null) {
            mahasiswaId = mahasiswa.getId_mahasiswa();
        } else {
            mahasiswa = new Mahasiswa();
        }
        if (mahasiswa != null) {
            shwNim.setText(mahasiswa.getNim_mahasiswa());
            shwNama.setText(mahasiswa.getNama_mahasiswa());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_saveN) {
            saveNilaiMahasiswa();
        }
    }
    private void saveNilaiMahasiswa()
    {
        String nama = shwNama.getText().toString().trim();
        String nim = shwNim.getText().toString().trim();
        String materi = edtMateri.getText().toString().trim();
        String nilai = edtNilai.getText().toString().trim();
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            shwNama.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            shwNim.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(materi)) {
            isEmptyFields = true;
            edtMateri.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nilai)) {
            isEmptyFields = true;
            edtNilai.setError("Data ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(CreateNMahasiswa.this, "Saving Data...",
                    Toast.LENGTH_SHORT).show();
            DatabaseReference dbMahasiswa = mDatabase.child("nilaimahasiswa");
            String id = dbMahasiswa.push().getKey();
            nilaiMahasiswa.setId(id);
            nilaiMahasiswa.setNama_mahasiswa(nama);
            nilaiMahasiswa.setNim_mahasiswa(nim);
            nilaiMahasiswa.setMateri(materi);
            nilaiMahasiswa.setNilai(nilai);
            //insert data
            dbMahasiswa.child(id).setValue(nilaiMahasiswa);
            finish();
        }
    }
}