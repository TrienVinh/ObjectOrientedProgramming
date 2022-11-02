package cuahangbandienthoai;

import java.io.IOException;

public class ChiTietHoaDonBanHang {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private double GiaSanPham;
    private int SoLuong;
    private String MaHoaDon, MaSanPham;

    public double getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(double GiaSanPham) {
        this.GiaSanPham = GiaSanPham;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public ChiTietHoaDonBanHang() {
    }

    public ChiTietHoaDonBanHang(double GiaSanPham, int SoLuong, String MaHoaDon, String MaSanPham) {
        this.SoLuong = SoLuong;
        this.MaHoaDon = MaHoaDon;
        this.MaSanPham = MaSanPham;
    }

    public String NhapThongTinChiTietHoaDon(String MaNhaCungCap, String MaHoaDon, String LoaiHoaDon, int SoThuTu, int SoSanPham) throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachChiTietSanPham danhsachchitietsanpham = new DanhSachChiTietSanPham();
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        if (LoaiHoaDon.equals("Hóa đơn nhập hàng")) {
            System.out.println("\nSản phẩm " + (SoThuTu + 1) + "/" + SoSanPham + ":");
            System.out.print("Nhập mã sản phẩm: ");
            while (true) {
                MaSanPham = kiemtradulieu.KiemTraSanPhamThuocNhaCungCap(MaNhaCungCap);
                if (MaSanPham.equals("-q")) {
                    return "-q";
                }
                if (!danhsachchitiethoadon.KiemTraSanPhamDaTonTai(MaHoaDon, MaSanPham)) {
                    break;
                } else {
                    System.out.print("Sản phẩm này đã có trong hóa đơn '" + MaHoaDon + "' ! Mời nhập lại: ");
                }
            }
            int ViTri1 = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
            System.out.print("Nhập số lượng sản phẩm: ");
            SoLuong = kiemtradulieu.KiemTraNhapSoLuong();
            if (SoLuong == -1) {
                return "-q";
            }
            for (int i = 0; i < SoLuong; i++) {
                danhsachchitietsanpham.ThemPhanTu1(MaSanPham, MaHoaDon);
            }
            danhsachsanpham.getDanhSachSanPham()[ViTri1].setSoLuong(danhsachsanpham.getDanhSachSanPham()[ViTri1].getSoLuong() + SoLuong);
            danhsachsanpham.LuuDuLieu();
            return MaSanPham;
        } else {
            System.out.println("\nSản phẩm " + (SoThuTu + 1) + "/" + SoSanPham + ":");
            System.out.print("Nhập mã sản phẩm: ");
            while (true) {
                MaSanPham = kiemtradulieu.KiemTraSanPhamConHang();
                if (MaSanPham.equals("-q")) {
                    return "-q";
                }
                if (!danhsachchitiethoadon.KiemTraSanPhamDaTonTai(MaHoaDon, MaSanPham)) {
                    break;
                } else {
                    System.out.print("Sản phẩm này đã có trong hóa đơn '" + MaHoaDon + "' !Mời nhập lại: ");
                }
            }
            int ViTri1 = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
            System.out.print("Nhập số lượng sản phẩm: ");
            SoLuong = kiemtradulieu.KiemTraSoLuongSanPham(MaSanPham);
            if (SoLuong == -1) {
                return "-q";
            }
            int Dem = 0;
            for (int i = 0; i < danhsachchitietsanpham.DemSoPhanTu(); i++) {
                if (danhsachchitietsanpham.getDanhSachChiTietSanPham()[i].getMaSanPham().equals(MaSanPham) && danhsachchitietsanpham.getDanhSachChiTietSanPham()[i].getMaHoaDonBanHang().equals("HDBH00")) {
                    danhsachchitietsanpham.ThemPhanTu2(MaHoaDon, i);
                    Dem++;
                }
                if (Dem == SoLuong) {
                    break;
                }
            }
            danhsachsanpham.getDanhSachSanPham()[ViTri1].setSoLuong(danhsachsanpham.getDanhSachSanPham()[ViTri1].getSoLuong() - SoLuong);
            danhsachsanpham.LuuDuLieu();
            return MaSanPham;
        }
    }

    public void HienThiThongTinChiTietHoaDon(double HeSo) throws IOException {
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
        String TenSanPham = "";
        if (ViTri != -1) {
            TenSanPham = danhsachsanpham.getDanhSachSanPham()[ViTri].getTenSanPham();
            if (danhsachsanpham.getDanhSachSanPham()[ViTri].getTrangThai() == 0) {
                TenSanPham += " (*)";
            }
        }
        System.out.format("|%-20s %-43s %-40s %-29s %-24s|\n", "", "     " + TenSanPham + " (" + MaSanPham + ")", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaSanPham * HeSo)), "     " + thaotac.ChuanHoaSoLuong(SoLuong) + " chiếc", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaSanPham * SoLuong * HeSo)));
    }

}
