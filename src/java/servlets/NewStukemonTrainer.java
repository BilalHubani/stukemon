/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.SessionStukemon;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entities.Trainer;
import javax.ejb.EJB;

/**
 *
 * @author dam
 */
@WebServlet(name = "NewStukemonTrainer", urlPatterns = {"/NewStukemonTrainer"})
public class NewStukemonTrainer extends HttpServlet {

    @EJB
    SessionStukemon ejb;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>NewStukemonTrainer</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>New Stukemon Trainer</h1>");
            // Comprobamos si han pulsado el botón
            if ("Guardar".equals(request.getParameter("alta"))) {
                // Tenemos que crear el trainer
                String name = request.getParameter("name");
                int pokeballs = Integer.parseInt(request.getParameter("pokeballs"));
                int potions = Integer.parseInt(request.getParameter("potions"));
                Trainer trainer = new Trainer(name, pokeballs, potions, 0);

                if (ejb.insertTrainer(trainer)) {
                    out.println("<p>Stukemon trainer dado de alta</p>");
                } else {
                    out.println("<p>Ya existe un Stukemon trainer con este nombre</p>");
                }
            } else {
                // Creamos el formulario de alta de un trainer
                out.println("<form method=\"POST\">");
                out.println("Name: <input type=\"text\" name=\"name\" required><br>");
                out.println("N. Pokeballs: <input type=\"number\" name=\"pokeballs\" required><br>");
                out.println("N. Potions: <input type=\"number\" name=\"potions\" required><br>");
                out.println("<input type=\"submit\" name=\"alta\" value=\"Guardar\">");
                out.println("</form>");
            }
            out.println("<br>");
            out.println("<form action=\"index.html\">");
                out.println("<input type=\"submit\" value=\"Main menu\">");
                out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
