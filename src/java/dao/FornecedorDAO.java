package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Fornecedor;


public class FornecedorDAO {
    
    private Connection con;

    public FornecedorDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public void edita(Fornecedor fornecedor) throws SQLException{
        String sql = "update fornecedor set nome = ?, descricao = ? where id = ?;";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, fornecedor.getNome());
        //st.setString(2, fornecedor.getDescricao());
        st.setInt(3, fornecedor.getId());
        st.execute();
        con.close();
    }
    
    public Fornecedor getOneById(int id_fornecedor) throws SQLException, ClassNotFoundException {
        String sql = "select * from fornecedor where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id_fornecedor);
        ResultSet rs = st.executeQuery();
        Fornecedor fornecedor = new Fornecedor();
        while(rs.next()) {
            fornecedor.setId(rs.getInt("id"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setCnpj(rs.getString("cnpj"));
            fornecedor.setEmail(rs.getString("email"));
            fornecedor.setTelefone(rs.getString("telefone"));
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            fornecedor.setEndereco(enderecoDAO.getOneById(rs.getInt("id_endereco")));
        }
        return fornecedor;
    }

    public void Cadastra(Fornecedor fornecedor) throws SQLException {

        String sql = "insert into fornecedor (nome, telefone, email, id_endereco, cnpj) values (?, ?, ?, 1, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, fornecedor.getNome());
        st.setString(2, fornecedor.getTelefone());
        st.setString(3, fornecedor.getEmail());       
        st.setString(4, fornecedor.getCnpj());

        
        //st.setString(2, fornecedor.getDescricao());
        st.execute();
        con.close();
        
    }
    
    public List<Fornecedor> consulta() throws SQLException {
        
            List<Fornecedor> fornecedores = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from fornecedor");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                //fornecedor.setDescricao(rs.getString("descricao"));
                fornecedores.add(fornecedor);
            }
            rs.close();
            st.close();
            con.close();
            return fornecedores;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from fornecedor where id = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
