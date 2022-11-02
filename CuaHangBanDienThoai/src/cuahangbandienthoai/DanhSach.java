package cuahangbandienthoai;

import java.io.IOException;

//3118410482 - Lư Triển Vinh
public interface DanhSach {

    void HienThiDanhSach() throws IOException;   //Hiện thị danh sách các phần tử

    boolean ThemPhanTu(String MaNhanVienDangNhap) throws IOException;   //Thêm 1 phần tử mới vào danh sách

    boolean CapNhatThongTinPhanTu(String MaNhanVienDangNhap) throws IOException;   //Cập nhật thông tin 1 phần tử trong danh sách

    boolean XoaPhanTu(String MaNhanVienDangNhap) throws IOException;   //Xóa 1 phần tử trong danh sách

    boolean TimKiemPhanTu() throws IOException;   //Tìm kiếm 1 phần tử trong danh sách

    void DocDuLieu() throws IOException;   //Đọc dữ liệu từ tập tin (đọc file)

    void LuuDuLieu() throws IOException;   //Lưu dữ liệu vào tập tin (ghi file)

    int KiemTraPhanTuTonTai(String Ma);   //Kiểm tra sự tồn tại của 1 phần tử trong danh sách

    int DemSoPhanTu() throws IOException;   //Đếm số phần tử trong danh sách 

    int DemSoPhanTuKhaDung();   //Đếm số phần tử khả dụng trong danh sách
    
}

