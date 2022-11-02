package cuahangbandienthoai;

import java.io.IOException;

public class ChiTietSanPham {

    ThaoTac thaotac = new ThaoTac();
    private String IMEI, MaHoaDonBanHang, MaHoaDonNhapHang, MaSanPham;

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMaHoaDonBanHang() {
        return MaHoaDonBanHang;
    }

    public void setMaHoaDonBanHang(String MaHoaDonBanHang) {
        this.MaHoaDonBanHang = MaHoaDonBanHang;
    }

    public String getMaHoaDonNhapHang() {
        return MaHoaDonNhapHang;
    }

    public void setMaHoaDonNhapHang(String MaHoaDonNhapHang) {
        this.MaHoaDonNhapHang = MaHoaDonNhapHang;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(String IMEI, String MaSanPham) {
        this.IMEI = IMEI;
        this.MaSanPham = MaSanPham;
    }

    public void NhapThongTinChiTietSanPham(String MaSanPham) throws IOException {
        DanhSachChiTietSanPham danhsachchitietsanpham = new DanhSachChiTietSanPham();
        this.MaSanPham = MaSanPham;
        IMEI = danhsachchitietsanpham.TaoIMEI();
    }

    public void HienThiThongTinChiTietSanPham() throws IOException {
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
        String TenSanPham = "";
        if (ViTri != -1) {
            TenSanPham = danhsachsanpham.getDanhSachSanPham()[ViTri].getTenSanPham();
            if (danhsachsanpham.getDanhSachSanPham()[ViTri].getTrangThai() == 0) {
                TenSanPham += " (*)";
            }
        }
        System.out.format("|%-43s|%-28s|%-29s|%-28s|\n", "     " + TenSanPham + " (" + MaSanPham + ")", "     " + IMEI, "     " + MaHoaDonNhapHang, "     " + MaHoaDonBanHang);
    }

}
