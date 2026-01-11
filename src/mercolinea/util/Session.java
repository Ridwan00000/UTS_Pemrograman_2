/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.util;


public class Session {

    private static int idUser;
    private static String nama;
    private static String username;
    private static String role;
    private static String status;
    private static boolean isLogin;

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        Session.idUser = idUser;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        Session.nama = nama;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        Session.role = role;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Session.status = status;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean login) {
        isLogin = login;
    }

    public static void clearSession() {
        idUser = 0;
        nama = null;
        username = null;
        role = null;
        status = null;
        isLogin = false;
    }
}

