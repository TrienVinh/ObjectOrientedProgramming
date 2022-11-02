package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachChiTietHoaDon {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-20s %-43s %-40s %-29s %-24s|\n";
    private final String TenTapTin = "DanhSachChiTietHoaDon.txt";
    private ChiTietHoaDonBanHang[] DanhSachChiTietHoaDon = new ChiTietHoaDonBanHang[0];

    public ChiTietHoaDonBanHang[] getDanhSachChiTietHoaDon() {
        return DanhSachChiTietHoaDon;
    }

    public DanhSachChiTietHoaDon() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach(String MaHoaDon, double HeSo) throws IOException {
        System.out.format(DinhDang, "", "     SẢN PHẨM", "     GIÁ", "     SỐ LƯỢNG", "     THÀNH TIỀN");
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaHoaDon.equals(DanhSachChiTietHoaDon[i].getMaHoaDon())) {
                DanhSachChiTietHoaDon[i].HienThiThongTinChiTietHoaDon(HeSo);
            }
        }
    }

    public boolean ThemPhanTu(String MaNhaCungCap, String MaHoaDon, String LoaiHoaDon, int SoThuTu, int SoSanPham) throws IOException {
        DanhSachSanPham danhsachsanpham = new DanhSachSanPham();
        SoPhanTuThemVao++;
        DanhSachChiTietHoaDon = Arrays.copyOf(DanhSachChiTietHoaDon, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachChiTietHoaDon[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new ChiTietHoaDonBanHang();
        DanhSachChiTietHoaDon[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaHoaDon(MaHoaDon);
        String ThemChiTietHoaDon = DanhSachChiTietHoaDon[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinChiTietHoaDon(MaNhaCungCap, MaHoaDon, LoaiHoaDon, SoThuTu, SoSanPham);
        if (ThemChiTietHoaDon.equals("-q")) {
            SoPhanTuThemVao--;
            return false;
        } else {
            int ViTri = danhsachsanpham.KiemTraPhanTuTonTai(ThemChiTietHoaDon);
            double GiaSanPham = danhsachsanpham.getDanhSachSanPham()[ViTri].getGiaSanPham();
            DanhSachChiTietHoaDon[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setGiaSanPham(GiaSanPham);
            LuuDuLieu();
            return true;
        }
    }

    public void DocDuLieu() throws IOException {
        FileReader filereader = new FileReader(TenTapTin);
        BufferedReader bufferedreader = new BufferedReader(filereader);
        String DuLieu;
        int i = 0;
        SoPhanTuTrongDanhSach = DemSoPhanTu();
        DanhSachChiTietHoaDon = Arrays.copyOf(DanhSachChiTietHoaDon, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachChiTietHoaDon[i] = new ChiTietHoaDonBanHang();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachChiTietHoaDon[i].setMaHoaDon(scanfile.next());
                DanhSachChiTietHoaDon[i].setMaSanPham(scanfile.next());
                DanhSachChiTietHoaDon[i].setGiaSanPham(scanfile.nextDouble());
                DanhSachChiTietHoaDon[i++].setSoLuong(scanfile.nextInt());
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
            String DuLieu = DanhSachChiTietHoaDon[i].getMaHoaDon() + ";" + DanhSachChiTietHoaDon[i].getMaSanPham() + ";" + DanhSachChiTietHoaDon[i].getGiaSanPham() + ";" + DanhSachChiTietHoaDon[i].getSoLuong();
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

    public int DemSoSanPhamTheoHoaDon(String MaHoaDon, String MaSanPham) {
        int ViTri = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (MaHoaDon.equals(DanhSachChiTietHoaDon[i].getMaHoaDon()) && MaSanPham.equals(DanhSachChiTietHoaDon[i].getMaSanPham())) {
                ViTri = i;
            }
        }
        return DanhSachChiTietHoaDon[ViTri].getSoLuong();
    }

    public boolean KiemTraSanPhamDaTonTai(String MaHoaDon, String MaSanPham) throws IOException {
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaHoaDon.equals(DanhSachChiTietHoaDon[i].getMaHoaDon()) && MaSanPham.equals(DanhSachChiTietHoaDon[i].getMaSanPham())) {
                return true;
            }
        }
        return false;
    }

    public double TinhTongTien(String MaHoaDon, double HeSo) throws IOException {
        double TongTien = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (DanhSachChiTietHoaDon[i].getMaHoaDon().equals(MaHoaDon)) {
                TongTien += (DanhSachChiTietHoaDon[i].getGiaSanPham() * DanhSachChiTietHoaDon[i].getSoLuong() * HeSo);
            }
        }
        return TongTien;
    }

}
