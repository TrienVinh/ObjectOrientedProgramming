package cuahangbandienthoai;

public class TaiKhoan {

    private int TrangThai;
    private String MaNhanVien, MatKhau, PhanQuyen, TinhTrang;

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(String PhanQuyen) {
        this.PhanQuyen = PhanQuyen;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(int TrangThai, String MaNhanVien, String MatKhau, String PhanQuyen, String TinhTrang) {
        this.TrangThai = TrangThai;
        this.MaNhanVien = MaNhanVien;
        this.MatKhau = MatKhau;
        this.PhanQuyen = PhanQuyen;
        this.TinhTrang = TinhTrang;
    }

    public void HienThiThongTinTaiKhoan() {
        System.out.format("|%-22s|%-33s|%-30s|%-25s|\n", "     " + MaNhanVien, "     " + MatKhau, "     " + PhanQuyen, "     " + TinhTrang);
    }

}
