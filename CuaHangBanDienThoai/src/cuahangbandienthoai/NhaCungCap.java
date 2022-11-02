package cuahangbandienthoai;

import java.io.IOException;

public class NhaCungCap {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    private int SoSanPham, TrangThai;
    private String DiaChi, MaNhaCungCap, SoDienThoai, TenNhaCungCap;

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String MaNhaCungCap) {
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getTenNhaCungCap() {
        return TenNhaCungCap;
    }

    public void setTenNhaCungCap(String TenNhaCungCap) {
        this.TenNhaCungCap = TenNhaCungCap;
    }

    public int getSoSanPham() {
        return SoSanPham;
    }

    public void setSoSanPham(int SoSanPham) {
        this.SoSanPham = SoSanPham;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public NhaCungCap() {
    }

    public NhaCungCap(int TrangThai, String DiaChi, String MaNhaCungCap, String SoDienThoai, String TenNhaCungCap, int SoSanPham) {
        this.TrangThai = TrangThai;
        this.DiaChi = DiaChi;
        this.MaNhaCungCap = MaNhaCungCap;
        this.SoDienThoai = SoDienThoai;
        this.TenNhaCungCap = TenNhaCungCap;
        this.SoSanPham = SoSanPham;
    }

    public boolean NhapThongTinNhaCungCap() throws IOException {
        System.out.print("Nhập tên nhà cung cấp: ");
        TenNhaCungCap = kiemtradulieu.KiemTraTenNhaCungCapDaTonTai();
        if (TenNhaCungCap.equals("-q")) {
            return false;
        }
        System.out.print("Nhập số điện thoại: ");
        SoDienThoai = kiemtradulieu.KiemTraSoDienThoaiNhaCungCapDaTonTai();
        if (SoDienThoai.equals("-q")) {
            return false;
        }
        System.out.print("Nhập địa chỉ: ");
        DiaChi = kiemtradulieu.KiemTraNhapDiaChi();
        if (DiaChi.equals("-q")) {
            return false;
        }
        return true;
    }

    public void HienThiThongTinNhaCungCap() {
        System.out.format("|%-25s|%-31s|%-23s|%-53s|\n", "     " + MaNhaCungCap, "     " + TenNhaCungCap, "     " + SoDienThoai, "     " + DiaChi);
    }

}
