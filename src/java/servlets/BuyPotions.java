/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bean.SessionStukemon;
import entities.Pokemon;
import entities.Trainer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dam
 */
@WebServlet(name = "BuyPotions", urlPatterns = {"/BuyPotions"})
public class BuyPotions extends HttpServlet {
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
            out.println("<title>BuyPotions</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Buy Potions</h1>");
            // Comprobamos si han pulsado el botón
            if ("Add".equals(request.getParameter("alta"))) {
                String trainer = request.getParameter("trainer");
                int potions = Integer.parseInt(request.getParameter("potions"));
                Trainer tr = ejb.getTrainerByName(trainer);
                if (ejb.setTrainerPotions(tr, potions)) {
                    out.println("<p>El trainer tiene ahora "+ (tr.getPotions()+potions) +" pociones</p>");
                } else {
                    out.println("<p>Al entrenador le faltan "+ (10*potions-tr.getPoints()) +" puntos</p>");
                }
            }
                out.println("<form method=\"POST\">");
                out.println("Trainer: <select name=\"trainer\">");
                // Leemos los trainers de la base de datos
                List<Trainer> trainers = ejb.selectAllTrainers();
                for (Trainer t : trainers) {
                    out.println("<option>" + t.getName() + "</option>");
                    
                }
                out.println("</select><br>");
                out.println("N. Potions: <input type=\"number\" min=0 name=\"potions\" required><br>");
                out.println("<input type=\"submit\" name=\"alta\" value=\"Add\">");
                out.println("</form>");
            
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
