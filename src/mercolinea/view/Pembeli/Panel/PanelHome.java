
package mercolinea.view.Pembeli.Panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import mercolinea.controller.ProdukController;
import mercolinea.model.Produk;
import java.util.List;

public class PanelHome extends javax.swing.JPanel {
    
    private JPanel panelGrid;
    private JScrollPane scrollPane;
    private JTextField txtSearch;
    private PanelProfil panelProfil;


    ProdukController controller = new ProdukController();

    public PanelHome(PanelProfil panelProfil) {
        this.panelProfil = panelProfil;
        setLayout(new BorderLayout(10, 10));
        initUI();
        loadProduk();
    }
    
    private void initUI() {

        /* ================= PANEL SEARCH ================= */
        JPanel panelSearch = new JPanel();

        txtSearch = new JTextField(25);
        JButton btnSearch = new JButton("Cari");

        btnSearch.addActionListener(e -> loadProduk());

        panelSearch.add(new JLabel("Cari Produk:"));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        /* ================= GRID PRODUK ================= */
        panelGrid = new JPanel(new GridLayout(0, 3, 15, 15));

        scrollPane = new JScrollPane(panelGrid);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(panelSearch, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadProduk() {
        panelGrid.removeAll();

        String keyword = txtSearch.getText().trim();

        List<Produk> list = controller.tampilProdukPembeli(keyword);

        for (Produk p : list) {
            panelGrid.add(new PanelProdukCard(p,this, panelProfil));
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }
    public void refreshProduk() {
        loadProduk();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
