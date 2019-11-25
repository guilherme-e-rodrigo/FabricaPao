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
@WebServlet(name = "GerenciarFornecedor", urlPatterns = {"/GerenciarFornecedor"})
public class GerenciarFornecedor extends HttpServlet {

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
            String telefone = request.getParameter("telefone");
            String email = request.getParameter("email");
            String cnpj = request.getParameter("cnpj");
            
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome(nome);
            fornecedor.setCnpj(cnpj);
            fornecedor.setEmail(email);
            fornecedor.setTelefone(telefone);
            
            Endereco endereco = new Endereco();
            
            String cep = request.getParameter("cep");
            String cidade = request.getParameter("cidade");
            String bairro = request.getParameter("bairro");
            String estado = request.getParameter("estado");
            String rua = request.getParameter("rua");
            int numero = Integer.valueOf(request.getParameter("numero"));
            String complemento = request.getParameter("complemento");
            
            endereco.setEstado(estado);
            endereco.setCidade(cidade);
            endereco.setBairro(bairro);
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);
            endereco.setCep(cep);
            
            
            
            EnderecoDAO edao = new EnderecoDAO();
            Endereco enderecoNovo = new Endereco();
            try {
                edao.Cadastra(endereco);
                enderecoNovo = edao.getOneByAddress(endereco);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                FornecedorDAO dao = new FornecedorDAO();
                fornecedor.setEndereco(enderecoNovo);
                dao.Cadastra(fornecedor);

            } catch (Exception e) {
                System.out.println("Erro ao cadastrar fornecedor: " + e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestDispatcher requestDispatcher = request
        .getRequestDispatcher("controleFornecedor.jsp");
        requestDispatcher.forward(request, response);
        
       } else if(acao.equals("Excluir")) {
           
           String id = request.getParameter("id_editar");
            System.out.println("id: "+id);
            try {
                
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                fornecedorDAO.remove(Integer.valueOf(id));
                
                RequestDispatcher requestDispatcher = request
	        .getRequestDispatcher("controleFornecedor.jsp");
                requestDispatcher.forward(request, response);
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       } else if(acao.equals("Editar")) {
            String id = request.getParameter("id_editar");
            try {
                
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                Fornecedor fornecedor = fornecedorDAO.getOneById(Integer.valueOf(id));
                
                if(fornecedor != null) {
                    request.setAttribute("fornecedor", fornecedor);
                    RequestDispatcher requestDispatcher = request
                    .getRequestDispatcher("editarFornecedor.jsp");
                    requestDispatcher.forward(request, response);
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciarUser.class.getName()).log(Level.SEVERE, null, ex);
            }
       } else if(acao.equals("Alterar")) {
           
            String nome = request.getParameter("nome");
            String telefone = request.getParameter("telefone");
            String email = request.getParameter("email");
            String cnpj = request.getParameter("cnpj");
            String id = request.getParameter("id");
           
            try {
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                Fornecedor fornecedor = fornecedorDAO.getOneById(Integer.valueOf(id));
                fornecedor.setNome(nome);
                fornecedor.setCnpj(cnpj);
                fornecedor.setEmail(email);
                fornecedor.setTelefone(telefone);
                fornecedorDAO.edita(fornecedor);
                
                RequestDispatcher requestDispatcher = request
                .getRequestDispatcher("controleFornecedor.jsp");
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
