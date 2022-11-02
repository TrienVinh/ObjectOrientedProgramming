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

public final class DanhSachLichSuHoatDong {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-129s|%-29s|\n";
    private final String TenTapTin = "DanhSachLichSuHoatDong.txt";
    private LichSuHoatDong[] DanhSachLichSuHoatDong = new LichSuHoatDong[0];

    public DanhSachLichSuHoatDong() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach() {
        System.out.println("Danh sách lịch sử hoạt động: ");
        thaotac.KeVienDanhSachLichSuHoatDong();
        System.out.format(DinhDang, "     HOẠT ĐỘNG", "     THỜI GIAN");
        thaotac.KeVienDanhSachLichSuHoatDong();
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            DanhSachLichSuHoatDong[i].HienThiLichSuHoatDong();
            thaotac.KeVienDanhSachLichSuHoatDong();

        }
        System.out.println("");
    }

    public void GhiHoatDong(String MaNhanVienDangNhap, String HoatDong,String ThoiGian) throws IOException {
        SoPhanTuThemVao++;
        DanhSachLichSuHoatDong = Arrays.copyOf(DanhSachLichSuHoatDong, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachLichSuHoatDong[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new LichSuHoatDong();
        DanhSachLichSuHoatDong[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].GhiHoatDong(MaNhanVienDangNhap,HoatDong,ThoiGian);
        LuuDuLieu();
    }

    public boolean TimKiemPhanTu() throws IOException {
        int LuaChon;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo nhân viên   2.Tìm kiếm theo thời gian");
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
        LichSuHoatDong[] TimKiemLichSuHoatDong = new LichSuHoatDong[DemSoPhanTu()];
        System.out.println("");
        switch (LuaChon) {
            case 1: {
                int Dem = 0;
                DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
                String MaNhanVien;
                System.out.println("Tìm kiếm theo nhân viên:");
                System.out.print("Nhập mã nhân viên muốn tìm: ");
                MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
                if (MaNhanVien.equals("-q")) {
                    return false;
                }
                int ViTri = danhsachtaikhoan.KiemTraPhanTuTonTai(MaNhanVien);
                if (ViTri != -1) {
                    for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                        if (MaNhanVien.equals(DanhSachLichSuHoatDong[i].getMaNhanVien())) {
                            TimKiemLichSuHoatDong[Dem] = DanhSachLichSuHoatDong[i];
                            Dem++;
                        }
                    }

                    if (Dem == 0) {
                        System.out.println("\nKhông tìm thấy lịch sử hoạt động của nhân viên '" + MaNhanVien + "' !\n");
                    } else {
                        if (Dem < 10) {
                            KetQua += "0";
                        }
                        System.out.println("Kết quả tìm kiếm: " + KetQua + Dem);
                        System.out.println("\nThông tin sản phẩm muốn tìm: ");
                        thaotac.KeVienDanhSachLichSuHoatDong();
                        System.out.format(DinhDang, "     HOẠT ĐỘNG", "     THỜI GIAN");
                        thaotac.KeVienDanhSachLichSuHoatDong();
                        for (int i = 0; i < Dem; i++) {
                            TimKiemLichSuHoatDong[i].HienThiLichSuHoatDong();
                            thaotac.KeVienDanhSachLichSuHoatDong();
                        }
                        System.out.println("");
                    }
                } else {
                    System.out.println("\nKhông tìm thấy nhân viên '" + MaNhanVien + "' !\n");
                }
                break;
            }
            case 2: {
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
                switch (LuaChon2) {
                    case 1: {
                        String NgayThang;
                        System.out.println("Tìm kiếm theo ngày cố định:");
                        System.out.print("Nhập ngày muốn tìm: ");
                        NgayThang = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayThang)) == Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachLichSuHoatDong[i].getThoiGian())))) {
                                TimKiemLichSuHoatDong[Dem] = DanhSachLichSuHoatDong[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy lịch sử hoạt động trong ngày " + NgayThang + "!\n");
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
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachLichSuHoatDong[i].getThoiGian()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachLichSuHoatDong[i].getThoiGian()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                                TimKiemLichSuHoatDong[Dem] = DanhSachLichSuHoatDong[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy lịch sử hoạt động từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
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
                    System.out.println("\nThông tin lịch sử hoạt động muốn tìm: ");
                    thaotac.KeVienDanhSachLichSuHoatDong();
                    System.out.format(DinhDang, "     MÃ HÓA ĐƠN", "     NHÂN VIÊN", "     NHÀ CUNG CÂP", "     THỜI GIAN", "     TỔNG TIỀN");
                    thaotac.KeVienDanhSachLichSuHoatDong();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemLichSuHoatDong[i].HienThiLichSuHoatDong();
                        thaotac.KeVienDanhSachLichSuHoatDong();
                    }
                    System.out.println("");
                }
            }
            break;
        }
        return true;
    }

    public void DocDuLieu() throws IOException {
        try (FileReader filereader = new FileReader(TenTapTin)) {
            BufferedReader bufferedreader = new BufferedReader(filereader);
            String DuLieu;
            int i = 0;
            SoPhanTuTrongDanhSach = DemSoPhanTu();
            DanhSachLichSuHoatDong = Arrays.copyOf(DanhSachLichSuHoatDong, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
            while ((DuLieu = bufferedreader.readLine()) != null) {
                if (DuLieu.length() != 1) {
                    DanhSachLichSuHoatDong[i] = new LichSuHoatDong();
                    Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                    DanhSachLichSuHoatDong[i].setMaNhanVien(scanfile.next());
                    DanhSachLichSuHoatDong[i].setHoatDong(scanfile.next());
                    DanhSachLichSuHoatDong[i++].setThoiGian(scanfile.next());
                }
            }
            bufferedreader.close();
        }
    }

    public void LuuDuLieu() throws IOException {
        File file = new File(TenTapTin);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            String DuLieu = DanhSachLichSuHoatDong[i].getMaNhanVien() + ";" + DanhSachLichSuHoatDong[i].getHoatDong() + ";" + DanhSachLichSuHoatDong[i].getThoiGian();
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
            if (Ma.equals(DanhSachLichSuHoatDong[i].getMaNhanVien())) {
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

    public static String LayThoiGianHienTai(){
        return ThaoTac.LayThoiGianHienTai();
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ LỊCH SỬ HOẠT ĐỘNG:");
        System.out.println("1.Tìm kiếm lịch sử hoạt động");
        System.out.println("2.Thoát");
    }

    public void Menu(String MaNhanVienDangNhap) throws IOException {
        int LuaChon;
        do {
            HienThiDanhSach();
            HienThiMenu();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon > 0 && LuaChon < 3) {
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
                        System.out.println("Tìm kiếm lịch sử hoạt động:");
                        boolean TimKiemLichSuHoatDong = TimKiemPhanTu();
                        if (!TimKiemLichSuHoatDong) {
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
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 2: {
                    System.out.println("Đã thoát !\n");
                    break;
                }
            }
        } while (LuaChon != 2);
    }

}
