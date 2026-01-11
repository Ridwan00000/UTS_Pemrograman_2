/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class KoneksiDB {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        Connection conn = null;
        try {
            

                String url = "jdbc:mysql://localhost:3306/mercolinea_db"
                           + "?useSSL=false"
                           + "&allowPublicKeyRetrieval=true"
                           + "&serverTimezone=Asia/Jakarta";

                String user = "root";
                String pass = ""; 

                conn = DriverManager.getConnection(url, user, pass);
            
            
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal: " + e.getMessage());
        }
        return conn;
    }
}
