/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.EnderecoDAO;
import dao.FornecedorDAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;

import java.io.IOException;
import java.sql.Date;
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
import model.Fornecedor;
import model.Produto;
import model.Usuario;



/**
 *
 * @author gui_v
 */
@WebServlet(name = "GerenciarProduto", urlPatterns = {"/GerenciarProduto"})
public class GerenciarProduto extends HttpServlet {

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
            
            
            
            String nome = request.getParameter("nome");
            String peso = request.getParameter("peso");
            String data = request.getParameter("data_producao");
            String dias_validade = request.getParameter("validade_dias");
            
            Produto produto = new Produto();
            produto.setData_producao(Date.valueOf(data));
            produto.setNome(nome);
            produto.setPeso(Float.valueOf(peso));
            produto.setValidade_dias(Integer.valueOf(dias_validade));
            
            ProdutoDAO produtoDAO = new ProdutoDAO();
            produtoDAO.Cadastra(produto);
            
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
        }   catch (SQLException ex) {
                Logger.getLogger(GerenciarProduto.class.getName()).log(Level.SEVERE, null, ex);
            }
        RequestDispatcher requestDispatcher = request
        .getRequestDispatcher("controleProdutos.jsp");
        requestDispatcher.forward(request, response);
        
       } else if(acao.equals("Excluir")) {
           
           String id = request.getParameter("id_editar");
           
            try {
                
                ProdutoDAO produtoDAO = new ProdutoDAO();
                System.out.println(id);
                produtoDAO.remove(Integer.valueOf(id));
                
                RequestDispatcher requestDispatcher = request
	        .getRequestDispatcher("controleProdutos.jsp");
                requestDispatcher.forward(request, response);
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       } else if(acao.equals("Editar")) {
            String id = request.getParameter("id_editar");
            try {
                
                ProdutoDAO produtoDAO = new ProdutoDAO();
                Produto produto = produtoDAO.getOneById(Integer.valueOf(id));
                
                if(produto != null) {
                    request.setAttribute("produto", produto);
                    RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("editarProduto.jsp");
                    requestDispatcher.forward(request, response);
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
       } else if(acao.equals("Alterar")) {
           
            String nome = request.getParameter("nome");
            String peso = request.getParameter("peso");
            String data_producao = request.getParameter("data_producao");
            String validade = request.getParameter("validade_dias");
            String id = request.getParameter("id");
           
            try {
                
                ProdutoDAO produtoDAO = new ProdutoDAO();
                Produto produto = produtoDAO.getOneById(Integer.valueOf(id));
                produto.setData_producao(Date.valueOf(data_producao));
                produto.setNome(nome);
                produto.setPeso(Float.valueOf(peso));
                produto.setValidade_dias(Integer.valueOf(validade));
                produto.setFornecedor(null);
                produtoDAO.edita(produto);
                
                RequestDispatcher requestDispatcher = request
                .getRequestDispatcher("controleProdutos.jsp");
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
