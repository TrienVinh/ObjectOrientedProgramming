package cuahangbandienthoai;

import java.io.IOException;

public class SanPham {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private double GiaSanPham;
    private int SoLuong, TrangThai;
    private String MaNhaCungCap, MaSanPham, MaThuongHieu, TenSanPham;

    public double getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(double GiaSanPham) {
        this.GiaSanPham = GiaSanPham;
    }

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String MaNhaCungCap) {
        this.MaNhaCungCap = MaNhaCungCap;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getMaThuongHieu() {
        return MaThuongHieu;
    }

    public void setMaThuongHieu(String MaThuongHieu) {
        this.MaThuongHieu = MaThuongHieu;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String TenSanPham) {
        this.TenSanPham = TenSanPham;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public SanPham() {
    }

    public SanPham(double GiaSanPham, int SoLuong, int TrangThai, String MaNhaCungCap, String MaSanPham, String MaThuongHieu, String TenSanPham) {
        this.GiaSanPham = GiaSanPham;
        this.SoLuong = SoLuong;
        this.TrangThai = TrangThai;
        this.MaNhaCungCap = MaNhaCungCap;
        this.MaSanPham = MaSanPham;
        this.MaThuongHieu = MaThuongHieu;
        this.TenSanPham = TenSanPham;
    }

    public boolean NhapThongTinSanPham() throws IOException {
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
        if (danhsachnhacungcap.DemSoPhanTuKhaDung() == 0) {
            System.out.println("\nDanh sách nhà cung cấp trống !\n");
            return false;
        }
        if (danhsachthuonghieu.DemSoPhanTuKhaDung() == 0) {
            System.out.println("\nDanh sách thương hiệu trống !\n");
            return false;
        }
        danhsachnhacungcap.HienThiDanhSach();
        System.out.print("Nhập mã nhà cung cấp: ");
        MaNhaCungCap = kiemtradulieu.KiemTraNhaCungCapDaTonTai();
        if (MaNhaCungCap.equals("-q")) {
            return false;
        }
        int ViTri = danhsachnhacungcap.KiemTraPhanTuTonTai(MaNhaCungCap);
        danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].setSoSanPham(danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getSoSanPham() + 1);
        danhsachnhacungcap.LuuDuLieu();
        System.out.println("");
        danhsachthuonghieu.HienThiDanhSach();
        System.out.print("Nhập mã thương hiệu: ");
        MaThuongHieu = kiemtradulieu.KiemTraThuongHieuDaTonTai();
        if (MaThuongHieu.equals("-q")) {
            return false;
        }
        System.out.print("Nhập tên sản phẩm: ");
        TenSanPham = kiemtradulieu.KiemTraTenSanPhamDaTonTai(MaNhaCungCap);
        if (TenSanPham.equals("-q")) {
            return false;
        }
        System.out.print("Nhập giá sản phẩm: ");
        GiaSanPham = kiemtradulieu.KiemTraNhapGiaSanPham();
        if (GiaSanPham == -1) {
            return false;
        }
        return true;
    }

    public void HienThiThongTinSanPham(double HeSo) throws IOException {
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
        int ViTri1 = danhsachnhacungcap.TruyXuatTenNhaCungCap(MaNhaCungCap);
        int ViTri2 = danhsachthuonghieu.TruyXuatTenThuongHieu(MaThuongHieu);
        String TenNhaCungCap = "";
        if (ViTri1 != -1) {
            TenNhaCungCap = danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri1].getTenNhaCungCap();
            if (danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri1].getTrangThai() == 0) {
                TenNhaCungCap += " (*)";
            }
        }
        String TenThuongHieu = "";
        if (ViTri2 != -1) {
            TenThuongHieu = danhsachthuonghieu.getDanhSachThuongHieu()[ViTri2].getTenThuongHieu();
            if (danhsachthuonghieu.getDanhSachThuongHieu()[ViTri2].getTrangThai() == 0) {
                TenThuongHieu += " (*)";
            }
        }
        System.out.format("|%-21s|%-37s|%-26s|%-26s|%-26s|%-18s|\n", "     " + MaSanPham, "     " + TenSanPham, "     " + TenNhaCungCap, "     " + TenThuongHieu, "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaSanPham * HeSo)), "     " + SoLuong);
    }

}
