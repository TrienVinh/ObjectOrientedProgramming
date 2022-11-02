package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachNhaCungCap implements DanhSach {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-25s|%-31s|%-23s|%-53s|\n";
    private final String TenTapTin = "DanhSachNhaCungCap.txt";
    private NhaCungCap[] DanhSachNhaCungCap = new NhaCungCap[0];

    public NhaCungCap[] getDanhSachNhaCungCap() {
        return DanhSachNhaCungCap;
    }

    public DanhSachNhaCungCap() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách nhà cung cấp trống !\n");
        } else {
            System.out.println("Danh sách nhà cung cấp: ");
            thaotac.KeVienDanhSachNhaCungCap();
            System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
            thaotac.KeVienDanhSachNhaCungCap();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachNhaCungCap[i].getTrangThai() == 1) {
                    DanhSachNhaCungCap[i].HienThiThongTinNhaCungCap();
                    thaotac.KeVienDanhSachNhaCungCap();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        SoPhanTuThemVao++;
        String MaNhaCungCap = "NCC";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaNhaCungCap += "0";
        }
        DanhSachNhaCungCap = Arrays.copyOf(DanhSachNhaCungCap, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new NhaCungCap();
        DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaNhaCungCap(MaNhaCungCap + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setSoSanPham(0);
        boolean ThemNhaCungCap = DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinNhaCungCap();
        if (ThemNhaCungCap) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' thêm nhà cung cấp '" + MaNhaCungCap + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + " (" + DanhSachNhaCungCap[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getTenNhaCungCap() + ")' vào danh sách",ThaoTac.LayThoiGianHienTai());
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
        String CapNhat, MaNhaCungCap;
        System.out.print("Nhập mã nhà cung cấp muốn cập nhật: ");
        MaNhaCungCap = kiemtradulieu.KiemTraNhaCungCapDaTonTai();
        if (MaNhaCungCap.equals("-q")) {
            return false;
        }
        int ViTri = KiemTraPhanTuTonTai(MaNhaCungCap);
        if (ViTri != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhaCungCap = DanhSachNhaCungCap[ViTri].getTenNhaCungCap();
            String XacNhan;
            System.out.print("\nXác nhận cập nhật thông tin nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")" + "' (y/n): ");
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
                        System.out.print("Nhập tên nhà cung cấp: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraTenNhaCungCapDaTonTai();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachNhaCungCap[ViTri].getTenNhaCungCap())) {
                                System.out.print("Tên mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật tên của nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhaCungCap[ViTri].setTenNhaCungCap(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.print("Nhập số điện thoại: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapSoDienThoaiNhaCungCap();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachNhaCungCap[ViTri].getSoDienThoai())) {
                                System.out.print("Số điện thoại mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                if (!KiemTraSoDienThoaiDaTonTai(CapNhat)) {
                                    danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật số điện thoại của nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")'",ThaoTac.LayThoiGianHienTai());
                                    DanhSachNhaCungCap[ViTri].setSoDienThoai(CapNhat);
                                    break;
                                } else {
                                    System.out.print("Số điện thoại này đã có trong danh sách ! Mời nhập lại: ");
                                }
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("Nhập địa chỉ: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapDiaChi();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachNhaCungCap[ViTri].getDiaChi())) {
                                System.out.print("Địa chỉ mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật địa chỉ của nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachNhaCungCap[ViTri].setDiaChi(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                }
                LuuDuLieu();
                System.out.println("\nCập nhật thành công !\n");
                System.out.println("Thông tin nhà cung cấp '" + MaNhaCungCap + "' sau khi cập nhật: ");
                thaotac.KeVienDanhSachNhaCungCap();
                System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
                thaotac.KeVienDanhSachNhaCungCap();
                DanhSachNhaCungCap[ViTri].HienThiThongTinNhaCungCap();
                thaotac.KeVienDanhSachNhaCungCap();
                System.out.println("");
            } else {
                System.out.println("\nHủy bỏ cập nhật thông tin nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")" + "' !\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy nhà cung cấp '" + MaNhaCungCap + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        String MaNhaCungCap;
        System.out.print("Nhập mã nhà cung cấp muốn xóa: ");
        MaNhaCungCap = kiemtradulieu.KiemTraNhaCungCapDaTonTai();
        if (MaNhaCungCap.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaNhaCungCap);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhaCungCap = DanhSachNhaCungCap[ViTri1].getTenNhaCungCap();
            String XacNhan;
            System.out.print("\nXác nhận xóa nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equalsIgnoreCase("y")) {
                DanhSachNhaCungCap[ViTri1].setTrangThai(0);
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' xóa nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nXóa thành công !\n");
            } else {
                System.out.println("\nHủy bỏ xóa nhà cung cấp '" + MaNhaCungCap + " (" + TenNhaCungCap + ")" + "' !\n");
            }

        } else {
            System.out.println("\nKhông tìm thấy nhà cung cấp '" + MaNhaCungCap + "' !\n");
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
                String MaNhaCungCap;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã nhà cung cấp muốn tìm: ");
                MaNhaCungCap = kiemtradulieu.KiemTraNhapMaNhaCungCap();
                if (MaNhaCungCap.equals("-q")) {
                    return false;
                }
                int ViTri = KiemTraPhanTuTonTai(MaNhaCungCap);
                if (ViTri != -1) {
                    System.out.println("\nThông tin nhà cung cấp muốn tìm: ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    DanhSachNhaCungCap[ViTri].HienThiThongTinNhaCungCap();
                    thaotac.KeVienDanhSachNhaCungCap();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy nhà cung cấp '" + MaNhaCungCap + "' !\n");
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
                NhaCungCap[] TimKiemNhaCungCap = new NhaCungCap[DemSoPhanTuKhaDung()];
                switch (LuaChon2) {
                    case 1: {
                        String TenGanDung;
                        System.out.println("Tìm kiếm gần đúng:");
                        System.out.print("Nhập tên nhà cung cấp muốn tìm: ");
                        TenGanDung = kiemtradulieu.KiemTraNhapTenGanDung();
                        if (TenGanDung.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachNhaCungCap[i].getTrangThai() == 1 && DanhSachNhaCungCap[i].getTenNhaCungCap().contains(TenGanDung)) {
                                TimKiemNhaCungCap[Dem] = DanhSachNhaCungCap[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy nhà cung cấp '" + TenGanDung + "' !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        String TenNhaCungCap;
                        System.out.println("Tìm kiếm đúng:");
                        System.out.print("Nhập tên nhà cung cấp muốn tìm: ");
                        TenNhaCungCap = kiemtradulieu.KiemTraTenNhaCungCapDaTonTai();
                        if (TenNhaCungCap.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachNhaCungCap[i].getTrangThai() == 1 && TenNhaCungCap.equalsIgnoreCase(DanhSachNhaCungCap[i].getTenNhaCungCap())) {
                                TimKiemNhaCungCap[Dem] = DanhSachNhaCungCap[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy nhà cung cấp '" + TenNhaCungCap + "' !\n");
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
                    System.out.println("\nThông tin nhà cung cấp muốn tìm: ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemNhaCungCap[i].HienThiThongTinNhaCungCap();
                        thaotac.KeVienDanhSachNhaCungCap();
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
                SoDienThoai = kiemtradulieu.KiemTraNhapSoDienThoaiNhaCungCap();
                if (SoDienThoai.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachNhaCungCap[i].getTrangThai() == 1 && SoDienThoai.equalsIgnoreCase(DanhSachNhaCungCap[i].getSoDienThoai())) {
                        ViTri = i;
                        break;
                    }
                }
                if (ViTri != -1) {
                    System.out.println("\nThông tin nhà cung cấp muốn tìm: ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
                    thaotac.KeVienDanhSachNhaCungCap();
                    DanhSachNhaCungCap[ViTri].HienThiThongTinNhaCungCap();
                    thaotac.KeVienDanhSachNhaCungCap();
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
        DanhSachNhaCungCap = Arrays.copyOf(DanhSachNhaCungCap, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachNhaCungCap[i] = new NhaCungCap();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachNhaCungCap[i].setMaNhaCungCap(scanfile.next());
                DanhSachNhaCungCap[i].setTenNhaCungCap(scanfile.next());
                DanhSachNhaCungCap[i].setSoSanPham(scanfile.nextInt());
                DanhSachNhaCungCap[i].setSoDienThoai(scanfile.next());
                DanhSachNhaCungCap[i].setDiaChi(scanfile.next());
                DanhSachNhaCungCap[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachNhaCungCap[i].getMaNhaCungCap() + ";" + DanhSachNhaCungCap[i].getTenNhaCungCap() + ";" + DanhSachNhaCungCap[i].getSoSanPham() + ";" + DanhSachNhaCungCap[i].getSoDienThoai() + ";" + DanhSachNhaCungCap[i].getDiaChi() + ";" + DanhSachNhaCungCap[i].getTrangThai();
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
            if (DanhSachNhaCungCap[i].getTrangThai() == 1 && Ma.equals(DanhSachNhaCungCap[i].getMaNhaCungCap())) {
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
            if (DanhSachNhaCungCap[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public void HienThiDanhSachNhaCungCapNhapHang() {
        System.out.println("Danh sách nhà cung cấp: ");
        thaotac.KeVienDanhSachNhaCungCap();
        System.out.format(DinhDang, "     MÃ NHÀ CUNG CẤP", "     TÊN NHÀ CUNG CẤP", "     SỐ ĐIỆN THOẠI", "     ĐỊA CHỈ");
        thaotac.KeVienDanhSachNhaCungCap();
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachNhaCungCap[i].getSoSanPham() > 0 && DanhSachNhaCungCap[i].getTrangThai() == 1) {
                DanhSachNhaCungCap[i].HienThiThongTinNhaCungCap();
                thaotac.KeVienDanhSachNhaCungCap();
            }
        }
        System.out.println("");

    }

    public boolean KiemTraSoDienThoaiDaTonTai(String SoDienThoai) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (SoDienThoai.equals(DanhSachNhaCungCap[i].getSoDienThoai())) {
                return true;
            }
        }
        return false;
    }

    public boolean KiemTraTenNhaCungCapDaTonTai(String TenNhaCungCap) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (TenNhaCungCap.equals(DanhSachNhaCungCap[i].getTenNhaCungCap())) {
                return true;
            }
        }
        return false;
    }

    public int TruyXuatMaNhaCungCap(String TenNhaCungCap) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachNhaCungCap[i].getTrangThai() == 1 && TenNhaCungCap.equals(DanhSachNhaCungCap[i].getTenNhaCungCap())) {
                return i;
            }
        }
        return -1;
    }

    public int TruyXuatTenNhaCungCap(String MaNhaCungCap) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (MaNhaCungCap.equals(DanhSachNhaCungCap[i].getMaNhaCungCap())) {
                return i;
            }
        }
        return -1;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin nhà cung cấp:");
        System.out.println("1.Tên nhà cung cấp");
        System.out.println("2.Ngày sinh");
        System.out.println("3.Phái");
        System.out.println("4.Số điện thoại");
        System.out.println("5.Địa chỉ");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ NHÀ CUNG CẤP:");
        System.out.println("1.Thêm nhà cung cấp");
        System.out.println("2.Cập nhật thông tin nhà cung cấp");
        System.out.println("3.Xóa nhà cung cấp");
        System.out.println("4.Tìm kiếm nhà cung cấp");
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
                        System.out.println("Thêm nhà cung cấp:");
                        boolean ThemNhaCungCap = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemNhaCungCap) {
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
                        System.out.println("Danh sách nhà cung cấp trống !\n");

                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin nhà cung cấp:");
                            boolean CapNhatThongTinNhaCungCap = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinNhaCungCap) {
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
                        System.out.println("Danh sách nhà cung cấp trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Xóa nhà cung cấp:");
                            boolean XoaNhaCungCap = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaNhaCungCap) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục xóa (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách nhà cung cấp trống !");
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
                        System.out.println("Danh sách nhà cung cấp trống !\n");
                    } else {
                        System.out.println("Tìm kiếm nhà cung cấp:");
                        boolean TimKiemNhaCungCap = TimKiemPhanTu();
                        if (!TimKiemNhaCungCap) {
                            System.out.println("\nĐã thoát !\n");
                        }
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
