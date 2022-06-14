package com.example.cuahangthietbionline.ultil;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.util.logging.LogRecord;

public class Server {
    public static String localhost = "192.168.56.1";
    public static String DuongDanLoaisp = "http://" + localhost + "/server/getloaisp.php";
    public static String DuongDansanphammoinhat = "http://" + localhost + "/server/getsanphammoinhat.php";
    public static String Duongdansanpham = "http://" + localhost + "/server/getsanpham.php?page=";
    public static String Duongdandonhang = "http://" + localhost + "/server/thongtinkhachhang.php";
    public static String Duongdanchitietdonhang = "http://" + localhost + "/server/chitietdonhang.php";
}
