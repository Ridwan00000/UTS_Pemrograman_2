/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import mercolinea.model.Transaksi;
import mercolinea.model.TransaksiModel;
import mercolinea.util.KoneksiDB;
import mercolinea.util.Session;
import java.sql.*;

/**
 *
 * @author ASUS
 */
public class TransaksiController {
    
    private TransaksiModel model = new TransaksiModel();
    
    public List<Transaksi> getRiwayatPembeli() {
        return model.getTransaksiPembeli();
    }

    public int getTotalBeliLogin() {
        return model.getTotalTransaksiByUser(Session.getIdUser());
    }
    
    
    public boolean beliProduk(int idProduk, int qty) {
        int idPembeli = Session.getIdUser();
        final double KOMISI_ADMIN = 0.02;


        try {
            Connection conn = KoneksiDB.getKoneksi();
            conn.setAutoCommit(false); // TRANSAKSI DB

            // 1. Ambil data produk
            var psProduk = conn.prepareStatement(
                "SELECT harga, stok, id_penjual, nama_produk FROM produk WHERE id_produk=?"
            );
            psProduk.setInt(1, idProduk);
            var rsProduk = psProduk.executeQuery();

            if (!rsProduk.next()) return false;

            double harga = rsProduk.getDouble("harga");
            int stok = rsProduk.getInt("stok");
            int idPenjual = rsProduk.getInt("id_penjual");
            String namaProduk = rsProduk.getString("nama_produk");

            if (stok < qty) return false;

            double total = harga * qty;
            double komisiAdmin = total * KOMISI_ADMIN;
            double bagianPenjual = total - komisiAdmin;

            // 2. Cek saldo pembeli
            var psSaldo = conn.prepareStatement(
                "SELECT saldo FROM users WHERE id_user=?"
            );
            psSaldo.setInt(1, idPembeli);
            var rsSaldo = psSaldo.executeQuery();

            rsSaldo.next();
            double saldo = rsSaldo.getDouble("saldo");

            if (saldo < total) return false;

            // 3. Insert transaksi
            var psTrans = conn.prepareStatement(
                "INSERT INTO transaksi (id_pembeli, total, status) VALUES (?,?, 'diproses')",
                java.sql.Statement.RETURN_GENERATED_KEYS
            );
            psTrans.setInt(1, idPembeli);
            psTrans.setDouble(2, total);
            psTrans.executeUpdate();

            var rsKey = psTrans.getGeneratedKeys();
            rsKey.next();
            int idTransaksi = rsKey.getInt(1);

            // 4. Insert transaksi_detail
            var psDetail = conn.prepareStatement(
                "INSERT INTO transaksi_detail (id_transaksi, id_produk, harga, qty, subtotal) VALUES (?,?,?,?,?)"
            );
            psDetail.setInt(1, idTransaksi);
            psDetail.setInt(2, idProduk);
            psDetail.setDouble(3, harga);
            psDetail.setInt(4, qty);
            psDetail.setDouble(5, total);
            psDetail.executeUpdate();

            // 5. Update stok
            var psStok = conn.prepareStatement(
                "UPDATE produk SET stok = stok - ? WHERE id_produk=?"
            );
            psStok.setInt(1, qty);
            psStok.setInt(2, idProduk);
            psStok.executeUpdate();

            // 6. Update saldo pembeli
            var psSaldoUpdate = conn.prepareStatement(
                "UPDATE users SET saldo = saldo - ? WHERE id_user=?"
            );
            psSaldoUpdate.setDouble(1, total);
            psSaldoUpdate.setInt(2, idPembeli);
            psSaldoUpdate.executeUpdate();
            
            var psSaldoPenjual = conn.prepareStatement(
                "UPDATE users SET saldo = saldo + ? WHERE id_user=?"
            );
            psSaldoPenjual.setDouble(1, bagianPenjual);
            psSaldoPenjual.setInt(2, idPenjual);
            psSaldoPenjual.executeUpdate();
            
            var psAdmin = conn.prepareStatement(
                "SELECT id_user FROM users WHERE role='admin' LIMIT 1"
            );
            var rsAdmin = psAdmin.executeQuery();

            if (!rsAdmin.next()) return false;
            int idAdmin = rsAdmin.getInt("id_user");

            var psSaldoAdmin = conn.prepareStatement(
                "UPDATE users SET saldo = saldo + ? WHERE id_user=?"
            );
            psSaldoAdmin.setDouble(1, komisiAdmin);
            psSaldoAdmin.setInt(2, idAdmin);
            psSaldoAdmin.executeUpdate();
            
            PreparedStatement psHistPembeli = conn.prepareStatement(
                "INSERT INTO saldo_history (id_user, keterangan, nominal, tipe) VALUES (?,?,?,?)"
            );
            psHistPembeli.setInt(1, idPembeli);
            psHistPembeli.setString(2, "Pembelian produk " + namaProduk);
            psHistPembeli.setDouble(3, total);
            psHistPembeli.setString(4, "KELUAR");
            psHistPembeli.executeUpdate();

            PreparedStatement psHistPenjual = conn.prepareStatement(
                "INSERT INTO saldo_history (id_user, keterangan, nominal, tipe) VALUES (?,?,?,?)"
            );
            psHistPenjual.setInt(1, idPenjual);
            psHistPenjual.setString(2, "Penjualan produk " + namaProduk+ ", ID: (" +idProduk+ ")");
            psHistPenjual.setDouble(3, bagianPenjual);
            psHistPenjual.setString(4, "MASUK");
            psHistPenjual.executeUpdate();
            
            PreparedStatement psHistAdmin = conn.prepareStatement(
                "INSERT INTO saldo_history (id_user, keterangan, nominal, tipe) VALUES (?,?,?,?)"
            );
            psHistAdmin.setInt(1, idAdmin);
            psHistAdmin.setString(2, "Komisi 2% transaksi");
            psHistAdmin.setDouble(3, komisiAdmin);
            psHistAdmin.setString(4, "MASUK");
            psHistAdmin.executeUpdate();



            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Object[]> getPesananPenjual() {
        List<Object[]> list = new ArrayList<>();
        int idPenjual = Session.getIdUser();

        String sql = """
            SELECT t.id_transaksi, t.tanggal, u.nama,
                   p.nama_produk, td.qty, td.subtotal, t.status
            FROM transaksi t
            JOIN transaksi_detail td ON t.id_transaksi = td.id_transaksi
            JOIN produk p ON td.id_produk = p.id_produk
            JOIN users u ON t.id_pembeli = u.id_user
            WHERE p.id_penjual = ?
            ORDER BY t.tanggal DESC
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idPenjual);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("id_transaksi"),
                    rs.getTimestamp("tanggal"),
                    rs.getString("nama"),
                    rs.getString("nama_produk"),
                    rs.getInt("qty"),
                    rs.getDouble("subtotal"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean updateStatusTerkirim(int idTransaksi) {
        String sql = "UPDATE transaksi SET status='terkirim' WHERE id_transaksi=?";

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idTransaksi);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getJumlahProdukPenjual() {
        String sql = "SELECT COUNT(*) FROM produk WHERE id_penjual = ? AND stok > 0";
        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, Session.getIdUser()); // ID PENJUAL LOGIN
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getJumlahPesananPenjual() {
        String sql = """
            SELECT COUNT(DISTINCT t.id_transaksi)
            FROM transaksi t
            JOIN transaksi_detail td ON t.id_transaksi = td.id_transaksi
            JOIN produk p ON td.id_produk = p.id_produk
            WHERE p.id_penjual = ?
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, Session.getIdUser()); // ID PENJUAL
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



}
