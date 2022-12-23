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

public class EditKmahasiswa extends AppCompatActivity implements View.OnClickListener {
    private EditText edtSmester, edtKode;
    private TextView shwNama, shwNim;
    private Button btnUpdate;

    public static final String EXTRA_KODEMAHASISWA = "extra_kodemahasiswa";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;

    private KodeMahasiswa kodeMahasiswa;
    private String kmahasiswaId;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kmahasiswa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        shwNama = findViewById(R.id.edt_edit_namak);
        shwNim = findViewById(R.id.edt_edit_nimk);
        edtSmester = findViewById(R.id.edt_edit_smester);
        edtKode = findViewById(R.id.edt_edit_kode);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);

        kodeMahasiswa = getIntent().getParcelableExtra(EXTRA_KODEMAHASISWA);
        if (kodeMahasiswa != null) {
            kmahasiswaId = kodeMahasiswa.getId();
        } else {
            kodeMahasiswa = new KodeMahasiswa();
        }
        if (kodeMahasiswa != null) {
            shwNim.setText(kodeMahasiswa.getNim());
            shwNama.setText(kodeMahasiswa.getNama());
            edtSmester.setText(kodeMahasiswa.getSmester());
            edtKode.setText(kodeMahasiswa.getKode());
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
        String smester = edtSmester.getText().toString().trim();
        String kode = edtKode.getText().toString().trim();
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            shwNama.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            shwNim.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(smester)) {
            isEmptyFields = true;
            edtSmester.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(kode)) {
            isEmptyFields = true;
            edtKode.setError("Field ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(EditKmahasiswa.this, "Updating Data...",
                    Toast.LENGTH_SHORT).show();
            kodeMahasiswa.setNim(nim);
            kodeMahasiswa.setNama(nama);
            kodeMahasiswa.setSmester(smester);
            kodeMahasiswa.setKode(kode);
            DatabaseReference dbMahasiswa =
                    mDatabase.child("kodemahasiswa");
            dbMahasiswa.child(kmahasiswaId).setValue(kodeMahasiswa);
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
                                            mDatabase.child("kodemahasiswa").child(kmahasiswaId);
                                    dbMahasiswa.removeValue();
                                    Toast.makeText(EditKmahasiswa.this,
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