package com.wipro.book.service;

import com.wipro.book.bean.BookBean;
import com.wipro.book.dao.BookDAO;

public class Administrator {

    public String addBook(BookBean bookBean) {

        if (bookBean == null)
            return "INVALID";

        if (bookBean.getIsbn() == null || bookBean.getIsbn().trim().isEmpty())
            return "INVALID";

        if (bookBean.getBookName() == null || bookBean.getBookName().trim().isEmpty())
            return "INVALID";

        if (bookBean.getBookType() != 'G' && bookBean.getBookType() != 'T')
            return "INVALID";

        if (bookBean.getCost() <= 0)
            return "INVALID";

        if (bookBean.getAuthor() == null)
            return "INVALID";

        int result = new BookDAO().createBook(bookBean);
        return (result == 1) ? "SUCCESS" : "FAILURE";
    }

    public BookBean viewBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty())
            return null;

        return new BookDAO().fetchBook(isbn.trim().toUpperCase());
    }
}
