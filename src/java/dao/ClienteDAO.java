package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Cliente;

public class ClienteDAO {
    
    private Connection con;

    public ClienteDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public void edita(Cliente c) throws SQLException{
        String sql = "update cliente set nome = ?, email = ?, cpf = ?, telefone = ?, cep = ?, cliente_login = ? where id_cliente = ?;";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, c.getNome());
        st.setString(2, c.getEmail());
        st.setString(3, c.getCpf());
        st.setString(4, c.getTelefone());
        st.setString(5, c.getCep());
        st.setString(6, c.getLogin());
        st.setInt(7, c.getId_cliente());
        st.execute();
        con.close();
    }

    public void Cadastra(Cliente c) throws SQLException {

        String sql = "insert into cliente (nome, email, cpf, telefone, cep, cliente_login, senha, chave_senha, adm) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, c.getNome());
        st.setString(2, c.getEmail());
        st.setString(3, c.getCpf());
        st.setString(4, c.getTelefone());
        st.setString(5, c.getCep());
        st.setString(6, c.getLogin());
        st.setString(7, c.getSenha());
        st.setString(8, c.getChave_senha());
        st.setBoolean(9, c.isAdm());
        st.execute();
        con.close();
        
    }
    
    public List<Cliente> consulta() throws SQLException {
        
            List<Cliente> clientes = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from cliente order by id_cliente;");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId_cliente(rs.getInt("id_cliente"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setCpf(rs.getString("cpf"));
                c.setCep(rs.getString("cep"));
                c.setLogin(rs.getString("cliente_login"));
                c.setSenha(rs.getString("senha"));
                c.setTelefone(rs.getString("telefone"));
                c.setAdm(rs.getBoolean("adm"));
                c.setChave_senha(rs.getString("chave_senha"));
                clientes.add(c);
            }
            rs.close();
            st.close();
            con.close();
            return clientes;
   }
       
    public void remove(int c) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from cliente where id_cliente = ?;");
            st.setInt(1, c);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
