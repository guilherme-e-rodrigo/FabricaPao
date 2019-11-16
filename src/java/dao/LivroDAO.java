package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Cliente;
import model.Livro;

public class LivroDAO {
    
    private Connection con;

    public LivroDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public void edita(Livro livro) throws SQLException{
        String sql = "update livro set titulo = ?, autor = ?, ano = ?, editora = ?, valor = ? where id_livro = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, livro.getTitulo());
        st.setString(2, livro.getAutor());
        st.setInt(3, livro.getAno());
        st.setString(4, livro.getEditora());
        st.setFloat(5, livro.getValor());
        st.setInt(6, livro.getId());
        st.execute();
        con.close();
    }

    public void Cadastra(Livro livro) throws SQLException {

        String sql = "insert into livro (titulo, autor, ano, editora, valor, url) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, livro.getTitulo());
        st.setString(2, livro.getAutor());
        st.setInt(3, livro.getAno());
        st.setString(4, livro.getEditora());
        st.setFloat(5, livro.getValor());
        st.setString(6, livro.getUrl());
        st.execute();
        con.close();
        
    }
    
    public List<Livro> consulta() throws SQLException {
        
            List<Livro> livros = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from livro order by id_livro;");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id_livro"));
                livro.setAno(rs.getInt("ano"));
                livro.setAutor(rs.getString("autor"));
                livro.setEditora(rs.getString("editora"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setValor(rs.getFloat("valor"));
                livro.setUrl(rs.getString("url"));
                livros.add(livro);
            }
            rs.close();
            st.close();
            con.close();
            return livros;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from livro where id_livro = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
