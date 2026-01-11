
package mercolinea.view.Pembeli.Panel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import mercolinea.model.Produk;


public class PanelProdukCard extends JPanel {

    private Produk produk;
    private PanelHome panelHome;
    private PanelProfil panelProfil;
    
    public PanelProdukCard(Produk produk, PanelHome panelHome, PanelProfil panelProfil) {
        this.produk = produk;
        this.panelHome = panelHome;
        this.panelProfil = panelProfil;
        initUI();
    }
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        setPreferredSize(new Dimension(220, 260));

        /* ================= GAMBAR (DUMMY) ================= */
        JPanel panelImage = new JPanel();
        panelImage.setPreferredSize(new Dimension(200, 120));
        panelImage.setBackground(new Color(245, 245, 245));

        JLabel lblImg = new JLabel("📦", SwingConstants.CENTER);
        lblImg.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        panelImage.add(lblImg);

        /* ================= INFO PRODUK ================= */
        JLabel lblNama = new JLabel("<html><body style='width:180px'>" 
                + produk.getNamaProduk() + "</body></html>");
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblHarga = new JLabel("Rp " + produk.getHarga());
        lblHarga.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHarga.setForeground(new Color(238, 77, 45)); // ORANYE SHOPEE

        JLabel lblStok = new JLabel("Stok: " + produk.getStok());
        lblStok.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStok.setForeground(Color.GRAY);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(new EmptyBorder(8, 8, 8, 8));

        panelInfo.add(lblNama);
        panelInfo.add(Box.createVerticalStrut(6));
        panelInfo.add(lblHarga);
        panelInfo.add(Box.createVerticalStrut(4));
        panelInfo.add(lblStok);

        add(panelImage, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);

        /* ================= CLICK EFFECT ================= */
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(PanelProdukCard.this);
                new DialogDetailProduk(parent, produk, panelHome, panelProfil).setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                setBorder(new LineBorder(new Color(238, 77, 45), 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
            }
        });
    }

//    private void bukaDetail() {
//        DialogDetailProduk dialog =
//            new DialogDetailProduk((JFrame) SwingUtilities.getWindowAncestor(this), produk);
//        dialog.setVisible(true);
//    }


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
