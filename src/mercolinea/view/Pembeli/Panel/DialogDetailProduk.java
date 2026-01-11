/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package mercolinea.view.Pembeli.Panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import mercolinea.controller.TransaksiController;
import mercolinea.model.Produk;

/**
 *
 * @author ASUS
 */
public class DialogDetailProduk extends JDialog {

    private Produk produk;    
    private PanelHome panelHome;
    private PanelProfil panelProfil;

    
    public DialogDetailProduk(JFrame parent, Produk produk, PanelHome panelHome, PanelProfil panelProfil) {
        super(parent, true);
        this.produk = produk;
        this.panelHome = panelHome;
        this.panelProfil = panelProfil;
        initUI();
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setTitle("Detail Produk");
        setSize(350, 350);
        setLayout(new BorderLayout(10, 10));

        JLabel lblNama = new JLabel(produk.getNamaProduk());
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblHarga = new JLabel("Harga: Rp " + produk.getHarga());
        JLabel lblStok = new JLabel("Stok: " + produk.getStok());

        JTextArea txtDesc = new JTextArea(produk.getDeskripsi());
        txtDesc.setEditable(false);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);

        JSpinner spQty = new JSpinner(
            new SpinnerNumberModel(1, 1, produk.getStok(), 1)
        );

        JButton btnBeli = new JButton("Beli");
        btnBeli.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // === ACTION BELI ===
        btnBeli.addActionListener(e -> {
            int qty = (int) spQty.getValue();

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Beli " + produk.getNamaProduk() +
                "\nJumlah: " + qty +
                "\nTotal: Rp " + (produk.getHarga() * qty),
                "Konfirmasi Pembelian",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            TransaksiController controller = new TransaksiController();
            boolean sukses = controller.beliProduk(produk.getIdProduk(), qty);

            if (sukses) {
                JOptionPane.showMessageDialog(this, "Pembelian berhasil!");
                if (panelProfil != null) {
                    panelProfil.refreshSaldo();
                    panelHome.refreshProduk();
                }
                dispose(); // tutup dialog
            } else {
                JOptionPane.showMessageDialog(this,
                    "Pembelian gagal.\nSaldo atau stok tidak mencukupi.",
                    "Gagal",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        JPanel top = new JPanel(new GridLayout(3, 1, 5, 5));
        top.add(lblNama);
        top.add(lblHarga);
        top.add(lblStok);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(new JLabel("Qty:"));
        bottom.add(spQty);
        bottom.add(btnBeli);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(txtDesc), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
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
