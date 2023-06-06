package com.example.kuysekolah.Model;

public class User {
    public String name,kelas,email,absen;
    public User(){

    }

    public User(String name, String kelas, String email,String absen) {
        this.name = name;
        this.kelas = kelas;
        this.email = email;
        this.absen = absen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbsen() {
        return absen;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
    }
}
