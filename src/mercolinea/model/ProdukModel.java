/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mercolinea.util.KoneksiDB;
/**
 *
 * @author ASUS
 */
public class ProdukModel {
    public void insert(Produk p) {
        try {
            String sql = "INSERT INTO produk (id_penjual, id_kategori, nama_produk, harga, stok, deskripsi) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setInt(1, p.getIdPenjual());
            ps.setInt(2, p.getIdKategori());
            ps.setString(3, p.getNamaProduk());
            ps.setDouble(4, p.getHarga());
            ps.setInt(5, p.getStok());
            ps.setString(6, p.getDeskripsi());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Insert produk error: " + e.getMessage());
        }
    }

    public void update(Produk p) {
        try {
            String sql = "UPDATE produk SET id_kategori=?, nama_produk=?, harga=?, stok=?, deskripsi=? "
                       + "WHERE id_produk=? AND id_penjual=?";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setInt(1, p.getIdKategori());
            ps.setString(2, p.getNamaProduk());
            ps.setDouble(3, p.getHarga());
            ps.setInt(4, p.getStok());
            ps.setString(5, p.getDeskripsi());
            ps.setInt(6, p.getIdProduk());
            ps.setInt(7, p.getIdPenjual());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update produk error: " + e.getMessage());
        }
    }

    public void delete(int idProduk, int idPenjual) {
        try {
            String sql = "DELETE FROM produk WHERE id_produk=? AND id_penjual=?";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setInt(1, idProduk);
            ps.setInt(2, idPenjual);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Delete produk error: " + e.getMessage());
        }
    }

    public ArrayList<Produk> getByPenjual(int idPenjual) {
        ArrayList<Produk> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produk WHERE id_penjual=?";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setInt(1, idPenjual);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produk p = new Produk();
                p.setIdProduk(rs.getInt("id_produk"));
                p.setIdPenjual(rs.getInt("id_penjual"));
                p.setIdKategori(rs.getInt("id_kategori"));
                p.setNamaProduk(rs.getString("nama_produk"));
                p.setHarga(rs.getDouble("harga"));
                p.setStok(rs.getInt("stok"));
                p.setDeskripsi(rs.getString("deskripsi"));
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println("Get produk error: " + e.getMessage());
        }
        return list;
    }
    
    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();

        String sql = """
            SELECT 
                id_produk,
                nama_produk,
                harga,
                stok,
                deskripsi,
                id_kategori
            FROM produk
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produk p = new Produk();
                p.setIdProduk(rs.getInt("id_produk"));
                p.setNamaProduk(rs.getString("nama_produk"));
                p.setHarga(rs.getInt("harga"));
                p.setStok(rs.getInt("stok"));
                p.setDeskripsi(rs.getString("deskripsi"));
                p.setIdKategori(rs.getInt("id_kategori"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return list;
}

    
    public List<Produk> filterProduk(String keyword, Integer idKategori) {
        List<Produk> list = new ArrayList<>();

        String sql = """
            SELECT 
                id_produk,
                nama_produk,
                harga,
                stok,
                deskripsi,
                id_kategori
            FROM produk
            WHERE nama_produk LIKE ?
        """;

        if (idKategori != null) {
            sql += " AND id_kategori = ?";
        }

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int index = 1;
            ps.setString(index++, "%" + keyword + "%");

            if (idKategori != null) {
                ps.setInt(index++, idKategori);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Produk p = new Produk();
                p.setIdProduk(rs.getInt("id_produk"));
                p.setNamaProduk(rs.getString("nama_produk"));
                p.setHarga(rs.getInt("harga"));
                p.setStok(rs.getInt("stok"));
                p.setDeskripsi(rs.getString("deskripsi"));
                p.setIdKategori(rs.getInt("id_kategori"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



}
