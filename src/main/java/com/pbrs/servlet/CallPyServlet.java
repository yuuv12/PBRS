package com.pbrs.servlet;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 用于获取网页请求、调用python程序并接收结果，字符串形式
*/

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


@WebServlet(urlPatterns = "/callPyServlet")
public class CallPyServlet extends HttpServlet{

    public static void main(String[] args) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python src/org/python/testjpserve.py");// 执行py文件

            // 用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        Process proc;
        String Bno = "1125";
        try {

            String path = Objects.requireNonNull(CallPyServlet.class.getResource("")).getPath();
            path = path.replaceFirst("/", "")+"../python/testjpserve.py";
//            System.out.println(path);

            String[] args = {"python", path, Bno};

            proc = Runtime.getRuntime().exec(args);// 执行py文件

            // 用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());

        }

    }
}