package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Mahasiswa;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.VH> {

    public interface Listener {
        void onEdit(Mahasiswa m);
        void onDelete(Mahasiswa m);
    }

    private List<Mahasiswa> list = new ArrayList<>();
    private final Listener listener;

    public MahasiswaAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<Mahasiswa> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mahasiswa, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Mahasiswa m = list.get(position);
        h.tvNama.setText(m.getNama());
        h.tvNim.setText("NIM: " + m.getNim());
        h.tvJurusan.setText("Jurusan: " + m.getJurusan());
        h.tvEmail.setText(m.getEmail());
        h.btnEdit.setOnClickListener(v -> listener.onEdit(m));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(m));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNama, tvNim, tvJurusan, tvEmail;
        Button btnEdit, btnDelete;

        VH(View v) {
            super(v);
            tvNama = v.findViewById(R.id.tvNama);
            tvNim = v.findViewById(R.id.tvNim);
            tvJurusan = v.findViewById(R.id.tvJurusan);
            tvEmail = v.findViewById(R.id.tvEmail);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
