/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.controller;

import java.util.ArrayList;
import mercolinea.model.KategoriModel;
import mercolinea.model.Kategori;

import java.sql.*;
import java.util.*;
import mercolinea.util.KoneksiDB;

/**
 *
 * @author ASUS
 */
public class AdminController {
    KategoriModel model = new KategoriModel();

    public ArrayList<Kategori> getAllKategori() {
        return model.getAll();
    }
    public ArrayList<Kategori> getAll() {
        return model.getAllWithCount();
    }

    public boolean tambahKategori(String nama) {
        try {
            model.insert(nama);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editKategori(int id, String nama) {
        try {
            model.update(id, nama);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hapusKategori(int idKategori) {
        try {
            int jumlah = model.countProduk(idKategori);
            if (jumlah > 0) return false; // BLOKIR
            model.delete(idKategori);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /* ================= PEMBELI ================= */
    public List<Object[]> getDataPembeli() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT u.id_user, u.nama, u.username,
                   COUNT(t.id_transaksi) AS total_beli
            FROM users u
            LEFT JOIN transaksi t ON u.id_user = t.id_pembeli
            WHERE u.role = 'pembeli'
            GROUP BY u.id_user
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int no = 1;
            while (rs.next()) {
                list.add(new Object[]{
                    no++,
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getInt("total_beli")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= PENJUAL ================= */
    public List<Object[]> getDataPenjual() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT u.id_user, u.nama, u.username,
                   COUNT(DISTINCT p.id_produk) AS jumlah_produk,
                   COUNT(td.id_detail) AS total_dibeli
            FROM users u
            LEFT JOIN produk p ON u.id_user = p.id_penjual
            LEFT JOIN transaksi_detail td ON p.id_produk = td.id_produk
            WHERE u.role = 'penjual'
            GROUP BY u.id_user
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int no = 1;
            while (rs.next()) {
                list.add(new Object[]{
                    no++,
                    rs.getInt("id_user"),
                    rs.getString("nama"),
                    rs.getString("username"),
                    rs.getInt("jumlah_produk"),
                    rs.getInt("total_dibeli")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= PRODUK ================= */
    public List<Object[]> getDataProduk() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT p.id_produk, p.nama_produk,
                   u.nama AS nama_penjual,
                   p.stok,
                   IFNULL(SUM(td.qty), 0) AS jumlah_terjual
            FROM produk p
            JOIN users u ON p.id_penjual = u.id_user
            LEFT JOIN transaksi_detail td ON p.id_produk = td.id_produk
            GROUP BY p.id_produk
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int no = 1;
            while (rs.next()) {
                list.add(new Object[]{
                    no++,
                    rs.getInt("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getString("nama_penjual"),
                    rs.getInt("stok"),
                    rs.getInt("jumlah_terjual")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
