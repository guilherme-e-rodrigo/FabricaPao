package control;

import dao.PedidoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Livro;
import model.Pedido;

@WebServlet(name = "GerenciaPedido", urlPatterns = {"/GerenciaPedido"})
public class GerenciaPedido extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        
        
        if (acao.equals("Comprar")) {
            session.setAttribute("id_livro", request.getParameter("id"));
            out.println("<html><body><script>location.href='realizarPedido.jsp';</script></body></html>");
        } else if (acao.equals("Confirmar Pedido")) {
            
            
            try {
                PedidoDAO dao = new PedidoDAO();
                Pedido pedido = new Pedido();
                java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                pedido.setData(date);
                int id_cliente = (int) session.getAttribute("id_cliente");
                pedido.setId_cliente(id_cliente);
                String id_livro = (String) session.getAttribute("id_livro");
                pedido.setId_livro(Integer.valueOf(id_livro));
                int quantidade = Integer.valueOf(request.getParameter("qtd"));
                pedido.setQuantidade(quantidade);
                float total = Livro.getLivros().get(Integer.valueOf(id_livro)).getValor() * quantidade;
                pedido.setTotal(total);
                dao.Cadastra(pedido);
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<html><body><script>location.href='index.jsp';</script></body></html>");

        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
