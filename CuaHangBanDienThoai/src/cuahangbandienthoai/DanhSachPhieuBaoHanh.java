package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

public final class DanhSachPhieuBaoHanh {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-32s|%-28s|%-43s|%-29s|\n";
    private final String TenTapTin = "DanhSachPhieuBaoHanh.txt";
    private PhieuBaoHanh[] DanhSachPhieuBaoHanh = new PhieuBaoHanh[0];

    public int getSoPhanTuThemVao() {
        return SoPhanTuThemVao;
    }

    public int getSoPhanTuTrongDanhSach() {
        return SoPhanTuTrongDanhSach;
    }

    public DanhSachPhieuBaoHanh() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach() throws IOException {
        DanhSachChiTietPhieuBaoHanh danhsachchitietphieubaohanh = new DanhSachChiTietPhieuBaoHanh();
        if (DemSoPhanTu() == 0) {
            System.out.println("Danh sách phiếu bảo hành trống !\n");
        } else {
            System.out.println("Danh sách phiếu bảo hành: ");
            for (int i = 0; i < DemSoPhanTu(); i++) {
                thaotac.KeVienDanhSachPhieuBaoHanh();
                System.out.format(DinhDang, "     MÃ PHIẾU BẢO HÀNH", "     MÃ HÓA ĐƠN BÁN HÀNG", "     NHÂN VIÊN", "     THỜI GIAN");
                thaotac.KeVienDanhSachPhieuBaoHanh();
                DanhSachPhieuBaoHanh[i].HienThiThongTinPhieuBaoHanh();
                thaotac.KeVienDanhSachPhieuBaoHanh();
                String MaPhieuBaoHanh = DanhSachPhieuBaoHanh[i].getMaPhieuBaoHanh();
                danhsachchitietphieubaohanh.HienThiDanhSach(MaPhieuBaoHanh);
                thaotac.KeVienDanhSachChiTietPhieuBaoHanh();
                System.out.println("");
            }
        }
    }

    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        DanhSachChiTietPhieuBaoHanh danhsachchitietphieubaohanh = new DanhSachChiTietPhieuBaoHanh();
        DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
        danhsachhoadonbanhang.HienThiDanhSach();
        SoPhanTuThemVao++;
        boolean ThemChiTietPhieuBaoHanh1 = true;
        String MaPhieuBaoHanh = "PBH";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaPhieuBaoHanh += "0";
        }
        DanhSachPhieuBaoHanh = Arrays.copyOf(DanhSachPhieuBaoHanh, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new PhieuBaoHanh();
        DanhSachPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaPhieuBaoHanh(MaPhieuBaoHanh + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setThoiGian(ThaoTac.LayThoiGianHienTai());
        String MaHoaDon = DanhSachPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinPhieuBaoHanh(MaNhanVienDangNhap);
        if (MaHoaDon.equals("-q")) {
            SoPhanTuThemVao--;
            return false;
        } else {
            DanhSachChiTietSanPham danhsachchitietsanpham = new DanhSachChiTietSanPham();
            int SoSanPham;
            System.out.print("Nhập số sản phẩm bảo hành: ");
            while (true) {
                SoSanPham = kiemtradulieu.KiemTraNhapSoSanPham();
                if (SoSanPham == -1) {
                    return false;
                }
                if (SoSanPham <= danhsachchitietsanpham.DemSoSanPhamTheoHoaDon(MaHoaDon)) {
                    break;
                } else {
                    System.out.print("Số sản phẩm bảo hành vượt quá số sản phẩm của hóa đơn '" + MaHoaDon + "' ! Mời nhập lại: ");
                }
            }
            for (int i = 0; i < SoSanPham; i++) {
                boolean ThemChiTietPhieuBaoHanh2 = danhsachchitietphieubaohanh.ThemPhanTu(MaHoaDon, MaPhieuBaoHanh + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), i, SoSanPham);
                if (!ThemChiTietPhieuBaoHanh2) {
                    ThemChiTietPhieuBaoHanh1 = false;
                    break;
                }
            }
            if (ThemChiTietPhieuBaoHanh1) {
                int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' lập phiếu bảo hành '" + MaPhieuBaoHanh + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + "'",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nLập phiếu bảo hành thành công !\n");
                return true;
            } else {
                SoPhanTuThemVao--;
                return false;
            }
        }
    }

    public boolean TimKiemPhanTu() throws IOException {
        DanhSachChiTietPhieuBaoHanh danhsachchitietphieubaohanh = new DanhSachChiTietPhieuBaoHanh();
        int LuaChon;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo hóa đơn   3.Tìm kiếm theo nhân viên   4.Tìm kiếm theo thời gian");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon == -1) {
                return false;
            }
            if (LuaChon > 0 && LuaChon < 5) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon) {
            case 1: {
                String MaPhieuBaoHanh;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã phiếu bảo hành muốn tìm: ");
                MaPhieuBaoHanh = kiemtradulieu.KiemTraNhapMaPhieuBaoHanh();
                if (MaPhieuBaoHanh.equals("-q")) {
                    return false;
                }
                int ViTri1 = KiemTraPhanTuTonTai(MaPhieuBaoHanh);
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin phiếu bảo hành muốn tìm: ");
                    thaotac.KeVienDanhSachPhieuBaoHanh();
                    System.out.format(DinhDang, "     MÃ PHIẾU BẢO HÀNH", "     MÃ HÓA ĐƠN BÁN HÀNG", "     NHÂN VIÊN", "     THỜI GIAN");
                    thaotac.KeVienDanhSachPhieuBaoHanh();
                    DanhSachPhieuBaoHanh[ViTri1].HienThiThongTinPhieuBaoHanh();
                    thaotac.KeVienDanhSachPhieuBaoHanh();
                    danhsachchitietphieubaohanh.HienThiDanhSach(MaPhieuBaoHanh);
                    thaotac.KeVienDanhSachChiTietPhieuBaoHanh();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy phiếu bảo hành '" + MaPhieuBaoHanh + "' !\n");
                }
                break;
            }
            case 2: {
                DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                int Dem = 0;
                String MaHoaDonBanHang;
                PhieuBaoHanh[] TimKiemPhieuBaoHanh = new PhieuBaoHanh[DemSoPhanTu()];
                System.out.println("Tìm kiếm theo hóa đơn:");
                System.out.print("Nhập mã phiếu bảo hành muốn tìm: ");
                MaHoaDonBanHang = kiemtradulieu.KiemTraHoaDonBanHangDaTonTai();
                if (MaHoaDonBanHang.equals("-q")) {
                    return false;
                }
                int ViTri1 = danhsachhoadonbanhang.KiemTraPhanTuTonTai(MaHoaDonBanHang);
                if (ViTri1 != -1) {
                    for (int i = 0; i < DemSoPhanTu(); i++) {
                        if (MaHoaDonBanHang.equalsIgnoreCase(DanhSachPhieuBaoHanh[i].getMaHoaDon())) {
                            TimKiemPhieuBaoHanh[Dem] = DanhSachPhieuBaoHanh[i];
                            Dem++;
                        }
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy phiếu bảo hành hóa đơn của '" + MaHoaDonBanHang + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin phiếu bảo hành muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        System.out.format(DinhDang, "     MÃ PHIẾU BẢO HÀNH", "     MÃ HÓA ĐƠN BÁN HÀNG", "     NHÂN VIÊN", "     THỜI GIAN");
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        TimKiemPhieuBaoHanh[i].HienThiThongTinPhieuBaoHanh();
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        String MaPhieuBaoHanh = TimKiemPhieuBaoHanh[i].getMaPhieuBaoHanh();
                        danhsachchitietphieubaohanh.HienThiDanhSach(MaPhieuBaoHanh);
                        thaotac.KeVienDanhSachChiTietPhieuBaoHanh();
                        System.out.println("");
                    }
                }
                break;
            }
            case 3: {
                int Dem = 0;
                String MaNhanVien;
                PhieuBaoHanh[] TimKiemPhieuBaoHanh = new PhieuBaoHanh[DemSoPhanTu()];
                System.out.println("Tìm kiếm theo nhân viên:");
                System.out.print("Nhập mã nhân viên muốn tìm: ");
                MaNhanVien = kiemtradulieu.KiemTraNhanVienDaTonTai();
                if (MaNhanVien.equals("-q")) {
                    return false;
                }

                for (int i = 0; i < DemSoPhanTu(); i++) {
                    if (MaNhanVien.equalsIgnoreCase(DanhSachPhieuBaoHanh[i].getMaNhanVien())) {
                        TimKiemPhieuBaoHanh[Dem] = DanhSachPhieuBaoHanh[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy phiếu bảo hành do nhân viên '" + MaNhanVien + "' lập !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin phiếu bảo hành muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        System.out.format(DinhDang, "     MÃ PHIẾU BẢO HÀNH", "     MÃ HÓA ĐƠN BÁN HÀNG", "     NHÂN VIÊN", "     THỜI GIAN");
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        TimKiemPhieuBaoHanh[i].HienThiThongTinPhieuBaoHanh();
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        String MaPhieuBaoHanh = TimKiemPhieuBaoHanh[i].getMaPhieuBaoHanh();
                        danhsachchitietphieubaohanh.HienThiDanhSach(MaPhieuBaoHanh);
                        thaotac.KeVienDanhSachChiTietPhieuBaoHanh();
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
                PhieuBaoHanh[] TimKiemPhieuBaoHanh = new PhieuBaoHanh[DemSoPhanTu()];
                switch (LuaChon2) {
                    case 1: {
                        String NgayThang;
                        System.out.println("Tìm kiếm theo ngày cố định:");
                        System.out.print("Nhập ngày muốn tìm: ");
                        NgayThang = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                        for (int i = 0; i < DemSoPhanTu(); i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayThang)) == Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachPhieuBaoHanh[i].getThoiGian())))) {
                                TimKiemPhieuBaoHanh[Dem] = DanhSachPhieuBaoHanh[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy phiếu bảo hành trong ngày " + NgayThang + "!\n");
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
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachPhieuBaoHanh[i].getThoiGian()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachPhieuBaoHanh[i].getThoiGian()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                                TimKiemPhieuBaoHanh[Dem] = DanhSachPhieuBaoHanh[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy phiếu bảo hành từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
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
                    System.out.println("\nThông tin phiếu bảo hành muốn tìm: ");
                    for (int i = 0; i < Dem; i++) {
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        System.out.format(DinhDang, "     MÃ PHIẾU BẢO HÀNH", "     MÃ HÓA ĐƠN BÁN HÀNG", "     NHÂN VIÊN", "     THỜI GIAN");
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        TimKiemPhieuBaoHanh[i].HienThiThongTinPhieuBaoHanh();
                        thaotac.KeVienDanhSachPhieuBaoHanh();
                        String MaPhieuBaoHanh = TimKiemPhieuBaoHanh[i].getMaPhieuBaoHanh();
                        danhsachchitietphieubaohanh.HienThiDanhSach(MaPhieuBaoHanh);
                        thaotac.KeVienDanhSachChiTietPhieuBaoHanh();
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
        DanhSachPhieuBaoHanh = Arrays.copyOf(DanhSachPhieuBaoHanh, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachPhieuBaoHanh[i] = new PhieuBaoHanh();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachPhieuBaoHanh[i].setMaPhieuBaoHanh(scanfile.next());
                DanhSachPhieuBaoHanh[i].setMaHoaDon(scanfile.next());
                DanhSachPhieuBaoHanh[i].setMaNhanVien(scanfile.next());
                DanhSachPhieuBaoHanh[i++].setThoiGian(scanfile.next());
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
            String DuLieu = DanhSachPhieuBaoHanh[i].getMaPhieuBaoHanh() + ";" + DanhSachPhieuBaoHanh[i].getMaHoaDon() + ";" + DanhSachPhieuBaoHanh[i].getMaNhanVien() + ";" + DanhSachPhieuBaoHanh[i].getThoiGian();
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
            if (Ma.equals(DanhSachPhieuBaoHanh[i].getMaPhieuBaoHanh())) {
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
        System.out.println("QUẢN LÝ BẢO HÀNH:");
        System.out.println("1.Lập phiếu bảo hành");
        System.out.println("2.Tìm kiếm phiếu bảo hành");
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
                        System.out.println("Lập phiếu bảo hành:");
                        boolean ThemPhieuBaoHanh = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemPhieuBaoHanh) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục lập phiếu bảo hành (y/n): ");
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
                        System.out.println("Danh sách phiếu bảo hành trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm phiếu bảo hành:");
                            boolean CapNhatThongTinPhieuBaoHanh = TimKiemPhanTu();
                            if (!CapNhatThongTinPhieuBaoHanh) {
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
