/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mercolinea.model.SaldoHistory;
import mercolinea.util.KoneksiDB;
import mercolinea.util.Session;

/**
 *
 * @author ASUS
 */
public class HistoryController {
    public List<SaldoHistory> getHistoryLogin() {
        List<SaldoHistory> list = new ArrayList<>();

        String sql = """
            SELECT tanggal, keterangan, nominal, tipe
            FROM saldo_history
            WHERE id_user = ?
            ORDER BY tanggal DESC
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, Session.getIdUser());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SaldoHistory h = new SaldoHistory();
                h.setTanggal(rs.getTimestamp("tanggal"));
                h.setKeterangan(rs.getString("keterangan"));
                h.setNominal(rs.getDouble("nominal"));
                h.setTipe(rs.getString("tipe"));
                list.add(h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
