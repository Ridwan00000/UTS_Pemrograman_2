/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.model;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import mercolinea.util.KoneksiDB;
/**
 *
 * @author ASUS
 */
public class KategoriModel {
    public ArrayList<Kategori> getAll() {
        ArrayList<Kategori> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM kategori";
            Statement st = KoneksiDB.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Kategori k = new Kategori();
                k.setIdKategori(rs.getInt("id_kategori"));
                k.setNamaKategori(rs.getString("nama_kategori"));
                list.add(k);
            }
        } catch (Exception e) {
            System.out.println("Load kategori error: " + e.getMessage());
        }
        return list;
    }
    public ArrayList<Kategori> getAllWithCount() {
        ArrayList<Kategori> list = new ArrayList<>();
        try {
            String sql = """
                SELECT k.id_kategori, k.nama_kategori,
                       COUNT(p.id_produk) AS jumlah_produk
                FROM kategori k
                LEFT JOIN produk p ON k.id_kategori = p.id_kategori
                GROUP BY k.id_kategori, k.nama_kategori
            """;

            Statement st = KoneksiDB.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Kategori k = new Kategori();
                k.setIdKategori(rs.getInt("id_kategori"));
                k.setNamaKategori(rs.getString("nama_kategori"));
                k.setJumlahProduk(rs.getInt("jumlah_produk"));
                list.add(k);
            }
        } catch (Exception e) {
            System.out.println("Load kategori error: " + e.getMessage());
        }
        return list;
    }

    public void insert(String namaKategori) throws Exception {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";
        PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
        ps.setString(1, namaKategori);
        ps.executeUpdate();
    }

    public void update(int idKategori, String namaKategori) throws Exception {
        String sql = "UPDATE kategori SET nama_kategori=? WHERE id_kategori=?";
        PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
        ps.setString(1, namaKategori);
        ps.setInt(2, idKategori);
        ps.executeUpdate();
    }

    public int countProduk(int idKategori) throws Exception {
        String sql = "SELECT COUNT(*) FROM produk WHERE id_kategori=?";
        PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
        ps.setInt(1, idKategori);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
        return 0;
    }

    public void delete(int idKategori) throws Exception {
        String sql = "DELETE FROM kategori WHERE id_kategori=?";
        PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
        ps.setInt(1, idKategori);
        ps.executeUpdate();
    }
    
    public List<String> getNamaKategori() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT nama_kategori FROM kategori";

        try (Statement st = KoneksiDB.getKoneksi().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString("nama_kategori"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
