package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public abstract class ConNguoi {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    private String DiaChi, HoTen, NgaySinh, Phai, SoDienThoai;

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getPhai() {
        return Phai;
    }

    public void setPhai(String Phai) {
        this.Phai = Phai;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public ConNguoi() {
    }

    public ConNguoi(String DiaChi, String HoTen, String NgaySinh, String Phai, String SoDienThoai) {
        this.DiaChi = DiaChi;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.Phai = Phai;
        this.SoDienThoai = SoDienThoai;
    }

    //3118410 - Lư Triển Vinh
    public abstract boolean NhapThongTin() throws IOException;

    public abstract void HienThiThongTin() throws IOException;

}


