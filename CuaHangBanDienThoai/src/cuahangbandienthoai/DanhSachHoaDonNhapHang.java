package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachHoaDonNhapHang {

    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-20s|%-43s|%-40s|%-29s|%-24s|\n";
    private final String TenTapTin = "DanhSachHoaDonNhapHang.txt";
    private HoaDonNhapHang[] DanhSachHoaDonNhapHang = new HoaDonNhapHang[0];

    public DanhSachHoaDonNhapHang() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach() throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        if (DemSoPhanTu() == 0) {
            System.out.println("Danh sách hóa đơn nhập hàng trống !\n");
        } else {
            System.out.println("Danh sách hóa đơn nhập hàng: ");
            for (int i = 0; i < DemSoPhanTu(); i++) {
                thaotac.KeVienDanhSachHoaDon();
                System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CẤP", "     THỜI GIAN", "     TỔNG TIỀN");
                thaotac.KeVienDanhSachHoaDon();
                DanhSachHoaDonNhapHang[i].HienThiThongTinHoaDonNhapHang();
                thaotac.KeVienDanhSachHoaDon();
                String MaHoaDon = DanhSachHoaDonNhapHang[i].getMaHoaDon();
                danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
                thaotac.KeVienDanhSachChiTietHoaDon();
                System.out.println("");
            }
        }
    }

    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        SoPhanTuThemVao++;
        boolean ThemChiTietHoaDonNhapHang = true;
        String MaHoaDonNhapHang = "HDNH";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaHoaDonNhapHang += "0";
        }
        DanhSachHoaDonNhapHang = Arrays.copyOf(DanhSachHoaDonNhapHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new HoaDonNhapHang();
        DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaHoaDon(MaHoaDonNhapHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setThoiGian(ThaoTac.LayThoiGianHienTai());
        DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinHoaDonNhapHang(MaNhanVienDangNhap);
        String MaNhaCungCap = DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapMaDoiTac();
        if (MaNhaCungCap.equals("-q")) {
            SoPhanTuThemVao--;
            return false;
        } else {
            DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
            int SoSanPham;
            danhsachsanpham.HienThiSanPhamTheoNhaCungCap(MaNhaCungCap);
            System.out.print("Nhập số sản phẩm nhập hàng: ");
            while (true) {
                SoSanPham = kiemtradulieu.KiemTraNhapSoSanPham();
                if (SoSanPham == -1) {
                    return false;
                }
                if (SoSanPham <= danhsachsanpham.DemSoSanPhamTheoNhaCungCap(MaNhaCungCap)) {
                    break;
                } else {
                    System.out.print("Số sản phẩm nhập hàng vượt quá số sản phẩm hiện có của nhà cung cấp '" + MaNhaCungCap + "' ! Mời nhập lại: ");
                }
            }
            for (int i = 0; i < SoSanPham; i++) {
                boolean ThemChiTietHoaDon = danhsachchitiethoadon.ThemPhanTu(MaNhaCungCap, MaHoaDonNhapHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), "Hóa đơn nhập hàng", i, SoSanPham);
                if (!ThemChiTietHoaDon) {
                    ThemChiTietHoaDonNhapHang = false;
                    break;
                }
            }
            if (ThemChiTietHoaDonNhapHang) {
                int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' lập hóa đơn nhập hàng '" + MaHoaDonNhapHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + "'",ThaoTac.LayThoiGianHienTai());
                DanhSachHoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTongTien(danhsachchitiethoadon.TinhTongTien(MaHoaDonNhapHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), 1.0));
                LuuDuLieu();
                System.out.println("\nLập hóa đơn nhập hàng thành công !\n");
                return true;
            } else {
                SoPhanTuThemVao--;
                return false;
            }
        }
    }

    public boolean TimKiemPhanTu() throws IOException {
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        int LuaChon;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo nhân viên   3.Tìm kiếm theo nhà cung cấp   4.Tìm kiếm theo thời gian   5.Tìm kiếm theo tổng tiền");
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
                String MaHoaDonNhapHang;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã hóa đơn nhập hàng muốn tìm: ");
                MaHoaDonNhapHang = kiemtradulieu.KiemTraNhapMaHoaDonNhapHang();
                if (MaHoaDonNhapHang.equals("-q")) {
                    return false;
                }
                int ViTri1 = KiemTraPhanTuTonTai(MaHoaDonNhapHang);
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin hóa đơn nhập hàng muốn tìm:");
                    thaotac.KeVienDanhSachHoaDon();
                    System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                    thaotac.KeVienDanhSachHoaDon();
                    DanhSachHoaDonNhapHang[ViTri1].HienThiThongTinHoaDonNhapHang();
                    thaotac.KeVienDanhSachHoaDon();
                    String MaHoaDon = DanhSachHoaDonNhapHang[ViTri1].getMaHoaDon();
                    danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
                    thaotac.KeVienDanhSachChiTietHoaDon();
                    System.out.println("");

                } else {
                    System.out.println("\nKhông tìm thấy hóa đơn nhập hàng '" + MaHoaDonNhapHang + "' !\n");
                }
                break;
            }
            case 2: {
                int Dem = 0;
                String MaNhanVien;
                HoaDonNhapHang[] TimKiemHoaDonNhapHang = new HoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao];
                System.out.println("Tìm kiếm theo nhân viên:");
                System.out.print("Nhập mã nhân viên muốn tìm: ");
                MaNhanVien = kiemtradulieu.KiemTraNhanVienDaTonTai();
                if (MaNhanVien.equals("-q")) {
                    return false;
                }

                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (MaNhanVien.equalsIgnoreCase(DanhSachHoaDonNhapHang[i].getMaNhanVien())) {
                        TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy hóa đơn nhập hàng do nhân viên '" + MaNhanVien + "' lập !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin hóa đơn nhập hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonNhapHang[i].HienThiThongTinHoaDonNhapHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonNhapHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
            case 3: {
                DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
                int Dem = 0;
                String TenNhaCungCap;
                HoaDonNhapHang[] TimKiemHoaDonNhapHang = new HoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao];
                System.out.println("Tìm kiếm theo nhà cung cấp:");
                System.out.print("Nhập tên nhà cung cấp muốn tìm: ");
                while (true) {
                    TenNhaCungCap = kiemtradulieu.KiemTraNhapTenNhaCungCap();
                    if (TenNhaCungCap.equals("-q")) {
                        return false;
                    }
                    int ViTri = danhsachnhacungcap.TruyXuatMaNhaCungCap(TenNhaCungCap);
                    if (ViTri != -1) {
                        String MaNhaCungCap = danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getMaNhaCungCap();
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (MaNhaCungCap.equalsIgnoreCase(DanhSachHoaDonNhapHang[i].getMaNhaCungCap())) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        break;
                    } else {
                        System.out.print("Không tìm thấy nhà cung cấp '" + TenNhaCungCap + "' ! Mời nhập lại: ");
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy hóa đơn của nhà cung cấp '" + TenNhaCungCap + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin hóa đơn nhập hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonNhapHang[i].HienThiThongTinHoaDonNhapHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonNhapHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
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
                HoaDonNhapHang[] TimKiemHoaDonNhapHang = new HoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao];
                switch (LuaChon2) {
                    case 1: {
                        String NgayThang;
                        System.out.println("Tìm kiếm theo ngày cố định:");
                        System.out.print("Nhập ngày muốn tìm: ");
                        NgayThang = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayThang)) == Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonNhapHang[i].getThoiGian())))) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng trong ngày " + NgayThang + "!\n");
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
                            NgayKetThuc = kiemtradulieu.KiemTraNhapNgayThang();
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
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonNhapHang[i].getThoiGian()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachHoaDonNhapHang[i].getThoiGian()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
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
                    System.out.println("\nThông tin hóa đơn nhập hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonNhapHang[i].HienThiThongTinHoaDonNhapHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonNhapHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
            case 5: {
                int Dem = 0, LuaChon2;
                HoaDonNhapHang[] TimKiemHoaDonNhapHang = new HoaDonNhapHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao];
                System.out.println("Tìm kiếm theo tổng tiền:");
                System.out.println("1.Dưới 100.000.000 VNĐ   2.Từ 100.000.000 VNĐ đến 200.000.000 VNĐ   3.Trên 200.000.000 VNĐ   4.Tùy chọn");
                System.out.print("Nhập lựa chọn: ");
                while (true) {
                    LuaChon2 = kiemtradulieu.KiemTraNhapLuaChon();
                    if (LuaChon2 == -1) {
                        return false;
                    }
                    if (LuaChon2 > 0 && LuaChon2 < 5) {
                        break;
                    } else {
                        System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                    }
                }
                System.out.println("");
                switch (LuaChon2) {
                    case 1: {
                        System.out.println("Tìm kiếm theo tổng tiền dưới 100.000.000 VNĐ:");
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (DanhSachHoaDonNhapHang[i].getTongTien() <= 100000000) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng có tổng tiền dưới 100.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Tìm kiếm theo tổng tiền từ 100.000.000 VNĐ đến 200.000.000 VNĐ:");
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (DanhSachHoaDonNhapHang[i].getTongTien() >= 100000000 && DanhSachHoaDonNhapHang[i].getTongTien() <= 200000000) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng có tổng tiền từ 10.000.000 VNĐ đến 20.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Tìm kiếm theo tổng tiền trên 20.000.000 VNĐ:");
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (DanhSachHoaDonNhapHang[i].getTongTien() >= 200000000) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng có tổng tiền trên 20.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 4: {
                        double GiaBatDau, GiaKetThuc;
                        System.out.println("Tìm kiếm theo tổng tiền tùy chọn:");
                        System.out.print("Nhập tổng tiền khởi đầu: ");
                        GiaBatDau = kiemtradulieu.KiemTraNhapMucGia();
                        if (GiaBatDau == -1) {
                            return false;
                        }
                        System.out.print("Nhập tổng tiền kết thúc: ");
                        while (true) {
                            GiaKetThuc = kiemtradulieu.KiemTraNhapMucGia();
                            if (GiaKetThuc == -1) {
                                return false;
                            }
                            if (GiaKetThuc > GiaBatDau) {
                                break;
                            } else {
                                System.out.print("Mức giá kết thúc phải lớn hơn tổng tiền khởi đầu ! Mời nhập lại: ");
                            }
                        }
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (DanhSachHoaDonNhapHang[i].getTongTien() >= GiaBatDau && DanhSachHoaDonNhapHang[i].getTongTien() <= GiaKetThuc) {
                                TimKiemHoaDonNhapHang[Dem] = DanhSachHoaDonNhapHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy hóa đơn nhập hàng có tổng tiền từ " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaBatDau)) + " đến " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaKetThuc)) + " !\n");
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
                    System.out.println("\nThông tin hóa đơn nhập hàng muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachHoaDon();
                        System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                        thaotac.KeVienDanhSachHoaDon();
                        TimKiemHoaDonNhapHang[i].HienThiThongTinHoaDonNhapHang();
                        thaotac.KeVienDanhSachHoaDon();
                        String MaHoaDon = TimKiemHoaDonNhapHang[i].getMaHoaDon();
                        danhsachchitiethoadon.HienThiDanhSach(MaHoaDon, 1.0);
                        thaotac.KeVienDanhSachChiTietHoaDon();
                        System.out.println("");
                    }
                }
                break;
            }
        }
        return true;
    }

    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachHoaDonNhapHang = Arrays.copyOf(DanhSachHoaDonNhapHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachHoaDonNhapHang[i] = new HoaDonNhapHang();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachHoaDonNhapHang[i].setMaHoaDon(scanfile.next());
                DanhSachHoaDonNhapHang[i].setMaNhanVien(scanfile.next());
                DanhSachHoaDonNhapHang[i].setMaNhaCungCap(scanfile.next());
                DanhSachHoaDonNhapHang[i].setThoiGian(scanfile.next());
                DanhSachHoaDonNhapHang[i++].setTongTien(scanfile.nextDouble());
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
            String DuLieu = DanhSachHoaDonNhapHang[i].getMaHoaDon() + ";" + DanhSachHoaDonNhapHang[i].getMaNhanVien() + ";" + DanhSachHoaDonNhapHang[i].getMaNhaCungCap() + ";" + DanhSachHoaDonNhapHang[i].getThoiGian() + ";" + DanhSachHoaDonNhapHang[i].getTongTien();
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

    public int KiemTraPhanTuTonTai(String Ma) throws IOException {
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (Ma.equals(DanhSachHoaDonNhapHang[i].getMaHoaDon())) {
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

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ HÓA ĐƠN NHẬP HÀNG:");
        System.out.println("1.Lập hóa đơn nhập hàng");
        System.out.println("2.Tìm kiếm hóa đơn nhập hàng");
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
                        System.out.println("Lập hóa đơn nhập hàng:");
                        boolean ThemHoaDonNhapHang = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemHoaDonNhapHang) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục lập hóa đơn nhập hàng (y/n): ");
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
                        System.out.println("Danh sách hóa đơn nhập hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm hóa đơn nhập hàng:");
                            boolean TimKiemHoaDonNhapHang = TimKiemPhanTu();
                            if (!TimKiemHoaDonNhapHang) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            System.out.println("");
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
