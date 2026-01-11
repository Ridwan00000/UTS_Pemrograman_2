
package mercolinea.model;


import java.sql.*;
import java.util.*;
import mercolinea.util.KoneksiDB;
import mercolinea.util.Session;

public class TransaksiModel {
    
    public List<Transaksi> getTransaksiPembeli() {
        List<Transaksi> list = new ArrayList<>();

        String sqlTransaksi = """
            SELECT id_transaksi, tanggal, total, status
            FROM transaksi
            WHERE id_pembeli = ?
            ORDER BY tanggal DESC
        """;

        String sqlDetail = """
            SELECT p.nama_produk, td.harga, td.qty, td.subtotal
            FROM transaksi_detail td
            JOIN produk p ON td.id_produk = p.id_produk
            WHERE td.id_transaksi = ?
        """;

        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sqlTransaksi)) {

            ps.setInt(1, Session.getIdUser());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setTanggal(rs.getTimestamp("tanggal"));
                t.setTotal(rs.getInt("total"));
                t.setStatus(rs.getString("status"));

                // ambil detail
                PreparedStatement psDetail = c.prepareStatement(sqlDetail);
                psDetail.setInt(1, t.getIdTransaksi());
                ResultSet rd = psDetail.executeQuery();

                List<TransaksiDetail> detailList = new ArrayList<>();
                while (rd.next()) {
                    TransaksiDetail d = new TransaksiDetail();
                    d.setNamaProduk(rd.getString("nama_produk"));
                    d.setHarga(rd.getInt("harga"));
                    d.setQty(rd.getInt("qty"));
                    d.setSubtotal(rd.getInt("subtotal"));
                    detailList.add(d);
                }

                t.setDetailList(detailList);
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public int getTotalTransaksiByUser(int idUser) {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE id_pembeli = ?";
        try (Connection c = KoneksiDB.getKoneksi();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
