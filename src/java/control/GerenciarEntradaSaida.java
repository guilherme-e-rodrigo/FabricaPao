/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.EnderecoDAO;
import dao.EntradaSaidaDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
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
import model.Endereco;
import model.EntradaSaida;
import model.Fornecedor;
import model.Produto;
import model.Usuario;



/**
 *
 * @author gui_v
 */
@WebServlet(name = "GerenciarEntradaSaida", urlPatterns = {"/GerenciarEntradaSaida"})
public class GerenciarEntradaSaida extends HttpServlet {

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
        
        if (acao.equals("Cadastrar")) {
        try {
            doGet(request, response);
            int idProduto = Integer.valueOf(request.getParameter("produto"));
            ProdutoDAO pdao = new ProdutoDAO();
            Produto produto = new Produto();
            try {
                produto = pdao.getOneById(idProduto);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
            }
            int qtd = Integer.valueOf(request.getParameter("quantidade"));
            boolean entrada = Boolean.valueOf(request.getParameter("entrada"));
            EntradaSaida es = new EntradaSaida();
            es.setEntrada(entrada);
            es.setQuantidade(qtd);
            es.setProduto(produto);
            EntradaSaidaDAO esdao = new EntradaSaidaDAO();
            esdao.Cadastra(es);
            response.sendRedirect("controleEntradaSaida.jsp");
        } catch(Exception e){
            
        }
            
       }else if(acao.equals("Excluir")) {
           
           String id = request.getParameter("id_editar");
           
            try {
                
                EntradaSaidaDAO dao = new EntradaSaidaDAO();
                dao.remove(Integer.valueOf(id));
                
                response.sendRedirect("controleEntradaSaida.jsp");

                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       } else if(acao.equals("Editar")) {
            String id = request.getParameter("id_editar");
            try {
                
                EntradaSaidaDAO dao = new EntradaSaidaDAO();
                EntradaSaida estoque = dao.getOneById(Integer.valueOf(id));
                
                if(estoque != null) {
                    request.setAttribute("estoque", estoque);
                    RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("editarEntradaSaida.jsp");
                    requestDispatcher.forward(request, response);
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
       } else if(acao.equals("Alterar")) {
            try {
                ProdutoDAO prodDAO = new ProdutoDAO();
                Produto prod = new Produto();
                int id_produto = Integer.valueOf(request.getParameter("produto"));
                prod = prodDAO.getOneById(id_produto);
                
                int quantidade = Integer.valueOf(request.getParameter("quantidade"));
                boolean isEntrada = Boolean.valueOf(request.getParameter("entrada"));
                EntradaSaida estoque = new EntradaSaida();
                estoque.setEntrada(isEntrada);
                estoque.setProduto(prod);
                estoque.setQuantidade(quantidade);
                
                try {
                    EntradaSaidaDAO dao = new EntradaSaidaDAO();
                    dao.Cadastra(estoque);
                    
                    RequestDispatcher requestDispatcher = request
                            .getRequestDispatcher("controleEntradaSaida.jsp");
                    requestDispatcher.forward(request, response);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciarEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarEntradaSaida.class.getName()).log(Level.SEVERE, null, ex);
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
