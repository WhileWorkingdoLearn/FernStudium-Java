package servlets.registration;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersonenInitializer implements ServletContextListener
{
    private ArrayList<Person> regUser;

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        ServletContext ctx = event.getServletContext();
        regUser = (ArrayList) ctx.getAttribute("personenliste");
        regUser.clear();
    }

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        regUser = new ArrayList<Person>();
        ServletContext ctx = event.getServletContext();
        ctx.setAttribute("personenliste", regUser);
        System.out.println("[Servlet:PersonenInitializer] personenliste created");
    }

}
