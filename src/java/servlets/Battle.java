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
@WebServlet(name = "Battle", urlPatterns = {"/Battle"})
public class Battle extends HttpServlet {
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
            out.println("<title>Battle</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Battle</h1>");
            // Comprobamos si han pulsado el botón
            if ("Select".equals(request.getParameter("alta4"))) {
                String trainer = request.getParameter("trainer1");
                String trainer2 = request.getParameter("trainer2");
                String stukemon = request.getParameter("stukemon1");
                String stukemon2 = request.getParameter("stukemon2");
                Trainer tr = ejb.getTrainerByName(trainer);
                Trainer tr2 = ejb.getTrainerByName(trainer2);
                Pokemon poke = ejb.getStukemonByName(stukemon);
                Pokemon poke2 = ejb.getStukemonByName(stukemon2);
                boolean winner = true;
                if (poke.getSpeed()>poke2.getSpeed()) {
                    // true gana poke, false gana poke2
                    
                    int attack = poke.getAttack()+(2*poke.getLevel() - (poke2.getDefense()/3));
                    poke2.setLife(poke2.getLife()-attack);
                    if(poke2.getLife()<0)
                        poke2.setLife(0);
                    if(poke2.getLife()==0){
                        winner = true;
                    }else{
                        int attack2 = poke2.getAttack()+(2*poke2.getLevel() - (poke.getDefense()/3));
                        poke.setLife(poke.getLife()-attack2);
                        if(poke.getLife()<0)
                            poke.setLife(0);                        
                    }
                            
                }else if(poke.getSpeed()<poke2.getSpeed()){
                    int attack2 = poke2.getAttack()+(2*poke2.getLevel() - (poke.getDefense()/3));
                        poke.setLife(poke.getLife()-attack2);
                        if(poke.getLife()<0)
                            poke.setLife(0);
                }else{
                    
                }
                out.println("<h3>Vida de "+poke.getName()+" "+poke.getLife()+"</h3>");
                out.println("<h3>Vida de "+poke2.getName()+" "+poke2.getLife()+"</h3>");
                if(winner){
                    out.println("<h3>Gana "+trainer+"</h3>");
                }else{
                    out.println("<h3>Gana "+trainer2+"</h3>");
                }
            }else if ("Select".equals(request.getParameter("alta3"))) {
                String trainer = request.getParameter("trainer1");
                String trainer2 = request.getParameter("trainer2");
                String stukemon = request.getParameter("stukemon1");
                Trainer tr2 = ejb.getTrainerByName(trainer2);
                out.println("<h3>"+trainer+"</h3>");                
                out.println("<h3>"+stukemon+"</h3>");
                out.println("<h3>"+trainer2+"</h3>");
                out.println("<form method=\"POST\">");
                out.println("Stukemon: <select name=\"stukemon\">");
                    for (Pokemon p : tr2.getPokemonCollection()) {
                        out.println("<option value='"+p.getName()+"'>" + p.getName() + " </option>");
                    }
                out.println("</select><br>");
                out.println("<input type=\"hidden\" name=\"trainer1\" value=\""+trainer+"\" />");
                out.println("<input type=\"hidden\" name=\"stukemon1\" value=\""+stukemon+"\" />");
                out.println("<input type=\"hidden\" name=\"trainer2\" value=\""+trainer2+"\" />");
                out.println("<input type=\"submit\" name=\"alta4\" value=\"Select\">");
                out.println("</form>");
            }else if ("Select".equals(request.getParameter("alta2"))) {
                String trainer = request.getParameter("trainer1");
                out.println("<h3>"+trainer+"</h3>");
                String stukemon = request.getParameter("stukemon1");
                out.println("<h3>"+stukemon+"</h3>");
                out.println("<form method=\"POST\">");
                out.println("Trainer 2: <select name=\"trainer2\">");
                // Leemos los trainers de la base de datos
                List<Trainer> trainers = ejb.selectAllTrainers();
                for (Trainer t : trainers) {
                    if(t.getPokemonCollection().size()>0 && !t.getName().equalsIgnoreCase(trainer))
                    out.println("<option>" + t.getName() + "</option>");                    
                }
                out.println("</select><br>");
                out.println("<input type=\"hidden\" name=\"trainer1\" value=\""+trainer+"\" />");
                out.println("<input type=\"hidden\" name=\"stukemon1\" value=\""+stukemon+"\" />");
                out.println("<input type=\"submit\" name=\"alta3\" value=\"Select\">");
                out.println("</form>");
            }else if ("Select".equals(request.getParameter("alta"))) {
                String trainer = request.getParameter("trainer");
                Trainer tr = ejb.getTrainerByName(trainer);
                out.println("<h3>"+trainer+"</h3>");
                out.println("<form method=\"POST\">");
                out.println("Stukemon: <select name=\"stukemon1\">");
                    for (Pokemon p : tr.getPokemonCollection()) {
                        out.println("<option value='"+p.getName()+"'>" + p.getName() + "</option>");
                    }
                out.println("</select><br>");
                out.println("<input type=\"hidden\" name=\"trainer1\" value=\""+trainer+"\" />");
                out.println("<input type=\"submit\" name=\"alta2\" value=\"Select\">");
                out.println("</form>");
//                out.println("Trainer 2: <select name=\"trainer\">");
//                // Leemos los trainers de la base de datos
//                List<Trainer> trainers = ejb.selectAllTrainers();
//                for (Trainer t : trainers) {
//                    if(t.getPokemonCollection().size()>0 && !t.getName().equalsIgnoreCase(trainer))
//                    out.println("<option>" + t.getName() + "</option>");                    
//                }
//                out.println("</select><br>");
//                out.println("<input type=\"submit\" name=\"alta\" value=\"Battle\">");
//                out.println("</form>");
            }else{
                out.println("<form method=\"POST\">");
                out.println("Trainer 1: <select name=\"trainer\">");
                // Leemos los trainers de la base de datos
                List<Trainer> trainers = ejb.selectAllTrainers();
                for (Trainer t : trainers) {
                    if(t.getPokemonCollection().size()>0)
                    out.println("<option>" + t.getName() + "</option>");                    
                }
                out.println("</select><br>");
                out.println("<input type=\"submit\" name=\"alta\" value=\"Select\">");
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
