package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.model.Mahasiswa;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private EditText etNim, etNama, etJurusan, etEmail;
    private Button btnSave;
    private ProgressBar progressBar;
    private boolean isEdit = false;
    private int editId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNim = view.findViewById(R.id.etNim);
        etNama = view.findViewById(R.id.etNama);
        etJurusan = view.findViewById(R.id.etJurusan);
        etEmail = view.findViewById(R.id.etEmail);
        btnSave = view.findViewById(R.id.btnSave);
        progressBar = view.findViewById(R.id.progressBar);

        Bundle args = getArguments();
        if (args != null && args.containsKey("id")) {
            isEdit = true;
            editId = args.getInt("id");
            etNim.setText(args.getString("nim"));
            etNama.setText(args.getString("nama"));
            etJurusan.setText(args.getString("jurusan"));
            etEmail.setText(args.getString("email"));
        }

        btnSave.setOnClickListener(v -> save());
    }

    private void save() {
        String nim = etNim.getText().toString().trim();
        String nama = etNama.getText().toString().trim();
        if (nim.isEmpty() || nama.isEmpty()) {
            Toast.makeText(getContext(), "NIM dan Nama wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Mahasiswa m = new Mahasiswa(editId, nim, nama,
                etJurusan.getText().toString().trim(),
                etEmail.getText().toString().trim());

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        Call<ResponseBody> call = isEdit ? api.update(m) : api.create(m);

        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(SecondFragment.this).navigateUp();
                } else {
                    progressBar.setVisibility(View.GONE);
                    btnSave.setEnabled(true);
                    Toast.makeText(getContext(), "Gagal (NIM mungkin sudah ada): " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                Toast.makeText(getContext(), "Koneksi gagal: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
