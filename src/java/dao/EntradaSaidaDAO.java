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
    
    public void edita(EntradaSaida entradaSaida) throws SQLException{
        String sql = "update entrada_saida set id_produto = ?, quantidade = ?, entrada= ? where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, entradaSaida.getProduto().getId());
        st.setInt(2, entradaSaida.getQuantidade());
        st.setBoolean(3, entradaSaida.isEntrada());
        st.setInt(4, entradaSaida.getId());
        st.execute();
        con.close();
    }

    public void Cadastra(EntradaSaida entradaSaida) throws SQLException {

        String sql = "insert into entrada_saida (id_produto, quantidade, entrada) values (?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, entradaSaida.getProduto().getId());
        st.setInt(2, entradaSaida.getQuantidade());
        st.setBoolean(3, entradaSaida.isEntrada());
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
                entradaSaida.setId(rs.getInt("id"));
                ProdutoDAO produtoDAO = new ProdutoDAO();
                entradaSaida.setProduto(produtoDAO.getOneById(rs.getInt("id_produto")));
                entradaSaida.setQuantidade(rs.getInt("quantidade"));
                entradaSaidas.add(entradaSaida);
            }
            rs.close();
            st.close();
            con.close();
            return entradaSaidas;
   }
       
    public void remove(int id) throws SQLException {
        try {
            System.out.println("id: "+id);
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
