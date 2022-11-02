package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachKhachHang implements DanhSach {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-23s|%-27s|%-19s|%-14s|%-23s|%-48s|\n";
    private final String TenTapTin = "DanhSachKhachHang.txt";
    private KhachHang[] DanhSachKhachHang = new KhachHang[0];

    public KhachHang[] getDanhSachKhachHang() {
        return DanhSachKhachHang;
    }

    public DanhSachKhachHang() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() throws IOException {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách khách hàng trống !\n");
        } else {
            System.out.println("Danh sách khách hàng: ");
            thaotac.KeVienDanhSachKhachHang();
            System.out.format(DinhDang, "     MÃ KHÁCH HÀNG", "     HỌ TÊN KHÁCH HÀNG", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
            thaotac.KeVienDanhSachKhachHang();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachKhachHang[i].getTrangThai() == 1) {
                    DanhSachKhachHang[i].HienThiThongTin();
                    thaotac.KeVienDanhSachKhachHang();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        SoPhanTuThemVao++;
        String MaKhachHang = "KH";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaKhachHang += "0";
        }
        DanhSachKhachHang = Arrays.copyOf(DanhSachKhachHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachKhachHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new KhachHang();
        DanhSachKhachHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaKhachHang(MaKhachHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachKhachHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        boolean ThemKhachHang = DanhSachKhachHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTin();
        if (ThemKhachHang) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' thêm khách hàng '" + MaKhachHang + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + " (" + DanhSachKhachHang[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getHoTen() + ")' vào danh sách",ThaoTac.LayThoiGianHienTai());
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
        String CapNhat, MaKhachHang;
        System.out.print("Nhập mã khách hàng muốn cập nhật: ");
        MaKhachHang = kiemtradulieu.KiemTraKhachHangDaTonTai();
        if (MaKhachHang.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaKhachHang);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenKhachHang = DanhSachKhachHang[ViTri1].getHoTen();
            String XacNhan;
            System.out.print("\nXác nhận cập nhật thông tin khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equals("y")) {
                HienThiMenuCapNhat();
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
                        System.out.print("Nhập họ tên khách hàng: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapHoTen();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachKhachHang[ViTri1].getHoTen())) {
                                System.out.print("Họ tên mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật họ tên của khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachKhachHang[ViTri1].setHoTen(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.print("Nhập ngày sinh: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapNgaySinh();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachKhachHang[ViTri1].getNgaySinh())) {
                                System.out.print("Ngày sinh mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật ngày sinh của khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachKhachHang[ViTri1].setNgaySinh(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Phái: 1.Nam   2.Nữ");
                        System.out.print("Nhập lựa chọn: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapPhai();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachKhachHang[ViTri1].getPhai())) {
                                System.out.print("Lựa chọn mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                DanhSachKhachHang[ViTri1].setPhai(CapNhat);
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật phái của khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")'",ThaoTac.LayThoiGianHienTai());
                                break;
                            }
                        }
                        break;
                    }
                    case 4: {
                        System.out.print("Nhập số điện thoại: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapSoDienThoai();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachKhachHang[ViTri1].getSoDienThoai())) {
                                System.out.print("Số điện thoại mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                if (!KiemTraSoDienThoaiDaTonTai(CapNhat)) {
                                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật số điện thoại của khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")'",ThaoTac.LayThoiGianHienTai());
                                    DanhSachKhachHang[ViTri1].setSoDienThoai(CapNhat);
                                    break;
                                } else {
                                    System.out.print("Số điện thoại này đã có trong danh sách ! Mời nhập lại: ");
                                }
                            }
                        }
                        break;
                    }
                    case 5: {
                        System.out.print("Nhập địa chỉ: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapDiaChi();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachKhachHang[ViTri1].getDiaChi())) {
                                System.out.print("Địa chỉ mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật địa chỉ của khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachKhachHang[ViTri1].setDiaChi(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                }
                LuuDuLieu();
                System.out.println("\nCập nhật thành công !\n");
                System.out.println("Thông tin khách hàng '" + MaKhachHang + "' sau khi cập nhật: ");
                thaotac.KeVienDanhSachKhachHang();
                System.out.format(DinhDang, "     MÃ KHÁCH HÀNG", "     HỌ TÊN KHÁCH HÀNG", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                thaotac.KeVienDanhSachKhachHang();
                DanhSachKhachHang[ViTri1].HienThiThongTin();
                thaotac.KeVienDanhSachKhachHang();
                System.out.println("");
            } else {
                System.out.println("\nHủy bỏ cập nhật thông tin khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")" + "' !\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy khách hàng '" + MaKhachHang + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        String MaKhachHang;
        System.out.print("Nhập mã khách hàng muốn xóa: ");
        MaKhachHang = kiemtradulieu.KiemTraKhachHangDaTonTai();
        if (MaKhachHang.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaKhachHang);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenKhachHang = DanhSachKhachHang[ViTri1].getHoTen();
            String XacNhan;
            System.out.print("\nXác nhận xóa khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equalsIgnoreCase("y")) {
                DanhSachKhachHang[ViTri1].setTrangThai(0);
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' xóa khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nXóa thành công !\n");
            } else {
                System.out.println("\nHủy bỏ xóa khách hàng '" + MaKhachHang + " (" + TenKhachHang + ")" + "' !\n");
            }

        } else {
            System.out.println("\nKhông tìm thấy khách hàng '" + MaKhachHang + "' !\n");
        }
        return true;
    }

    @Override
    public boolean TimKiemPhanTu() throws IOException {
        int LuaChon;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo tên   3.Tìm kiếm theo số điện thoại");
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
                String MaKhachHang;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã khách hàng muốn tìm: ");
                MaKhachHang = kiemtradulieu.KiemTraNhapMaKhachHang();
                if (MaKhachHang.equals("-q")) {
                    return false;
                }
                int ViTri1 = KiemTraPhanTuTonTai(MaKhachHang);
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin khách hàng muốn tìm: ");
                    thaotac.KeVienDanhSachKhachHang();
                    System.out.format(DinhDang, "     MÃ KHÁCH HÀNG", "     HỌ TÊN KHÁCH HÀNG", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachKhachHang();
                    DanhSachKhachHang[ViTri1].HienThiThongTin();
                    thaotac.KeVienDanhSachKhachHang();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy khách hàng '" + MaKhachHang + "' !\n");
                }
                break;
            }
            case 2: {
                int Dem = 0, LuaChon2;
                System.out.println("Tìm kiếm theo tên:");
                System.out.println("1.Tìm kiếm gần đúng   2.Tìm kiếm đúng");
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
                KhachHang[] TimKiemKhachHang = new KhachHang[DemSoPhanTuKhaDung()];
                switch (LuaChon2) {
                    case 1: {
                        String TenGanDung;
                        System.out.println("Tìm kiếm gần đúng:");
                        System.out.print("Nhập tên khách hàng muốn tìm: ");
                        TenGanDung = kiemtradulieu.KiemTraNhapTenGanDung();
                        if (TenGanDung.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachKhachHang[i].getTrangThai() == 1 && DanhSachKhachHang[i].getHoTen().contains(TenGanDung)) {
                                TimKiemKhachHang[Dem] = DanhSachKhachHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy khách hàng '" + TenGanDung + "' !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        String TenKhachHang;
                        System.out.println("Tìm kiếm đúng:");
                        System.out.print("Nhập họ tên khách hàng muốn tìm: ");
                        TenKhachHang = kiemtradulieu.KiemTraNhapHoTen();
                        if (TenKhachHang.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachKhachHang[i].getTrangThai() == 1 && TenKhachHang.equalsIgnoreCase(DanhSachKhachHang[i].getHoTen())) {
                                TimKiemKhachHang[Dem] = DanhSachKhachHang[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy khách hàng '" + TenKhachHang + "' !\n");
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
                    System.out.println("\nThông tin khách hàng muốn tìm: ");
                    thaotac.KeVienDanhSachKhachHang();
                    System.out.format(DinhDang, "     MÃ KHÁCH HÀNG", "     HỌ TÊN KHÁCH HÀNG", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachKhachHang();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemKhachHang[i].HienThiThongTin();
                        thaotac.KeVienDanhSachKhachHang();
                    }
                    System.out.println("");
                }
                break;
            }
            case 3: {
                int ViTri1 = -1;
                String SoDienThoai;
                System.out.println("Tìm kiếm theo số điện thoại:");
                System.out.print("Nhập số điện thoại muốn tìm: ");
                SoDienThoai = kiemtradulieu.KiemTraNhapSoDienThoai();
                if (SoDienThoai.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachKhachHang[i].getTrangThai() == 1 && SoDienThoai.equalsIgnoreCase(DanhSachKhachHang[i].getSoDienThoai())) {
                        ViTri1 = i;
                        break;
                    }
                }
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin khách hàng muốn tìm: ");
                    thaotac.KeVienDanhSachKhachHang();
                    System.out.format(DinhDang, "     MÃ KHÁCH HÀNG", "     HỌ TÊN KHÁCH HÀNG", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachKhachHang();
                    DanhSachKhachHang[ViTri1].HienThiThongTin();
                    thaotac.KeVienDanhSachKhachHang();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy số điện thoại '" + SoDienThoai + "' !\n");
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
        DanhSachKhachHang = Arrays.copyOf(DanhSachKhachHang, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachKhachHang[i] = new KhachHang();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachKhachHang[i].setMaKhachHang(scanfile.next());
                DanhSachKhachHang[i].setHoTen(scanfile.next());
                DanhSachKhachHang[i].setNgaySinh(scanfile.next());
                DanhSachKhachHang[i].setPhai(scanfile.next());
                DanhSachKhachHang[i].setSoDienThoai(scanfile.next());
                DanhSachKhachHang[i].setDiaChi(scanfile.next());
                DanhSachKhachHang[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachKhachHang[i].getMaKhachHang() + ";" + DanhSachKhachHang[i].getHoTen() + ";" + DanhSachKhachHang[i].getNgaySinh() + ";" + DanhSachKhachHang[i].getPhai() + ";" + DanhSachKhachHang[i].getSoDienThoai() + ";" + DanhSachKhachHang[i].getDiaChi() + ";" + DanhSachKhachHang[i].getTrangThai();
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
            if (DanhSachKhachHang[i].getTrangThai() == 1 && Ma.equals(DanhSachKhachHang[i].getMaKhachHang())) {
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
            if (DanhSachKhachHang[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public int TruyXuatTenKhachHang(String Ma) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (Ma.equals(DanhSachKhachHang[i].getMaKhachHang())) {
                return i;
            }
        }
        return -1;
    }

    public boolean KiemTraSoDienThoaiDaTonTai(String SoDienThoai) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (SoDienThoai.equals(DanhSachKhachHang[i].getSoDienThoai())) {
                return true;
            }
        }
        return false;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin khách hàng:");
        System.out.println("1.Họ tên khách hàng");
        System.out.println("2.Ngày sinh");
        System.out.println("3.Phái");
        System.out.println("4.Số điện thoại");
        System.out.println("5.Địa chỉ");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ KHÁCH HÀNG:");
        System.out.println("1.Thêm khách hàng");
        System.out.println("2.Cập nhật thông tin khách hàng");
        System.out.println("3.Xóa khách hàng");
        System.out.println("4.Tìm kiếm khách hàng");
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
                    while (true) {
                        System.out.println("Thêm khách hàng:");
                        boolean ThemKhachHang = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemKhachHang) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục thêm (y/n): ");
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
                    if (DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách khách hàng trống !\n");

                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin khách hàng:");
                            boolean CapNhatThongTinKhachHang = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinKhachHang) {
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
                        System.out.println("Danh sách khách hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Xóa khách hàng:");
                            boolean XoaKhachHang = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaKhachHang) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách khách hàng trống !");
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
                        System.out.println("Danh sách khách hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm khách hàng:");
                            boolean TimKiemKhachHang = TimKiemPhanTu();
                            if (!TimKiemKhachHang) {
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
                case 5: {
                    System.out.println("Đã thoát !\n");
                    break;
                }
            }
        } while (LuaChon != 5);
    }

}
