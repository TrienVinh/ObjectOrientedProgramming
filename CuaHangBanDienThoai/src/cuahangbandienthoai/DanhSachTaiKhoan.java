package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachTaiKhoan {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-22s|%-33s|%-30s|%-25s|\n";
    private final String TenTapTin = "DanhSachTaiKhoan.txt";
    private TaiKhoan[] DanhSachTaiKhoan = new TaiKhoan[0];
    Scanner scanner = new Scanner(System.in);

    public TaiKhoan[] getDanhSachTaiKhoan() {
        return DanhSachTaiKhoan;
    }

    public DanhSachTaiKhoan() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach() {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách tài khoản trống !\n");
        } else {
            System.out.println("Danh sách tài khoản: ");
            thaotac.KeVienDanhSachTaiKhoan();
            System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     MẬT KHẨU", "     PHÂN QUYỀN", "     TÌNH TRẠNG");
            thaotac.KeVienDanhSachTaiKhoan();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachTaiKhoan[i].getTrangThai() == 1) {
                    DanhSachTaiKhoan[i].HienThiThongTinTaiKhoan();
                    thaotac.KeVienDanhSachTaiKhoan();
                }
            }
            System.out.println("");
        }
    }

    public void ThemPhanTu(String MaNhanVien, String MatKhau) throws IOException {
        SoPhanTuThemVao++;
        DanhSachTaiKhoan = Arrays.copyOf(DanhSachTaiKhoan, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new TaiKhoan();
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaNhanVien(MaNhanVien);
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMatKhau(MatKhau);
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setPhanQuyen("Nhân viên bán hàng");
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTinhTrang("Bình thường");
        DanhSachTaiKhoan[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        LuuDuLieu();
    }

    public boolean PhucHoiMatKhau(String MaNhanVienDangNhap) throws IOException {
        String MaNhanVien;
        System.out.print("Nhập mã nhân viên: ");
        MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri1 != -1) {
            String NgaySinh = danhsachnhanvien.getDanhSachNhanVien()[ViTri1].getNgaySinh();
            if (DanhSachTaiKhoan[ViTri1].getMatKhau().equals(thaotac.DatMatKhauMacDinh(NgaySinh))) {
                System.out.println("\nMật khẩu hiện tại của tài khoản của nhân viên '" + MaNhanVien + "' đã là mật khẩu mặc định !\n");
            } else {
                if (MaNhanVien.equals(MaNhanVienDangNhap)) {
                    System.out.println("\nKhông thể tự phục hồi mật khẩu !\n");
                } else {

                    String XacNhan;
                    System.out.print("\nXác nhận phục hồi mật khẩu tài khoản của nhân viên '" + MaNhanVien + "' (y/n): ");
                    XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                    if (XacNhan.equals("y")) {
                        DanhSachTaiKhoan[ViTri1].setMatKhau(thaotac.DatMatKhauMacDinh(NgaySinh));
                        int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen();
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' phục hồi mật khẩu cho nhân viên '" + MaNhanVien + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri1].getHoTen() + ")'",ThaoTac.LayThoiGianHienTai());
                        LuuDuLieu();
                        System.out.println("\nPhục hồi mật khẩu thành công !\n");
                    } else {
                        System.out.println("\nHủy bỏ phục hồi mật khẩu !\n");
                    }
                }
            }
        } else {
            System.out.println("\nKhông tìm thấy tài khoản của nhân viên '" + MaNhanVien + "' !\n");
        }
        return true;
    }

    public boolean KhoaTaiKhoan(String MaNhanVienDangNhap) throws IOException {
        String MaNhanVien;
        System.out.print("Nhập mã nhân viên: ");
        MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri1 != -1) {
            if (DanhSachTaiKhoan[ViTri1].getTinhTrang().equals("Bị khóa")) {
                System.out.println("\nTài khoản của nhân viên '" + MaNhanVien + "' hiện tại đã bị khóa !\n");
            } else {
                if (MaNhanVien.equals(MaNhanVienDangNhap)) {
                    System.out.println("\nKhông thể tự khóa tài khoản !\n");
                } else {
                    int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                    String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen();
                    String XacNhan;
                    System.out.print("\nXác nhận khoá tài khoản của nhân viên '" + MaNhanVien + "' (y/n): ");
                    XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                    if (XacNhan.equals("y")) {
                        DanhSachTaiKhoan[ViTri1].setTinhTrang("Bị khóa");
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' khóa tài khoản của nhân viên '" + MaNhanVien + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri1].getHoTen() + ")'",ThaoTac.LayThoiGianHienTai());
                        LuuDuLieu();
                        System.out.println("\nKhóa tài khoản thành công !\n");
                    } else {
                        System.out.println("\nHủy bỏ khóa tài khoản của nhân viên '" + MaNhanVien + "' !\n");
                    }
                }
            }

        } else {
            System.out.println("\nKhông tìm thấy tài khoản của nhân viên '" + MaNhanVien + "' !\n");
        }
        return true;
    }

    public boolean MoKhoaTaiKhoan(String MaNhanVienDangNhap) throws IOException {
        String MaNhanVien;
        System.out.print("Nhập mã nhân viên: ");
        MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri1 != -1) {
            if (DanhSachTaiKhoan[ViTri1].getTinhTrang().equals("Bình thường")) {
                System.out.println("\nTài khoản của nhân viên '" + MaNhanVien + "' hiện tại đã được mở khóa !\n");
            } else {
                if (MaNhanVien.equals(MaNhanVienDangNhap)) {
                    System.out.println("\nKhông thể tự mở khóa tài khoản !\n");
                } else {
                    int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                    String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen();
                    String XacNhan;
                    System.out.print("\nXác nhận mở khoá tài khoản của nhân viên '" + MaNhanVien + "' (y/n): ");
                    XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                    if (XacNhan.equals("y")) {
                        DanhSachTaiKhoan[ViTri1].setTinhTrang("Bình thường");
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' mở khóa cho tài khoản của nhân viên '" + MaNhanVien + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri1].getHoTen() + ")'",ThaoTac.LayThoiGianHienTai());
                        LuuDuLieu();
                        System.out.println("\nMở khóa tài khoản thành công !\n");
                    } else {
                        System.out.println("\nHủy bỏ mở khóa tài khoản của nhân viên '" + MaNhanVien + "' !\n");
                    }
                }
            }
        } else {
            System.out.println("\nKhông tìm thấy tài khoản của nhân viên '" + MaNhanVien + "' !\n");
        }
        return true;
    }

    public void XoaTaiKhoan(int ViTri) throws IOException {
        DanhSachTaiKhoan[ViTri].setTrangThai(0);
        LuuDuLieu();
    }

    public boolean TimKiemTaiKhoan() throws IOException {
        String MaNhanVien;
        System.out.print("Nhập mã nhân viên của tài khoản muốn tìm: ");
        MaNhanVien = kiemtradulieu.KiemTraNhapMaNhanVien();
        if (MaNhanVien.equals("-q")) {
            return false;
        }
        int ViTri = KiemTraPhanTuTonTai(MaNhanVien);
        if (ViTri != -1) {
            System.out.println("\nThông tin tài khoản muốn tìm: ");
            thaotac.KeVienDanhSachTaiKhoan();
            System.out.format(DinhDang, "     MÃ NHÂN VIÊN", "     MẬT KHẨU", "     PHÂN QUYỀN", "     TÌNH TRẠNG");
            thaotac.KeVienDanhSachTaiKhoan();
            DanhSachTaiKhoan[ViTri].HienThiThongTinTaiKhoan();
            thaotac.KeVienDanhSachTaiKhoan();
            System.out.println("");
        } else {
            System.out.println("Không tìm thấy tài khoản của nhân viên '" + MaNhanVien + "' !");
        }
        return true;
    }

    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachTaiKhoan = Arrays.copyOf(DanhSachTaiKhoan, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachTaiKhoan[i] = new TaiKhoan();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachTaiKhoan[i].setMaNhanVien(scanfile.next());
                DanhSachTaiKhoan[i].setMatKhau(scanfile.next());
                DanhSachTaiKhoan[i].setPhanQuyen(scanfile.next());
                DanhSachTaiKhoan[i].setTinhTrang(scanfile.next());
                DanhSachTaiKhoan[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachTaiKhoan[i].getMaNhanVien() + ";" + DanhSachTaiKhoan[i].getMatKhau() + ";" + DanhSachTaiKhoan[i].getPhanQuyen() + ";" + DanhSachTaiKhoan[i].getTinhTrang() + ";" + DanhSachTaiKhoan[i].getTrangThai();
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
            if (DanhSachTaiKhoan[i].getTrangThai() == 1 && Ma.equals(DanhSachTaiKhoan[i].getMaNhanVien())) {
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

    public int DemSoPhanTuKhaDung() {
        int SoDongDaXoa = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachTaiKhoan[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public int DemSoTaiKhoanBiKhoa() {
        int SoTaiKhoanBiKhoa = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachTaiKhoan[i].getTinhTrang().equals("Bị khóa") && DanhSachTaiKhoan[i].getTrangThai() == 1) {
                SoTaiKhoanBiKhoa++;
            }
        }
        return SoTaiKhoanBiKhoa;
    }

    public int DemSoTaiKhoanCoMatKhauMacDinh() throws IOException {
        int SoTaiKhoanCoMatKhauMacDinh = 0;
        for (int i = 1; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachTaiKhoan[i].getMatKhau().equals(thaotac.DatMatKhauMacDinh(danhsachnhanvien.getDanhSachNhanVien()[i].getNgaySinh())) && DanhSachTaiKhoan[i].getTrangThai() == 1) {
                SoTaiKhoanCoMatKhauMacDinh++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoTaiKhoanCoMatKhauMacDinh;
    }

    public int DemSoTaiKhoanDuocMoKhoa() {
        int SoTaiKhoanDuocMoKhoa = 0;
        for (int i = 1; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachTaiKhoan[i].getTinhTrang().equals("Bình thường") && DanhSachTaiKhoan[i].getTrangThai() == 1) {
                SoTaiKhoanDuocMoKhoa++;
            }
        }
        return SoTaiKhoanDuocMoKhoa;
    }

    public boolean DoiMatKhau(String MaNhanVienDangNhap) throws IOException {
        String XacNhan;
        System.out.print("Xác nhận đổi mật khẩu (y/n): ");
        XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
        if (XacNhan.equals("y")) {
            int ViTri = KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String MatKhau;
            System.out.println("Mật khẩu mới phải chứa ký tự số, ký tự viết thường và ký tự viết hoa !");
            while (true) {
                System.out.print("Nhập mật khẩu cũ: ");
                MatKhau = scanner.nextLine();
                if (MatKhau.equals("-q")) {
                    return false;
                }
                if (thaotac.MaHoaMatKhau(MatKhau).equals(DanhSachTaiKhoan[ViTri].getMatKhau())) {
                    System.out.print("Nhập mật khẩu mới: ");
                    MatKhau = kiemtradulieu.KiemTraNhapMatKhau();
                    if (MatKhau.equals("-q")) {
                        return false;
                    }
                    DanhSachTaiKhoan[ViTri].setMatKhau(MatKhau);
                    LuuDuLieu();
                    System.out.println("\nĐổi mật khẩu thành công !\n");
                    return true;
                } else {
                    System.out.print("Mật khẩu cũ không chính xác ! Mời nhập lại: ");
                }
            }
        } else {
            System.out.println("\nHủy bỏ đổi mật khẩu !");
            return false;
        }
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ TÀI KHOẢN:");
        System.out.println("1.Phục hồi mật khẩu");
        System.out.println("2.Khóa tài khoản");
        System.out.println("3.Mở khóa tài khoản");
        System.out.println("4.Tìm kiếm tài khoản");
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
                        if (DemSoTaiKhoanCoMatKhauMacDinh() == 1) {
                            System.out.println("Không còn tài khoản nào cần phục hồi mật khẩu !");
                            break;
                        }
                        System.out.println("Phục hồi mật khẩu:");
                        boolean PhucHoiMatKhau = PhucHoiMatKhau(MaNhanVienDangNhap);
                        if (!PhucHoiMatKhau) {
                            break;
                        }
                        if (DemSoTaiKhoanCoMatKhauMacDinh() == 1) {
                            System.out.println("Không còn tài khoản nào cần phục hồi mật khẩu !");
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục phục hồi mật khẩu (y/n): ");
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
                    while (true) {
                        if (DemSoTaiKhoanDuocMoKhoa() == 1) {
                            System.out.println("Không còn tài khoản nào cần khóa !");
                            break;
                        }
                        System.out.println("Khóa tài khoản:");
                        boolean KhoaTaiKhoan = KhoaTaiKhoan(MaNhanVienDangNhap);
                        if (!KhoaTaiKhoan) {
                            break;
                        }
                        if (DemSoTaiKhoanDuocMoKhoa() == 1) {
                            System.out.println("Không còn tài khoản nào cần khóa !");
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục khóa tài khoản (y/n): ");
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
                case 3: {
                    while (true) {
                        if (DemSoTaiKhoanBiKhoa() == 0) {
                            System.out.println("Không còn tài khoản nào cần mở khóa !");
                            break;
                        }
                        System.out.println("Mở khóa tài khoản:");
                        boolean MoKhoaTaiKhoan = MoKhoaTaiKhoan(MaNhanVienDangNhap);
                        if (!MoKhoaTaiKhoan) {
                            break;
                        }
                        if (DemSoTaiKhoanBiKhoa() == 0) {
                            System.out.println("Không còn tài khoản nào cần mở khóa !");
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục mở khóa tài khoản (y/n): ");
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
                case 4: {

                    while (true) {
                        System.out.println("Tìm kiếm tài khoản:");
                        boolean TimKiemTaiKhoan = TimKiemTaiKhoan();
                        if (!TimKiemTaiKhoan) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục tìm kiếm tài khoản (y/n): ");
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
                case 5: {
                    System.out.println("Đã thoát !\n");
                    break;
                }
            }

        } while (LuaChon != 5);
    }

}
