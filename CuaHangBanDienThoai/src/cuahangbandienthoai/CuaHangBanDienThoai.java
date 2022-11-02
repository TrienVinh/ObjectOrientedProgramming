package cuahangbandienthoai;

import java.io.IOException;

public class CuaHangBanDienThoai {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private String MaNhanVienDangNhap;

    public boolean DangNhap() throws IOException {
        DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
        DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
        DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
        String MatKhau;
        System.out.print("Nhập mã nhân viên: ");
        MaNhanVienDangNhap = kiemtradulieu.KiemTraMaNhanVienDangNhap();
        if (MaNhanVienDangNhap.equals("-q")) {
            return false;
        }
        int ViTri = danhsachtaikhoan.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
        System.out.print("Nhập mật khẩu: ");
        MatKhau = kiemtradulieu.KiemTraMatKhauDangNhap(ViTri);
        if (MatKhau.equals("-q")) {
            return false;
        }
        String PhanQuyen = danhsachtaikhoan.getDanhSachTaiKhoan()[ViTri].getPhanQuyen();
        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng nhập vào hệ thống",ThaoTac.LayThoiGianHienTai());
        if (PhanQuyen.equals("Nhân viên bán hàng")) {
            System.out.println("\nXin chào, nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng nhập với quyền nhân viên bán hàng !\n");
            MenuNhanVienBanHang();
        } else {
            System.out.println("\nXin chào, nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng nhập với quyền quản trị viên !\n");
            MenuQuanTriVien();
        }
        return true;
    }

    public void HienThiMenu() {
        System.out.println("===== CỬA HÀNG BÁN ĐIỆN THOẠI =====");
        System.out.println("1.Đăng nhập");
        System.out.println("2.Thoát");
    }

