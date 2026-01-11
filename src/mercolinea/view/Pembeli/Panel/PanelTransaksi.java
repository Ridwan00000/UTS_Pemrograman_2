
package mercolinea.view.Pembeli.Panel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import mercolinea.controller.TransaksiController;
import mercolinea.model.Transaksi;
import mercolinea.model.TransaksiDetail;

public class PanelTransaksi extends javax.swing.JPanel {

    TransaksiController controller = new TransaksiController();
    private JPanel listPanel;
    public PanelTransaksi() {
        setLayout(new BorderLayout());

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        listPanel.removeAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Transaksi t : controller.getRiwayatPembeli()) {

            JPanel card = new JPanel(new BorderLayout());
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
            card.setBackground(new Color(245, 245, 245));

            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel header = new JLabel(
                "Tanggal: " + sdf.format(t.getTanggal()) +
                " | Status: " + t.getStatus()
            );
            header.setFont(new Font("Segoe UI", Font.BOLD, 13));

            JPanel detailPanel = new JPanel();
            detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
            detailPanel.setOpaque(false);

            for (TransaksiDetail d : t.getDetailList()) {
                detailPanel.add(new JLabel(
                    "• " + d.getNamaProduk() + " x" + d.getQty() +
                    " — Rp " + d.getSubtotal()
                ));
            }

            JLabel total = new JLabel("Total: Rp " + t.getTotal());
            total.setFont(new Font("Segoe UI", Font.BOLD, 14));

            card.add(header, BorderLayout.NORTH);
            card.add(detailPanel, BorderLayout.CENTER);
            card.add(total, BorderLayout.SOUTH);

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        revalidate();
        repaint();
    }

    public void refresh() {
        loadData();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
