package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachSanPham implements DanhSach {

    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-21s|%-37s|%-26s|%-26s|%-26s|%-18s|\n";
    private final String TenTapTin = "DanhSachSanPham.txt";
    private SanPham[] DanhSachSanPham = new SanPham[0];

    public SanPham[] getDanhSachSanPham() {
        return DanhSachSanPham;
    }

    public DanhSachSanPham() throws IOException {
        DocDuLieu();
    }

    @Override
    public void HienThiDanhSach() throws IOException {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách sản phẩm trống !\n");
        } else {
            System.out.println("Danh sách sản phẩm: ");
            thaotac.KeVienDanhSachSanPham();
            System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
            thaotac.KeVienDanhSachSanPham();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachSanPham[i].getTrangThai() == 1) {
                    DanhSachSanPham[i].HienThiThongTinSanPham(1.05);
                    thaotac.KeVienDanhSachSanPham();
                }
            }
            System.out.println("");
        }
    }

    @Override
    public boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException {
        DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
        SoPhanTuThemVao++;
        String MaSanPham = "SP";
        if (SoPhanTuTrongDanhSach + SoPhanTuThemVao < 10) {
            MaSanPham += "0";
        }
        DanhSachSanPham = Arrays.copyOf(DanhSachSanPham, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new SanPham();
        DanhSachSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaSanPham(MaSanPham + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao));
        DanhSachSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setTrangThai(1);
        boolean ThemSanPham = DanhSachSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinSanPham();
        if (ThemSanPham) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
            danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' thêm sản phẩm '" + MaSanPham + String.valueOf(SoPhanTuTrongDanhSach + SoPhanTuThemVao) + " (" + DanhSachSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].getTenSanPham() + ")' vào danh sách",ThaoTac.LayThoiGianHienTai());
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
        DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
        int LuaChon;
        String CapNhat, MaSanPham;
        System.out.print("Nhập mã sản phẩm muốn cập nhật: ");
        MaSanPham = kiemtradulieu.KiemTraSanPhamDaTonTai();
        if (MaSanPham.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaSanPham);
        if (ViTri1 != -1) {
            int ViTri2 = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenSanPham = DanhSachSanPham[ViTri1].getTenSanPham();
            String XacNhan;
            System.out.print("\nXác nhận cập nhật thông tin sản phẩm '" + MaSanPham + " (" + TenSanPham + ")" + "' (y/n): ");
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
                        System.out.print("Nhập tên sản phẩm: ");
                        while (true) {
                            CapNhat = kiemtradulieu.KiemTraNhapTenSanPham();
                            if (CapNhat.equals("-q")) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachSanPham[ViTri1].getTenSanPham())) {
                                System.out.print("Tên sản phẩm mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật tên của sản phẩm '" + MaSanPham + " (" + TenSanPham + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachSanPham[ViTri1].setTenSanPham(CapNhat);
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.print("Nhập giá sản phẩm: ");
                        while (true) {
                            CapNhat = String.valueOf(kiemtradulieu.KiemTraNhapGiaSanPham());
                            if (Double.parseDouble(CapNhat) == -1) {
                                return false;
                            }
                            if (CapNhat.equals(DanhSachSanPham[ViTri1].getGiaSanPham())) {
                                System.out.print("Giá sản phẩm mới không có gì thay đổi ! Mời nhập lại: ");
                            } else {
                                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri2].getHoTen() + ")' cập nhật giá của sản phẩm '" + MaSanPham + " (" + TenSanPham + ")'",ThaoTac.LayThoiGianHienTai());
                                DanhSachSanPham[ViTri1].setGiaSanPham(Double.parseDouble(CapNhat));
                                break;
                            }
                        }
                        break;
                    }
                }
                LuuDuLieu();
                System.out.println("\nCập nhật thành công !\n");
                System.out.println("Thông tin sản phẩm '" + MaSanPham + "' sau khi cập nhật: ");
                thaotac.KeVienDanhSachSanPham();
                System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                thaotac.KeVienDanhSachSanPham();
                DanhSachSanPham[ViTri1].HienThiThongTinSanPham(1.05);
                thaotac.KeVienDanhSachSanPham();
                System.out.println("");
            } else {
                System.out.println("\nHủy bỏ cập nhật thông tin sản phẩm '" + MaSanPham + " (" + TenSanPham + ")" + "' !\n");
            }
        } else {
            System.out.println("\nKhông tìm thấy sản phẩm '" + MaSanPham + "' !\n");
        }
        return true;
    }

    @Override
    public boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException {
        DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
        String MaSanPham;
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        MaSanPham = kiemtradulieu.KiemTraSanPhamDaTonTai();
        if (MaSanPham.equals("-q")) {
            return false;
        }
        int ViTri1 = KiemTraPhanTuTonTai(MaSanPham);
        if (ViTri1 != -1) {
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
            String TenSanPham = DanhSachSanPham[ViTri1].getTenSanPham();
            String XacNhan;
            System.out.print("\nXác nhận xóa sản phẩm '" + MaSanPham + " (" + TenSanPham + ")" + "' (y/n): ");
            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
            if (XacNhan.equalsIgnoreCase("y")) {
                DanhSachSanPham[ViTri1].setTrangThai(0);
                danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen() + ")' xóa sản phẩm '" + MaSanPham + " (" + TenSanPham + ")' khỏi danh sách",ThaoTac.LayThoiGianHienTai());
                LuuDuLieu();
                System.out.println("\nXóa thành công !\n");
            } else {
                System.out.println("\nHủy bỏ xóa sản phẩm '" + MaSanPham + " (" + TenSanPham + ")" + "' !\n");
            }

        } else {
            System.out.println("\nKhông tìm thấy sản phẩm '" + MaSanPham + "' !\n");
        }
        return true;
    }

    @Override
    public boolean TimKiemPhanTu() throws IOException {
        int LuaChon1;
        String KetQua = "";
        System.out.println("1.Tìm kiếm theo mã   2.Tìm kiếm theo tên   3.Tìm kiếm theo mức giá   4.Tìm theo nhà cung cấp   5.Tìm kiếm theo thương hiệu");
        System.out.print("Nhập lựa chọn: ");
        while (true) {
            LuaChon1 = kiemtradulieu.KiemTraNhapLuaChon();
            if (LuaChon1 == -1) {
                return false;
            }
            if (LuaChon1 > 0 && LuaChon1 < 6) {
                break;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
        System.out.println("");
        switch (LuaChon1) {
            case 1: {
                String MaSanPham;
                System.out.println("Tìm kiếm theo mã:");
                System.out.print("Nhập mã sản phẩm muốn tìm: ");
                MaSanPham = kiemtradulieu.KiemTraNhapMaSanPham();
                if (MaSanPham.equals("-q")) {
                    return false;
                }
                int ViTri1 = KiemTraPhanTuTonTai(MaSanPham);
                if (ViTri1 != -1) {
                    System.out.println("\nThông tin sản phẩm muốn tìm: ");
                    thaotac.KeVienDanhSachSanPham();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                    thaotac.KeVienDanhSachSanPham();
                    DanhSachSanPham[ViTri1].HienThiThongTinSanPham(1.05);
                    thaotac.KeVienDanhSachSanPham();
                    System.out.println("");
                } else {
                    System.out.println("\nKhông tìm thấy sản phẩm '" + MaSanPham + "' !\n");
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
                SanPham[] TimKiemSanPham = new SanPham[DemSoPhanTuKhaDung()];
                switch (LuaChon2) {
                    case 1: {
                        String TenGanDung;
                        System.out.println("Tìm kiếm gần đúng:");
                        System.out.print("Nhập tên sản phẩm muốn tìm: ");
                        TenGanDung = kiemtradulieu.KiemTraNhapTenGanDung();
                        if (TenGanDung.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && DanhSachSanPham[i].getTenSanPham().contains(TenGanDung)) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm '" + TenGanDung + "' !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        String TenSanPham;
                        System.out.println("Tìm kiếm đúng:");
                        System.out.print("Nhập tên sản phẩm muốn tìm: ");
                        TenSanPham = kiemtradulieu.KiemTraNhapTenSanPham();
                        if (TenSanPham.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && TenSanPham.equalsIgnoreCase(DanhSachSanPham[i].getTenSanPham())) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm '" + TenSanPham + "' !\n");
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
                    System.out.println("\nThông tin sản phẩm muốn tìm: ");
                    thaotac.KeVienDanhSachSanPham();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                    thaotac.KeVienDanhSachSanPham();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemSanPham[i].HienThiThongTinSanPham(1.05);
                        thaotac.KeVienDanhSachSanPham();
                    }
                    System.out.println("");
                }
                break;
            }
            case 3: {
                int Dem = 0, LuaChon2;
                SanPham[] TimKiemSanPham = new SanPham[DemSoPhanTuKhaDung()];
                System.out.println("Tìm kiếm theo mức giá:");
                System.out.println("1.Dưới 10.000.000 VNĐ   2.Từ 10.000.000 VNĐ đến 20.000.000 VNĐ   3.Trên 20.000.000 VNĐ   4.Tùy chọn");
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
                        System.out.println("Tìm kiếm theo mức giá dưới 10.000.000 VNĐ:");
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && (DanhSachSanPham[i].getGiaSanPham() * 1.05) <= 10000000) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm có mức giá dưới 10.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Tìm kiếm theo mức giá từ 10.000.000 VNĐ đến 20.000.000 VNĐ:");
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && (DanhSachSanPham[i].getGiaSanPham() * 1.05) >= 10000000 && (DanhSachSanPham[i].getGiaSanPham() * 1.05) <= 20000000) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm có mức giá từ 10.000.000 VNĐ đến 20.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Tìm kiếm theo mức giá trên 20.000.000 VNĐ:");
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && (DanhSachSanPham[i].getGiaSanPham() * 1.05) >= 20000000) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm có mức giá trên 20.000.000 VNĐ !\n");
                        } else {
                            if (Dem < 10) {
                                KetQua += "0";
                            }
                        }
                        break;
                    }
                    case 4: {
                        double GiaBatDau, GiaKetThuc;
                        System.out.println("Tìm kiếm theo mức giá tùy chọn:");
                        System.out.print("Nhập mức giá khởi đầu: ");
                        GiaBatDau = kiemtradulieu.KiemTraNhapMucGia();
                        if (GiaBatDau == -1) {
                            return false;
                        }
                        System.out.print("Nhập mức giá kết thúc: ");
                        while (true) {
                            GiaKetThuc = kiemtradulieu.KiemTraNhapMucGia();
                            if (GiaKetThuc == -1) {
                                return false;
                            }
                            if (GiaKetThuc > GiaBatDau) {
                                break;
                            } else {
                                System.out.print("Mức giá kết thúc phải lớn hơn mức giá khởi đầu ! Mời nhập lại: ");
                            }
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && (DanhSachSanPham[i].getGiaSanPham() * 1.05) >= GiaBatDau && (DanhSachSanPham[i].getGiaSanPham() * 1.05) <= GiaKetThuc) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        if (Dem == 0) {
                            System.out.println("\nKhông tìm thấy sản phẩm có mức giá từ " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaBatDau)) + " đến " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(GiaKetThuc)) + " !\n");
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
                    System.out.println("\nThông tin sản phẩm muốn tìm: ");
                    thaotac.KeVienDanhSachSanPham();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                    thaotac.KeVienDanhSachSanPham();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemSanPham[i].HienThiThongTinSanPham(1.05);
                        thaotac.KeVienDanhSachSanPham();
                    }
                    System.out.println("");
                }
                break;
            }
            case 4: {
                DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
                int Dem = 0;
                SanPham[] TimKiemSanPham = new SanPham[DemSoPhanTuKhaDung()];
                String TenNhaCungCap;
                System.out.println("Tìm kiếm theo nhà cung cấp");
                System.out.print("Nhập tên nhà cung cấp của sản phẩm muốn tìm: ");
                while (true) {
                    TenNhaCungCap = kiemtradulieu.KiemTraNhapTenNhaCungCap();
                    if (TenNhaCungCap.equals("-q")) {
                        return false;
                    }
                    int ViTri = danhsachnhacungcap.TruyXuatMaNhaCungCap(TenNhaCungCap);
                    if (ViTri != -1) {
                        String MaNhaCungCap = danhsachnhacungcap.getDanhSachNhaCungCap()[ViTri].getMaNhaCungCap();
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && MaNhaCungCap.equals(DanhSachSanPham[i].getMaNhaCungCap())) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        break;
                    } else {
                        System.out.print("\nKhông tìm thấy nhà cung cấp '" + TenNhaCungCap + "' ! Mời nhập lại: ");
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy sản phẩm của nhà cung cấp '" + TenNhaCungCap + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin sản phẩm muốn tìm: ");
                    thaotac.KeVienDanhSachSanPham();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                    thaotac.KeVienDanhSachSanPham();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemSanPham[i].HienThiThongTinSanPham(1.05);
                        thaotac.KeVienDanhSachSanPham();
                    }
                    System.out.println("");
                }
                break;
            }

            case 5: {
                DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
                int Dem = 0;
                SanPham[] TimKiemSanPham = new SanPham[DemSoPhanTuKhaDung()];
                String TenThuongHieu;
                System.out.println("Tìm kiếm theo thương hiệu");
                System.out.print("Nhập tên thương hiệu của sản phẩm muốn tìm: ");
                while (true) {
                    TenThuongHieu = kiemtradulieu.KiemTraNhapTenThuongHieu();
                    if (TenThuongHieu.equals("-q")) {
                        return false;
                    }
                    int ViTri = danhsachthuonghieu.TruyXuatMaThuongHieu(TenThuongHieu);
                    if (ViTri != -1) {
                        String MaThuongHieu = danhsachthuonghieu.getDanhSachThuongHieu()[ViTri].getMaThuongHieu();
                        if (TenThuongHieu.equals("-q")) {
                            return false;
                        }
                        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                            if (DanhSachSanPham[i].getTrangThai() == 1 && MaThuongHieu.equals(DanhSachSanPham[i].getMaThuongHieu())) {
                                TimKiemSanPham[Dem] = DanhSachSanPham[i];
                                Dem++;
                            }
                        }
                        break;
                    } else {
                        System.out.println("\nKhông tìm thấy thương hiệu '" + TenThuongHieu + "' ! Mời nhập lại: ");
                    }
                }
                if (Dem == 0) {
                    System.out.println("\nKhông tìm thấy sản phẩm của thương hiệu '" + TenThuongHieu + "' !\n");
                } else {
                    if (Dem < 10) {
                        KetQua += "0";
                    }
                    System.out.println("\nKết quả tìm kiếm: " + KetQua + Dem);
                    System.out.println("\nThông tin sản phẩm muốn tìm: ");
                    thaotac.KeVienDanhSachSanPham();
                    System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
                    thaotac.KeVienDanhSachSanPham();
                    for (int i = 0; i < Dem; i++) {
                        TimKiemSanPham[i].HienThiThongTinSanPham(1.05);
                        thaotac.KeVienDanhSachSanPham();
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
        DanhSachSanPham = Arrays.copyOf(DanhSachSanPham, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachSanPham[i] = new SanPham();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachSanPham[i].setMaSanPham(scanfile.next());
                DanhSachSanPham[i].setTenSanPham(scanfile.next());
                DanhSachSanPham[i].setMaNhaCungCap(scanfile.next());
                DanhSachSanPham[i].setMaThuongHieu(scanfile.next());
                DanhSachSanPham[i].setGiaSanPham(scanfile.nextDouble());
                DanhSachSanPham[i].setSoLuong(scanfile.nextInt());
                DanhSachSanPham[i++].setTrangThai(scanfile.nextInt());
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
            String DuLieu = DanhSachSanPham[i].getMaSanPham() + ";" + DanhSachSanPham[i].getTenSanPham() + ";" + DanhSachSanPham[i].getMaNhaCungCap() + ";" + DanhSachSanPham[i].getMaThuongHieu() + ";" + DanhSachSanPham[i].getGiaSanPham() + ";" + DanhSachSanPham[i].getSoLuong() + ";" + DanhSachSanPham[i].getTrangThai();

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
    public int KiemTraPhanTuTonTai(String Ma
    ) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachSanPham[i].getTrangThai() == 1 && Ma.equals(DanhSachSanPham[i].getMaSanPham())) {
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
            if (DanhSachSanPham[i].getTrangThai() == 0) {
                SoDongDaXoa++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoDongDaXoa;
    }

    public int DemSoSanPhamConHang() {
        int SoSanPhamHetHang = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachSanPham[i].getTrangThai() == 1 && DanhSachSanPham[i].getSoLuong() == 0) {
                SoSanPhamHetHang++;
            }
        }
        return SoPhanTuTrongDanhSach + SoPhanTuThemVao - SoSanPhamHetHang;
    }

    public int DemSoSanPhamTheoNhaCungCap(String MaNhaCungCap) {
        int Dem = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachSanPham[i].getTrangThai() == 1 && MaNhaCungCap.equals(DanhSachSanPham[i].getMaNhaCungCap())) {
                Dem++;
            }
        }
        return Dem;
    }

    public void HienThiSanPhamTheoNhaCungCap(String MaNhaCungCap) throws IOException {
        if (DemSoPhanTuKhaDung() == 0) {
            System.out.println("Danh sách sản phẩm trống !\n");
        } else {
            System.out.println("Danh sách sản phẩm: ");
            thaotac.KeVienDanhSachSanPham();
            System.out.format(DinhDang, "     MÃ SẢN PHẨM", "     TÊN SẢN PHẨM", "     NHÀ CUNG CẤP", "     THƯƠNG HIỆU", "     GIÁ SẢN PHẨM", "     SỐ LƯỢNG");
            thaotac.KeVienDanhSachSanPham();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (DanhSachSanPham[i].getTrangThai() == 1 && MaNhaCungCap.equals(DanhSachSanPham[i].getMaNhaCungCap())) {
                    DanhSachSanPham[i].HienThiThongTinSanPham(1.0);
                    thaotac.KeVienDanhSachSanPham();
                }
            }
            System.out.println("");
        }
    }

    public boolean KiemTraNhaCungCapDaTonTai(String MaNhaCungCap) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachSanPham[i].getTrangThai() == 1 && MaNhaCungCap.equals(DanhSachSanPham[i].getMaNhaCungCap())) {
                return true;
            }
        }
        return false;
    }

    public boolean KiemTraTenSanPhamDaTonTai(String TenSanPham) {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachSanPham[i].getTrangThai() == 1 && TenSanPham.equals(DanhSachSanPham[i].getTenSanPham())) {
                return true;
            }
        }
        return false;
    }

    public void HienThiMenuCapNhat() {
        System.out.println("\nCập nhật thông tin sản phẩm:");
        System.out.println("1.Tên sản phẩm");
        System.out.println("2.Giá sản phẩm");
    }

    public void HienThiMenu() {
        System.out.println("QUẢN LÝ SẢN PHẨM:");
        System.out.println("1.Thêm sản phẩm");
        System.out.println("2.Cập nhật thông tin sản phẩm");
        System.out.println("3.Xóa sản phẩm");
        System.out.println("4.Tìm kiếm sản phẩm");
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
                        System.out.println("Thêm sản phẩm:");
                        boolean ThemSanPham = ThemPhanTu(MaNhanVienDangNhap);
                        if (!ThemSanPham) {
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
                        System.out.println("Danh sách sản phẩm trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Cập nhật thông tin sản phẩm:");
                            boolean CapNhatThongTinSanPham = CapNhatThongTinPhanTu(MaNhanVienDangNhap);
                            if (!CapNhatThongTinSanPham) {
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
                        System.out.println("Danh sách sản phẩm trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Xóa sản phẩm:");
                            boolean XoaSanPham = XoaPhanTu(MaNhanVienDangNhap);
                            if (!XoaSanPham) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục xóa (y/n): ");
                            XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan.equals("n")) {
                                break;
                            }
                            if (DemSoPhanTuKhaDung() == 0) {
                                System.out.println("Danh sách sản phẩm trống !");
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
                        System.out.println("Danh sách sản phẩm trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm sản phẩm:");
                            boolean TimKiemSanPham = TimKiemPhanTu();
                            if (!TimKiemSanPham) {
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
