package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapter.MahasiswaAdapter;
import com.example.myapplication.api.ApiService;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.model.Mahasiswa;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private MahasiswaAdapter adapter;
    private ProgressBar progressBar;
    private ApiService api;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api = RetrofitClient.getInstance().create(ApiService.class);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new MahasiswaAdapter(new MahasiswaAdapter.Listener() {
            @Override
            public void onEdit(Mahasiswa m) {
                Bundle b = new Bundle();
                b.putInt("id", m.getId());
                b.putString("nim", m.getNim());
                b.putString("nama", m.getNama());
                b.putString("jurusan", m.getJurusan());
                b.putString("email", m.getEmail());
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, b);
            }

            @Override
            public void onDelete(Mahasiswa m) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Hapus data")
                        .setMessage("Hapus " + m.getNama() + "?")
                        .setPositiveButton("Hapus", (d, w) -> deleteMahasiswa(m.getId()))
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        view.findViewById(R.id.fabAdd).setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment));

        loadData();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        api.getAll().enqueue(new Callback<List<Mahasiswa>>() {
            @Override
            public void onResponse(Call<List<Mahasiswa>> call, Response<List<Mahasiswa>> response) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body());
                } else {
                    Toast.makeText(getContext(), "Server error: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mahasiswa>> call, Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Koneksi gagal: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteMahasiswa(int id) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Integer> idMap = new HashMap<>();
        idMap.put("id", id);
        api.delete(idMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Data dihapus", Toast.LENGTH_SHORT).show();
                loadData();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Gagal: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
