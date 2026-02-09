package com.wipro.book.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.wipro.book.bean.BookBean;

@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        BookBean book = (BookBean) session.getAttribute("book");

        out.println("<html><body>");
        out.println("<h2>Book Details</h2>");
        out.println("ISBN: " + book.getIsbn() + "<br>");
        out.println("Title: " + book.getBookName() + "<br>");
        out.println("Type: " + book.getBookType() + "<br>");
        out.println("Author: " + book.getAuthor().getAuthorName() + "<br>");
        out.println("Contact: " + book.getAuthor().getContactNo() + "<br>");
        out.println("Cost: " + book.getCost());
        out.println("</body></html>");
    }
}
