package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.EntradaSaida;
import model.Usuario;


public class EntradaSaidaDAO {
    
    private Connection con;

    public EntradaSaidaDAO() throws ClassNotFoundException {
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

    public void Cadastra(EntradaSaida entradaSaida) throws SQLException {

        String sql = "insert into entrada_saida (id_produto, id_fornecedor, quantidade, entrada, id_usuario, venda) values (?, ?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, entradaSaida.getProduto().getId());
        st.setInt(2, entradaSaida.getFornecedor().getId());
        st.setInt(3, entradaSaida.getQuantidade());
        st.setBoolean(4, entradaSaida.isEntrada());
        st.setInt(5, entradaSaida.getUsuario().getId());
        st.setBoolean(6, entradaSaida.isVenda());
        st.execute();
        con.close();
        
    }
    
    public List<EntradaSaida> consulta() throws SQLException, ClassNotFoundException {
        
            List<EntradaSaida> entradaSaidas = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from entrada_saida");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                EntradaSaida entradaSaida = new EntradaSaida();
                entradaSaida.setEntrada(rs.getBoolean("entrada"));
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                entradaSaida.setFornecedor(fornecedorDAO.getOneById(rs.getInt("id_fornecedor")));
                entradaSaida.setId(rs.getInt("id"));
                ProdutoDAO produtoDAO = new ProdutoDAO();
                entradaSaida.setProduto(produtoDAO.getOneById(rs.getInt("id_produto")));
                entradaSaida.setQuantidade(rs.getInt("quantidade"));
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                entradaSaida.setUsuario(usuarioDAO.getOneById(rs.getInt("id_usuario")));
                entradaSaida.setVenda(rs.getBoolean("venda"));
                entradaSaidas.add(entradaSaida);
            }
            rs.close();
            st.close();
            con.close();
            return entradaSaidas;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from entrada_saida where id = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
