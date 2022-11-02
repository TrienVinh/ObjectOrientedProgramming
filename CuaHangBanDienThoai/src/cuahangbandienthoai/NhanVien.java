package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public class NhanVien extends ConNguoi {

    ThaoTac thaotac=new ThaoTac();
    private int TrangThai;
    private String MaNhanVien;

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {

        this.MaNhanVien = MaNhanVien;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public NhanVien() {
    }

    public NhanVien(int TrangThai, String MaNhanVien, String DiaChi, String HoTen, String NgaySinh, String Phai, String SoDienThoai) {
        super(DiaChi, HoTen, NgaySinh, Phai, SoDienThoai);
        this.TrangThai = TrangThai;
        this.MaNhanVien = MaNhanVien;
    }

    @Override
    public boolean NhapThongTin() throws IOException {
        DanhSachTaiKhoan danhsachtaikhoan=new DanhSachTaiKhoan();
        System.out.print("Nhập họ tên nhân viên: ");
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
                System.out.print("Nhân viên phải đủ 18 tuổi ! Mời nhập lại: ");
            }
        }
        System.out.println("Phái: 1.Nam   2.Nữ");
        System.out.print("Nhập lựa chọn: ");
        String Phai = kiemtradulieu.KiemTraNhapPhai();
        if (Phai.equals("-q")) {
            return false;
        }
        System.out.print("Nhập số điện thoại: ");
        String SoDienThoai = super.kiemtradulieu.KiemTraNhapSoDienThoai();;
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
        System.out.format("|%-22s|%-27s|%-19s|%-14s|%-23s|%-48s|\n","     " + MaNhanVien, "     " + getHoTen(), "     " + getNgaySinh(), "     " + getPhai(), "     " + getSoDienThoai(), "     " + getDiaChi());
    }

}
