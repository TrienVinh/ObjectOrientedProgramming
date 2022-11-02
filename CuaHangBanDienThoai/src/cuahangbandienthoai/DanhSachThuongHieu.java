package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachThuongHieu implements DanhSach {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-24s|%-35s|%-23s|\n";
    private final String TenTapTin = "DanhSachThuongHieu.txt";
    private ThuongHieu[] DanhSachThuongHieu = new ThuongHieu[0];

    public ThuongHieu[] getDanhSachThuongHieu() {
        return DanhSachThuongHieu;
    }

    public DanhSachThuongHieu() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách thương hiệu trống !\n");
        } else {
            System.out.println("Danh sách thương hiệu: ");
            thaotac.KeVienDanhSachThuongHieu();
            System.out.format(DinhDang, "     MÃ THƯƠNG HIỆU", "     TÊN THƯƠNG HIỆU", "     QUỐC GIA");
            thaotac.KeVienDanhSachThuongHieu();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachThuongHieu[i].getTrangThai() == 1) {
                    DanhSachThuongHieu[i].HienThiThongTinThuongHieu();
                    thaotac.KeVienDanhSachThuongHieu();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        SoPhanTuThemVao++;
        String MaThuongHieu = "TH";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaThuongHieu += "0";
        }
        DanhSachThuongHieu = Arrays.copyOf(DanhSachThuongHieu, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachThuongHieu[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new ThuongHieu();
        DanhSachThuongHieu[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaThuongHieu(MaThuongHieu + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachThuongHieu[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        boolean ThemThuongHieu = DanhSachThuongHieu[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinThuongHieu();
        if (ThemThuongHieu) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' thêm thương hiệu '" + MaThuongHieu + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + " (" + DanhSachThuongHieu[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getTenThuongHieu() + ")' vào danh sách",ThaoTac.LayThoiGianHienTai());
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
        String CapNhat, MaThuongHieu;
        System.out.print("Nhập mã thương hiệu muốn cập nhật: ");
        MaThuongHieu = kiemtradulieu.KiemTraThuongHieuDaTonTai();
        if (MaThuongHieu.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaThuongHieu);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenThuongHieu = DanhSachThuongHieu[ViTri1].getTenThuongHieu();
            String XacNhan;
            System.out.print("\nXác nhận cập nhật thông tin thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equals("y")) {
                HienThiMenuCapNhat();
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
                        System.out.print("Nhập tên thương hiệu: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapTenThuongHieu();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachThuongHieu[ViTri1].getTenThuongHieu())) {
                                System.out.print("Tên thương hiệu mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật tên của thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachThuongHieu[ViTri1].setTenThuongHieu(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.print("Nhập quốc gia: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapQuocGia();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachThuongHieu[ViTri1].getQuocGia())) {
                                System.out.print("Quốc gia mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật quốc gia của thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachThuongHieu[ViTri1].setQuocGia(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                }
                LuuDuLieu();
                System.out.println("\nCập nhật thành công !\n");
                System.out.println("Thông tin thương hiệu '" + MaThuongHieu + "' sau khi cập nhật: ");
                thaotac.KeVienDanhSachThuongHieu();
                System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     QUỐC GIA");
                thaotac.KeVienDanhSachThuongHieu();
                DanhSachThuongHieu[ViTri1].HienThiThongTinThuongHieu();
                thaotac.KeVienDanhSachThuongHieu();
                System.out.println("");
            } else {
                System.out.println("\nHủy bỏ cập nhật thông tin thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")" + "' !\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy thương hiệu '" + MaThuongHieu + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        String MaThuongHieu;
        System.out.print("Nhập mã thương hiệu muốn xóa: ");
        MaThuongHieu = kiemtradulieu.KiemTraThuongHieuDaTonTai();
        if (MaThuongHieu.equals("-q")) {
            return false;
        }
        int ViTri = KiemTraPhanTuTonTai(MaThuongHieu);
        if (ViTri != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenThuongHieu = DanhSachThuongHieu[ViTri].getTenThuongHieu();
            String XacNhan;
            System.out.print("\nXác nhận xóa thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equalsIgnoreCase("y")) {
                DanhSachThuongHieu[ViTri].setTrangThai(0);
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' xóa thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nXóa thành công !\n");
            } else {
                System.out.println("\nHủy bỏ xóa thương hiệu '" + MaThuongHieu + " (" + TenThuongHieu + ")" + "' !\n");
            }

        } else {
            System.out.println("\nKhông tìm thấy thương hiệu '" + MaThuongHieu + "' !\n");
        }
        return true;
    }

    @Override
    public boolean TimKiemPhanTu() {
        int LuaChon1;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo tên   3.Tìm kiếm theo quốc gia");
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
                String MaThuongHieu;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã thương hiệu muốn tìm: ");
                MaThuongHieu = kiemtradulieu.KiemTraNhapMaThuongHieu();
                if (MaThuongHieu.equals("-q")) {
                    return false;
                }
                int ViTri = KiemTraPhanTuTonTai(MaThuongHieu);
                if (ViTri != -1) {
                    System.out.println("\nThông tin thương hiệu muốn tìm: ");
                    thaotac.KeVienDanhSachThuongHieu();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     QUỐC GIA");
                    thaotac.KeVienDanhSachThuongHieu();
                    DanhSachThuongHieu[ViTri].HienThiThongTinThuongHieu();
                    thaotac.KeVienDanhSachThuongHieu();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy thương hiệu '" + MaThuongHieu + "' !\n");
                }
                break;
            }
            case 2: {
                String TenThuongHieu;
                System.out.println("Tìm kiếm theo tên:");
                System.out.print("Nhập tên thương hiệu muốn tìm: ");
                TenThuongHieu = kiemtradulieu.KiemTraNhapTenThuongHieu();
                if (TenThuongHieu.equals("-q")) {
                    return false;
                }
                int ViTri = -1;
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachThuongHieu[i].getTrangThai() == 1 && DanhSachThuongHieu[i].getTenThuongHieu().equals(TenThuongHieu)) {
                        ViTri = i;
                        break;
                    }
                }
                if (ViTri != -1) {
                    System.out.println("\nThông tin thương hiệu muốn tìm: ");
                    thaotac.KeVienDanhSachThuongHieu();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     QUỐC GIA");
                    thaotac.KeVienDanhSachThuongHieu();
                    DanhSachThuongHieu[ViTri].HienThiThongTinThuongHieu();
                    thaotac.KeVienDanhSachThuongHieu();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy thương hiệu '" + TenThuongHieu + "' !\n");
                }
                break;
            }
            case 3: {
                int Dem = 0;
                ThuongHieu[] TimKiemThuongHieu = new ThuongHieu[DemSoPhanTuKhaDung()];
                String QuocGia;
                System.out.println("Tìm kiếm theo quốc gia:");
                System.out.print("Nhập quốc gia của thương hiệu muốn tìm: ");
                QuocGia = kiemtradulieu.KiemTraNhapQuocGia();
                if (QuocGia.equals("-q")) {
                    return false;
                }
                for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                    if (DanhSachThuongHieu[i].getTrangThai() == 1 && QuocGia.equalsIgnoreCase(DanhSachThuongHieu[i].getQuocGia())) {
                        TimKiemThuongHieu[Dem] = DanhSachThuongHieu[i];
                        Dem++;
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy thương hiệu của quốc gia '" + QuocGia + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                }
                if (Dem > 0) {
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin thương hiệu muốn tìm: ");
                    thaotac.KeVienDanhSachThuongHieu();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     QUỐC GIA");
                    thaotac.KeVienDanhSachThuongHieu();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemThuongHieu[i].HienThiThongTinThuongHieu();
                        thaotac.KeVienDanhSachThuongHieu();
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
        DanhSachThuongHieu = Arrays.copyOf(DanhSachThuongHieu, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachThuongHieu[i] = new ThuongHieu();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachThuongHieu[i].setMaThuongHieu(scanfile.next());
                DanhSachThuongHieu[i].setTenThuongHieu(scanfile.next());
                DanhSachThuongHieu[i].setQuocGia(scanfile.next());
                DanhSachThuongHieu[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachThuongHieu[i].getMaThuongHieu() + ";" + DanhSachThuongHieu[i].getTenThuongHieu() + ";" + DanhSachThuongHieu[i].getQuocGia() + ";" + DanhSachThuongHieu[i].getTrangThai();

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
            if (DanhSachThuongHieu[i].getTrangThai() == 1 && Ma.equals(DanhSachThuongHieu[i].getMaThuongHieu())) {
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
            if (DanhSachThuongHieu[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public boolean KiemTraTenThuongHieuDaTonTai(String TenThuongHieu) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (TenThuongHieu.equals(DanhSachThuongHieu[i].getTenThuongHieu())) {
                return true;
            }
        }
        return false;
    }

    public int TruyXuatMaThuongHieu(String TenThuongHieu) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachThuongHieu[i].getTrangThai() == 1 && TenThuongHieu.equals(DanhSachThuongHieu[i].getTenThuongHieu())) {
                return i;
            }
        }
        return -1;
    }

    public int TruyXuatTenThuongHieu(String MaThuongHieu) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (MaThuongHieu.equals(DanhSachThuongHieu[i].getMaThuongHieu())) {
                return i;
            }
        }
        return -1;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin thương hiệu:");
        System.out.println("1.Tên thương hiệu");
        System.out.println("2.Quốc gia");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ SẢN PHẨM:");
        System.out.println("1.Thêm thương hiệu");
        System.out.println("2.Cập nhật thông tin thương hiệu");
        System.out.println("3.Xóa thương hiệu");
        System.out.println("4.Tìm kiếm thương hiệu");
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
                        System.out.println("Thêm thương hiệu:");
                        boolean ThemThuongHieu = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemThuongHieu) {
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
                        System.out.println("Danh sách thương hiệu trống !\n");

                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin thương hiệu:");
                            boolean CapNhatThongTinThuongHieu = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinThuongHieu) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục cập nhật (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            System.out.println("");
                        }
                    }
                    System.out.println("\nĐã thoát !\n");
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 3: {
                    if (DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách thương hiệu trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Xóa thương hiệu:");
                            boolean XoaThuongHieu = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaThuongHieu) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục xóa (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách thương hiệu trống !");
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
                        System.out.println("Danh sách thương hiệu trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm thương hiệu:");
                            boolean TimKiemThuongHieu = TimKiemPhanTu();
                            if (!TimKiemThuongHieu) {
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
