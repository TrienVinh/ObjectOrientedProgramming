package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public abstract class HoaDon {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private double TongTien;
    private String MaHoaDon, MaNhanVien, ThoiGian;

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

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String ThoiGian) {
        this.ThoiGian = ThoiGian;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public HoaDon() {
    }

    public HoaDon(double TongTien, String MaHoaDon, String MaNhanVien, String ThoiGian) {
        this.TongTien = TongTien;
        this.MaHoaDon = MaHoaDon;
        this.MaNhanVien = MaNhanVien;
        this.ThoiGian = ThoiGian;
    }

    //3118410482 - Lư Triển Vinh
    public void GhiThongTinHoaDon(String MaNhanVienDangNhap) {
        MaNhanVien = MaNhanVienDangNhap;
    }

    public abstract String NhapMaDoiTac() throws IOException;

    public void HienThiMaHoaDon() {
        System.out.format("|%-20s", "     " + MaHoaDon);
    }
    
    public void HienThiTenNhanVien() throws IOException {
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
        System.out.format("|%-43s", "     " + TenNhanVien + " (" + MaNhanVien + ")" + TrangThai);
    }

    public void HienThiThoiGian() {
        System.out.format("|%-29s", "     " + ThoiGian);
    }

    public void HienThiTongTien() {
        System.out.format("|%-24s|\n", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(TongTien)));
    }

}
