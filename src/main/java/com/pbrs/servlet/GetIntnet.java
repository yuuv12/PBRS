package com.pbrs.servlet;
/*
Date: 5/27/2024
Author: lu0qlng
Description: 获取客户端ip地址，用于展示和远程链接
*/

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.InetAddress;

@WebServlet("/getIn")
public class GetIntnet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //获取本机的InetAddress实例
        InetAddress address =InetAddress.getLocalHost();
        System.out.println(address.getHostName());//获取计算机名
        System.out.println(address.getHostAddress());//获取IP地址

//        byte[] bytes = address.getAddress();//获取字节数组形式的IP地址,以点分隔的四部分
//
//         获取其他主机的InetAddress实例
//        InetAddress address2 =InetAddress.getByName("其他主机名");
//        InetAddress address3 =InetAddress.getByName("IP地址");
    }
}
