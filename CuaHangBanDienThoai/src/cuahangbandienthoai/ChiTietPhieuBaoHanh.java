package cuahangbandienthoai;

import java.io.IOException;

public class ChiTietPhieuBaoHanh {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    private String IMEI, MaPhieuBaoHanh, MaSanPham, NoiDung, TinhTrang;

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMaPhieuBaoHanh() {
        return MaPhieuBaoHanh;
    }

    public void setMaPhieuBaoHanh(String MaPhieuBaoHanh) {
        this.MaPhieuBaoHanh = MaPhieuBaoHanh;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String MaSanPham) {
        this.MaSanPham = MaSanPham;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public ChiTietPhieuBaoHanh() {
    }

    public ChiTietPhieuBaoHanh(String IMEI, String MaPhieuBaoHanh, String MaSanPham, String NoiDung, String TinhTrang) {
        this.IMEI = IMEI;
        this.MaPhieuBaoHanh = MaPhieuBaoHanh;
        this.MaSanPham = MaSanPham;
        this.NoiDung = NoiDung;
        this.TinhTrang = TinhTrang;
    }

    public String NhapThongTinChiTietBaoHanh(String MaPhieuBaoHanh, String MaHoaDon, int SoThuTu, int SoSanPham) throws IOException {
        System.out.println("\nSản phẩm " + (SoThuTu + 1) + "/" + SoSanPham + ":");
        System.out.print("Nhập mã sản phẩm: ");
        MaSanPham = kiemtradulieu.KiemTraSanPhamThuocHoaDon(MaPhieuBaoHanh, MaHoaDon);
        if (MaSanPham.equals("-q")) {
            return "-q";
        }
        System.out.print("Nhập IMEI: ");
        IMEI = kiemtradulieu.KiemTraIMEIThuocHoaDon(MaPhieuBaoHanh, MaHoaDon, MaSanPham);
        if (IMEI.equals("-q")) {
            return "-q";

        }
        System.out.print("Nhập nội dung bảo hành: ");
        NoiDung = kiemtradulieu.KiemTraNhapNoiDung();
        if (NoiDung.equals("-q")) {
            return "-q";
        }
        System.out.print("Nhập tình trạng: ");
        TinhTrang = kiemtradulieu.KiemTraNhapTinhTrang();
        if (TinhTrang.equals("-q")) {
            return "-q";
        }
        this.MaPhieuBaoHanh = MaPhieuBaoHanh;
        return MaSanPham;
    }

    public void HienThiThongTinChiTietBaoHanh() throws IOException {
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
        String TenSanPham = "";
        if (ViTri != -1) {
            TenSanPham = danhsachsanpham.getDanhSachSanPham()[ViTri].getTenSanPham();
            if (danhsachsanpham.getDanhSachSanPham()[ViTri].getTrangThai() == 0) {
                TenSanPham += " (*)";
            }
        }
        System.out.format("|%-32s %-28s %-43s %-29s|\n", "     " + TenSanPham + " (" + MaSanPham + ")", "     " + IMEI, "     " + NoiDung, "     " + TinhTrang);
    }

}
