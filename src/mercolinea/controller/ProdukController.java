/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.controller;

import java.util.ArrayList;
import java.util.List;
import mercolinea.model.ProdukModel;
import mercolinea.model.Produk;
import mercolinea.model.KategoriModel;
import mercolinea.util.Session;

/**
 *
 * @author ASUS
 */
public class ProdukController {
    ProdukModel model = new ProdukModel();
    KategoriModel kat = new  KategoriModel();
    

    public void tambahProduk(int idKategori, String nama, double harga, int stok, String deskripsi) {
        Produk p = new Produk();
        p.setIdPenjual(Session.getIdUser());
        p.setIdKategori(idKategori);
        p.setNamaProduk(nama);
        p.setHarga(harga);
        p.setStok(stok);
        p.setDeskripsi(deskripsi);
        model.insert(p);
    }

    public void updateProduk(int idProduk, int idKategori, String nama, double harga, int stok, String deskripsi) {
        Produk p = new Produk();
        p.setIdProduk(idProduk);
        p.setIdPenjual(Session.getIdUser());
        p.setIdKategori(idKategori);
        p.setNamaProduk(nama);
        p.setHarga(harga);
        p.setStok(stok);
        p.setDeskripsi(deskripsi);
        model.update(p);
    }

    public void hapusProduk(int idProduk) {
        model.delete(idProduk, Session.getIdUser());
    }

    public ArrayList<Produk> tampilProduk() {
        return model.getByPenjual(Session.getIdUser());
    }
    
    public List<Produk> tampilProdukPembeli(String keyword) {
        return model.filterProduk(keyword, null);
    }


    public List<String> getListKategori() {
        return kat.getNamaKategori();
    }
    
    public List<Produk> tampilSemuaProduk() {
        return model.getAllProduk();
    }

    public List<Produk> cariDanFilterProduk(String keyword, Integer idKategori) {
        return model.filterProduk(keyword, idKategori);
    }



}
