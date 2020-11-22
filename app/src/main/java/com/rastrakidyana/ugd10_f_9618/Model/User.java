package com.rastrakidyana.ugd10_f_9618.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "npm")
    public int npmU;

    @ColumnInfo(name = "nama")
    public String namaU;

    @ColumnInfo(name = "fakultas")
    public String fakultasU;

    @ColumnInfo(name = "jurusan")
    public String jurusanU;

    @ColumnInfo(name = "ipk")
    public Double ipkU;

    @ColumnInfo(name = "foto")
    public String fotoU;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNpmU() {
        return npmU;
    }

    public void setNpmU(int npmU) {
        this.npmU = npmU;
    }

    public String getNamaU() {
        return namaU;
    }

    public void setNamaU(String namaU) {
        this.namaU = namaU;
    }

    public String getFakultasU() {
        return fakultasU;
    }

    public void setFakultasU(String fakultasU) {
        this.fakultasU = fakultasU;
    }

    public String getJurusanU() {
        return jurusanU;
    }

    public void setJurusanU(String jurusanU) {
        this.jurusanU = jurusanU;
    }

    public Double getIpkU() {
        return ipkU;
    }

    public void setIpkU(Double ipkU) {
        this.ipkU = ipkU;
    }

    public String getFotoU() {
        return fotoU;
    }

    public void setFotoU(String fotoU) {
        this.fotoU = fotoU;
    }
}