    public void Menu() throws IOException {
        int LuaChon;
        String XacNhan = "n";
        do {
            HienThiMenu();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon == 1 || LuaChon == 2) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                }
            }
            switch (LuaChon) {
                case 1: {
                    System.out.println("ĐĂNG NHẬP:");
                    boolean DangNhap = DangNhap();
                    if (!DangNhap) {
                        System.out.println("\nĐã thoát !\n");
                        thaotac.TiepTuc();
                        System.out.println("");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Xác nhân thoát (y/n): ");
                    XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                    if (XacNhan.equals("y")) {
                        System.out.println("\nĐã thoát !\n");
                    } else {
                        System.out.println("");
                    }
                    break;
                }
            }
        } while (XacNhan.equals("n"));
    }

    public void HienThiMenuNhanVienBanHang() {
        System.out.println("=== NHÂN VIÊN BÁN HÀNG ===");
        System.out.println("1.Quản lý khách hàng.");
        System.out.println("2.Xem danh sách sản phẩm.");
        System.out.println("3.Tìm kiếm sản phẩm.");
        System.out.println("4.Xem danh sách hóa đơn bán hàng.");
        System.out.println("5.Lập hóa đơn bán hàng.");
        System.out.println("6.Tìm kiếm hóa đơn bán hàng.");
        System.out.println("7.Xem danh sách phiếu bảo hành.");
        System.out.println("8.Lập phiếu bảo hành.");
        System.out.println("9.Tìm kiếm phiếu bảo hành.");
        System.out.println("10.Xem danh sách khuyến mãi.");
        System.out.println("11.Tìm kiếm khuyến mãi.");
        System.out.println("12.Đổi mật khẩu.");
        System.out.println("13.Đăng xuất.");
    }

    public void MenuNhanVienBanHang() throws IOException {
        DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
        int LuaChon;
        String XacNhan1 = "n";
        do {
            HienThiMenuNhanVienBanHang();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon > 0 && LuaChon < 14) {
                    break;
                } else {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                }
            }
            switch (LuaChon) {
                case 1: {
                    DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
                    System.out.println("");
                    danhsachkhachhang.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 2: {
                    DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
                    System.out.println("");
                    danhsachsanpham.HienThiDanhSach();
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 3: {
                    DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
                    System.out.println("");
                    danhsachsanpham.HienThiDanhSach();
                    String XacNhan2;
                    if (danhsachsanpham.DemSoPhanTuKhaDung() == 0) {
                        System.out.println("Danh sách sản phẩm trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm sản phẩm:");
                            boolean TimKiemSanPham = danhsachsanpham.TimKiemPhanTu();
                            if (!TimKiemSanPham) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan2.equals("n")) {
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
                case 4: {
                    DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                    System.out.println("");
                    danhsachhoadonbanhang.HienThiDanhSach();
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 5: {
                    DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                    System.out.println("");
                    danhsachhoadonbanhang.HienThiDanhSach();
                    String XacNhan2;
                    while (true) {
                        System.out.println("Lập hóa đơn bán hàng:");
                        boolean LapHoaDonBanHang = danhsachhoadonbanhang.ThemPhanTu(MaNhanVienDangNhap);
                        if (!LapHoaDonBanHang) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục lập hóa đơn bán hàng (y/n): ");
                        XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                        if (XacNhan2.equals("n")) {
                            break;
                        }
                        System.out.println("");
                    }
                    System.out.println("\nĐã thoát !\n");

                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 6: {
                    DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                    System.out.println("");
                    danhsachhoadonbanhang.HienThiDanhSach();
                    String XacNhan2;
                    if (danhsachhoadonbanhang.DemSoPhanTu() == 0) {
                        System.out.println("Danh sách hóa đơn bán hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Tìm kiếm hóa đơn bán hàng:");
                            boolean TimKiemHoaDonBanHang = danhsachhoadonbanhang.TimKiemPhanTu();
                            if (!TimKiemHoaDonBanHang) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan2.equals("n")) {
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
                case 7: {
                    DanhSachPhieuBaoHanh danhsachphieubaohanh = new DanhSachPhieuBaoHanh();
                    System.out.println("");
                    danhsachphieubaohanh.HienThiDanhSach();
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 8: {
                    DanhSachPhieuBaoHanh danhsachphieubaohanh = new DanhSachPhieuBaoHanh();
                    System.out.println("");
                    danhsachphieubaohanh.HienThiDanhSach();
                    String XacNhan2;
                    while (true) {
                        System.out.println("Lập hóa đơn bán hàng:");
                        boolean LapPhieuBaoHanh = danhsachphieubaohanh.ThemPhanTu(MaNhanVienDangNhap);
                        if (!LapPhieuBaoHanh) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục lập phiếu bảo hành (y/n): ");
                        XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                        if (XacNhan2.equals("n")) {
                            break;
                        }
                        System.out.println("");
                    }
                    System.out.println("\nĐã thoát !\n");
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 9: {
                    DanhSachPhieuBaoHanh danhsachphieubaohanh = new DanhSachPhieuBaoHanh();
                    System.out.println("");
                    danhsachphieubaohanh.HienThiDanhSach();
                    String XacNhan2;
                    if (danhsachphieubaohanh.DemSoPhanTu() == 0) {
                        System.out.println("Danh sách phiếu bảo hành trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Lập phiếu bảo hành:");
                            boolean TimKiemPhieuBaoHanh = danhsachphieubaohanh.TimKiemPhanTu();
                            if (!TimKiemPhieuBaoHanh) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan2.equals("n")) {
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
                case 10: {
                    DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
                    System.out.println("");
                    danhsachkhuyenmai.HienThiDanhSach();
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 11: {
                    DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
                    System.out.println("");
                    danhsachkhuyenmai.HienThiDanhSach();
                    String XacNhan2;
                    if (danhsachkhuyenmai.DemSoPhanTu() == 0) {
                        System.out.println("Danh sách hóa đơn bán hàng trống !\n");
                    } else {
                        while (true) {
                            System.out.println("Lập hóa đơn bán hàng:");
                            boolean TimKiemKhuyenMai = danhsachkhuyenmai.TimKiemPhanTu();
                            if (!TimKiemKhuyenMai) {
                                break;
                            }
                            System.out.print("Xác nhận tiếp tục tìm kiếm (y/n): ");
                            XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                            if (XacNhan2.equals("n")) {
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
                case 12: {
                    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
                    DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
                    System.out.println("\nĐổi mật khẩu:");
                    boolean DoiMatKhau = danhsachtaikhoan.DoiMatKhau(MaNhanVienDangNhap);
                    if (DoiMatKhau) {
                        int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đổi mật khẩu",ThaoTac.LayThoiGianHienTai());
                    } else {
                        System.out.println("\nĐã thoát !\n");
                    }
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;

                }
                case 13: {
                    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
                    System.out.println("");
                    System.out.print("Xác nhận đăng xuất (y/n): ");
                    XacNhan1 = kiemtradulieu.KiemTraNhapXacNhan();
                    System.out.println("");
                    if (XacNhan1.equals("y")) {
                        int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng xuất khỏi hệ thống",ThaoTac.LayThoiGianHienTai());
                        System.out.println("Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng xuất !\n");
                        break;
                    }
                }
            }
        } while (XacNhan1.equals("n"));
    }

    public void HienThiMenuQuanTriVien() {
        System.out.println("=== QUẢN TRỊ VIÊN ===");
        System.out.println("1.Quản lý khách hàng.");
        System.out.println("2.Quản lý nhân viên.");
        System.out.println("3.Quản lý tài khoản.");
        System.out.println("4.Quản lý nhà cung cấp.");
        System.out.println("5.Quản lý thương hiệu");
        System.out.println("6.Quản lý sản phẩm.");
        System.out.println("7.Quản lý hóa đơn nhập hàng.");
        System.out.println("8.Quản lý hóa đơn bán hàng.");
        System.out.println("9.Quản lý khuyến mãi.");
        System.out.println("10.Quản lý phiếu bảo hành.");
        System.out.println("11.Quản lý lịch sử hoạt động.");
        System.out.println("12.Thống kê doanh thu.");
        System.out.println("13.Đổi mật khẩu.");
        System.out.println("14.Đăng xuất.");
    }

    public void MenuQuanTriVien() throws IOException {
        int LuaChon;
        String XacNhan = "n";
        do {
            HienThiMenuQuanTriVien();
            System.out.print("Nhập lựa chọn: ");
            while (true) {
                LuaChon = kiemtradulieu.KiemTraNhapLuaChon();
                if (LuaChon > 0 && LuaChon < 15) {
                    break;
                } else {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                }
            }
            switch (LuaChon) {
                case 1: {
                    DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
                    System.out.println("");
                    danhsachkhachhang.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 2: {
                    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
                    System.out.println("");
                    danhsachnhanvien.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 3: {
                    DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
                    System.out.println("");
                    danhsachtaikhoan.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 4: {
                    DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
                    System.out.println("");
                    danhsachnhacungcap.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 5: {
                    DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
                    System.out.println("");
                    danhsachthuonghieu.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 6: {
                    DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
                    System.out.println("");
                    danhsachsanpham.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 7: {
                    DanhSachHoaDonNhapHang danhsachhoadonnhaphang = new DanhSachHoaDonNhapHang();
                    System.out.println("");
                    danhsachhoadonnhaphang.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 8: {
                    DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                    System.out.println("");
                    danhsachhoadonbanhang.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 9: {
                    DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
                    System.out.println("");
                    danhsachkhuyenmai.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 10: {
                    DanhSachPhieuBaoHanh danhsachphieubaohanh = new DanhSachPhieuBaoHanh();
                    System.out.println("");
                    danhsachphieubaohanh.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 11: {
                    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
                    System.out.println("");
                    danhsachlichsuhoatdong.Menu(MaNhanVienDangNhap);
                    break;
                }
                case 12: {
                    DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
                    String XacNhan2;
                    System.out.println("");
                    while (true) {
                        boolean ThongKeDoanhThu = danhsachhoadonbanhang.ThongKeDoanhThu();
                        if (!ThongKeDoanhThu) {
                            break;
                        }
                        System.out.print("Xác nhận tiếp tục thống kê (y/n): ");
                        XacNhan2 = kiemtradulieu.KiemTraNhapXacNhan();
                        if (XacNhan2.equals("n")) {
                            break;
                        }
                        System.out.println("");
                    }
                    System.out.println("\nĐã thoát !\n");
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 13: {
                    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
                    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
                    DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
                    System.out.println("\nĐổi mật khẩu:");
                    boolean DoiMatKhau = danhsachtaikhoan.DoiMatKhau(MaNhanVienDangNhap);
                    if (DoiMatKhau) {
                        int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đổi mật khẩu",ThaoTac.LayThoiGianHienTai());
                    } else {
                        System.out.println("\nĐã thoát !\n");
                    }
                    thaotac.TiepTuc();
                    System.out.println("");
                    break;
                }
                case 14: {
                    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
                    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
                    System.out.println("");
                    System.out.print("Xác nhận đăng xuất (y/n): ");
                    XacNhan = kiemtradulieu.KiemTraNhapXacNhan();
                    System.out.println("");
                    if (XacNhan.equals("y")) {
                        int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVienDangNhap);
                        String TenNhanVien = danhsachnhanvien.getDanhSachNhanVien()[ViTri].getHoTen();
                        danhsachlichsuhoatdong.GhiHoatDong(MaNhanVienDangNhap, "Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng xuất khỏi hệ thống",ThaoTac.LayThoiGianHienTai());
                        System.out.println("Nhân viên '" + MaNhanVienDangNhap + " (" + TenNhanVien + ")' đăng xuất !\n");
                        break;
                    }
                }
            }
        } while (XacNhan.equals("n"));
    }

}
