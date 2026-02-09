package com.wipro.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wipro.book.bean.BookBean;
import com.wipro.book.util.DBUtil;

public class BookDAO {

    public int createBook(BookBean bookBean) {

        String query = "INSERT INTO BOOK_TBL VALUES (?,?,?,?,?)";
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, bookBean.getIsbn().toUpperCase());
            ps.setString(2, bookBean.getBookName());
            ps.setString(3, String.valueOf(bookBean.getBookType()));
            ps.setInt(4, bookBean.getAuthor().getAuthorCode());
            ps.setFloat(5, bookBean.getCost());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BookBean fetchBook(String isbn) {

        String query = "SELECT * FROM BOOK_TBL WHERE ISBN = ?";
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, isbn.toUpperCase());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookBean book = new BookBean();
                book.setIsbn(rs.getString("ISBN"));
                book.setBookName(rs.getString("BOOK_TITLE"));
                book.setBookType(rs.getString("BOOK_TYPE").charAt(0));
                book.setCost(rs.getFloat("BOOK_COST"));
                book.setAuthor(new AuthorDAO().getAuthor(rs.getInt("AUTHOR_CODE")));
                return book;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
