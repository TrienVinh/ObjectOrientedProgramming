package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public class KhachHang extends ConNguoi {

    private int TrangThai;
    private String MaKhachHang;

    public String getMaKhachHang() {
        return MaKhachHang;
    }

    public void setMaKhachHang(String MaKhachHang) {
        this.MaKhachHang = MaKhachHang;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public KhachHang() {
    }

    public KhachHang(int TrangThai, String MaKhachHang, String DiaChi, String HoTen, String NgaySinh, String Phai, String SoDienThoai) {
        super(DiaChi, HoTen, NgaySinh, Phai, SoDienThoai);
        this.TrangThai = TrangThai;
        this.MaKhachHang = MaKhachHang;
    }

    @Override
    public boolean NhapThongTin() throws IOException {
        System.out.print("Nhập họ tên khách hàng: ");
        String HoTen = kiemtradulieu.KiemTraNhapHoTen();
        if (HoTen.equals("-q")) {
            return false;
        }
        String NgaySinh;
        System.out.print("Nhập ngày sinh: ");
        while (true) {
            NgaySinh = kiemtradulieu.KiemTraNhapNgaySinh();
            if (NgaySinh.equals("-q")) {
                return false;
            }
            if (kiemtradulieu.KiemTraTuoiHopLe(NgaySinh)) {
                break;
            } else {
                System.out.print("Khách hàng phải đủ 18 tuổi ! Mời nhập lại: ");
            }
        }
        System.out.println("Phái: 1.Nam   2.Nữ");
        System.out.print("Nhập lựa chọn: ");
        String Phai = kiemtradulieu.KiemTraNhapPhai();
        if (Phai.equals("-q")) {
            return false;
        }
        System.out.print("Nhập số điện thoại: ");
        String SoDienThoai = kiemtradulieu.KiemTraNhapSoDienThoai();
        if (SoDienThoai.equals("-q")) {
            return false;
        }
        System.out.print("Nhập địa chỉ: ");
        String DiaChi = kiemtradulieu.KiemTraNhapDiaChi();
        if (DiaChi.equals("-q")) {
            return false;
        }
        return true;
    }

    @Override
    public void HienThiThongTin() throws IOException {
        System.out.format("|%-23s|%-27s|%-19s|%-14s|%-23s|%-48s|\n","     " + MaKhachHang, "     " + getHoTen(), "     " + getNgaySinh(), "     " + getPhai(), "     " + getSoDienThoai(), "     " + getDiaChi());

    }

}
