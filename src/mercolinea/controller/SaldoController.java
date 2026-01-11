package mercolinea.controller;

import mercolinea.model.UserModel;
import mercolinea.util.Session;

public class SaldoController {

    private UserModel model = new UserModel();

    public double getSaldoLogin() {
        return model.getSaldo(Session.getIdUser());
    }

    public boolean tambahSaldo(double nominal) {
        if (nominal <= 0) return false;
        return model.tambahSaldo(Session.getIdUser(), nominal);
    }
}
