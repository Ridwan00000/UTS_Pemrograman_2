
package mercolinea.model;

import java.util.Date;
import java.util.List;

public class Transaksi {
    
    private int idTransaksi;
    private Date tanggal;
    private int total;
    private String status;
    private List<TransaksiDetail> detailList;

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransaksiDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TransaksiDetail> detailList) {
        this.detailList = detailList;
    }
    
    
}
