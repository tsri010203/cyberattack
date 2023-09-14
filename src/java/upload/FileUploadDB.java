/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package upload;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/uploadImg")
@MultipartConfig(maxFileSize = 16177215)
public class FileUploadDB extends HttpServlet {

    private String dbURL = "jdbc:mysql://localhost:3306/osn";
    private String dbUser = "root";
    private String dbPass = "root";

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String msg = request.getParameter("msg");
        System.out.println("msg :" + msg);
        HttpSession session = request.getSession();
        session.setAttribute("Smsg", msg);
        String uid = (String) session.getAttribute("sesid");
        String name = (String) session.getAttribute("sesname");

        InputStream inputStream = null;

        Part filePart = request.getPart("photo");
        if (filePart != null) {

            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            inputStream = filePart.getInputStream();
        }

        Connection conn = null;

        try {

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);


            String sql = "INSERT INTO timeline (uid, name, msg, photo) values (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, uid);
            statement.setString(2, name);
            statement.setString(3, msg);

            if (inputStream != null) {
                statement.setBlob(4, inputStream);
            }
            int row = statement.executeUpdate();
            if (row > 0) {
                response.sendRedirect("block.jsp");
            } else {
                response.sendRedirect("time_line_up.jsp?msg=success");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}