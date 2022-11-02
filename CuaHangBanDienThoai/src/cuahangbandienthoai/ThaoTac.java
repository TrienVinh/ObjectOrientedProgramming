package cuahangbandienthoai;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ThaoTac {

    Scanner scanner = new Scanner(System.in);


    public String ChuanHoaChuoi1(String DauVao) {   //Hàm chuẩn hóa chuỗi danh từ riêng
        DauVao = DauVao.replaceAll(";", "");
        DauVao = DauVao.toLowerCase();
        String[] Chuoi = DauVao.split(" ");
        DauVao = "";
        for (int i = 0; i < Chuoi.length; i++) {
            DauVao += String.valueOf(Chuoi[i].charAt(0)).toUpperCase() + Chuoi[i].substring(1);
            if (i < Chuoi.length - 1) {
                DauVao += " ";
            }
        }
        return DauVao;
    }

    public String ChuanHoaChuoi2(String DauVao) {   //Hàm chuẩn hóa chuỗi thông thường
        DauVao = DauVao.replaceAll(";", "");
        DauVao = DauVao.toLowerCase();
        char[] Chuoi = DauVao.toCharArray();
        DauVao = "";
        for (int i = 0; i < Chuoi.length; i++) {
            DauVao += Chuoi[i];
            if (i == 0) {
                DauVao = DauVao.toUpperCase();
            }
        }
        return DauVao;
    }

    public String ChuanHoaDiaChi(String DiaChi) {   //Hàm chuẩn hóa địa chỉ
        DiaChi = ChuanHoaChuoi1(DiaChi);
        DiaChi = XoaKhoangTrangThua(DiaChi);
        DiaChi = DiaChi.replaceAll("Đường Số", "đường số");
        DiaChi = DiaChi.replaceAll("Phường", "phường");
        DiaChi = DiaChi.replace("Quận", "quận");
        HashMap<Integer, String> hashmap = new HashMap<>();
        StringTokenizer stringtokenizer = new StringTokenizer(DiaChi, " ");
        int Dem = 0;
        while (stringtokenizer.hasMoreTokens()) {
            hashmap.put(Dem, stringtokenizer.nextToken());
            Dem++;
        }
        DiaChi = hashmap.get(0).toUpperCase();
        for (int i = 1; i < Dem; i++) {
            if (i < Dem) {
                DiaChi += " ";
            }
            if (hashmap.get(i).matches("[0-9]{1}")) {
                DiaChi += "0" + hashmap.get(i);
            } else {
                DiaChi += hashmap.get(i);
            }
        }
        return DiaChi;
    }

    public static String ChuanHoaNgayThang(int Ngay, int Thang, int Nam) { //Hàm chuẩn hóa ngày tháng
        String ngay = "";
        String thang = "";
        if (Ngay < 10) {
            ngay += "0";
        }
        if (Thang < 10) {
            thang += "0";
        }
        return ngay + String.valueOf(Ngay) + "/" + thang + String.valueOf(Thang) + "/" + String.valueOf(Nam);
    }

    public String ChuanHoaTenNhaCungCap(String TenNhaCungCap) { //Hàm chuẩn hóa tên nhà cung cấp
        TenNhaCungCap = ChuanHoaChuoi1(TenNhaCungCap);
        TenNhaCungCap = TenNhaCungCap.replaceAll("Cellphones", "CellphoneS");
        TenNhaCungCap = TenNhaCungCap.replaceAll("Fpt", "FPT");
        return TenNhaCungCap;
    }

    public String ChuanHoaTenSanPham(String TenSanPham) {   //Hàm chuẩn hóa tên sản phẩm
        TenSanPham = ChuanHoaChuoi1(TenSanPham);
        TenSanPham = TenSanPham.replaceAll("gb", "GB");
        TenSanPham = TenSanPham.replaceAll("Màu", "màu");
        TenSanPham = TenSanPham.replaceAll("Xr", "XR");
        TenSanPham = TenSanPham.replaceAll("Xs", "XS");
        return TenSanPham;
    }

    public String ChuanHoaSoLuong(int SoLuong) {    //Hàm chuẩn hóa số lượng
        if (SoLuong < 10) {
            return "0" + SoLuong;
        } else {
            return String.valueOf(SoLuong);
        }
    }

    public static String ChuanHoaThoiGian(int Ngay, int Thang, int Nam, int Gio, int Phut) {   //Hàm chuẩn hóa thời gian
        String ngay = "", thang = "", gio = "", phut = "";
        if (Ngay < 10) {
            ngay += "0";
        }
        if (Thang < 10) {
            thang += "0";
        }
        if (Gio < 10) {
            gio += "0";
        }
        if (Phut < 10) {
            phut += "0";
        }
        return ngay + String.valueOf(Ngay) + "/" + thang + String.valueOf(Thang) + "/" + String.valueOf(Nam) + " - " + gio + String.valueOf(Gio) + ":" + phut + String.valueOf(Phut);
    }

    public String ChuyenKieuDuLieu(double DauVao) { //Hàm chuyển kiểu dữ liệu số sang chuỗi
        char[] string = String.valueOf(DauVao).toCharArray();
        int ViTri1 = 0, ViTri2 = 0;
        String So1 = "";
        for (int i = 0; i < string.length; i++) {
            if (string[i] == '.') {
                ViTri1 = i;
            }
            if (string[i] == 'E') {
                ViTri2 = i;
            }
        }
        String KetQua = "";
        if (ViTri2 == 0) {
            for (int i = 0; i < string.length - 2; i++) {
                KetQua += string[i];
            }
        } else {
            for (int i = ViTri2 + 1; i < (string.length); i++) {
                So1 += string[i];
            }
            int So2 = Integer.parseInt(So1);
            KetQua += string[0];
            for (int i = ViTri1 + 1; i < ViTri2; i++) {
                KetQua += string[i];
            }
            if (ViTri2 - ViTri1 != 0) {
                for (int j = ViTri2 - 1; j < So2 + 1; j++) {
                    KetQua += "0";
                }
            }
        }
        return KetQua;
    }

    public String DatMatKhauMacDinh(String NgaySinh) {  //Hàm đặt mật khẩu mặc định
        String MatKhau = "";
        StringTokenizer stringtokenizer = new StringTokenizer(NgaySinh, "/");
        while (stringtokenizer.hasMoreTokens()) {
            MatKhau += stringtokenizer.nextToken();
        }
        return MaHoaMatKhau(MatKhau);
    }

    public int DemKhoangTrang(String DauVao) {  //Hàm đếm khoảng trắng
        DauVao = XoaKhoangTrangThua(DauVao);
        char[] Chuoi = DauVao.toCharArray();
        int Dem = 0;
        for (int i = 0; i < Chuoi.length; i++) {
            if (Chuoi[i] == ' ') {
                Dem++;
            }
        }
        return Dem;
    }

    public String GhepNgayThang(String NgayThang) { //Hàm ghép chuỗi ngày tháng
        HashMap<Integer, String> hashmap = TachNgayThang(NgayThang);
        int Ngay = Integer.parseInt(hashmap.get(0));
        int Thang = Integer.parseInt(hashmap.get(1));
        int Nam = Integer.parseInt(hashmap.get(2));
        String ngay = "";
        String thang = "";
        if (Ngay < 10) {
            ngay += "0";
        }
        if (Thang < 10) {
            thang += "0";
        }
        return String.valueOf(Nam) + thang + String.valueOf(Thang) + ngay + String.valueOf(Ngay);
    }

    public String HienThiDinhDangPhanTram(double DauVao) {  //Hàm hiển thị định dạng phần trăm
        return ChuyenKieuDuLieu(DauVao) + "%";
    }

    public String HienThiDinhDangTienTe(String DauVao) {    //Hàm hiển thị định dạng tiền tệ
        StringBuilder stringbuilder = new StringBuilder(DauVao);
        DauVao = String.valueOf(stringbuilder.reverse());
        char[] Chuoi = DauVao.toCharArray();
        DauVao = "";
        for (int i = 0; i < Chuoi.length; i++) {
            DauVao += Chuoi[i];
            if ((i + 1) % 3 == 0 && i + 1 < Chuoi.length) {
                DauVao += '.';
            }
        }
        stringbuilder = new StringBuilder(DauVao);
        return String.valueOf(stringbuilder.reverse()) + " VNĐ";
    }

    public void KeVienDanhSachChiTietHoaDon() { //Hàm kẻ viền cho danh sách chi tiết hóa đơn
        System.out.print("+");
        for (int i = 0; i < 160; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachChiTietPhieuBaoHanh() { //Hàm kẻ viền cho danh sách chi tiết bảo hành
        System.out.print("+");
        for (int i = 0; i < 135; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachChiTietSanPham() { //Hàm kẻ viền cho danh sách chi tiết sản phẩm
        System.out.print("+");
        for (int i = 0; i < 43; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 28; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 29; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 28; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachHoaDon() {    //Hàm kẻ viền cho danh sách hóa đơn
        System.out.print("+");
        for (int i = 0; i < 20; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 43; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 40; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 29; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 24; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachKhuyenMai() { //Hàm kẻ viền cho danh sách khuyến mãi
        System.out.print("+");
        for (int i = 0; i < 24; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 30; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 40; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 22; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachKhachHang() { //Hàm kẻ viền cho danh sách khách hàng
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 27; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 19; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 14; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 48; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachLichSuHoatDong() {    //Hàm kẻ viền cho danh sách lịch sử hoạt động
        System.out.print("+");
        for (int i = 0; i < 129; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 29; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachNhaCungCap() {    //Hàm kẻ viền cho danh sách nhà cung cấp
        System.out.print("+");
        for (int i = 0; i < 25; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 31; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 53; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachNhanVien() { //Hàm kẻ viền cho danh sách nhân viên
        System.out.print("+");
        for (int i = 0; i < 22; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 27; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 19; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 14; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 48; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachPhieuBaoHanh() { //Hàm kẻ viền cho danh sách phiếu bảo hành
        System.out.print("+");
        for (int i = 0; i < 32; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 28; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 43; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 29; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachSanPham() {   //Hàm kẻ viền cho danh sách sản phẩm
        System.out.print("+");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 37; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 26; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 26; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 26; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 18; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachTaiKhoan() {  //Hàm kẻ viền cho danh sách tài khoản
        System.out.print("+");
        for (int i = 0; i < 22; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 33; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 30; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 25; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public void KeVienDanhSachThuongHieu() {    //Hàm kẻ viền cho danh sách thương hiệu
        System.out.print("+");
        for (int i = 0; i < 24; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 35; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        for (int i = 0; i < 23; i++) {
            System.out.print("-");
        }
        System.out.print("+");
        System.out.println("");
    }

    public static String LayNgayHienTai(){
        Calendar calendar = Calendar.getInstance();
        int NgayHienTai=calendar.get(Calendar.DATE);
        int ThangHienTai = calendar.get(Calendar.MONTH) + 1;
        int NamHienTai = calendar.get(Calendar.YEAR);
        return ChuanHoaNgayThang(NgayHienTai,ThangHienTai,NamHienTai);
    }

    //3118410482 - Lư Triển Vinh
    public static String LayThoiGianHienTai(){
        Calendar calendar = Calendar.getInstance();
        int NgayHienTai=calendar.get(Calendar.DATE);
        int ThangHienTai = calendar.get(Calendar.MONTH) + 1;
        int NamHienTai = calendar.get(Calendar.YEAR);
        int  GioHienTai = calendar.get(Calendar.HOUR_OF_DAY);
        int PhutHienTai = calendar.get(Calendar.MINUTE);
        return ChuanHoaThoiGian(NgayHienTai, ThangHienTai, NamHienTai, GioHienTai, PhutHienTai);
    }

    public String MaHoaMatKhau(String MatKhau) {    //Hàm mã hóa mật khẩu bằng mã Affine
        final int a = 5, b = 7;
        char[] Chuoi = MatKhau.toCharArray();
        MatKhau = "";
        for (int i = 0; i < Chuoi.length; i++) {
            if (String.valueOf(Chuoi[i]).matches("[0-9]")) {
                Chuoi[i] = (char) ((a * ((int) Chuoi[i] - 48) + b) % 9);
                MatKhau += String.valueOf((char) ((int) Chuoi[i] + 48));
            }
            if (String.valueOf(Chuoi[i]).matches("[a-z]")) {
                Chuoi[i] = (char) ((a * ((int) Chuoi[i] - 65) + b) % 26);
                MatKhau += String.valueOf((char) ((int) Chuoi[i] + 65));
            }
            if (String.valueOf(Chuoi[i]).matches("[A-Z]")) {
                Chuoi[i] = (char) ((a * ((int) Chuoi[i] - 97) + b) % 26);
                MatKhau += String.valueOf((char) ((int) Chuoi[i] + 97));
            }
        }
        return MatKhau;
    }

    public HashMap TachNgayThang(String NgayThang) {    //Hàm tách ngày tháng
        int Dem = 0;
        HashMap<Integer, String> hashmap = new HashMap<>();
        StringTokenizer stringtokenizer = new StringTokenizer(NgayThang, "/");
        while (stringtokenizer.hasMoreTokens()) {
            hashmap.put(Dem, stringtokenizer.nextToken());
            Dem++;
        }
        return hashmap;
    }

    public String TachThoiGian(String ThoiGian) {
        return ThoiGian.substring(0, 10);
    }

    public void TiepTuc() { //Hàm tiếp tục
        String DauVao;
        System.out.print("Nhấn Enter để tiếp tục...");
        DauVao = scanner.nextLine();
    }

    public String XoaKhoangTrangThua(String DauVao) {   //Hàm xóa khoảng trắng thừa
        DauVao = DauVao.trim();
        DauVao = DauVao.replaceAll("\\s+", " ");
        return DauVao;
    }

}
