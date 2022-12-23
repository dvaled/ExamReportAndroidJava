package com.example.examreport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNmahasiswa extends AppCompatActivity implements View.OnClickListener {
    private EditText  edtMateri, edtNilai;
    private TextView shwNama, shwNim;
    private Button btnUpdate;

    public static final String EXTRA_NILAIMAHASISWA = "extra_nilaimahasiswa";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private NilaiMahasiswa nilaiMahasiswa;
    private String nmahasiswaId;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nmahasiswa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        shwNama = findViewById(R.id.edt_edit_nama);
        shwNim = findViewById(R.id.edt_edit_nim);
        edtMateri = findViewById(R.id.edt_edit_materi);
        edtNilai = findViewById(R.id.edt_edit_nilai);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        nilaiMahasiswa = getIntent().getParcelableExtra(EXTRA_NILAIMAHASISWA);
        if (nilaiMahasiswa != null) {
            nmahasiswaId = nilaiMahasiswa.getId();
        } else {
            nilaiMahasiswa = new NilaiMahasiswa();
        }
        if (nilaiMahasiswa != null) {
            shwNim.setText(nilaiMahasiswa.getNim_mahasiswa());
            shwNama.setText(nilaiMahasiswa.getNama_mahasiswa());
            edtMateri.setText(nilaiMahasiswa.getMateri());
            edtNilai.setText(nilaiMahasiswa.getNilai());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateMahasiswa();
        }
    }
    private void updateMahasiswa() {
        String nama = shwNama.getText().toString().trim();
        String nim = shwNim.getText().toString().trim();
        String materi = edtMateri.getText().toString().trim();
        String nilai = edtNilai.getText().toString().trim();
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            shwNama.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            shwNim.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(materi)) {
            isEmptyFields = true;
            edtMateri.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nilai)) {
            isEmptyFields = true;
            edtNilai.setError("Field ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(EditNmahasiswa.this, "Updating Data...",
                    Toast.LENGTH_SHORT).show();
            nilaiMahasiswa.setNim_mahasiswa(nim);
            nilaiMahasiswa.setNama_mahasiswa(nama);
            nilaiMahasiswa.setMateri(materi);
            nilaiMahasiswa.setNilai(nilai);
            DatabaseReference dbMahasiswa =
                    mDatabase.child("nilaimahasiswa");
            dbMahasiswa.child(nmahasiswaId).setValue(nilaiMahasiswa);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //pilih menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        showAlertDialog(ALERT_DIALOG_CLOSE);
    }
    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
        }
        AlertDialog.Builder alertDialogBuilder = new
                AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new
                        DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isDialogClose) {
                                    finish();
                                } else {
                                    DatabaseReference dbMahasiswa =
                                            mDatabase.child("nilaimahasiswa").child(nmahasiswaId);
                                    dbMahasiswa.removeValue();
                                    Toast.makeText(EditNmahasiswa.this,
                                            "Deleting data...",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }).setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface

                                                        dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
