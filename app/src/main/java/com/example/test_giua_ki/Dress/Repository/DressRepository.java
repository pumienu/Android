package com.example.test_giua_ki.Dress.Repository;

import com.example.test_giua_ki.Dress.Model.Dress;

import java.util.ArrayList;

public class DressRepository {
    private static ArrayList<Dress> dressList = new ArrayList<>();

    public DressRepository(ArrayList<Dress> lst) {
        for (Dress p: lst){
            this.dressList.add(p);
        }
    }
    public DressRepository(){

    }

    public static ArrayList<Dress> getDressList() {
        return dressList;
    }

    public static void setDressList(ArrayList<Dress> dressList) {
        DressRepository.dressList = dressList;
    }

    public void addDress(Dress p){
        this.dressList.add(p);
    }

    public Dress getDress(Integer id){
        Dress result;
        for (Dress p : dressList) {
            if (id == p.getId())
                return p;
        }
        return  null;
    }
}
