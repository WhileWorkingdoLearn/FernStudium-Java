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
 * Servlet implementation class SignOffServlet
 */
@WebServlet("/SignOffServlet")
public class SignOffServlet extends HttpServlet
{

    private synchronized void removeUser(String name, String pw)
    {

        if (name != null && pw != null)
        {
            ServletContext ctx = getServletContext();
            ArrayList<Person> personenListe = (ArrayList<Person>) ctx.getAttribute("personenliste");
            Person person = null;
            for (Person checkPerson : personenListe)
            {
                if (checkPerson.getUsername().equalsIgnoreCase(name) && checkPerson.isPasswordValid(pw))
                {
                    person = checkPerson;
                    // personenListe.remove(person);
                    break;
                }
            }
            if (person != null)
            {
                personenListe.remove(person);
            }
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
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
        out.println("<title>Servlet zum Abmelden</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Abmeldung</h1>");
        out.println("<form method=\"post\">");
        out.println("<ul>");
        out.println("<li>Geben Sie ihren Namen ein <input type= \"text\" name=\"benutzername\" size=\"40\"></li>");
        out.println("<li>Geben Sie ihr Password ein <input type= \"password\" name=\"passwort\" size=\"40\"></li>");
        out.println("</ul>");
        out.println("<input type=\"submit\" value=\"Absenden\"/>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        String userName = request.getParameter("benutzername");
        String pw = request.getParameter("passwort");
        removeUser(userName, pw);
        System.out.println(userName);

    }

}
