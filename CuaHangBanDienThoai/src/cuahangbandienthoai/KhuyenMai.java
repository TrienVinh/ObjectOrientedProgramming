package cuahangbandienthoai;

import java.util.Calendar;

public class KhuyenMai {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private double DieuKien, PhanTram;
    private int TrangThai;
    private String MaKhuyenMai, NgayBatDau, NgayKetThuc;
    static Calendar calendar = Calendar.getInstance();
    private String HoatDong, MaNhanVien, ThoiGian;
    private final int NgayHienTai = calendar.get(Calendar.DATE);
    private final int ThangHienTai = calendar.get(Calendar.MONTH) + 1;
    private final int NamHienTai = calendar.get(Calendar.YEAR);

    public double getDieuKien() {
        return DieuKien;
    }

    public void setDieuKien(double DieuKien) {
        this.DieuKien = DieuKien;
    }

    public String getMaKhuyenMai() {
        return MaKhuyenMai;
    }

    public void setMaKhuyenMai(String MaKhuyenMai) {
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public String getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(String NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public String getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(String NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public double getPhanTram() {
        return PhanTram;
    }

    public void setPhanTram(double PhanTram) {
        this.PhanTram = PhanTram;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public KhuyenMai() {
    }

    public KhuyenMai(double PhanTram, String MaKhuyenMai, String NgayBatDau, String NgayKetThuc) {
        this.PhanTram = PhanTram;
        this.MaKhuyenMai = MaKhuyenMai;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
    }

    public boolean NhapThongTinKhuyenMai() {
        System.out.print("Nhập phần trăm khuyến mãi: ");
        PhanTram = kiemtradulieu.KiemTraNhapPhanTram();
        if (PhanTram == -1) {
            return false;
        }
        System.out.print("Nhập điều kiện khuyến mãi: ");
        DieuKien = kiemtradulieu.KiemTraNhapDieuKien();
        System.out.print("Nhập ngày bắt đầu: ");
        while (true) {
            NgayBatDau = kiemtradulieu.KiemTraNhapNgayThangKhuyenMai();
            if (NgayBatDau.equals("-q")) {
                return false;
            }
            if (Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau)) >= Integer.parseInt(thaotac.GhepNgayThang(thaotac.ChuanHoaNgayThang(TrangThai, TrangThai, TrangThai)))) {
                break;
            } else {
                System.out.print("Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại ! Mời nhập lại: ");
            }
        }
        System.out.print("Nhập ngày kết thúc: ");
        while (true) {
            NgayKetThuc = kiemtradulieu.KiemTraNhapNgayThangKhuyenMai();
            if (NgayKetThuc.equals("-q")) {
                return false;
            }
            if (Integer.parseInt(thaotac.GhepNgayThang(NgayKetThuc)) >= Integer.parseInt(thaotac.GhepNgayThang(NgayBatDau))) {
                break;
            } else {
                System.out.print("Ngày kết thúc phải lớn hoặc bằng hơn ngày bắt đầu ! Mời nhập lại: ");
            }
        }
        return true;
    }

    public void HienThiThongTinKhuyenMai() {
        System.out.format("|%-24s|%-30s|%-40s|%-22s|%-23s|\n", "     " + MaKhuyenMai, "     " + thaotac.HienThiDinhDangPhanTram(PhanTram), "     " + "Trên " + thaotac.HienThiDinhDangTienTe(thaotac.ChuyenKieuDuLieu(DieuKien)), "     " + NgayBatDau, "     " + NgayKetThuc);
    }

}
