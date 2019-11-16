package control;

import dao.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Criptografia;

@WebServlet(name = "GerenciaCliente", urlPatterns = {"/GerenciaCliente"})
public class GerenciaCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String acao = request.getParameter("acao");
        PrintWriter out = response.getWriter();
        if (acao.equals("Cadastrar")) {
            
            if (validaEmail(request.getParameter("email"))) {
                out.println("<html><body><script>alert('Email Inválido!');location.href='cadastraCliente.jsp';</script></body></html>");                
            } else {
                Cliente cliente = new Cliente();
                cliente.setCep(request.getParameter("cep"));
                cliente.setCpf(request.getParameter("cpf"));
                cliente.setEmail(request.getParameter("email"));
                cliente.setLogin(request.getParameter("login"));
                cliente.setNome(request.getParameter("nome"));
                Criptografia criptografia = new Criptografia();
                String senha = request.getParameter("senha");
                String chave = criptografia.genKey(senha.length());
                senha = criptografia.criptografa(senha, chave);
                cliente.setSenha(senha);
                cliente.setChave_senha(chave);
                cliente.setAdm(false);
                cliente.setTelefone(request.getParameter("telefone"));
                try {
                    ClienteDAO dao = new ClienteDAO();
                    dao.Cadastra(cliente);
                    out.println("<html><body><script>alert('Cadastro Efetuado!');location.href='login.jsp';</script></body></html>");
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(GerenciaCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } else if (acao.equals("Excluir")) {
            int id = Integer.valueOf(request.getParameter("id_excluir"));
            try {
                ClienteDAO dao = new ClienteDAO();
                dao.remove(id);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher rd = request.getRequestDispatcher("gerenciaClientes.jsp");
            rd.forward(request, response);
            
        } else if (acao.equals("Editar")) {
            int id = Integer.valueOf(request.getParameter("id_editar"));
            Cliente cliente = new Cliente();
            for (int i = 0; i < Cliente.getClientes().size(); i++) {
                if (Cliente.getClientes().get(i).getId_cliente() == id) {
                    System.out.println("ID no for: " + Cliente.getClientes().get(i).getId_cliente());
                    try {
                        Cliente.atualizaClientes();
                        cliente.setAdm(false);
                        cliente.setCep(Cliente.getClientes().get(i).getCep());
                        //cliente.setChave_senha(Cliente.getClientes().get(posicao).getChave_senha());
                        cliente.setCpf(Cliente.getClientes().get(i).getCpf());
                        cliente.setEmail(Cliente.getClientes().get(i).getEmail());
                        cliente.setId_cliente(id);
                        cliente.setLogin(Cliente.getClientes().get(i).getLogin());
                        cliente.setNome(Cliente.getClientes().get(i).getNome());
                        //cliente.setSenha(Cliente.getClientes().get(posicao).getSenha());
                        cliente.setTelefone(Cliente.getClientes().get(i).getTelefone());
                        request.setAttribute("cliente", cliente);
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(GerenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("editaCliente.jsp");
            rd.forward(request, response);
            
        } else if (acao.equals("Salvar")) {
            Cliente cliente = new Cliente();
            cliente.setId_cliente(Integer.valueOf(request.getParameter("id")));
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setTelefone(request.getParameter("telefone"));
            cliente.setCep(request.getParameter("cep"));
            cliente.setLogin(request.getParameter("login"));
            cliente.setEmail(request.getParameter("email"));
            try {
                ClienteDAO dao = new ClienteDAO();
                dao.edita(cliente);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    private boolean validaEmail(String email){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email);
        if (m.find() && m.group().equals(email)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
