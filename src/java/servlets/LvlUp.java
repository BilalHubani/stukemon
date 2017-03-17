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
import java.util.Collection;
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
@WebServlet(name = "LvlUp", urlPatterns = {"/LvlUp"})
public class LvlUp extends HttpServlet {

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
            out.println("<title>UpgradeLife</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Upgrade Life</h1>");
            

            if ("Select".equals(request.getParameter("mod"))) {
                String trainer2 = request.getParameter("trai");
                String stukemon = request.getParameter("stukemon");
                Trainer tr2 = ejb.getTrainerByName(trainer2);
                Pokemon poke = ejb.getStukemonByName(stukemon);
                if (ejb.lvlUpLife(tr2, poke)) {
                    out.println("<p>El Stukemon tiene ahora " + (poke.getLife() + 50) + " de vida</p>");
                } else {
                    out.println("<p>Error</p>");
                }
            } else {
                
                out.println("<form method=\"POST\">");
                out.println("Trainer: <select name=\"trainer\">");
                // Leemos los trainers de la base de datos
                List<Trainer> trainers = ejb.selectAllTrainers();
                for (Trainer t : trainers) {
                    out.println("<option value='"+t.getName()+"'>" + t.getName() + " " + t.getPotions() + "</option>");
                }
                out.println("</select><br>");
                out.println("<input type=\"submit\" name=\"alta\" value=\"Add\">");
                out.println("</form><br>");
                // Comprobamos si han pulsado el botón
            if ("Add".equals(request.getParameter("alta"))) {
                String trainer = request.getParameter("trainer");
                Trainer t = ejb.getTrainerByName(trainer);
                if (t.getPokemonCollection().size()>0) {
                    out.println("<form method=\"POST\">");
                    out.println("Stukemon: <select name=\"stukemon\">");
                    for (Pokemon p : t.getPokemonCollection()) {
                        out.println("<option value='"+p.getName()+"'>" + p.getName() + " " + p.getLife() + "</option>");
                    }
                    out.println("</select><br>");
                    out.println("<input type=\"hidden\" name=\"trai\" value=\""+trainer+"\" />");
                    out.println("<input type=\"submit\" name=\"mod\" value=\"Select\">");
                }else out.println("esta vacio");
                
            }
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
