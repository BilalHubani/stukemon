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
@WebServlet(name = "NewStukemon", urlPatterns = {"/NewStukemon"})
public class NewStukemon extends HttpServlet {
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
            out.println("<title>NewStukemon</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>New Stukemon</h1>");
            // Comprobamos si han pulsado el botón
            if ("Guardar".equals(request.getParameter("alta"))) {
                // Tenemos que crear el stukemon
                //String name, String type, String ability, int attack, int defense, int speed, int life, int level
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                String ability = request.getParameter("ability");
                int attack = Integer.parseInt(request.getParameter("attack"));
                int defense = Integer.parseInt(request.getParameter("defense"));
                int speed = Integer.parseInt(request.getParameter("speed"));
                int life = Integer.parseInt(request.getParameter("life"));
                String trainer = request.getParameter("trainer");
                Pokemon stukemon = new Pokemon(name, type, ability, attack, defense, speed, life, 0);
                Trainer tr = ejb.getTrainerByName(trainer);
                stukemon.setTrainer(tr);
                if (ejb.insertStukemon(stukemon)) {
                    out.println("<p>Stukemon dado de alta</p>");
                } else {
                    out.println("<p>Ya existe un Stukemon con este nombre</p>");
                }
            } else {
                // Creamos el formulario de alta de un trainer
                out.println("<form method=\"POST\">");
                out.println("Name: <input type=\"text\" name=\"name\" required><br>");
                out.println("Type: <input type=\"text\" name=\"type\" required><br>");
                out.println("Ability: <input type=\"text\" name=\"ability\" required><br>");
                out.println("Attack: <input type=\"number\" name=\"attack\" required><br>");
                out.println("Defense: <input type=\"number\" name=\"defense\" required><br>");
                out.println("Speed: <input type=\"number\" name=\"speed\" required><br>");
                out.println("Life: <input type=\"number\" name=\"life\" required><br>");
                out.println("Trainer: <select name=\"trainer\">");
                // Leemos los trainers de la base de datos
                List<Trainer> trainers = ejb.selectAllTrainers();
                for (Trainer t : trainers) {
                    if(t.getPokemonCollection().size() <6){
                    out.println("<option>" + t.getName() + "</option>");
                    }
                }
                out.println("</select><br>");
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
