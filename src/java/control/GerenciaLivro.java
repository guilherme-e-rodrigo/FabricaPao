package control;

import dao.LivroDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Livro;

@WebServlet(name = "GerenciaLivro", urlPatterns = {"/GerenciaLivro"})
public class GerenciaLivro extends HttpServlet {

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
        if (acao.equals("Excluir")) {
            
            int id = Integer.valueOf(request.getParameter("id_excluir"));
            try {
                LivroDAO dao = new LivroDAO();
                dao.remove(id);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestDispatcher rd = request.getRequestDispatcher("gerenciaLivros.jsp");
            rd.forward(request, response);
        } else if (acao.equals("Cadastrar Livro")) {
            String url = "img/padrao.png";
            request.setAttribute("url", url);
            RequestDispatcher rd = request.getRequestDispatcher("cadastraLivro.jsp");
            rd.forward(request, response);
        } else if (acao.equals("Cadastrar")) {
            String url = request.getParameter("url");
            System.out.println(url);
            Livro livro = new Livro();
            livro.setAno(Integer.valueOf(request.getParameter("ano")));
            livro.setAutor(request.getParameter("autor"));
            livro.setEditora(request.getParameter("editora"));
            livro.setTitulo(request.getParameter("titulo"));
            livro.setValor(Float.valueOf(request.getParameter("valor")));
            try {
                LivroDAO dao = new LivroDAO();
                dao.Cadastra(livro);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("<html><body><script>alert('Cadastro Efetuado!');location.href='gerenciaLivros.jsp';</script></body></html>");
        } else if (acao.equals("Editar")) {
            int id = Integer.valueOf(request.getParameter("id_editar"));
            System.out.println("ID antes do for:" + id);
            Livro livro = new Livro();
            for (int i = 0; i < Livro.getLivros().size(); i++) {
                System.out.println("Entrou no for");
                if (id == Livro.getLivros().get(i).getId()) {
                    System.out.println("ID no for: " + Livro.getLivros().get(i).getId());
                    try {
                        Livro.atualizaLivros();
                        livro.setAno(Livro.getLivros().get(i).getAno());
                        livro.setAutor(Livro.getLivros().get(i).getAutor());
                        livro.setEditora(Livro.getLivros().get(i).getEditora());
                        livro.setId(Livro.getLivros().get(i).getId());
                        livro.setTitulo(Livro.getLivros().get(i).getTitulo());
                        livro.setUrl(Livro.getLivros().get(i).getUrl());
                        livro.setValor(Livro.getLivros().get(i).getValor());
                        request.setAttribute("livro", livro);
                    } catch (ClassNotFoundException | SQLException ex) {
                         Logger.getLogger(GerenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("editaLivro.jsp");
            rd.forward(request, response);
        } else if (acao.equals("Salvar")){
            Livro livro = new Livro();
            livro.setId(Integer.valueOf(request.getParameter("id")));
            livro.setAno(Integer.valueOf(request.getParameter("ano")));
            livro.setAutor(request.getParameter("autor"));
            livro.setEditora(request.getParameter("editora"));
            livro.setTitulo(request.getParameter("titulo"));
            livro.setValor(Float.valueOf(request.getParameter("valor")));
            try{
                LivroDAO dao = new LivroDAO();
                dao.edita(livro);
                out.println("<html><body><script>alert('Livro Editado!');location.href='gerenciaLivros.jsp';</script></body></html>");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(GerenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
