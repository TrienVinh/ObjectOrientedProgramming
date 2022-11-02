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

public final class DanhSachKhuyenMai implements DanhSach {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-24s|%-30s|%-40s|%-22s|%-23s|\n";
    private final String TenTapTin = "DanhSachKhuyenMai.txt";
    private KhuyenMai[] DanhSachKhuyenMai = new KhuyenMai[0];

    public KhuyenMai[] getDanhSachKhuyenMai() {
        return DanhSachKhuyenMai;
    }

    public DanhSachKhuyenMai() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách khuyến mãi trống !\n");
        } else {
            System.out.println("Danh sách khuyến mãi: ");
            thaotac.KeVienDanhSachKhuyenMai();
            System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
            thaotac.KeVienDanhSachKhuyenMai();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachKhuyenMai[i].getTrangThai() == 1) {
                    DanhSachKhuyenMai[i].HienThiThongTinKhuyenMai();
                    thaotac.KeVienDanhSachKhuyenMai();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        SoPhanTuThemVao++;
        String MaKhuyenMai = "KM";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaKhuyenMai += "0";
        }
        DanhSachKhuyenMai = Arrays.copyOf(DanhSachKhuyenMai, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachKhuyenMai[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new KhuyenMai();
        DanhSachKhuyenMai[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaKhuyenMai(MaKhuyenMai + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachKhuyenMai[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        boolean ThemKhuyenMai = DanhSachKhuyenMai[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinKhuyenMai();
        if (ThemKhuyenMai) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' thêm khuyến mãi '" + MaKhuyenMai + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + "' vào danh sách",ThaoTac.LayThoiGianHienTai());
            LuuDuLieu();
            System.out.println("\nThêm thành công !\n");
            return true;
        } else {
            SoPhanTuThemVao--;
            return false;
        }
    }

    @Override
    public boolean CapNhatThongTinPhanTu(String MaNhanVienDangNhap) throws IOException {
        int LuaChon;
        String CapNhat, MaKhuyenMai;
        System.out.print("Nhập mã khuyến mãi muốn cập nhật: ");
        MaKhuyenMai = kiemtradulieu.KiemTraKhuyenMaiDaTonTai();
        if (MaKhuyenMai.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaKhuyenMai);
        if (ViTri1 != -1) {
            if (Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) <= Integer.parseInt(thaotac.GhepNgayThang(DanhSachKhuyenMai[ViTri1].getNgayKetThuc()))) {
                int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                String XacNhan;
                System.out.print("\nXác nhận cập nhật thông tin khuyến mãi '" + MaKhuyenMai + "' (y/n): ");
                XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                if (XacNhan.equals("y")) {
                    HienThiMenuCapNhat();
                    System.out.print("Nhập lựa chọn: ");
                    while (true) {
                        LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                        if (LuaChon == -1) {
                            return false;
                        }
                        if (LuaChon > 0 && LuaChon < 4) {
                            break;
                        } else {
                            System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                        }
                    }
                    System.out.println("");
                    switch (LuaChon) {
                        case 1: {
                            System.out.print("Nhập phần trăm khuyến mãi: ");
                            while (true) {
                                CapNhat = String.valueOf(kiemtradulieu.KiemTraNhapPhanTram());
                                if (Double.parseDouble(CapNhat) == -1) {
                                    return false;
                                }
                                if (Double.parseDouble(CapNhat) == DanhSachKhuyenMai[ViTri1].getPhanTram()) {
                                    System.out.print("Phần trăm khuyến mãi mới không có gì thay đổi ! Mời nhập lại: ");
                                } else {
                                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật phần trăm khuyến mãi của khuyến mãi '" + MaKhuyenMai + "'",ThaoTac.LayThoiGianHienTai());
                                    DanhSachKhuyenMai[ViTri1].setPhanTram(Double.parseDouble(CapNhat));
                                    break;
                                }
                            }
                            break;
                        }
                        case 2: {
                            System.out.print("Nhập điều kiện: ");
                            while (true) {
                                CapNhat = String.valueOf(kiemtradulieu.KiemTraNhapDieuKien());
                                if (Double.parseDouble(CapNhat) == -1) {
                                    return false;
                                }
                                if (Double.parseDouble(CapNhat) == DanhSachKhuyenMai[ViTri1].getPhanTram()) {
                                    System.out.print("Điều kiện mới không có gì thay đổi ! Mời nhập lại: ");
                                } else {
                                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật điều kiện khuyến mãi của khuyến mãi '" + MaKhuyenMai + "'",ThaoTac.LayThoiGianHienTai());
                                    DanhSachKhuyenMai[ViTri1].setDieuKien(Double.parseDouble(CapNhat));
                                    break;
                                }
                            }
                            break;
                        }
                        case 3: {
                            String NgayBatDau, NgayKetThuc;
                            while (true) {
                                System.out.print("Nhập ngày bắt đầu: ");
                                while (true) {
                                    NgayBatDau = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                                    if (NgayBatDau.equals("-q")) {
                                        return false;
                                    }
                                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) >= Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai()))) {
                                        break;
                                    } else {
                                        System.out.print("Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại ! Mời nhập lại: ");
                                    }
                                }
                                System.out.print("Nhập ngày kết thúc: ");
                                while (true) {
                                    NgayKetThuc = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                                    if (NgayKetThuc.equals("-q")) {
                                        return false;
                                    }
                                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc)) >= Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau))) {
                                        break;
                                    } else {
                                        System.out.print("Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu ! Mời nhập lại: ");
                                    }
                                }
                                if (DanhSachKhuyenMai[ViTri1].getNgayBatDau().equals(NgayBatDau) && DanhSachKhuyenMai[ViTri1].getNgayKetThuc().equals(NgayKetThuc)) {
                                    System.out.println("Thời gian khuyến mãi không có gì thay đổi !\n");
                                } else {
                                    DanhSachKhuyenMai[ViTri1].setNgayBatDau(NgayBatDau);
                                    DanhSachKhuyenMai[ViTri1].setNgayKetThuc(NgayKetThuc);
                                    break;
                                }
                            }
                        }
                    }
                    LuuDuLieu();
                    System.out.println("\nCập nhật thành công !\n");
                    System.out.println("Thông tin khuyến mãi '" + MaKhuyenMai + "' sau khi cập nhật: ");
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
                    thaotac.KeVienDanhSachKhuyenMai();
                    DanhSachKhuyenMai[ViTri1].HienThiThongTinKhuyenMai();
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.println("");
                } else {
                    System.out.println("\nHủy bỏ cập nhật thông tin khuyến mãi '" + MaKhuyenMai + "' !\n");
                }
            } else {
                System.out.println("\nHiện tại khuyến mãi '" + MaKhuyenMai + "' đã hết hạn, không thể cập nhật thông tin khuyến mãi!\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy khuyến mãi '" + MaKhuyenMai + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        String MaKhuyenMai;
        System.out.print("Nhập mã khuyến mãi muốn xóa: ");
        MaKhuyenMai = kiemtradulieu.KiemTraKhuyenMaiDaTonTai();
        if (MaKhuyenMai.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaKhuyenMai);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String XacNhan;
            System.out.print("\nXác nhận xóa khuyến mãi '" + MaKhuyenMai + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equalsIgnoreCase("y")) {
                DanhSachKhuyenMai[ViTri1].setTrangThai(0);
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' xóa của khuyến mãi '" + MaKhuyenMai + "' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nXóa thành công !\n");
            } else {
                System.out.println("\nHủy bỏ xóa khuyến mãi '" + MaKhuyenMai + "' !\n");
            }

        } else {
            System.out.println("\nKhông tìm thấy khuyến mãi '" + MaKhuyenMai + "' !\n");
        }
        return true;
    }

    @Override
    public boolean TimKiemPhanTu() {
        int LuaChon1;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo phần trăm khuyến mãi   3.Tìm kiếm theo điều kiện   4.Tìm kiếm theo thời gian");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon1 = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon1 == -1) {
                return false;
            }
            if (LuaChon1 > 0 && LuaChon1 < 5) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon1) {
            case 1: {
                String MaKhuyenMai;
                System.out.println("Tìm kiếm theo mã");
                System.out.print("Nhập mã khuyến mãi muốn tìm: ");
                MaKhuyenMai = kiemtradulieu.KiemTraNhapMaKhuyenMai();
                if (MaKhuyenMai.equals("-q")) {
                    return false;
                }
                int ViTri2 = KiemTraPhanTuTonTai(MaKhuyenMai);
                if (ViTri2 != -1) {
                    System.out.println("\nThông tin khuyến mãi muốn tìm: ");
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
                    thaotac.KeVienDanhSachKhuyenMai();
                    DanhSachKhuyenMai[ViTri2].HienThiThongTinKhuyenMai();
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy khuyến mãi '" + MaKhuyenMai + "' !\n");
                }
                break;
            }
            case 2: {
                int Dem = 0;
                KhuyenMai[] TimKiemKhuyenMai = new KhuyenMai[DemSoPhanTuKhaDung()];
                double PhanTram;
                System.out.println("Tìm kiếm theo phần trăm:");
                System.out.print("Nhập phần trăm khuyến mãi muốn tìm: ");
                PhanTram = kiemtradulieu.KiemTraNhapPhanTram();
                if (PhanTram == -1) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachKhuyenMai[i].getTrangThai() == 1 && PhanTram == DanhSachKhuyenMai[i].getPhanTram()) {
                        TimKiemKhuyenMai[Dem] = DanhSachKhuyenMai[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy khuyến mãi có phần trăm khuyến mãi '" + thaotac.HienThiDinhDangPhanTram(PhanTram) + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin khuyến mãi muốn tìm: ");
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
                    thaotac.KeVienDanhSachKhuyenMai();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemKhuyenMai[i].HienThiThongTinKhuyenMai();
                        thaotac.KeVienDanhSachKhuyenMai();
                    }
                    System.out.println("");
                }
                break;
            }
            case 3: {
                int Dem = 0;
                KhuyenMai[] TimKiemKhuyenMai = new KhuyenMai[DemSoPhanTuKhaDung()];
                double DieuKien;
                System.out.println("Tìm kiếm theo điều kiện:");
                System.out.print("Nhập điều kiện khuyến mãi muốn tìm: ");
                DieuKien = kiemtradulieu.KiemTraNhapDieuKien();
                if (DieuKien == -1) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachKhuyenMai[i].getTrangThai() == 1 && DieuKien >= DanhSachKhuyenMai[i].getDieuKien()) {
                        TimKiemKhuyenMai[Dem] = DanhSachKhuyenMai[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy khuyến mãi có điều kiện khuyến mãi trên '" + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DieuKien)) + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin khuyến mãi muốn tìm: ");
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
                    thaotac.KeVienDanhSachKhuyenMai();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemKhuyenMai[i].HienThiThongTinKhuyenMai();
                        thaotac.KeVienDanhSachKhuyenMai();
                    }
                    System.out.println("");
                }
                break;
            }
            case 4: {
                int Dem = 0;
                KhuyenMai[] TimKiemKhuyenMai = new KhuyenMai[DemSoPhanTuKhaDung()];
                String NgayBatDau, NgayKetThuc;
                System.out.println("Tìm kiếm theo thời gian:");
                System.out.print("Nhập ngày bắt đầu: ");
                while (true) {
                    NgayBatDau = kiemtradulieu.KiemTraNhapNgayThangTimKiem();
                    if (NgayBatDau.equals("-q")) {
                        return false;
                    }
                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) < Integer.parseInt(ThaoTac.LayNgayHienTai())) {
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
                    if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) <= Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachKhuyenMai[i].getNgayBatDau()))) && Integer.parseInt(thaotac.GhepNgayThang(thaotac.TachThoiGian(DanhSachKhuyenMai[i].getNgayKetThuc()))) <= Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc))) {
                        TimKiemKhuyenMai[Dem] = DanhSachKhuyenMai[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy khuyến mãi từ " + NgayBatDau + " đến " + NgayKetThuc + " !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin khuyến mãi muốn tìm: ");
                    thaotac.KeVienDanhSachKhuyenMai();
                    System.out.format(DinhDang, "     MÃ KHUYẾN MÃI", "     PHẦN TRĂM KHUYẾN MÃI", "     ĐIỀU KIỆN KHUYẾN MÃI", "     NGÀY BẮT ĐẦU", "     NGÀY KẾT THÚC");
                    thaotac.KeVienDanhSachKhuyenMai();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemKhuyenMai[i].HienThiThongTinKhuyenMai();
                        thaotac.KeVienDanhSachKhuyenMai();
                    }
                    System.out.println("");
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachKhuyenMai = Arrays.copyOf(DanhSachKhuyenMai, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachKhuyenMai[i] = new KhuyenMai();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachKhuyenMai[i].setMaKhuyenMai(scanfile.next());
                DanhSachKhuyenMai[i].setPhanTram(scanfile.nextDouble());
                DanhSachKhuyenMai[i].setDieuKien(scanfile.nextDouble());
                DanhSachKhuyenMai[i].setNgayBatDau(scanfile.next());
                DanhSachKhuyenMai[i].setNgayKetThuc(scanfile.next());
                DanhSachKhuyenMai[i++].setTrangThai(scanfile.nextInt());
            }
        }
        bufferedreader.close();
        filereader.close();
    }

    @Override
    public void LuuDuLieu() throws IOException {
        File file = new File(TenTapTin);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            String DuLieu = DanhSachKhuyenMai[i].getMaKhuyenMai() + ";" + DanhSachKhuyenMai[i].getPhanTram() + ";" + DanhSachKhuyenMai[i].getDieuKien() + ";" + DanhSachKhuyenMai[i].getNgayBatDau() + ";" + DanhSachKhuyenMai[i].getNgayKetThuc() + ";" + DanhSachKhuyenMai[i].getTrangThai();
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

    @Override
    public int KiemTraPhanTuTonTai(String Ma) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachKhuyenMai[i].getTrangThai() == 1 && Ma.equals(DanhSachKhuyenMai[i].getMaKhuyenMai())) {
                return i;
            }
        }
        return -1;
    }

    @Override
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

    @Override
    public int DemSoPhanTuKhaDung() {
        int SoDongDaXoa = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachKhuyenMai[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public boolean KiemTraKhuyenMaiDaTonTai() {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachKhuyenMai[i].getTrangThai() == 1 && Integer.parseInt(thaotac.GhepNgayThang(DanhSachKhuyenMai[i].getNgayBatDau())) <= Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) && Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) <= Integer.parseInt(thaotac.GhepNgayThang(DanhSachKhuyenMai[i].getNgayKetThuc()))) {
                return true;
            }
        }
        return false;
    }

    public String TimKiemKhuyenMai(double TongTien) throws IOException {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachKhuyenMai[i].getTrangThai() == 1 && Integer.parseInt(thaotac.GhepNgayThang(DanhSachKhuyenMai[i].getNgayBatDau())) <= Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) && Integer.parseInt(thaotac.GhepNgayThang(ThaoTac.LayNgayHienTai())) <= Integer.parseInt(thaotac.GhepNgayThang(DanhSachKhuyenMai[i].getNgayKetThuc())) && TongTien >= DanhSachKhuyenMai[i].getDieuKien()) {
                return DanhSachKhuyenMai[i].getMaKhuyenMai();
            }
        }
        return "Không";
    }

    public int TruyXuatMaKhuyenMai(String MaKhuyenMai) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (MaKhuyenMai.equals(DanhSachKhuyenMai[i].getMaKhuyenMai())) {
                return i;
            }
        }
        return -1;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin khuyến mãi:");
        System.out.println("1.Phần trăm khuyến mãi");
        System.out.println("2.Điều kiện");
        System.out.println("3.Thời gian khuyến mãi");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ KHUYẾN MÃI:");
        System.out.println("1.Thêm khuyến mãi");
        System.out.println("2.Cập nhật thông tin khuyến mãi");
        System.out.println("3.Xóa khuyến mãi");
        System.out.println("4.Tìm kiếm khuyến mãi");
        System.out.println("5.Thoát");
    }

    public void Menu(String MaNhanVienDangNhap) throws IOException {
        int LuaChon;
        do {
            HienThiDanhSach();
            HienThiMenu();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon > 0 && LuaChon < 6) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                }
            }
            String XacNhan;
            switch (LuaChon) {
                case 1: {
                    if (!KiemTraKhuyenMaiDaTonTai()) {
                        while (true) {
                            System.out.println("Thêm khuyến mãi:");
                            boolean ThemKhuyenMai = ThemPhanTu(MaNhanVienDangNhap);
                            if (!ThemKhuyenMai) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục thêm (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            System.out.println("");
                        }
                    } else {
                        System.out.println("Hiện tại không thể thêm khuyến mãi !");
                    }
                    System.out.println("\nĐã thoát !\n");
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 2: {
                    if (DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách khuyến mãi trống !\n");

                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin khuyến mãi:");
                            boolean CapNhatThongTinKhuyenMai = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinKhuyenMai) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục cập nhật (y/n): ");
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
                    if (DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách khuyến mãi trống !");
                    } else {
                        while (true) {
                            System.out.println("Xóa khuyến mãi:");
                            boolean XoaKhuyenMai = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaKhuyenMai) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách khuyến mãi trống !");
                                break;
                            } else {
                                System.out.print("Xác nhận tiếp tục xóa (y/n): ");
                                XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                                if (XacNhan.equals("n")) {
                                    break;
                                }
                            }
                            System.out.println("");
                        }
                        System.out.println("\nĐã thoát !\n");
                    }
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 4: {
                    if (DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách khuyến mãi trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm khuyến mãi:");
                            boolean TimKiemKhuyenMai = TimKiemPhanTu();
                            if (!TimKiemKhuyenMai) {
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
                case 5: {
                    System.out.println("Đã thoát !\n");
                    break;
                }
            }
        } while (LuaChon != 5);
    }

}
