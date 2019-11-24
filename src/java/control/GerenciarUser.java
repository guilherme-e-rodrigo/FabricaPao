/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Criptografia;
import model.Usuario;

/**
 *
 * @author gui_v
 */
@WebServlet(name = "GerenciarUser", urlPatterns = {"/GerenciarUser"})
public class GerenciarUser extends HttpServlet {

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
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet GerenciarUser</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet GerenciarUser at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
        System.out.print(acao);
        if (acao.equals("Add")) {
            try {
                doGet(request, response);
                String nome = request.getParameter("nome");
                String login = request.getParameter("login");
                String senha = request.getParameter("senha");
                String is_admin = request.getParameter("radioAdmin");

                Criptografia criptografia = new Criptografia();
                String chave = criptografia.genKey(senha.length());
                String senha_criptografada = criptografia.criptografa(senha, chave);

                Usuario usuario = new Usuario();
                usuario.setChave_senha(chave);
                usuario.setIs_admin(Boolean.valueOf(is_admin));
                usuario.setLogin(login);
                usuario.setNome(nome);
                usuario.setSenha(senha_criptografada);

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                usuarioDAO.Cadastra(usuario);
                
                RequestDispatcher requestDispatcher = request
	        .getRequestDispatcher("controleUsuarios.jsp");
                requestDispatcher.forward(request, response);

            } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
            }   catch (SQLException ex) {
                    Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
                }
       } else if(acao.equals("Excluir")) {
           
           String id = request.getParameter("id_editar");
           
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.remove(Integer.valueOf(id));
                
                RequestDispatcher requestDispatcher = request
	        .getRequestDispatcher("controleUsuarios.jsp");
                requestDispatcher.forward(request, response);
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       } else if(acao.equals("Editar")) {
            String id = request.getParameter("id_editar");
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.getOneById(Integer.valueOf(id));
                if(usuario != null) {
                    Criptografia criptografia = new Criptografia();
                    String senha = criptografia.decriptografa(usuario.getSenha(), usuario.getChave_senha());
                    usuario.setSenha(senha);
                    request.setAttribute("usuario", usuario);
                    RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("editarUsuario.jsp");
                    requestDispatcher.forward(request, response);
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
       } else if(acao.equals("Alterar")) {
           String nome = request.getParameter("nome");
           String login = request.getParameter("login");
           String is_admin = request.getParameter("radioAdmin");
           String id = request.getParameter("id");
           String senha = request.getParameter("senha");
           
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuario = usuarioDAO.getOneById(Integer.valueOf(id));
                usuario.setLogin(login);
                usuario.setNome(nome);
                usuario.setIs_admin(Boolean.valueOf(is_admin));
                Criptografia criptografia = new Criptografia();
                String nova_chave = criptografia.genKey(senha.length());
                String nova_senha = criptografia.criptografa(senha, nova_chave);
                usuario.setChave_senha(nova_chave);
                usuario.setSenha(nova_senha);
                usuarioDAO.edita(usuario);
                RequestDispatcher requestDispatcher = request
                .getRequestDispatcher("controleUsuarios.jsp");
                requestDispatcher.forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
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
