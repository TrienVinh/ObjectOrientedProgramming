package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachNhanVien implements DanhSach {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-22s|%-27s|%-19s|%-14s|%-23s|%-48s|\n";
    private final String TenTapTin = "DanhSachNhanVien.txt";
    private NhanVien[] DanhSachNhanVien = new NhanVien[0];

    public NhanVien[] getDanhSachNhanVien() {
        return DanhSachNhanVien;
    }

    public DanhSachNhanVien() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() throws IOException {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách nhân viên trống !\n");
        } else {
            System.out.println("Danh sách nhân viên: ");
            thaotac.KeVienDanhSachNhanVien();
            System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     HỌ TÊN NHÂN VIÊN", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
            thaotac.KeVienDanhSachNhanVien();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachNhanVien[i].getTrangThai() == 1) {
                    DanhSachNhanVien[i].HienThiThongTin();
                    thaotac.KeVienDanhSachNhanVien();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        SoPhanTuThemVao++;
        String MaNhanVien = "NV";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaNhanVien += "0";
        }
        DanhSachNhanVien = Arrays.copyOf(DanhSachNhanVien, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new NhanVien();
        DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaNhanVien(MaNhanVien + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        boolean ThemNhanVien = DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTin();
        if (ThemNhanVien) {
            DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
            String NgaySinh = DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getNgaySinh();
            danhsachtaikhoan.ThemPhanTu(MaNhanVien + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao), thaotac.DatMatKhauMacDinh(NgaySinh));
            int ViTri = KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri].getHoTen() + ")' thêm nhân viên '" + MaNhanVien + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + " (" + DanhSachNhanVien[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getHoTen() + ")' vào danh sách",ThaoTac.LayThoiGianHienTai());
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
        String CapNhat, MaNhanVien;
        System.out.print("Nhập mã nhân viên muốn cập nhật: ");
        MaNhanVien = kiemtradulieu.KiemTraNhanVienDaTonTai();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri1 != -1) {
            int ViTri2 = KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = DanhSachNhanVien[ViTri1].getHoTen();
            String XacNhan;
            System.out.print("\nXác nhận cập nhật thông tin nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")" + "' (y/n): ");
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
                        System.out.print("Nhập họ tên nhân viên: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapHoTen();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachNhanVien[ViTri1].getHoTen())) {
                                System.out.print("Họ tên mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' cập nhật họ tên của nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhanVien[ViTri1].setHoTen(CapNhat);
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
                            if (CapNhat.equals(DanhSachNhanVien[ViTri1].getNgaySinh())) {
                                System.out.print("Ngày sinh mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' cập nhật ngày sinh của nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhanVien[ViTri1].setNgaySinh(CapNhat);
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
                            if (CapNhat.equals(DanhSachNhanVien[ViTri1].getPhai())) {
                                System.out.print("Lựa chọn mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' cập nhật phái của nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhanVien[ViTri1].setPhai(CapNhat);
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
                            if (CapNhat.equals(DanhSachNhanVien[ViTri1].getSoDienThoai())) {
                                System.out.print("Số điện thoại mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                if (!KiemTraSoDienThoaiDaTonTai(CapNhat)) {
                                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' cập nhật số điện thoại của nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")'",ThaoTac.LayThoiGianHienTai());
                                    DanhSachNhanVien[ViTri1].setSoDienThoai(CapNhat);
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
                            if (CapNhat.equals(DanhSachNhanVien[ViTri1].getDiaChi())) {
                                System.out.print("Địa chỉ mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' cập nhật địa chỉ của nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhanVien[ViTri1].setDiaChi(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                }
                LuuDuLieu();
                System.out.println("\nCập nhật thành công !\n");
                System.out.println("Thông tin nhân viên '" + MaNhanVien + "' sau khi cập nhật: ");
                thaotac.KeVienDanhSachNhanVien();
                System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     HỌ TÊN NHÂN VIÊN", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                thaotac.KeVienDanhSachNhanVien();
                DanhSachNhanVien[ViTri1].HienThiThongTin();
                thaotac.KeVienDanhSachNhanVien();
                System.out.println("");
            } else {
                System.out.println("\nHủy bỏ cập nhật thông tin nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")" + "' !\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy nhân viên '" + MaNhanVien + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        String MaNhanVien;
        System.out.print("Nhập mã nhân viên muốn xóa: ");
        MaNhanVien = kiemtradulieu.KiemTraNhanVienDaTonTai();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri1 != -1) {
            if (MaNhanVien.equals(MaNhanVienDangNhap)) {
                System.out.println("\nKhông thể tự xóa mình khỏi danh sách !\n");
            } else {
                int ViTri2 = KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                String TenNhanVien = DanhSachNhanVien[ViTri1].getHoTen();
                String XacNhan;
                System.out.println("\nNếu xóa nhân viên khỏi danh sách thì tài khoản của nhân viên đó cũng sẽ bị xóa theo !");
                System.out.print("Xác nhận xóa nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")" + "' (y/n): ");
                XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                if (XacNhan.equalsIgnoreCase("y")) {
                    DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
                    DanhSachNhanVien[ViTri1].setTrangThai(0);
                    danhsachtaikhoan.XoaTaiKhoan(ViTri1);
                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + DanhSachNhanVien[ViTri2].getHoTen() + ")' xóa nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                    LuuDuLieu();
                    System.out.println("\nXóa thành công !\n");
                } else {
                    System.out.println("\nHủy bỏ xóa nhân viên '" + MaNhanVien + " (" + TenNhanVien + ")" + "' !\n");
                }
            }

        } else {
            System.out.println("\nKhông tìm thấy nhân viên '" + MaNhanVien + "' !\n");
        }
        return true;
    }

    @Override
    public boolean TimKiemPhanTu() throws IOException {
        int LuaChon1;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo tên   3.Tìm kiếm theo số điện thoại");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon1 = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon1 == -1) {
                return false;
            }
            if (LuaChon1 > 0 && LuaChon1 < 4) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon1) {
            case 1: {
                String MaNhanVien;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã nhân viên muốn tìm: ");
                MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
                if (MaNhanVien.equals("-q")) {
                    return false;
                }
                int ViTri = KiemTraPhanTuTonTai(MaNhanVien);
                if (ViTri != -1) {
                    System.out.println("\nThông tin nhân viên muốn tìm: ");
                    thaotac.KeVienDanhSachNhanVien();
                    System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     HỌ TÊN NHÂN VIÊN", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachNhanVien();
                    DanhSachNhanVien[ViTri].HienThiThongTin();
                    thaotac.KeVienDanhSachNhanVien();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy nhân viên '" + MaNhanVien + "' !\n");
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
                NhanVien[] TimKiemNhanVien = new NhanVien[DemSoPhanTuKhaDung()];
                switch (LuaChon2) {
                    case 1: {
                        String TenGanDung;
                        System.out.println("Tìm kiếm gần đúng:");
                        System.out.print("Nhập tên nhân viên muốn tìm: ");
                        TenGanDung = kiemtradulieu.KiemTraNhapTenGanDung();
                        if (TenGanDung.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachNhanVien[i].getTrangThai() == 1 && DanhSachNhanVien[i].getHoTen().contains(TenGanDung)) {
                                TimKiemNhanVien[Dem] = DanhSachNhanVien[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy nhân viên '" + TenGanDung + "' !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        String TenNhanVien;
                        System.out.println("Tìm kiếm đúng:");
                        System.out.print("Nhập họ tên nhân viên muốn tìm: ");
                        TenNhanVien = kiemtradulieu.KiemTraNhapHoTen();
                        if (TenNhanVien.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachNhanVien[i].getTrangThai() == 1 && TenNhanVien.equalsIgnoreCase(DanhSachNhanVien[i].getHoTen())) {
                                TimKiemNhanVien[Dem] = DanhSachNhanVien[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy nhân viên '" + TenNhanVien + "' !\n");
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
                    System.out.println("\nThông tin nhân viên muốn tìm: ");
                    thaotac.KeVienDanhSachNhanVien();
                    System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     HỌ TÊN NHÂN VIÊN", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachNhanVien();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemNhanVien[i].HienThiThongTin();
                        thaotac.KeVienDanhSachNhanVien();
                    }
                    System.out.println("");
                }
                break;
            }
            case 3: {
                int ViTri = -1;
                String SoDienThoai;
                System.out.println("Tìm kiếm theo số điện thoại:");
                System.out.print("Nhập số điện thoại muốn tìm: ");
                SoDienThoai = kiemtradulieu.KiemTraNhapSoDienThoai();
                if (SoDienThoai.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachNhanVien[i].getTrangThai() == 1 && SoDienThoai.equalsIgnoreCase(DanhSachNhanVien[i].getSoDienThoai())) {
                        ViTri = i;
                        break;
                    }
                }
                if (ViTri != -1) {
                    System.out.println("\nThông tin nhân viên muốn tìm: ");
                    thaotac.KeVienDanhSachNhanVien();
                    System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     HỌ TÊN NHÂN VIÊN", "     NGÀY SINH", "     PHÁI", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ THƯỜNG TRÚ");
                    thaotac.KeVienDanhSachNhanVien();
                    DanhSachNhanVien[ViTri].HienThiThongTin();
                    thaotac.KeVienDanhSachNhanVien();
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
        DanhSachNhanVien = Arrays.copyOf(DanhSachNhanVien, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachNhanVien[i] = new NhanVien();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachNhanVien[i].setMaNhanVien(scanfile.next());
                DanhSachNhanVien[i].setHoTen(scanfile.next());
                DanhSachNhanVien[i].setNgaySinh(scanfile.next());
                DanhSachNhanVien[i].setPhai(scanfile.next());
                DanhSachNhanVien[i].setSoDienThoai(scanfile.next());
                DanhSachNhanVien[i].setDiaChi(scanfile.next());
                DanhSachNhanVien[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachNhanVien[i].getMaNhanVien() + ";" + DanhSachNhanVien[i].getHoTen() + ";" + DanhSachNhanVien[i].getNgaySinh() + ";" + DanhSachNhanVien[i].getPhai() + ";" + DanhSachNhanVien[i].getSoDienThoai() + ";" + DanhSachNhanVien[i].getDiaChi() + ";" + DanhSachNhanVien[i].getTrangThai();
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
            if (DanhSachNhanVien[i].getTrangThai() == 1 && Ma.equals(DanhSachNhanVien[i].getMaNhanVien())) {
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
            if (DanhSachNhanVien[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public boolean KiemTraSoDienThoaiDaTonTai(String SoDienThoai) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (SoDienThoai.equals(DanhSachNhanVien[i].getSoDienThoai())) {
                return true;
            }
        }
        return false;
    }

    public int TruyXuatTenNhanVien(String Ma) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (Ma.equals(DanhSachNhanVien[i].getMaNhanVien())) {
                return i;
            }
        }
        return -1;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin nhân viên:");
        System.out.println("1.Họ tên nhân viên");
        System.out.println("2.Ngày sinh");
        System.out.println("3.Phái");
        System.out.println("4.Số điện thoại");
        System.out.println("5.Địa chỉ");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ NHÂN VIÊN:");
        System.out.println("1.Thêm nhân viên");
        System.out.println("2.Cập nhật thông tin nhân viên");
        System.out.println("3.Xóa nhân viên");
        System.out.println("4.Tìm kiếm nhân viên");
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
                        System.out.println("Thêm nhân viên:");
                        boolean ThemNhanVien = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemNhanVien) {
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
                        System.out.println("Danh sách nhân viên trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin nhân viên:");
                            boolean CapNhatThongTinNhanVien = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinNhanVien) {
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
                        System.out.println("Danh sách nhân viên trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Xóa nhân viên:");
                            boolean XoaNhanVien = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaNhanVien) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách nhân viên trống !");
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
                        System.out.println("Danh sách nhân viên trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm nhân viên:");
                            boolean TimKiemNhanVien = TimKiemPhanTu();
                            if (!TimKiemNhanVien) {
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
