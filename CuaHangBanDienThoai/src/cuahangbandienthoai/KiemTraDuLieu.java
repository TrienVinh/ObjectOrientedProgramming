package cuahangbandienthoai;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class KiemTraDuLieu {

    Scanner scanner = new Scanner(System.in);
    ThaoTac thaotac = new ThaoTac();
    static Calendar calendar = Calendar.getInstance();
    private final int NgayHienTai = calendar.get(Calendar.DATE);
    private final int ThangHienTai = calendar.get(Calendar.MONTH) + 1;
    private final int NamHienTai = calendar.get(Calendar.YEAR);

    public String KiemTraHoaDonBanHangDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của hóa đơn bán hàng
        DanhSachHoaDonBanHang danhsachhoadonbanhang = new DanhSachHoaDonBanHang();
        String MaHoaDonBanHang;
        while (true) {
            MaHoaDonBanHang = KiemTraNhapMaHoaDonBanHang();
            if (MaHoaDonBanHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachhoadonbanhang.KiemTraPhanTuTonTai(MaHoaDonBanHang);
            if (ViTri != -1) {
                return MaHoaDonBanHang;
            } else {
                System.out.print("Không tìm thấy mã hóa đơn bán hàng '" + MaHoaDonBanHang + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraIMEIThuocHoaDon(String MaPhieuBaoHanh, String MaHoaDon, String MaSanPham) throws IOException {  //Hàm kiểm tra IMEI thuộc hóa đơn
        DanhSachChiTietPhieuBaoHanh danhsachchitietphieubaohanh = new DanhSachChiTietPhieuBaoHanh();
        DanhSachChiTietSanPham danhsachchitietsanpham = new DanhSachChiTietSanPham();
        String IMEI;
        while (true) {
            IMEI = KiemTraNhapIMEI();
            if (IMEI.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (danhsachchitietsanpham.KiemTraSanPhamThuocHoaDon(MaHoaDon, MaSanPham)) {
                if (danhsachchitietsanpham.KiemTraIMEIThuocSanPham(IMEI, MaSanPham)) {
                    if (!danhsachchitietphieubaohanh.KiemTraIMEIDaTonTai(MaPhieuBaoHanh, MaSanPham, IMEI)) {
                        return IMEI;
                    } else {
                        System.out.print("IMEI này đã có trong phiếu bảo hành '" + MaPhieuBaoHanh + "' ! Mời nhập lại: ");
                    }
                } else {
                    System.out.print("IMEI này không thuộc sản phẩm '" + MaSanPham + "' ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Sản phẩm này không thuộc hóa đơn '" + MaHoaDon + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraKhachHangDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của khách hàng
        DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
        String MaKhachHang;
        while (true) {
            MaKhachHang = KiemTraNhapMaKhachHang();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachkhachhang.KiemTraPhanTuTonTai(MaKhachHang);
            if (ViTri != -1) {
                return MaKhachHang;
            } else {
                System.out.print("Không tìm thấy khách hàng '" + MaKhachHang + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraKhuyenMaiDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của khuyến mãi
        DanhSachKhuyenMai danhsachkhuyenmai = new DanhSachKhuyenMai();
        String MaKhuyenMai;
        while (true) {
            MaKhuyenMai = KiemTraNhapMaKhuyenMai();
            if (MaKhuyenMai.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachkhuyenmai.KiemTraPhanTuTonTai(MaKhuyenMai);
            if (ViTri != -1) {
                return MaKhuyenMai;
            } else {
                System.out.print("Không tìm thấy khuyến mãi '" + MaKhuyenMai + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraMaNhanVienDangNhap() throws IOException {    //Hàm kiểm tra mã nhân viên khi đăng nhập
        DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
        String MaNhanVien;
        while (true) {
            MaNhanVien = KiemTraNhapMaNhanVien();
            if (MaNhanVien.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachtaikhoan.KiemTraPhanTuTonTai(MaNhanVien);
            if (ViTri != -1) {
                if (danhsachtaikhoan.getDanhSachTaiKhoan()[ViTri].getTinhTrang().equals("Bình thường")) {
                    return MaNhanVien;
                } else {
                    System.out.println("\nTài khoản này đã bị khóa. Liên hệ quản trị viên để mở khóa !");
                    return "-q";
                }
            } else {
                System.out.print("Không tìm thấy tài khoản của nhân viên '" + MaNhanVien + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraMatKhauDangNhap(int ViTri) throws IOException {    //Hàm kiểm tra mật khẩu khi đăng nhập
        DanhSachTaiKhoan danhsachtaikhoan = new DanhSachTaiKhoan();
        String MatKhau;
        while (true) {
            MatKhau = scanner.nextLine();
            if (MatKhau.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (thaotac.MaHoaMatKhau(MatKhau).equals(danhsachtaikhoan.getDanhSachTaiKhoan()[ViTri].getMatKhau())) {
                return MatKhau;
            } else {
                System.out.print("Mật khẩu không chính xác ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhaCungCapDaTonTai() throws IOException {  //Hàm kiểm tra sự tồn tại của nhà cung cấp
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        String MaNhaCungCap;
        while (true) {
            MaNhaCungCap = KiemTraNhapMaNhaCungCap();
            if (MaNhaCungCap.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachnhacungcap.KiemTraPhanTuTonTai(MaNhaCungCap);
            if (ViTri != -1) {
                return MaNhaCungCap;
            } else {
                System.out.print("Không tìm thấy nhà cung cấp '" + MaNhaCungCap + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhanVienDaTonTai() throws IOException {    //Hàm kiểm tra sự tồn tại của nhân viên
        DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
        String MaNhanVien;
        while (true) {
            MaNhanVien = KiemTraNhapMaNhanVien();
            if (MaNhanVien.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachnhanvien.KiemTraPhanTuTonTai(MaNhanVien);
            if (ViTri != -1) {
                return MaNhanVien;
            } else {
                System.out.print("Không tìm thấy nhân viên '" + MaNhanVien + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapDiaChi() { //Hàm kiểm tra nhập địa chỉ
        String DiaChi;
        while (true) {
            DiaChi = scanner.nextLine();
            DiaChi = thaotac.XoaKhoangTrangThua(DiaChi);
            if (DiaChi.equalsIgnoreCase("-q")) {
                return "-q";
            }
            DiaChi = DiaChi.toLowerCase();
            if (DiaChi.contains("phường") && DiaChi.contains("quận") && String.valueOf(DiaChi.charAt(0)).matches("[0-9]") && thaotac.DemKhoangTrang(DiaChi) > 5) {
                return thaotac.ChuanHoaDiaChi(DiaChi);
            } else {
                System.out.print("Địa chỉ không hợp lệ ! Mời nhập lại: ");
            }
        }
    }

    public double KiemTraNhapDieuKien() {   //Hàm kiểm tra nhập điều kiện khuyến mãi
        double DieuKien;
        while (true) {
            try {
                DieuKien = Double.parseDouble(scanner.nextLine());
                if (DieuKien == -1) {
                    return -1;
                }
                if (DieuKien == 0) {
                    return 0;
                }
                if (DieuKien > 0) {
                    return DieuKien;
                } else {
                    System.out.print("Điều kiện không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Điều kiện không đúng định dạng ! Mời nhập lại: ");
            }

        }
    }

    public double KiemTraNhapGiaSanPham() { //Hàm kiểm tra nhập giá sản phẩm
        double GiaSanPham;
        while (true) {
            try {
                GiaSanPham = Double.parseDouble(scanner.nextLine());
                if (GiaSanPham == -1) {
                    return -1;
                }
                if (GiaSanPham > 0) {
                    return GiaSanPham;
                } else {
                    System.out.print("Giá sản phẩm không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Giá sản phẩm không đúng định dạng ! Mời nhập lại: ");
            }

        }
    }

    public String KiemTraNhapHoTen() {  //Hàm kiểm tra nhập họ tên
        String HoTen;
        while (true) {
            HoTen = scanner.nextLine();
            if (HoTen.equalsIgnoreCase("-q")) {
                return "-q";
            }
            HoTen = thaotac.XoaKhoangTrangThua(HoTen);
            if (HoTen.matches("[\\pL\\pMn*\\s*]+")) {
                if (thaotac.DemKhoangTrang(HoTen) > 0) {
                    return thaotac.ChuanHoaChuoi1(HoTen);
                } else {
                    System.out.print("Họ tên không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Họ tên không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapIMEI() {   //Hàm kiểm tra nhập IMEI
        String IMEI;
        while (true) {
            IMEI = scanner.nextLine();
            if (IMEI.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (IMEI.matches("[0-9]{15}")) {
                return thaotac.XoaKhoangTrangThua(IMEI);

            } else {
                System.out.print("IMEI không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public int KiemTraNhapLuaChon() {   //Hàm kiểm tra nhập lựa chọn
        int LuaChon;
        while (true) {
            try {
                LuaChon = Integer.parseInt(scanner.nextLine());
                return LuaChon;
            } catch (NumberFormatException exception) {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaPhieuBaoHanh() { //Hàm kiểm tra nhập mã phiếu bảo hành
        String MaPhieuBaoHanh;
        while (true) {
            MaPhieuBaoHanh = scanner.nextLine();
            if (MaPhieuBaoHanh.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaPhieuBaoHanh = MaPhieuBaoHanh.toUpperCase();
            MaPhieuBaoHanh = thaotac.XoaKhoangTrangThua(MaPhieuBaoHanh);
            if (MaPhieuBaoHanh.matches("PBH" + "[0-9]{2}")) {
                return MaPhieuBaoHanh;
            } else {
                System.out.print("Mã bảo hành không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaHoaDonBanHang() {    //Hàm kiểm tra nhập mã hóa đơn bán hàng
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("HDBH" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã hóa đơn bán hàng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaHoaDonNhapHang() {   //Hàm kiểm tra nhập mã hóa đơn nhập hàng
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("HDNH" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã hóa đơn nhập hàng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaKhachHang() {    //Hàm kiểm tra nhập mã khách hàng
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("KH" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã khách hàng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaKhuyenMai() {    //Hàm kiểm tra nhập mã khuyến mãi
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("KM" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã khuyến mãi không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaNhaCungCap() {   //Hàm kiểm tra nhập mã nhà cung cấp
        String MaNhaCungCap;
        while (true) {
            MaNhaCungCap = scanner.nextLine();
            if (MaNhaCungCap.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaNhaCungCap = MaNhaCungCap.toUpperCase();
            MaNhaCungCap = thaotac.XoaKhoangTrangThua(MaNhaCungCap);
            if (MaNhaCungCap.matches("NCC" + "[0-9]{2}")) {
                return MaNhaCungCap;
            } else {
                System.out.print("Mã nhà cung cấp không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaNhanVien() { //Hàm kiểm tra nhập mã nhân viên
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("NV" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã nhân viên không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaSanPham() {  //Hàm kiểm tra nhập mã sản phẩm
        String MaNhaCungCap;
        while (true) {
            MaNhaCungCap = scanner.nextLine();
            if (MaNhaCungCap.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaNhaCungCap = MaNhaCungCap.toUpperCase();
            MaNhaCungCap = thaotac.XoaKhoangTrangThua(MaNhaCungCap);
            if (MaNhaCungCap.matches("SP" + "[0-9]{2}")) {
                return MaNhaCungCap;
            } else {
                System.out.print("Mã sản phẩm không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMaThuongHieu() {   //Hàm kiểm tra nhập mã thương hiệu
        String MaKhachHang;
        while (true) {
            MaKhachHang = scanner.nextLine();
            if (MaKhachHang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MaKhachHang = MaKhachHang.toUpperCase();
            MaKhachHang = thaotac.XoaKhoangTrangThua(MaKhachHang);
            if (MaKhachHang.matches("TH" + "[0-9]{2}")) {
                return MaKhachHang;
            } else {
                System.out.print("Mã thương hiệu không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapMatKhau() {    //Hàm kiểm tra nhập mật khẩu
        String MatKhau;
        while (true) {
            MatKhau = scanner.nextLine();
            if (MatKhau.equalsIgnoreCase("-q")) {
                return "-q";
            }
            MatKhau = thaotac.XoaKhoangTrangThua(MatKhau);
            if (MatKhau.matches("^[A-Za-z0-9]{8,}+$"))  {
                return MatKhau;
            }
            else {
                System.out.println("Mật khẩu không được chứa khoảng trắng, ký tự đặc biệt và phải dài ít nhất 8 ký tự !");
                System.out.print("Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapNoiDung() { //Hàm kiểm tra nhập nội dung bảo hành
        String NoiDung;
        while (true) {
            NoiDung = scanner.nextLine();
            if (NoiDung.equalsIgnoreCase("-q")) {
                return "-q";
            }
            NoiDung = thaotac.XoaKhoangTrangThua(NoiDung);
            if (NoiDung.matches("[\\pL\\pMn*\\s*]+")) {
                if (thaotac.DemKhoangTrang(NoiDung) > 0) {
                    if (NoiDung.equalsIgnoreCase("Bình thường")) {
                        System.out.print("Nội dung bảo hành không hợp lệ ! Mời nhập lại: ");
                    } else {
                        return thaotac.ChuanHoaChuoi2(NoiDung);

                    }
                } else {
                    System.out.print("Nội dung bảo hành không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Nội dung bảo hành không đúng định dạng ! Mời nhập lại: ");

            }
        }
    }

    public String KiemTraNhapTinhTrang() {  //Hàm kiểm tra nhập tình trạng bảo hành
        String TinhTrang;
        while (true) {
            TinhTrang = scanner.nextLine();
            if (TinhTrang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            TinhTrang = thaotac.XoaKhoangTrangThua(TinhTrang);
            if (TinhTrang.matches("[\\pL\\pMn*\\s*]+")) {
                if (thaotac.DemKhoangTrang(TinhTrang) > 0) {
                    return thaotac.ChuanHoaChuoi2(TinhTrang);
                } else {
                    System.out.print("Tình trạng bảo hành không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Tình trạng bảo hành không đúng định dạng ! Mời nhập lại: ");

            }
        }
    }

    public double KiemTraNhapMucGia() { //Hàm kiểm tra nhập mức giá
        double GiaSanPham;
        while (true) {
            try {
                GiaSanPham = Double.parseDouble(scanner.nextLine());
                if (GiaSanPham == -1) {
                    return -1;
                }
                if (GiaSanPham >= 0) {
                    return GiaSanPham;
                } else {
                    System.out.print("Mức giá không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Mức giá không đúng định dạng ! Mời nhập lại: ");
            }

        }
    }

    public String KiemTraNhapNgaySinh() {   //Hàm kiểm tra nhập ngày sinh
        String NgaySinh;
        while (true) {
            NgaySinh = scanner.nextLine();
            if (NgaySinh.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (NgaySinh.matches("[0-9]{1,2}" + "/" + "[0-9]{1,2}" + "/" + "[0-9]{4}")) {
                if (KiemTraNhapNgayThangHopLe(NgaySinh)) {
                    HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgaySinh);
                    int Ngay = Integer.parseInt(hashmap.get(0));
                    int Thang = Integer.parseInt(hashmap.get(1));
                    int Nam = Integer.parseInt(hashmap.get(2));
                    return thaotac.ChuanHoaNgayThang(Ngay, Thang, Nam);
                } else {
                    System.out.print("Ngày sinh không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Ngày sinh không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapNgayThang() {  //Hàm kiểm tra nhập ngày tháng
        String NgayThang;
        while (true) {
            NgayThang = scanner.nextLine();
            if (NgayThang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (NgayThang.matches("[0-9]{1,2}" + "/" + "[0-9]{1,2}" + "/" + "[0-9]{4}")) {
                if (KiemTraNhapNgayThangHopLe(NgayThang)) {
                    HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
                    int Ngay = Integer.parseInt(hashmap.get(0));
                    int Thang = Integer.parseInt(hashmap.get(1));
                    int Nam = Integer.parseInt(hashmap.get(2));
                    return thaotac.ChuanHoaNgayThang(Ngay, Thang, Nam);
                } else {
                    System.out.print("Ngày tháng không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Ngày tháng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapNgayThangKhuyenMai() { //Hàm kiểm tra nhập ngày tháng khuyến mãi
        String NgayThang;
        while (true) {
            NgayThang = scanner.nextLine();
            if (NgayThang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (NgayThang.matches("[0-9]{1,2}" + "/" + "[0-9]{1,2}" + "/" + "[0-9]{4}")) {
                if (KiemTraNhapNgayThangKhuyenMaiHopLe(NgayThang)) {
                    HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
                    int Ngay = Integer.parseInt(hashmap.get(0));
                    int Thang = Integer.parseInt(hashmap.get(1));
                    int Nam = Integer.parseInt(hashmap.get(2));
                    return thaotac.ChuanHoaNgayThang(Ngay, Thang, Nam);
                } else {
                    System.out.print("Ngày khuyến mãi phải lớn hơn bằng ngày hiên tại ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Ngày tháng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapNgayThangTimKiem() {   //Hàm kiểm tra nhập ngày tháng tìm kiếm
        String NgayThang;
        while (true) {
            NgayThang = scanner.nextLine();
            if (NgayThang.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (NgayThang.matches("[0-9]{1,2}" + "/" + "[0-9]{1,2}" + "/" + "[0-9]{4}")) {
                if (KiemTraNhapNgayThangTimKiemHopLe(NgayThang)) {
                    HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
                    int Ngay = Integer.parseInt(hashmap.get(0));
                    int Thang = Integer.parseInt(hashmap.get(1));
                    int Nam = Integer.parseInt(hashmap.get(2));
                    return thaotac.ChuanHoaNgayThang(Ngay, Thang, Nam);
                } else {
                    System.out.print("Ngày tháng không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Ngày tháng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public boolean KiemTraNhapNgayThangHopLe(String NgayThang) {    //Hàm kiểm tra nhập ngày tháng hợp lệ
        HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
        int Ngay = Integer.parseInt(hashmap.get(0));
        int Thang = Integer.parseInt(hashmap.get(1));
        int Nam = Integer.parseInt(hashmap.get(2));
        if (Ngay < 1 || Ngay > 31) {
            return false;
        }
        if (Thang < 1 || Thang > 12) {
            return false;
        }
        if (Nam > NamHienTai || Nam < NamHienTai - 100) {
            return false;
        }
        if (Nam == NamHienTai && Thang >= ThangHienTai && Ngay > NgayHienTai) {
            return false;
        }
        switch (Thang) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                if (Ngay > 31) {
                    return false;
                }
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                if (Ngay > 30) {
                    return false;
                }
            }
            case 2: {
                if (Nam % 400 == 0) {
                    if (Ngay > 29) {
                        return false;
                    }
                } else {
                    if (Ngay > 28) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean KiemTraNhapNgayThangKhuyenMaiHopLe(String NgayThang) {   //Hàm kiểm tra nhập ngày tháng khuyến mãi hợp lệ
        HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
        int Ngay = Integer.parseInt(hashmap.get(0));
        int Thang = Integer.parseInt(hashmap.get(1));
        int Nam = Integer.parseInt(hashmap.get(2));
        if (Ngay < 1 || Ngay > 31) {
            return false;
        }
        if (Thang < 1 || Thang > 12) {
            return false;
        }
        if (Nam == NamHienTai && Thang <= ThangHienTai && Ngay < NgayHienTai) {
            return false;
        }
        switch (Thang) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                if (Ngay > 31) {
                    return false;
                }
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                if (Ngay > 30) {
                    return false;
                }
            }
            case 2: {
                if (Nam % 400 == 0) {
                    if (Ngay > 29) {
                        return false;
                    }
                } else {
                    if (Ngay > 28) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean KiemTraNhapNgayThangTimKiemHopLe(String NgayThang) { //Hàm kiểm tra nhập ngày tháng tìm kiếm hợp lệ
        HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
        int Ngay = Integer.parseInt(hashmap.get(0));
        int Thang = Integer.parseInt(hashmap.get(1));
        int Nam = Integer.parseInt(hashmap.get(2));
        if (Ngay < 1 || Ngay > 31) {
            return false;
        }
        if (Thang < 1 || Thang > 12) {
            return false;
        }
        switch (Thang) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                if (Ngay > 31) {
                    return false;
                }
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                if (Ngay > 30) {
                    return false;
                }
            }
            case 2: {
                if (Nam % 400 == 0) {
                    if (Ngay > 29) {
                        return false;
                    }
                } else {
                    if (Ngay > 28) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String KiemTraNhapPhanQuyen() {  //Hàm kiểm tra nhập phân quyền
        int LuaChon;
        while (true) {
            LuaChon = KiemTraNhapLuaChon();
            switch (LuaChon) {
                case -1:
                    return "-q";
                case 1:
                    return "Nhân viên bán hàng";
                case 2:
                    return "Quản trị viên";
                default: {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                    break;
                }
            }
        }
    }

    public String KiemTraNhapPhai() {   //Hàm kiểm tra nhập phái
        int LuaChon;
        while (true) {
            LuaChon = KiemTraNhapLuaChon();
            switch (LuaChon) {
                case -1:
                    return "-q";
                case 1:
                    return "Nam";
                case 2:
                    return "Nữ";
                default: {
                    System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
                    break;
                }
            }
        }
    }

    public double KiemTraNhapPhanTram() {  //Hàm kiểm tra nhập phần trăm khuyến mãi
        double PhanTramKhuyenMai;
        while (true) {
            try {
                PhanTramKhuyenMai = Double.parseDouble(scanner.nextLine());
                if (PhanTramKhuyenMai == -1) {
                    return -1;
                }
                if (PhanTramKhuyenMai > 0 && PhanTramKhuyenMai < 101) {
                    return PhanTramKhuyenMai;
                } else {
                    System.out.print("Phần trăm khuyến mãi không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Phần trăm khuyến mãi không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public double KiemTraNhapPhanTramLoi() {  //Hàm kiểm tra nhập phần trăm khuyến mãi
        double PhanTramKhuyenMai;
        while (true) {
            try {
                PhanTramKhuyenMai = Double.parseDouble(scanner.nextLine());
                if (PhanTramKhuyenMai == -1) {
                    return -1;
                }
                if (PhanTramKhuyenMai > 0 && PhanTramKhuyenMai < 101) {
                    return PhanTramKhuyenMai;
                } else {
                    System.out.print("Phần trăm lời không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Phần trăm lời không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapQuocGia() {    //Hàm kiểm tra nhập quốc gia
        String QuocGia;
        while (true) {
            QuocGia = scanner.nextLine();
            if (QuocGia.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (QuocGia.matches("[\\pL\\pMn*\\s*]+")) {
                return thaotac.ChuanHoaChuoi1(QuocGia);
            } else {
                System.out.print("Tên quốc gia không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapSoDienThoai() {    //Hàm kiểm tra nhập số điện thoại
        String SoDienThoai;
        while (true) {
            SoDienThoai = scanner.nextLine();
            if (SoDienThoai.equalsIgnoreCase("-q")) {
                return "-q";
            }
            SoDienThoai = thaotac.XoaKhoangTrangThua(SoDienThoai);
            if (SoDienThoai.matches("[0-9]{10}")) {
                int[] MaTinhHopLe = {86, 96, 97, 98, 32, 33, 34, 35, 36, 37, 38, 39, 88, 91, 94, 83, 84, 85, 81, 82, 89, 90, 93, 70, 79, 77, 76, 78, 92, 56, 58};
                String MaTinh = SoDienThoai.substring(1, 3);
                for (int i = 0; i < MaTinhHopLe.length; i++) {
                    if (Integer.parseInt(MaTinh) == MaTinhHopLe[i]) {
                        return SoDienThoai;
                    }
                }
                System.out.print("Số điện thoại không hợp lệ ! Mời nhập lại: ");
            } else {
                System.out.print("Số điện thoại không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapSoDienThoaiNhaCungCap() {  //Hàm kiểm tra nhập số điện thoại nhà cung cấp
        String SoDienThoai;
        while (true) {
            SoDienThoai = scanner.nextLine();
            if (SoDienThoai.equalsIgnoreCase("-q")) {
                return "-q";
            }
            SoDienThoai = thaotac.XoaKhoangTrangThua(SoDienThoai);
            if (SoDienThoai.matches("[0-9]{8}")) {
                String MaTongDai = SoDienThoai.substring(0, 4);
                if (Integer.parseInt(MaTongDai) == 1800 || Integer.parseInt(MaTongDai) == 1900) {
                    return SoDienThoai;
                } else {
                    System.out.print("Số điện thoại không hợp lệ ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Số điện thoại không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public int KiemTraNhapSoLuong() {   //Hàm kiểm tra nhập số lượng
        int SoLuong;
        while (true) {
            try {
                SoLuong = Integer.parseInt(scanner.nextLine());
                if (SoLuong == -1) {
                    return -1;
                }
                if (SoLuong > 0) {
                    return SoLuong;
                } else {
                    System.out.print("Số lượng không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Số lượng không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public int KiemTraNhapSoSanPham() { //Hàm kiểm tra nhập số sản phẩm
        int SoLuong;
        while (true) {
            try {
                SoLuong = Integer.parseInt(scanner.nextLine());
                if (SoLuong == -1) {
                    return -1;
                }
                if (SoLuong > 0) {
                    return SoLuong;
                } else {
                    System.out.print("Số sản phẩm không hợp lệ ! Mời nhập lại: ");
                }
            } catch (NumberFormatException exception) {
                System.out.print("Số sản phẩm không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapTenGanDung() { //Hàm kiểm tra nhập tên gần đúng
        String Ten;
        while (true) {
            Ten = scanner.nextLine();
            if (Ten.equalsIgnoreCase("-q")) {
                return "-q";
            }
            Ten = thaotac.XoaKhoangTrangThua(Ten);
            if (Ten.matches("[\\pL\\pMn*\\s*]+")) {
                Ten = thaotac.ChuanHoaTenNhaCungCap(Ten);
                Ten = thaotac.ChuanHoaTenSanPham(Ten);
                return Ten;
            } else {
                System.out.print("Tên không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapTenNhaCungCap() {  //Hàm kiểm tra nhập tên nhà cung cấp
        String TenNhaCungCap;
        while (true) {
            TenNhaCungCap = scanner.nextLine();
            if (TenNhaCungCap.equalsIgnoreCase("-q")) {
                return "-q";
            }
            TenNhaCungCap = thaotac.XoaKhoangTrangThua(TenNhaCungCap);
            if (TenNhaCungCap != null) {
                return thaotac.ChuanHoaTenNhaCungCap(TenNhaCungCap);
            } else {
                System.out.print("Tên nhà cung cấp không hợp lệ ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapTenSanPham() { //Hàm kiểm tra nhập tên sản phẩm
        String TenSanPham;
        while (true) {
            TenSanPham = scanner.nextLine();
            if (TenSanPham.equalsIgnoreCase("-q")) {
                return "-q";
            }
            TenSanPham = thaotac.XoaKhoangTrangThua(TenSanPham);
            if (TenSanPham != null) {
                return thaotac.ChuanHoaTenSanPham(TenSanPham);
            } else {
                System.out.print("Tên sản phẩm không hợp lệ ! Mời nhập lại: ");
            }

        }
    }

    public String KiemTraNhapTenThuongHieu() {  //Hàm kiểm tra nhập tên thương hiệu
        String TenThuongHieu;
        while (true) {
            TenThuongHieu = scanner.nextLine();
            if (TenThuongHieu.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (TenThuongHieu.matches("^[A-Za-z]+$")) {
                return thaotac.ChuanHoaChuoi1(TenThuongHieu);
            } else {
                System.out.print("Tên thương hiệu không đúng định dạng ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraNhapXacNhan() {    //Hàm kiểm tra nhập xác nhận
        String XacNhan;
        while (true) {
            XacNhan = scanner.nextLine();
            XacNhan = thaotac.XoaKhoangTrangThua(XacNhan);
            XacNhan = XacNhan.toLowerCase();
            if (XacNhan.matches("[n|y]")) {
                return XacNhan;
            } else {
                System.out.print("Lựa chọn không hợp lệ ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraSanPhamConHang() throws IOException { //Hàm kiểm tra sự tồn tại của sản phẩm
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        String MaSanPham;
        while (true) {
            MaSanPham = KiemTraSanPhamDaTonTai();
            if (MaSanPham.equalsIgnoreCase("-q")) {
                return MaSanPham;
            }
            int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);

            if (danhsachsanpham.getDanhSachSanPham()[ViTri].getSoLuong() > 0) {
                return MaSanPham;
            } else {
                System.out.print("Sản phẩm này đã hết hàng ! Mời nhập lại: ");
            }
        }

    }

    public String KiemTraSanPhamDaTonTai() throws IOException { //Hàm kiểm tra sự tồn tại của sản phẩm
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        String MaSanPham;
        while (true) {
            MaSanPham = KiemTraNhapMaSanPham();
            if (MaSanPham.equalsIgnoreCase("-q")) {
                return MaSanPham;
            }
            int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
            if (ViTri != -1) {
                return MaSanPham;
            } else {
                System.out.print("Không tìm thấy sản phẩm '" + MaSanPham + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraSanPhamThuocHoaDon(String MaPhieuBaoHanh, String MaHoaDon) throws IOException {   //Hàm kiểm tra sản phẩm thuộc hóa đơn
        DanhSachChiTietHoaDon danhsachchitiethoadon = new DanhSachChiTietHoaDon();
        DanhSachChiTietPhieuBaoHanh danhsachchitietphieubaohanh = new DanhSachChiTietPhieuBaoHanh();
        String MaSanPham;
        while (true) {
            MaSanPham = KiemTraSanPhamDaTonTai();
            if (MaSanPham.equalsIgnoreCase("-q")) {
                return "-q";
            }
            boolean SanPhamDaTonTai = danhsachchitiethoadon.KiemTraSanPhamDaTonTai(MaHoaDon, MaSanPham);
            if (SanPhamDaTonTai) {
                System.out.println("SL: " + danhsachchitietphieubaohanh.DemSoSanPhamBaoHanh(MaPhieuBaoHanh, MaSanPham));
                int SoSanPham = danhsachchitietphieubaohanh.DemSoSanPhamBaoHanh(MaPhieuBaoHanh, MaSanPham);
                if (SoSanPham < danhsachchitiethoadon.DemSoSanPhamTheoHoaDon(MaHoaDon, MaSanPham)) {
                    return MaSanPham;
                } else {
                    System.out.print("Tất cả sản phẩm '" + MaSanPham + "' đã được bảo hành ! Mời nhập lại: ");
                }
            } else {
                System.out.print("Sản phẩm này không thuộc hóa đơn '" + MaHoaDon + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraSanPhamThuocNhaCungCap(String MaNhaCungCap) throws IOException {   //Hàm kiểm tra sản phẩm thuộc nhà cung cấp
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        String MaSanPham;
        while (true) {
            MaSanPham = KiemTraSanPhamDaTonTai();
            if (MaSanPham.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);
            if (MaNhaCungCap.equals(danhsachsanpham.getDanhSachSanPham()[ViTri].getMaNhaCungCap())) {
                return MaSanPham;
            } else {
                System.out.print("Sản phẩm này không thuộc nhà cung cấp '" + MaNhaCungCap + "' ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraSoDienThoaiDaTonTai() throws IOException { //Hàm kiểm tra sự tồn tại của số điện thoại
        DanhSachKhachHang danhsachkhachhang = new DanhSachKhachHang();
        DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
        String SoDienThoai;
        while (true) {
            SoDienThoai = KiemTraNhapSoDienThoai();
            if (SoDienThoai.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (!danhsachkhachhang.KiemTraSoDienThoaiDaTonTai(SoDienThoai) && !danhsachnhanvien.KiemTraSoDienThoaiDaTonTai(SoDienThoai)) {
                return SoDienThoai;
            } else {
                System.out.print("Số điện thoại này đã có trong danh sách ! Mời nhập lại: ");
            }
        }

    }

    public String KiemTraSoDienThoaiNhaCungCapDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của số điện thoại nhà cung cấp
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        String SoDienThoai;
        while (true) {
            SoDienThoai = KiemTraNhapSoDienThoaiNhaCungCap();
            if (SoDienThoai.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (!danhsachnhacungcap.KiemTraSoDienThoaiDaTonTai(SoDienThoai)) {
                return SoDienThoai;
            } else {
                System.out.print("Số điện thoại này đã có trong danh sách ! Mời nhập lại: ");
            }
        }
    }

    public int KiemTraSoLuongSanPham(String MaSanPham) throws IOException { //Hàm kiểm tra số lượng sản phẩm
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        int SoLuong;
        while (true) {
            SoLuong = KiemTraNhapSoLuong();
            if (SoLuong == -1) {
                return -1;
            }
            int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(MaSanPham);

            if (SoLuong <= danhsachsanpham.getDanhSachSanPham()[ViTri].getSoLuong()) {
                return SoLuong;
            } else {
                System.out.print("Sản phẩm này chỉ còn " + danhsachsanpham.getDanhSachSanPham()[ViTri].getSoLuong() + " chiếc ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraTenSanPhamDaTonTai(String MaNhaCungCap) throws IOException {   //Hàm kiểm tra sự tồn tại của tên sản phẩm
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        String TenSanPham;
        while (true) {
            TenSanPham = KiemTraNhapTenSanPham();
            if (TenSanPham.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (danhsachsanpham.KiemTraNhaCungCapDaTonTai(MaNhaCungCap)) {
                if (!danhsachsanpham.KiemTraTenSanPhamDaTonTai(TenSanPham)) {
                    return TenSanPham;
                } else {
                    System.out.print("Sản phẩm này đã có trong danh sách ! Mời nhập lại: ");
                }
            } else {
                return TenSanPham;
            }
        }
    }

    public String KiemTraTenNhaCungCapDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của tên nhà cung cấp
        DanhSachNhaCungCap danhsachnhacungcap = new DanhSachNhaCungCap();
        String TenNhaCungCap;
        while (true) {
            TenNhaCungCap = KiemTraNhapTenNhaCungCap();
            if (TenNhaCungCap.equalsIgnoreCase("-q")) {
                return "-q";
            }
            TenNhaCungCap = thaotac.ChuanHoaTenNhaCungCap(TenNhaCungCap);
            if (!danhsachnhacungcap.KiemTraTenNhaCungCapDaTonTai(TenNhaCungCap)) {
                return TenNhaCungCap;
            } else {
                System.out.print("Tên nhà cung cấp này đã có trong danh sách ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraTenThuongHieuDaTonTai() throws IOException {   //Hàm kiểm tra sự tồn tại của tên thương hiệu
        DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
        String TenThuongHieu;
        while (true) {
            TenThuongHieu = KiemTraNhapTenThuongHieu();
            if (TenThuongHieu.equalsIgnoreCase("-q")) {
                return "-q";
            }
            if (!danhsachthuonghieu.KiemTraTenThuongHieuDaTonTai(TenThuongHieu)) {
                return TenThuongHieu;
            } else {
                System.out.print("Tên thương hiệu này đã có trong danh sách ! Mời nhập lại: ");
            }
        }
    }

    public String KiemTraThuongHieuDaTonTai() throws IOException {  //Hàm kiểm tra sự tồn tại của thương hiệu
        DanhSachThuongHieu danhsachthuonghieu = new DanhSachThuongHieu();
        String MaThuongHieu;
        while (true) {
            MaThuongHieu = KiemTraNhapMaThuongHieu();
            if (MaThuongHieu.equalsIgnoreCase("-q")) {
                return "-q";
            }
            int ViTri = danhsachthuonghieu.KiemTraPhanTuTonTai(MaThuongHieu);
            if (ViTri != -1) {
                return MaThuongHieu;
            } else {
                System.out.print("Không tìm thấy thương hiệu '" + MaThuongHieu + "' ! Mời nhập lại: ");
            }
        }
    }

    public boolean KiemTraTuoiHopLe(String NgayThang) { //Hàm kiểm tra tuổi hợp lệ (trên 18 tuổi)
        HashMap<Integer, String> hashmap = thaotac.TachNgayThang(NgayThang);
        int Ngay = Integer.parseInt(hashmap.get(0));
        int Thang = Integer.parseInt(hashmap.get(1));
        int Nam = Integer.parseInt(hashmap.get(2));
        if (NamHienTai - Nam > 18) {
            return true;
        } else if (NamHienTai - Nam == 18) {
            if (ThangHienTai >= Thang && NgayHienTai >= Ngay) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
