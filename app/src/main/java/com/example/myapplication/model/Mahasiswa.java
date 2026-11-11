package com.example.myapplication.model;

public class Mahasiswa {
    private int id;
    private String nim;
    private String nama;
    private String jurusan;
    private String email;

    public Mahasiswa() {}

    public Mahasiswa(int id, String nim, String nama, String jurusan, String email) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
