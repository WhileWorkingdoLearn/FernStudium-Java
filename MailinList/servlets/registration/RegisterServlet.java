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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet
{

    @SuppressWarnings("unchecked")
    private synchronized void addUser(String name, String pw)
    {

        if (name != null && pw != null)
        {
            System.out.println("[RegisterServlet]: Person Created");
            Person newPerson = new Person(name, pw);
            ServletContext ctx = getServletContext();
            ArrayList<Person> personenListe = (ArrayList<Person>) ctx.getAttribute("personenliste");
            personenListe.add(newPerson);
            System.out.println(newPerson.getUsername());
            for (Person person : personenListe)
            {
                System.out.println(person);
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
        out.println("<title>Servlet zur Registrierung</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Anmeldung</h1>");
        out.println("<form method=\"post\">");
        out.println("<ul>");
        out.println("<li>Geben Sie ihren Namen ein <input type= \"text\" name=\"benutzername\" size=\"40\"></li>");
        out.println("<li>Geben Sie ihr Password ein <input type= \"password\" name=\"passwort\" size=\"40\"></li>");
        // out.println("<label for=\"User\">User Name:</label>");

        out.println("</ul>");
        out.println("<input type=\"submit\" value=\"Absenden\"/>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        String userName = request.getParameter("benutzername");
        String pw = request.getParameter("passwort");
        // addUser(userName, pw);

        if (userName != null && pw != null && pw.length() > 0)
        {
            System.out.println("[RegisterServlet]: Person Created");
            Person newPerson = new Person(userName, pw);
            ServletContext ctx = getServletContext();
            ArrayList<Person> personenListe = (ArrayList<Person>) ctx.getAttribute("personenliste");
            personenListe.add(newPerson);
            System.out.println(newPerson.getUsername());
            for (Person person : personenListe)
            {
                System.out.println(person);
            }

        }

    }

}
