package com.example.onthick;

public class SinhVien {
    private int id;
    private String Ten;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SinhVien() {
    }

    public SinhVien(int id, String ten) {
        this.id = id;
        Ten = ten;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public SinhVien(String ten, int id) {
        Ten = ten;
        this.id = id;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "id=" + id +
                ", Ten='" + Ten + '\'' +
                '}';
    }
}
