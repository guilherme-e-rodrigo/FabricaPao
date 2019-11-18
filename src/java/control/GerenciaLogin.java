/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.EnderecoDAO;
import dao.FornecedorDAO;
import dao.UsuarioDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Criptografia;
import model.Endereco;
import model.Fornecedor;
import model.Usuario;



/**
 *
 * @author gui_v
 */
@WebServlet(name = "GerenciaLogin", urlPatterns = {"/GerenciaLogin"})
public class GerenciaLogin extends HttpServlet {

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
        
        String acao = request.getParameter("acao"); //busca o value do botao clicado
        
        if (acao.equals("Login")) {
        
            doGet(request, response);
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            
            if(login.equals("admin") && senha.equals("admin")) {
                RequestDispatcher requestDispatcher = request
	        .getRequestDispatcher("controleEstoque.html");
                requestDispatcher.forward(request, response);
            }
            
            Criptografia criptografia = new Criptografia();
            UsuarioDAO usuarioDAO;
            try {
                usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.getOneByLogin(login);
                if(usuario != null) {
                    String senha_decriptografada = criptografia.decriptografa(usuario.getSenha(), usuario.getChave_senha());
                    if(senha_decriptografada.equals(senha) && usuario.getLogin().equals(login)) {
                        RequestDispatcher requestDispatcher = request
	                .getRequestDispatcher("controleEstoque.html");
                        requestDispatcher.forward(request, response);
                    }
                } else {
                    RequestDispatcher requestDispatcher = request
	                .getRequestDispatcher("index.html");
                        requestDispatcher.forward(request, response);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciaLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }    
        
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
