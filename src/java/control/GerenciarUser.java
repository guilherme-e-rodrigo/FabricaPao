/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.EnderecoDAO;
import dao.FornecedorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Fornecedor;

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
            
            fornecedor.setEndereco(endereco);
            
            EnderecoDAO edao = new EnderecoDAO();
            try {
                edao.Cadastra(endereco);
            } catch (SQLException ex) {
                Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                FornecedorDAO dao = new FornecedorDAO();
                dao.Cadastra(fornecedor);

            } catch (Exception e) {
                System.out.println("Erro ao cadastrar fornecedor: " + e.getMessage());
            }
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciarFornecedor.class.getName()).log(Level.SEVERE, null, ex);
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
