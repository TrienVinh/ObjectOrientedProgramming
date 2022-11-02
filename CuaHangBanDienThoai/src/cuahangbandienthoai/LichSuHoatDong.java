package cuahangbandienthoai;

public class LichSuHoatDong {

    ThaoTac thaotac = new ThaoTac();
    private String HoatDong, MaNhanVien, ThoiGian;


    public String getHoatDong() {
        return HoatDong;
    }

    public void setHoatDong(String HoatDong) {
        this.HoatDong = HoatDong;
    }

    public String getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(String MaNhanVien) {
        this.MaNhanVien = MaNhanVien;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String ThoiGian) {
        this.ThoiGian = ThoiGian;
    }

    public LichSuHoatDong() {
    }

    public LichSuHoatDong(String MaNhanVienDangNhap, String HoatDong, String ThoiGian) {
        this.HoatDong = HoatDong;
        this.MaNhanVien = MaNhanVienDangNhap;
        this.ThoiGian = ThoiGian;
    }

    public void GhiHoatDong(String MaNhanVienDangNhap, String HoatDong, String ThoiGian) {
        MaNhanVien = MaNhanVienDangNhap;
        this.HoatDong=HoatDong;
        this.ThoiGian=ThoiGian;
    }

    public void HienThiLichSuHoatDong() {
        System.out.format("|%-129s|%-29s|\n", "     " + HoatDong, "     " + ThoiGian);
    }

}
