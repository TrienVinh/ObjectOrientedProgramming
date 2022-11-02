package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachHoaDonBanHang {

    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang1 = "|%-20s|%-43s|%-40s|%-29s|%-24s|\n";
    private final String DinhDang2 = "|%-20s %-43s %-40s %-29s %-24s|\n";
    private final String TenTapTin = "DanhSachHoaDonBanHang.txt";
    private HoaDonBanHang[] DanhSachHoaDonBanHang = new HoaDonBanHang[0];

    public DanhSachHoaDonBanHang() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach() throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
        if (DemSoPhanTu() == 0) {
            System.out.println("Danh sách hóa đơn bán hàng trống !\n");
        } else {
            System.out.println("Danh sách hóa đơn bán hàng: ");
            for (int i = 0; i < DemSoPhanTu(); i++) {
                thaotac.KeVienDanhSachHoaDon();
                System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                thaotac.KeVienDanhSachHoaDon();
                DanhSachHoaDonBanHang[i].HienThiThongTinHoaDonBanHang();
                thaotac.KeVienDanhSachHoaDon();
                String MaKhuyenMai = DanhSachHoaDonBanHang[i].getMaKhuyenMai();
                double PhanTram = 0;
                int ViTri1 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                if (ViTri1 != -1) {
                    if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri1].getTrangThai() == 0) {
                        MaKhuyenMai += " (*)";
                    }
                    PhanTram = DanhSachHoaDonBanHang[i].getPhanTram();
                }
                String MaHoaDon = DanhSachHoaDonBanHang[i].getMaHoaDon();
                danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                int ViTri2 = KiemTraPhanTuTonTai(MaHoaDon);
                System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri2].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                thaotac.KeVienDanhSachChiTietHoaDon();
                System.out.println("");
            }
        }
    }

    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        boolean ThemChiTietHoaDonBanHang = true;
        SoPhanTuThemVao++;
        String MaHoaDonBanHang = "HDBH";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaHoaDonBanHang += "0";
        }
        if (danhsachsanpham.DemSoSanPhamConHang() == 0) {
            System.out.println("Không còn sản phẩm nào để bán !");
            return false;
        }
        DanhSachHoaDonBanHang = Arrays.copyOf(DanhSachHoaDonBanHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new HoaDonBanHang();
        DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaHoaDon(MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setThoiGian(ThaoTac.LayThoiGianHienTai());
        DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinHoaDonBanHang(MaNhanVienDangNhap);
        String MaKhachHang = DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapMaDoiTac();
        if (MaKhachHang.equals("-q")) {
            SoPhanTuThemVao--;
            return false;
        } else {
            int SoSanPham;
            danhsachsanpham.HienThiDanhSach();
            System.out.print("Nhập số sản phẩm bán hàng: ");
            while (true) {
                SoSanPham = kiemtradulieu.KiemTraNhapSoSanPham();
                if (SoSanPham == -1) {
                    return false;
                }
                if (SoSanPham < danhsachsanpham.DemSoSanPhamConHang()) {
                    break;
                } else {
                    System.out.print("Số sản phẩm bán hàng vượt quá số sản phẩm hiện có của cửa hàng ! Mời nhập lại: ");
                }
            }
            for (int i = 0; i < SoSanPham; i++) {
                boolean ThemChiTietHoaDon = danhsachchitiethoadon.ThemPhanTu(MaKhachHang, MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), "Hóa đơn bán hàng", i, SoSanPham);
                if (!ThemChiTietHoaDon) {
                    ThemChiTietHoaDonBanHang = false;
                    break;
                }
            }
            if (ThemChiTietHoaDonBanHang) {
                int ViTri1 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri1].getHoTen();
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' lập hóa đơn bán hàng '" + MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + "'", ThaoTac.LayThoiGianHienTai());
                DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaKhuyenMai(danhsachkhuyenmai.TimKiemKhuyenMai(danhsachchitiethoadon.TinhTongTien(MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), 1.05)));
                String MaKhuyenMai = danhsachkhuyenmai.TimKiemKhuyenMai(danhsachchitiethoadon.TinhTongTien(MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), 1.05));
                int ViTri2 = danhsachkhuyenmai.KiemTraPhanTuTonTai(MaKhuyenMai);
                double PhanTram = 0;
                if (ViTri2 != -1) {
                    PhanTram = danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getPhanTram();
                }
                DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setPhanTram(PhanTram);
                DanhSachHoaDonBanHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTongTien(danhsachchitiethoadon.TinhTongTien(MaHoaDonBanHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), 1.05) * (1 - (PhanTram / 100)));
                LuuDuLieu();
                System.out.println("\nLập hóa đơn bán hàng thành công !\n");
                return true;
            } else {
                SoPhanTuThemVao--;
                return false;
            }
        }
    }

    public boolean TimKiemPhanTu() throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();

        int LuaChon;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo nhân viên   3.Tìm kiếm theo khách hàng   4.Tìm kiếm theo thời gian   5.Tìm kiếm theo tổng tiền");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon == -1) {
                return false;
            }
            if (LuaChon > 0 && LuaChon < 6) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon) {
            case 1: {
                String MaHoaDonBanHang;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã hóa đơn bán hàng muốn tìm: ");
                MaHoaDonBanHang = kiemtradulieu.KiemTraNhapMaHoaDonBanHang();
                if (MaHoaDonBanHang.equals("-q")) {
                    return false;
                }
                int ViTri1 = KiemTraPhanTuTonTai(MaHoaDonBanHang);
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin hóa đơn bán hàng muốn tìm: ");
                    thaotac.KeVienDanhSachHoaDon();
                    System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                    thaotac.KeVienDanhSachHoaDon();
                    DanhSachHoaDonBanHang[ViTri1].HienThiThongTinHoaDonBanHang();
                    thaotac.KeVienDanhSachHoaDon();
                    String MaHoaDon = DanhSachHoaDonBanHang[ViTri1].getMaHoaDon();
                    danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                    String MaKhuyenMai = DanhSachHoaDonBanHang[ViTri1].getMaKhuyenMai();
                    int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                    if (ViTri2 != -1) {
                        if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                            MaKhuyenMai += " (*)";
                        }
                    }
                    double PhanTram = DanhSachHoaDonBanHang[ViTri1].getPhanTram();
                    int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                    System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                    System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                    thaotac.KeVienDanhSachChiTietHoaDon();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy hóa đơn bán hàng '" + MaHoaDonBanHang + "' !\n");
                }
                break;
            }
            case 2: {
                int Dem = 0;
                String MaNhanVien;
                HoaDonBanHang[] TimKiemHoaDonBanHang = new HoaDonBanHang[DemSoPhanTu()];
                System.out.println("Tìm kiếm theo nhân viên:");
                System.out.print("Nhập mã nhân viên muốn tìm: ");
                MaNhanVien = kiemtradulieu.KiemTraNhanVienDaTonTai();
                if (MaNhanVien.equals("-q")) {
                    return false;
                }

                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (MaNhanVien.equalsIgnoreCase(DanhSachHoaDonBanHang[i].getMaNhanVien())) {
                        TimKiemHoaDonBanHang[Dem] = DanhSachHoaDonBanHang[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy hóa đơn bán hàng do nhân viên '" + MaNhanVien + "' lập !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin hóa đơn bán hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonBanHang[i].HienThiThongTinHoaDonBanHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonBanHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                        String MaKhuyenMai = TimKiemHoaDonBanHang[i].getMaKhuyenMai();
                        int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                        if (ViTri2 != -1) {
                            if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                                MaKhuyenMai += " (*)";
                            }
                        }
                        double PhanTram = TimKiemHoaDonBanHang[i].getPhanTram();
                        int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                        System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                        System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
            case 3: {
                DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
                int Dem = 0;
                String MaKhachHang;
                HoaDonBanHang[] TimKiemHoaDonBanHang = new HoaDonBanHang[DemSoPhanTu()];
                System.out.println("Tìm kiếm theo khách hàng:");
                System.out.print("Nhập mã khách hàng muốn tìm: ");
                MaKhachHang = kiemtradulieu.KiemTraKhachHangDaTonTai();
                if (MaKhachHang.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (MaKhachHang.equalsIgnoreCase(DanhSachHoaDonBanHang[i].getMaKhachHang())) {
                        TimKiemHoaDonBanHang[Dem] = DanhSachHoaDonBanHang[i];
                        Dem++;
                    }
                }

                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy hóa đơn của khách hàng '" + MaKhachHang + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin hóa đơn bán hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonBanHang[i].HienThiThongTinHoaDonBanHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonBanHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                        String MaKhuyenMai = TimKiemHoaDonBanHang[i].getMaKhuyenMai();
                        int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                        if (ViTri2 != -1) {
                            if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                                MaKhuyenMai += " (*)";
                            }
                        }
                        double PhanTram = TimKiemHoaDonBanHang[i].getPhanTram();
                        int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                        System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                        System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
            case 4: {
                int Dem = 0, LuaChon2;
                System.out.println("Tìm kiếm theo thời gian:");
                System.out.println("1.Theo ngày   2.Tùy chọn");
                System.out.print("Nhập lựa chọn: ");
                while (true) {
                    LuaChon2 = kiemtradulieu.KiemTraNhapLuaChon();
                    if (LuaChon2 == -1) {
                        return false;
                    }
                    if (LuaChon2 > 0 && LuaChon2 < 3) {
                        break;
                    } else {
                        System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                    }
                }
                System.out.println("");
                HoaDonBanHang[] TimKiemHoaDonBanHang = new HoaDonBanHang[DemSoPhanTu()];
                switch (LuaChon2) {
                    case 1: {
                        String NgayThang;
                        System.out.println("Tìm kiếm theo ngày cố định:");
                        System.out.print("Nhập ngày muốn tìm: ");
                        NgayThang = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayThang)) == Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian())))) {
                                TimKiemHoaDonBanHang[Dem] = DanhSachHoaDonBanHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn bán hàng trong ngày " + NgayThang + "!\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        String NgayBatDau, NgayKetThuc;
                        System.out.println("Tìm kiếm theo thời gian tùy chọn:");
                        System.out.print("Nhập ngày bắt đầu: ");
                        while (true) {
                            NgayBatDau = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                            if (NgayBatDau.equals("-q")) {
                                return false;
                            }
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) < Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai()))) {
                                break;
                            } else {
                                System.out.print("Ngày bắt đầu phải nhỏ hơn ngày hiện tại ! Mời nhập lại: ");
                            }
                        }
                        System.out.print("Nhập ngày kết thúc: ");
                        while (true) {
                            NgayKetThuc = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                            if (NgayKetThuc.equals("-q")) {
                                return false;
                            }
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc)) > Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau))) {
                                break;
                            } else {
                                System.out.print("Ngày kết thúc phải lớn hơn ngày bắt đầu ! Mời nhập lại: ");
                            }
                        }
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                                TimKiemHoaDonBanHang[Dem] = DanhSachHoaDonBanHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn bán hàng từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                }
                if (Dem > 0) {
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin hóa đơn bán hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonBanHang[i].HienThiThongTinHoaDonBanHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonBanHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                        String MaKhuyenMai = TimKiemHoaDonBanHang[i].getMaKhuyenMai();
                        int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                        if (ViTri2 != -1) {
                            if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                                MaKhuyenMai += " (*)";
                            }
                        }
                        double PhanTram = TimKiemHoaDonBanHang[i].getPhanTram();
                        int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                        System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                        System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
            case 5: 
        }
        return true;
    }

    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachHoaDonBanHang = Arrays.copyOf(DanhSachHoaDonBanHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachHoaDonBanHang[i] = new HoaDonBanHang();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachHoaDonBanHang[i].setMaHoaDon(scanfile.next());
                DanhSachHoaDonBanHang[i].setMaNhanVien(scanfile.next());
                DanhSachHoaDonBanHang[i].setMaKhachHang(scanfile.next());
                DanhSachHoaDonBanHang[i].setThoiGian(scanfile.next());
                DanhSachHoaDonBanHang[i].setMaKhuyenMai(scanfile.next());
                DanhSachHoaDonBanHang[i].setPhanTram(scanfile.nextDouble());
                DanhSachHoaDonBanHang[i++].setTongTien(scanfile.nextDouble());
            }
        }
        bufferedreader.close();
        filereader.close();
    }

    public void LuuDuLieu() throws IOException {
        File file = new File(TenTapTin);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            String DuLieu = DanhSachHoaDonBanHang[i].getMaHoaDon() + ";" + DanhSachHoaDonBanHang[i].getMaNhanVien() + ";" + DanhSachHoaDonBanHang[i].getMaKhachHang() + ";" + DanhSachHoaDonBanHang[i].getThoiGian() + ";" + DanhSachHoaDonBanHang[i].getMaKhuyenMai() + ";" + DanhSachHoaDonBanHang[i].getPhanTram() + ";" + DanhSachHoaDonBanHang[i].getTongTien();
            bufferedwriter.write(DuLieu);
            bufferedwriter.newLine();
        }
        if (bufferedwriter != null) {
            bufferedwriter.close();
        }
        if (filewriter != null) {
            filewriter.close();
        }
    }

    public int KiemTraPhanTuTonTai(String Ma) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (Ma.equals(DanhSachHoaDonBanHang[i].getMaHoaDon())) {
                return i;
            }
        }
        return -1;
    }

    public int DemSoPhanTu() throws IOException {
        FileReader filereader = null;
        BufferedReader bufferedreader = null;
        try {
            filereader = new FileReader(TenTapTin);
            bufferedreader = new BufferedReader(filereader);
            String DuLieu;
            SoPhanTuTrongDanhSach = 0;
            while ((DuLieu = bufferedreader.readLine()) != null) {
                if (DuLieu.length() != 1) {
                    SoPhanTuTrongDanhSach++;
                }
            }
        } catch (IOException e) {
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException exception) {
                }
            }
            if (filereader != null) {
                try {
                    filereader.close();
                } catch (IOException exception) {
                }
            }
        }
        return SoPhanTuTrongDanhSach;
    }

    public boolean KiemTraThoiHanBaoHanh(String MaHoaDon) {
        int ViTri = KiemTraPhanTuTonTai(MaHoaDon);
        if (ViTri != -1) {
            String ThoiGian = DanhSachHoaDonBanHang[ViTri].getThoiGian();
            ThoiGian = thaotac.TachThoiGian(ThoiGian);
            if (Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) - Integer.parseInt(thaotac.GhepNgayThang(ThoiGian)) <= 10000) {
                return true;
            }
        }
        return false;
    }

    public boolean ThongKeDoanhThu() throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();

        double DoanhThu = 0;
        HoaDonBanHang[] ThongKeDoanhThu = new HoaDonBanHang[DemSoPhanTu()];
        int Dem = 0, LuaChon;
        System.out.println("Thống kê doanh thu bán hàng:");
        System.out.println("1.Theo ngày   2.Tùy chọn");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon == -1) {
                return false;
            }
            if (LuaChon > 0 && LuaChon < 3) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon) {
            case 1: {
                String NgayThang;
                System.out.println("Thống kê theo ngày cố định:");
                System.out.print("Nhập ngày muốn tìm: ");
                NgayThang = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                if (NgayThang.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayThang)) == Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian())))) {
                        DoanhThu += DanhSachHoaDonBanHang[i].getTongTien();
                        ThongKeDoanhThu[Dem] = DanhSachHoaDonBanHang[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông có doanh thu bán hàng trong ngày " + NgayThang + " !\n");

                } else {
                    System.out.println("");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        ThongKeDoanhThu[i].HienThiThongTinHoaDonBanHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = ThongKeDoanhThu[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                        String MaKhuyenMai = ThongKeDoanhThu[i].getMaKhuyenMai();
                        int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                        if (ViTri2 != -1) {
                            if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                                MaKhuyenMai += " (*)";
                            }
                        }
                        double PhanTram = ThongKeDoanhThu[i].getPhanTram();
                        int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                        System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                        System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                    System.out.println("Doanh thu bán hàng trong ngày " + NgayThang + ": " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DoanhThu)) + "\n");
                }
                break;
            }
            case 2: {
                String NgayBatDau, NgayKetThuc;
                System.out.println("Thống kê theo thời gian tùy chọn:");
                System.out.print("Nhập ngày bắt đầu: ");
                while (true) {
                    NgayBatDau = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                    if (NgayBatDau.equals("-q")) {
                        return false;
                    }
                    break;
                }
                System.out.print("Nhập ngày kết thúc: ");
                while (true) {
                    NgayKetThuc = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                    if (NgayKetThuc.equals("-q")) {
                        return false;
                    }
                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc)) > Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau))) {
                        break;
                    } else {
                        System.out.print("Ngày kết thúc phải lớn hơn ngày bắt đầu ! Mời nhập lại: ");
                    }
                }
                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonBanHang[i].getThoiGian()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                        DoanhThu += DanhSachHoaDonBanHang[i].getTongTien();
                        ThongKeDoanhThu[Dem] = DanhSachHoaDonBanHang[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông có doanh thu bán hàng từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
                } else {
                    System.out.println("");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang1, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     KHÁCH HÀNG", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        ThongKeDoanhThu[i].HienThiThongTinHoaDonBanHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = ThongKeDoanhThu[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.05);
                        String MaKhuyenMai = ThongKeDoanhThu[i].getMaKhuyenMai();
                        int ViTri2 = danhsachkhuyenmai.TruyXuatMaKhuyenMai(MaKhuyenMai);
                        if (ViTri2 != -1) {
                            if (danhsachkhuyenmai.getDanhSachKhuyenMai()[ViTri2].getTrangThai() == 0) {
                                MaKhuyenMai += " (*)";
                            }
                        }
                        double PhanTram = ThongKeDoanhThu[i].getPhanTram();
                        int ViTri3 = KiemTraPhanTuTonTai(MaHoaDon);
                        System.out.format(DinhDang2, "", "     Mã khuyến mãi: " + MaKhuyenMai, "", "     Tổng tiền:", "     " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DanhSachHoaDonBanHang[ViTri3].getTongTien() + danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100))));
                        System.out.format(DinhDang2, "", "     Phần trăm khuyến mãi: " + thaotac.HienThiDinhDangPhanTram(PhanTram), "", "     Giảm:", "     -" + (thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(danhsachchitiethoadon.TinhTongTien(MaHoaDon, 1.05) * (PhanTram / 100)))));
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                    System.out.println("Doanh thu bán hàng từ ngày " + NgayBatDau + " đến ngày " + NgayKetThuc + ": " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DoanhThu)) + "\n");
                }

                break;
            }
        }

        return true;
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ HÓA ĐƠN BÁN HÀNG:");
        System.out.println("1.Lập hóa đơn bán hàng");
        System.out.println("2.Tìm kiếm hóa đơn bán hàng");
        System.out.println("3.Thoát");
    }

    public void Menu(String MaNhanVienDangNhap) throws IOException {
        int LuaChon;
        do {
            HienThiDanhSach();
            HienThiMenu();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon > 0 && LuaChon < 4) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                }
            }
            String XacNhan;
            switch (LuaChon) {
                case 1: {
                    while (true) {
                        System.out.println("Lập hóa đơn bán hàng:");
                        boolean ThemHoaDonBanHang = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemHoaDonBanHang) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục lập hóa đơn bán hàng (y/n): ");
                        XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                        if (XacNhan.equals("n")) {
                            SoPhanTuThemVao--;
                            break;
                        } else {
                            SoPhanTuTrongDanhSach++;
                            SoPhanTuThemVao--;
                        }
                        System.out.println("");
                    }
                    System.out.println("\nĐã thoát !\n");
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 2: {
                    if (DemSoPhanTu() == 0) {
                        System.out.println("Danh sách hóa đơn bán hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm hóa đơn bán hàng:");
                            boolean CapNhatThongTinHoaDonBanHang = TimKiemPhanTu();
                            if (!CapNhatThongTinHoaDonBanHang) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                        }
                        System.out.println("\nĐã thoát !\n");
                    }
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 3: {
                    System.out.println("Đã thoát !\n");
                    break;
                }
            }
        } while (LuaChon != 3);
    }

}
