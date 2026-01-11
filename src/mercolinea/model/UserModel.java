/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mercolinea.util.KoneksiDB;
import mercolinea.util.Session;
/**
 *
 * @author ASUS
 */
public class UserModel {

    public User login(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username=? AND password=? AND status='aktif'";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setNama(rs.getString("nama"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public boolean register(String nama, String username, String password, String role) {
        try {
            String sql = "INSERT INTO users (nama, username, password, role, status) VALUES (?, ?, ?, ?, 'aktif')";
            PreparedStatement ps = KoneksiDB.getKoneksi().prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
     public double getSaldo(int idUser) {
        String sql = "SELECT saldo FROM users WHERE id_user = ?";
        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saldo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateSaldo(int idUser, double saldoBaru) {
        String sql = "UPDATE users SET saldo = ? WHERE id_user = ?";
        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, saldoBaru);
            ps.setInt(2, idUser);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tambahSaldo(int idUser, double nominal) {
        String sqlUpdateSaldo =
            "UPDATE users SET saldo = saldo + ? WHERE id_user = ?";

        String sqlHistory =
            "INSERT INTO saldo_history (id_user, keterangan, nominal, tipe) VALUES (?,?,?,?)";

        try (Connection c = KoneksiDB.getKoneksi()) {
            c.setAutoCommit(false); // 🔒 TRANSAKSI

            // 1️⃣ Update saldo
            try (PreparedStatement psSaldo = c.prepareStatement(sqlUpdateSaldo)) {
                psSaldo.setDouble(1, nominal);
                psSaldo.setInt(2, idUser);
                psSaldo.executeUpdate();
            }

            // 2️⃣ Insert history saldo
            try (PreparedStatement psHist = c.prepareStatement(sqlHistory)) {
                psHist.setInt(1, idUser);
                psHist.setString(2, "Top Up Saldo");
                psHist.setDouble(3, nominal);
                psHist.setString(4, "MASUK");
                psHist.executeUpdate();
            }

            c.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
