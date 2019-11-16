package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cliente;
import model.Criptografia;

@WebServlet(name = "GerenciaLogin", urlPatterns = {"/GerenciaLogin"})
public class GerenciaLogin extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        int id = 0;
        if (acao.equals("Login")) {
            try {
                boolean logou = false;
                boolean adm = false;
                Cliente.atualizaClientes();
                Criptografia criptografia = new Criptografia();
                for (int i = 0; i < Cliente.getClientes().size(); i++) {
                    String senhaDecp = criptografia.decriptografa(Cliente.getClientes().get(i).getSenha(), Cliente.getClientes().get(i).getChave_senha());
                    if (Cliente.getClientes().get(i).getLogin().equals(login) && senha.equals(senhaDecp)) {
                        logou = true;
                        id = Cliente.getClientes().get(i).getId_cliente();
                        if (Cliente.getClientes().get(i).isAdm()) {
                            adm = true;
                        }
                    }
                }
                if (logou == true) {
                    HttpSession session = request.getSession();
                    session.setAttribute("login", login);
                    session.setAttribute("senha", senha);
                    session.setAttribute("id_cliente", id);
                    if (adm) {
                        out.println("<html><body><script>alert('Login Efetuado!');location.href='indexAdm.jsp';</script></body></html>");
                    } else {
                        out.println("<html><body><script>alert('Login Efetuado!');location.href='index.jsp';</script></body></html>");
                    }
                    
                } else {
                    out.println("<html><body><script>alert('Login ou Senha Incorretos!');location.href='login.jsp';</script></body></html>");
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
