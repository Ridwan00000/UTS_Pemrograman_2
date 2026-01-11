/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package mercolinea.view.Pembeli.Panel;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import mercolinea.controller.SaldoController;
import mercolinea.util.Session;

public class DialogTambahSaldo extends JDialog {
    
    private PanelProfil panelProfil;

    private JTextField txtSaldo;
    SaldoController controller = new SaldoController();
    
    public DialogTambahSaldo(JFrame parent, PanelProfil panelProfil) {
        super(parent, "Tambah Saldo", true);
        this.panelProfil = panelProfil;
        initUI();
        setSize(300, 200);
        setLocationRelativeTo(parent);
    }


    
    private void initUI() {

        JLabel lblTitle = new JLabel("Tambah Saldo", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        txtSaldo = new JTextField();
        txtSaldo.setHorizontalAlignment(JTextField.RIGHT);

        JButton btnTambah = new JButton("Tambah Saldo");
        btnTambah.setBackground(new java.awt.Color(238, 77, 45));
        btnTambah.setForeground(java.awt.Color.WHITE);
        btnTambah.setFocusPainted(false);

        btnTambah.addActionListener(e -> tambahSaldo());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        center.add(new JLabel("Masukkan Nominal:"));
        center.add(txtSaldo);
        center.add(Box.createVerticalStrut(15));
        center.add(btnTambah);

        add(lblTitle, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void tambahSaldo() {
        try {
            double saldo = Double.parseDouble(txtSaldo.getText());

            if (controller.tambahSaldo(saldo)) {
                JOptionPane.showMessageDialog(this, "Saldo berhasil ditambahkan");

                panelProfil.refreshSaldo(); // 🔥 INI KUNCINYA
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambah saldo");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka valid");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
