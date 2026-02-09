package com.wipro.book.servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.wipro.book.bean.BookBean;
import com.wipro.book.bean.AuthorBean;
import com.wipro.book.dao.AuthorDAO;
import com.wipro.book.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("AddBook".equals(operation)) {

            String result = addBook(request);

            if ("SUCCESS".equals(result))
                response.sendRedirect("menu.html");
            else if ("INVALID".equals(result))
                response.sendRedirect("Invalid.html");
            else
                response.sendRedirect("Failure.html");

        } else if ("Search".equals(operation)) {

            String isbn = request.getParameter("isbn");
            BookBean book = new Administrator().viewBook(isbn);

            if (book == null) {
                response.sendRedirect("Invalid.html");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("book", book);
                RequestDispatcher rd = request.getRequestDispatcher("ViewServlet");
                rd.forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private String addBook(HttpServletRequest request) {

        try {
            String isbn = request.getParameter("isbn");
            String bookName = request.getParameter("bookName");
            String bookTypeStr = request.getParameter("booktype");
            String authorName = request.getParameter("authorName");
            String costStr = request.getParameter("cost");

            if (isbn == null || isbn.trim().isEmpty() ||
                bookName == null || bookName.trim().isEmpty() ||
                bookTypeStr == null || bookTypeStr.trim().isEmpty() ||
                authorName == null || authorName.trim().isEmpty() ||
                costStr == null || costStr.trim().isEmpty()) {

                return "INVALID";
            }

            char bookType = bookTypeStr.charAt(0);
            float cost = Float.parseFloat(costStr);

            AuthorBean author = new AuthorDAO().getAuthor(authorName);
            if (author == null) {
                return "INVALID";
            }

            BookBean book = new BookBean();
            book.setIsbn(isbn.toUpperCase());
            book.setBookName(bookName);
            book.setBookType(bookType);
            book.setCost(cost);
            book.setAuthor(author);

            return new Administrator().addBook(book);

        } catch (Exception e) {
            e.printStackTrace(); 
            return "FAILURE";
        }
    }

}
