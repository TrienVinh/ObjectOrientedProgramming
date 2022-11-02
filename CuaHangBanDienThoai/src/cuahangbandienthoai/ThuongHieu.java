package cuahangbandienthoai;

import java.io.IOException;

public class ThuongHieu {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    private int TrangThai;
    private String QuocGia, MaThuongHieu, TenThuongHieu;

    public String getMaThuongHieu() {
        return MaThuongHieu;
    }

    public void setMaThuongHieu(String MaThuongHieu) {
        this.MaThuongHieu = MaThuongHieu;
    }

    public String getQuocGia() {
        return QuocGia;
    }

    public void setQuocGia(String QuocGia) {
        this.QuocGia = QuocGia;
    }

    public String getTenThuongHieu() {
        return TenThuongHieu;
    }

    public void setTenThuongHieu(String TenThuongHieu) {
        this.TenThuongHieu = TenThuongHieu;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public ThuongHieu() {
    }

    public ThuongHieu(int TrangThai, String QuocGia, String MaThuongHieu, String TenThuongHieu) {
        this.TrangThai = TrangThai;
        this.QuocGia = QuocGia;
        this.MaThuongHieu = MaThuongHieu;
        this.TenThuongHieu = TenThuongHieu;
    }

    public boolean NhapThongTinThuongHieu() throws IOException {
        System.out.print("Nhập tên thương hiệu: ");
        TenThuongHieu = kiemtradulieu.KiemTraTenThuongHieuDaTonTai();
        if (TenThuongHieu.equals("-q")) {
            return false;
        }
        System.out.print("Nhập quốc gia: ");
        QuocGia = kiemtradulieu.KiemTraNhapQuocGia();
        if (QuocGia.equals("-q")) {
            return false;
        }
        return true;
    }

    public void HienThiThongTinThuongHieu() {
        System.out.format("|%-24s|%-35s|%-23s|\n", "     " + MaThuongHieu, "     " + TenThuongHieu, "     " + QuocGia);
    }

}
