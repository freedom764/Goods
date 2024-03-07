package com.example.goods;

public class Pokupatel {
    String id;
    String fio;
    String city;
    String telephone;
    String email;
    String password;
int isprodavec;
int blokirovka;

    public Pokupatel(String id, String fio, String city, String telephone, String email, String password, int isprodavec, int blokirovka) {
        this.id = id;
        this.fio = fio;
        this.city = city;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.isprodavec = isprodavec;
        this.blokirovka = blokirovka;

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
    public String getFio() {
        return fio;
    }

    public int getBlokirovka() {
        return blokirovka;
    }
    public int getIsprodavec() {
        return isprodavec;
    }

}

