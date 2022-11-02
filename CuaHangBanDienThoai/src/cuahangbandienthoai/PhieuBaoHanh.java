package cuahangbandienthoai;

import java.io.IOException;

public class PhieuBaoHanh {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private String MaHoaDon, MaNhanVien, MaPhieuBaoHanh, ThoiGian;

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getMaPhieuBaoHanh() {
        return MaPhieuBaoHanh;
    }

    public void setMaPhieuBaoHanh(String MaPhieuBaoHanh) {
        this.MaPhieuBaoHanh = MaPhieuBaoHanh;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String ThoiGian) {
        this.ThoiGian = ThoiGian;
    }

    public PhieuBaoHanh() {
    }

    public PhieuBaoHanh(String MaHoaDon, String MaNhanVien, String MaPhieuBaoHanh, String ThoiGian) {
        this.MaHoaDon = MaHoaDon;
        this.MaNhanVien = MaNhanVien;
        this.MaPhieuBaoHanh = MaPhieuBaoHanh;
        this.ThoiGian = ThoiGian;
    }

    public String NhapThongTinPhieuBaoHanh(String MaNhanVienDangNhap) throws IOException {
        DanhSachChiTietSanPham danhsachchitietsanpham = new DanhSachChiTietSanPham();
        DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
        danhsachhoadonbanhang.HienThiDanhSach();
        System.out.print("Nhập mã hóa đơn bán hàng: ");
        MaHoaDon = kiemtradulieu.KiemTraHoaDonBanHangDaTonTai();
        if (MaHoaDon.equals("-q")) {
            return "-q";
        }
        if (!danhsachhoadonbanhang.KiemTraThoiHanBaoHanh(MaHoaDon)) {
            System.out.println("\nHóa đơn này đã hết thời gian bảo hành !");
            return "-q";
        }
        System.out.println("");
        danhsachchitietsanpham.HienThiDanhSachTheoSanPham(MaHoaDon);
        MaNhanVien = MaNhanVienDangNhap;
        return MaHoaDon;
    }

    public void HienThiThongTinPhieuBaoHanh() throws IOException {
        DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
        int ViTri = danhsachnhanvien.TruyXuatTenNhanVien(MaNhanVien);
        String TenNhanVien = "";
        String TrangThai = "";
        if (ViTri != -1) {
            TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            if (danhsachnhanvien.getDanhSachNhanVien()[ViTri].getTrangThai() == 0) {
                TrangThai += " (*)";
            }
        }
        System.out.format("|%-32s|%-28s|%-43s|%-29s|\n", "     " + MaPhieuBaoHanh, "     " + MaHoaDon, "     " + TenNhanVien + " (" + MaNhanVien + ")" + TrangThai, "     " + ThoiGian);
    }

}
