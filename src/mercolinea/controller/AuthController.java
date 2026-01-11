/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mercolinea.controller;

import mercolinea.model.User;
import mercolinea.model.UserModel;
import mercolinea.util.Session;
/**
 *
 * @author ASUS
 */
public class AuthController {
   UserModel userModel = new UserModel();

    public boolean login(String username, String password) {
        User user = userModel.login(username, password);

        if (user != null) {
            Session.setIdUser(user.getIdUser());
            Session.setNama(user.getNama());
            Session.setUsername(user.getUsername());
            Session.setRole(user.getRole());
            Session.setStatus(user.getStatus());
            Session.setLogin(true);
            return true;
        }
        return false;
    }

    public boolean register(String nama, String username, String password, String role) {
        return userModel.register(nama, username, password, role);
    }
}
