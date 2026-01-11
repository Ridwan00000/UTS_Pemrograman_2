package mercolinea.view.Pembeli.Panel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mercolinea.controller.SaldoController;
import mercolinea.controller.TransaksiController;
import mercolinea.util.Session;
import mercolinea.view.LoginForm;

public class PanelProfil extends javax.swing.JPanel {

    private JLabel lblNama;
    private JLabel lblTotalBeli;
    private JLabel lblSaldo;

    private SaldoController saldoController = new SaldoController();
    private TransaksiController transaksiController = new TransaksiController();

    
    
    public PanelProfil() {
        initComponents();
        setLayout(new BorderLayout());
        initUI();
        loadData();
        refreshSaldo();
    }

    private void initUI() {

        /* ================= TITLE ================= */
        JLabel title = new JLabel("Profil Saya");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(15, 20, 10, 20));

        /* ================= CARD PROFIL ================= */
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(20, 25, 20, 25)
        ));

        lblNama = new JLabel();
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 16));

        lblTotalBeli = new JLabel();
        lblTotalBeli.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lblSaldo = new JLabel();
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblSaldo.setForeground(new Color(238, 77, 45)); // Oranye

        JButton btnTambahSaldo = new JButton("Tambah Saldo");
        btnTambahSaldo.setBackground(new Color(238, 77, 45));
        btnTambahSaldo.setForeground(Color.WHITE);
        btnTambahSaldo.setFocusPainted(false);
        btnTambahSaldo.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnTambahSaldo.addActionListener(e -> openTambahSaldo());
        


        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(245, 245, 245));
        logoutPanel.setBorder(new EmptyBorder(10, 20, 20, 20));


        card.add(lblNama);
        card.add(Box.createVerticalStrut(10));
        card.add(lblSaldo);
        card.add(Box.createVerticalStrut(20));
        card.add(btnTambahSaldo);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 245, 245));
        wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));
        wrapper.add(card, BorderLayout.NORTH);

        add(title, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        lblNama.setText("Nama      : " + Session.getNama());
        refreshSaldo();

        int total = transaksiController.getTotalBeliLogin();
        lblTotalBeli.setText("Total Beli: " + total + " transaksi");
    }



    private void openTambahSaldo() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        new DialogTambahSaldo(parent, this).setVisible(true);
    }

    
    public void refreshSaldo() {
        lblSaldo.setText("Saldo     : Rp " + saldoController.getSaldoLogin());
    }
    
//    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
