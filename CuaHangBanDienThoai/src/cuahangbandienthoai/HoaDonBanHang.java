package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public class HoaDonBanHang extends HoaDon {

    private double PhanTram;
    private String MaKhachHang, MaKhuyenMai;

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(String MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
    }

    public String getMaKhuyenMai() {
        return MaKhuyenMai;
    }

    public void setMaKhuyenMai(String MaKhuyenMai) {
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public double getPhanTram() {
        return PhanTram;
    }

    public void setPhanTram(double PhanTram) {
        this.PhanTram = PhanTram;
    }

    public HoaDonBanHang() {
    }

    public HoaDonBanHang(String MaKhachHang, String MaKhuyenMai, double TongTien, String MaHoaDon, String MaNhanVien, String ThoiGian) {
        super(TongTien, MaHoaDon, MaNhanVien, ThoiGian);
        this.MaKhachHang = MaKhachHang;
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public void NhapThongTinHoaDonBanHang(String MaNhanVienDangNhap) throws IOException {
        super.GhiThongTinHoaDon(MaNhanVienDangNhap);
    }

    //3118410482 - Lư Triển Vinh
    @Override
    public String NhapMaDoiTac() throws IOException {
        DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
        if (danhsachkhachhang.DemSoPhanTuKhaDung() == 0) {
            System.out.println("\nDanh sách khách hàng trống !");
            return "-q";
        }
        System.out.println("");
        danhsachkhachhang.HienThiDanhSach();
        System.out.print("Nhập mã khách hàng: ");
        MaKhachHang = kiemtradulieu.KiemTraKhachHangDaTonTai();
        if (MaKhachHang.equals("-q")) {
            return "-q";
        }
        System.out.println("");
        return MaKhachHang;
    }

    public void HienThiThongTinHoaDonBanHang() throws IOException {
        DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
        int ViTri = danhsachkhachhang.TruyXuatTenKhachHang(MaKhachHang);
        String TenKhachHang = "";
        String TrangThai = "";
        if (ViTri != -1) {
            TenKhachHang = danhsachkhachhang.getDanhSachKhachHang()[ViTri].getHoTen();
            if (danhsachkhachhang.getDanhSachKhachHang()[ViTri].getTrangThai() == 0) {
                TrangThai += " (*)";
            }
        }
        super.HienThiMaHoaDon();
        super.HienThiTenNhanVien();
        System.out.format("|%-40s", "     " + TenKhachHang + " (" + MaKhachHang + ")" + TrangThai);
        super.HienThiThoiGian();
        super.HienThiTongTien();
    }

}
