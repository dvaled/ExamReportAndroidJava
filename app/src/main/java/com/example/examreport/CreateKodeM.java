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

public class CreateKodeM extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSmester, edtKode;
    private TextView shwNama, shwNim;
    private Button btnSaveK;

    public static final String EXTRA_KODEMAHASISWA = "extra_kodemahasiswa";
    private KodeMahasiswa kodeMahasiswa;
    private Mahasiswa mahasiswa;
    private String mahasiswaId;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kode_m);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        shwNama =findViewById(R.id.edt_nama);
        shwNim = findViewById(R.id.edt_nim);
        edtSmester = findViewById(R.id.edt_smester);
        edtKode = findViewById(R.id.edt_kode);
        btnSaveK = findViewById(R.id.btn_savek);
        btnSaveK.setOnClickListener(this);

        kodeMahasiswa = new KodeMahasiswa();


        mahasiswa = getIntent().getParcelableExtra(EXTRA_KODEMAHASISWA);
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
        if (view.getId() == R.id.btn_savek) {
            saveKodeMahasiswa();
        }
    }
    private void saveKodeMahasiswa()
    {
        String nama = shwNama.getText().toString().trim();
        String nim = shwNim.getText().toString().trim();
        String smester = edtSmester.getText().toString().trim();
        String kode = edtKode.getText().toString().trim();
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            shwNama.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            shwNim.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(smester)) {
            isEmptyFields = true;
            edtSmester.setError("Data ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(kode)) {
            isEmptyFields = true;
            edtKode.setError("Data ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(CreateKodeM.this, "Saving Data...",
                    Toast.LENGTH_SHORT).show();
            DatabaseReference dbMahasiswa =
                    mDatabase.child("kodemahasiswa");
            String id = dbMahasiswa.push().getKey();
            kodeMahasiswa.setId(id);
            kodeMahasiswa.setNama(nama);
            kodeMahasiswa.setNim(nim);
            kodeMahasiswa.setSmester(smester);
            kodeMahasiswa.setKode(kode);
            //insert data
            dbMahasiswa.child(id).setValue(kodeMahasiswa);
            finish();
        }
    }
}