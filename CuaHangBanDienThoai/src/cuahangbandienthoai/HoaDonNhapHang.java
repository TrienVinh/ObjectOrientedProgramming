package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public class HoaDonNhapHang extends HoaDon {

    private String MaNhaCungCap;

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String MaNhaCungCap) {
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public HoaDonNhapHang() {
    }

    public HoaDonNhapHang(String MaNhaCungCap, double TongTien, String MaHoaDon, String MaNhanVien, String ThoiGian) {
        super(TongTien, MaHoaDon, MaNhanVien, ThoiGian);
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public void NhapThongTinHoaDonNhapHang(String MaNhanVienDangNhap) throws IOException {
        super.GhiThongTinHoaDon(MaNhanVienDangNhap);

    }

    //3118410482 - Lư Triển Vinh
    @Override
    public String NhapMaDoiTac() throws IOException {
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        if (danhsachnhacungcap.DemSoPhanTuKhaDung() == 0) {
            System.out.println("\nDanh sách nhà cung cấp trống !\n");
            return "-q";
        }
        System.out.println("");
        danhsachnhacungcap.HienThiDanhSachNhaCungCapNhapHang();
        System.out.print("Nhập mã nhà cung cấp: ");
        while (true) {
            MaNhaCungCap = kiemtradulieu.KiemTraNhaCungCapDaTonTai();
            if (MaNhaCungCap.equals("-q")) {
                return "-q";
            }
            int ViTri = danhsachnhacungcap.KiemTraPhanTuTonTai(MaNhaCungCap);
            if (danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getSoSanPham() > 0) {
                break;
            } else {
                System.out.print("Nhà cung cấp này hiện tại chưa có sản phẩm ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        return MaNhaCungCap;
    }

    public void HienThiThongTinHoaDonNhapHang() throws IOException {
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        int ViTri = danhsachnhacungcap.TruyXuatTenNhaCungCap(MaNhaCungCap);
        String TenNhaCungCap = "";
        if (ViTri != -1) {
            TenNhaCungCap = danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getTenNhaCungCap();
            if (danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getTrangThai() == 0) {
                TenNhaCungCap += " (*)";
            }
        }
        super.HienThiMaHoaDon();
        super.HienThiTenNhanVien();
        System.out.format("|%-40s", "     " + TenNhaCungCap);
        super.HienThiThoiGian();
        super.HienThiTongTien();
    }

}
