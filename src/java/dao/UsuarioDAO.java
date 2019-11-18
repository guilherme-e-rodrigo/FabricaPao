package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Fornecedor;
import model.Produto;
import model.Usuario;


public class UsuarioDAO {
    
    private Connection con;

    public UsuarioDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public Usuario getOneById(int id_usuario) throws SQLException, ClassNotFoundException {
        String sql = "select * from usuario where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id_usuario);
        ResultSet rs = st.executeQuery();
        Usuario usuario = new Usuario();
        while(rs.next()) {
            usuario.setChave_senha(rs.getString("chave_senha"));
            usuario.setId(rs.getInt("id"));
            usuario.setIs_admin(rs.getBoolean("is_admin"));
            usuario.setLogin(rs.getString("login"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
        }
        return usuario;
    }
    
    public Usuario getOneByLogin(String login) throws SQLException {
        String sql = "select * from usuario where login = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, login);
        ResultSet rs = st.executeQuery();
        Usuario usuario = null;
        while(rs.next()) {
            usuario = new Usuario();
            usuario.setChave_senha(rs.getString("chave_senha"));
            usuario.setId(rs.getInt("id"));
            usuario.setIs_admin(rs.getBoolean("is_admin"));
            usuario.setLogin(rs.getString("login"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSenha(rs.getString("senha"));
        }
        return usuario;
    }
    
    public void edita(Usuario usuario) throws SQLException{
        String sql = "update usuario set nome = ?, user_login = ?, senha = ?, chave_senha = ?, is_admin = ? where id = ?;";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, usuario.getNome());
        st.setString(2, usuario.getLogin());
        st.setString(3, usuario.getSenha());
        st.setString(4, usuario.getChave_senha());
        st.setBoolean(5, usuario.isIs_admin());
        st.setInt(6, usuario.getId());
        st.execute();
        con.close();
    }

    public void Cadastra(Usuario usuario) throws SQLException {

        String sql = "insert into usuario (nome, user_login, senha, chave_senha, is_admin) values (?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, usuario.getNome());
        st.setString(2, usuario.getLogin());
        st.setString(3, usuario.getSenha());
        st.setString(4, usuario.getChave_senha());
        st.setBoolean(5, usuario.isIs_admin());
        st.execute();
        con.close();
        
    }
    
    public List<Usuario> consulta() throws SQLException, ClassNotFoundException {
        
            List<Usuario> usuarios = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from usuario");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setChave_senha(rs.getString("chave_senha"));
                usuario.setId(rs.getInt("id"));
                usuario.setIs_admin(rs.getBoolean("is_admin"));
                usuario.setLogin(rs.getString("login"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuarios.add(usuario);
            }
            rs.close();
            st.close();
            con.close();
            return usuarios;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from usuario where id = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
