package javauction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import javauction.model.customer;
import javauction.model.OpStatus;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as register.do
 */
public class register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // prepare variables
        OpStatus status;
        String next_page = "/register.jsp";
        Connection db = (Connection) getServletContext().getAttribute("db");


        // get the user input
        customer user = new customer();
        user.username = request.getParameter("username");
        user.email = request.getParameter("email");
        user.name = request.getParameter("name");
        user.lastname = request.getParameter("lastname");
        user.password = request.getParameter("password");
        user.vat = request.getParameter("vat");
        user.phonenumber = request.getParameter("phone");
        user.homeaddress = request.getParameter("address");
        user.city = request.getParameter("city");
        user.country = request.getParameter("country");
        user.postcode = request.getParameter("postcode");
        user.latitude = request.getParameter("latitude");
        user.longitude = request.getParameter("longitude");

        // tell the customer to register a new user
        status = user.register(request.getParameter("repeat_password"), db);

        // generate the message that we want to send to the user
        if (status == OpStatus.Success) {
            request.setAttribute("regStatus", "Successfully registered");
            next_page = "/welcome.jsp";
        } else if (status == OpStatus.Error) {
            request.setAttribute("regStatus", "There was an error");
        } else if (status == OpStatus.UsernameExist) {
            request.setAttribute("regStatus", "This username already exist");
        } else if (status == OpStatus.DiffPass) {
            request.setAttribute("regStatus", "You didn't provide the same password");
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
