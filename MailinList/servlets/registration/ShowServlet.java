package servlets.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/ShowServlet")
public class ShowServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet zeigt alle User</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Registrierte User</h1>");
        out.println("<ul>");
        ServletContext ctx = getServletContext();
        try
        {
            ArrayList<Person> personenListe = (ArrayList<Person>) ctx.getAttribute("personenliste");
            for (Person person : personenListe)
            {
                out.println("<li>" + person.getUsername() + "</li>");
                System.out.println(person);
            }
        }
        catch (Exception e)
        {

        }
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");

    }

}
