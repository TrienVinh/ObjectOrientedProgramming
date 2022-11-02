package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public final class DanhSachChiTietSanPham {

    DanhSachLichSuHoatDong danhsachlichsuhoatdong = new DanhSachLichSuHoatDong();
    DanhSachNhanVien danhsachnhanvien = new DanhSachNhanVien();
    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    Random random = new Random();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-43s|%-28s|%-29s|%-28s|\n";
    private final String TenTapTin = "DanhSachChiTietSanPham.txt";
    private ChiTietSanPham[] DanhSachChiTietSanPham = new ChiTietSanPham[0];

    public ChiTietSanPham[] getDanhSachChiTietSanPham() {
        return DanhSachChiTietSanPham;
    }

    public DanhSachChiTietSanPham() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSachTheoSanPham(String MaHoaDon) throws IOException {
        if (DemSoPhanTu() == 0) {
            System.out.println("Danh sách sản phẩm bảo hành trống !\n");
        } else {
            System.out.println("Danh sách sản phẩm bảo hành: ");
            thaotac.KeVienDanhSachChiTietSanPham();
            System.out.format(DinhDang, "     SẢN PHẨM", "     IMEI", "     MÃ HÓA ĐƠN NHẬP HÀNG", "     MÃ HÓA ĐƠN BÁN HÀNG");
            thaotac.KeVienDanhSachChiTietSanPham();
            for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
                if (MaHoaDon.equals(DanhSachChiTietSanPham[i].getMaHoaDonBanHang())) {
                    DanhSachChiTietSanPham[i].HienThiThongTinChiTietSanPham();
                    thaotac.KeVienDanhSachChiTietSanPham();
                }
            }
            System.out.println("");
        }
    }

    public void ThemPhanTu1(String MaSanPham, String MaHoaDonNhapHang) throws IOException {
        SoPhanTuThemVao++;
        DanhSachChiTietSanPham = Arrays.copyOf(DanhSachChiTietSanPham, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachChiTietSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new ChiTietSanPham();
        DanhSachChiTietSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinChiTietSanPham(MaSanPham);
        DanhSachChiTietSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaHoaDonNhapHang(MaHoaDonNhapHang);
        DanhSachChiTietSanPham[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaHoaDonBanHang("HDBH00");
        LuuDuLieu();
    }

    public void ThemPhanTu2(String MaHoaDonBanHang, int ViTri) throws IOException {
        DanhSachChiTietSanPham = Arrays.copyOf(DanhSachChiTietSanPham, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachChiTietSanPham[ViTri].setMaHoaDonBanHang(MaHoaDonBanHang);
        LuuDuLieu();
    }

    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachChiTietSanPham = Arrays.copyOf(DanhSachChiTietSanPham, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachChiTietSanPham[i] = new ChiTietSanPham();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachChiTietSanPham[i].setMaSanPham(scanfile.next());
                DanhSachChiTietSanPham[i].setIMEI(scanfile.next());
                DanhSachChiTietSanPham[i].setMaHoaDonNhapHang(scanfile.next());
                DanhSachChiTietSanPham[i++].setMaHoaDonBanHang(scanfile.next());
            }
        }
        bufferedreader.close();
        filereader.close();
    }

    public void LuuDuLieu() throws IOException {
        File file = new File(TenTapTin);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter filewriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            String DuLieu = DanhSachChiTietSanPham[i].getMaSanPham() + ";" + DanhSachChiTietSanPham[i].getIMEI() + ";" + DanhSachChiTietSanPham[i].getMaHoaDonNhapHang() + ";" + DanhSachChiTietSanPham[i].getMaHoaDonBanHang();
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

    public int DemSoSanPhamTheoHoaDon(String MaHoaDon) throws IOException {
        int Dem = 0;
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaHoaDon.equals(DanhSachChiTietSanPham[i].getMaHoaDonBanHang())) {
                Dem++;
            }
        }
        return Dem;
    }

    public boolean KiemTraIMEIDaTonTai(String IMEI) throws IOException {
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (IMEI.equals(DanhSachChiTietSanPham[i].getIMEI())) {
                return true;
            }
        }
        return false;
    }

    public boolean KiemTraIMEIThuocSanPham(String IMEI, String MaSanPham) throws IOException {
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (IMEI.equals(DanhSachChiTietSanPham[i].getIMEI()) && MaSanPham.equals(DanhSachChiTietSanPham[i].getMaSanPham())) {
                return true;
            }
        }
        return false;
    }

    public boolean KiemTraSanPhamThuocHoaDon(String MaHoaDon, String MaSanPham) throws IOException {
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaHoaDon.equals(DanhSachChiTietSanPham[i].getMaHoaDonBanHang()) && MaSanPham.equals(DanhSachChiTietSanPham[i].getMaSanPham())) {
                return true;
            }
        }
        return false;
    }

    public String TaoIMEI() throws IOException {
        while (true) {
            String IMEI = "";
            for (int i = 0; i < 15; i++) {
                IMEI += String.valueOf(random.nextInt(10));
            }
            if (!KiemTraIMEIDaTonTai(IMEI)) {
                return IMEI;
            }
        }
    }

}
