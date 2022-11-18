package com.itheima.web;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收用户名和密码
        // receive username and password
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. 调用MyBatis完成查询
        // query via myBatis
        // 2.1 获取SqlSessionFactory对象
        // acquire SqlSessionFactory object
        /*String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);*/

        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

        // 2.2 获取SqlSession对象
        // acquire SqlSession object
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 2.3 获取Mapper
        // acquire Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 2.4 调用方法
        // call Mapper method
        User user = userMapper.select(username, password);
        // 2.5 释放资源
        // release sqlSession resource
        sqlSession.close();

        // 获取字符输出流，并设置对应的content-type
        // acquire output stream, and set content-type accordingly
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // 3. 判断user是否为null
        // determine if user is null
        if (user != null) {
            // 登录成功 Login Successful
            writer.write("登陆成功 Login Successful");
        }else {
            // 登录失败 Login Failed
            writer.write("登录失败 Login Failed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
