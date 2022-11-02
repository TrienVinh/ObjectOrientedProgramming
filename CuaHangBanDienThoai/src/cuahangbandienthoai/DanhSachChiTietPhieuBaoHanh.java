package cuahangbandienthoai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public final class DanhSachChiTietPhieuBaoHanh {

    KiemTraDuLieu kiemtradulieu = new KiemTraDuLieu();
    ThaoTac thaotac = new ThaoTac();
    private int SoPhanTuThemVao = 0;
    private int SoPhanTuTrongDanhSach = 0;
    private final String DinhDang = "|%-32s %-28s %-43s %-29s|\n";
    private final String TenTapTin = "DanhSachChiTietPhieuBaoHanh.txt";
    private ChiTietPhieuBaoHanh[] DanhSachChiTietPhieuBaoHanh = new ChiTietPhieuBaoHanh[0];

    public DanhSachChiTietPhieuBaoHanh() throws IOException {
        DocDuLieu();
    }

    public void HienThiDanhSach(String MaPhieuBaoHanh) throws IOException {
        System.out.format(DinhDang, "     SẢN PHẨM", "     IMEI", "     NỘI DUNG BẢO HÀNH", "     TÌNH TRẠNG");
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaPhieuBaoHanh.equals(DanhSachChiTietPhieuBaoHanh[i].getMaPhieuBaoHanh())) {
                DanhSachChiTietPhieuBaoHanh[i].HienThiThongTinChiTietBaoHanh();
            }
        }
    }

    public boolean ThemPhanTu(String MaHoaDon, String MaPhieuBaoHanh, int SoThuTu, int SoSanPham) throws IOException {
        SoPhanTuThemVao++;
        DanhSachChiTietPhieuBaoHanh = Arrays.copyOf(DanhSachChiTietPhieuBaoHanh, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        DanhSachChiTietPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1] = new ChiTietPhieuBaoHanh();
        DanhSachChiTietPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].setMaPhieuBaoHanh(MaPhieuBaoHanh);
        String ThemChiTietPhieuBaoHanh = DanhSachChiTietPhieuBaoHanh[SoPhanTuTrongDanhSach + SoPhanTuThemVao - 1].NhapThongTinChiTietBaoHanh(MaPhieuBaoHanh, MaHoaDon, SoThuTu, SoSanPham);
        if (ThemChiTietPhieuBaoHanh.equals("-q")) {
            SoPhanTuThemVao--;
            return false;
        } else {
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
        DanhSachChiTietPhieuBaoHanh = Arrays.copyOf(DanhSachChiTietPhieuBaoHanh, SoPhanTuTrongDanhSach + SoPhanTuThemVao);
        while ((DuLieu = bufferedreader.readLine()) != null) {
            if (DuLieu.length() != 1) {
                DanhSachChiTietPhieuBaoHanh[i] = new ChiTietPhieuBaoHanh();
                Scanner scanfile = new Scanner(DuLieu).useDelimiter(";");
                DanhSachChiTietPhieuBaoHanh[i].setMaPhieuBaoHanh(scanfile.next());
                DanhSachChiTietPhieuBaoHanh[i].setMaSanPham(scanfile.next());
                DanhSachChiTietPhieuBaoHanh[i].setIMEI(scanfile.next());
                DanhSachChiTietPhieuBaoHanh[i].setNoiDung(scanfile.next());
                DanhSachChiTietPhieuBaoHanh[i++].setTinhTrang(scanfile.next());
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
            String DuLieu = DanhSachChiTietPhieuBaoHanh[i].getMaPhieuBaoHanh() + ";" + DanhSachChiTietPhieuBaoHanh[i].getMaSanPham() + ";" + DanhSachChiTietPhieuBaoHanh[i].getIMEI() + ";" + DanhSachChiTietPhieuBaoHanh[i].getNoiDung() + ";" + DanhSachChiTietPhieuBaoHanh[i].getTinhTrang();
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

    public int DemSoSanPhamBaoHanh(String MaPhieuBaoHanh, String MaSanPham) {
        int Dem = 0;
        for (int i = 0; i < SoPhanTuTrongDanhSach + SoPhanTuThemVao; i++) {
            if (MaPhieuBaoHanh.equals(DanhSachChiTietPhieuBaoHanh[i].getMaPhieuBaoHanh()) && MaSanPham.equals(DanhSachChiTietPhieuBaoHanh[i].getMaSanPham())) {
                Dem++;
            }
        }
        return Dem;
    }

    public boolean KiemTraIMEIDaTonTai(String MaPhieuBaoHanh, String MaSanPham, String IMEI) throws IOException {
        for (int i = 0; i < DemSoPhanTu(); i++) {
            if (MaPhieuBaoHanh.equals(DanhSachChiTietPhieuBaoHanh[i].getMaPhieuBaoHanh()) && IMEI.equals(DanhSachChiTietPhieuBaoHanh[i].getIMEI()) && MaSanPham.equals(DanhSachChiTietPhieuBaoHanh[i].getMaSanPham())) {
                return true;
            }
        }
        return false;
    }

}
