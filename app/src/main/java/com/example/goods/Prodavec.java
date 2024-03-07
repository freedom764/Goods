package com.example.goods;

public class    Prodavec {
    String id;
    String fio;

    String city;
    String magazin;
    String adres;
    String email;
    String password;
    String telephone;
    int blokirovka;
    int isprodavec;



    public Prodavec(String id, String fio, String city, String magazin, String adres, String telephone, String email, String password, int blokirovka, int isprodavec) {
        this.id = id;
        this.fio = fio;
        this.city = city;
        this.magazin = magazin;
        this.adres = adres;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.blokirovka = blokirovka;
        this.isprodavec = isprodavec;
    }

    public String getId() {
        return id;
    }

    public String getNameprodavec() {
        return fio;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }
    public String getTelephone() {
        return telephone;
    }

    public String getNamemagazin() {
        return magazin;
    }

    public String getAdres() {
        return adres;
    }
    public int getBlokirovka() {
        return blokirovka;
    }
    public int getIsprodavec() {
        return isprodavec;
    }
}
