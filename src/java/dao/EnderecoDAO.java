package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Endereco;
import model.Fornecedor;


public class EnderecoDAO {
    
    private Connection con;

    public EnderecoDAO() throws ClassNotFoundException {
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
    
    public Endereco getOneById(int id_endereco) throws SQLException {
        String sql = "select * from endereco where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id_endereco);
        ResultSet rs = st.executeQuery();
        Endereco endereco = new Endereco();
        while(rs.next()) {    
            endereco.setBairro(rs.getString("bairro"));
            endereco.setCep(rs.getString("cep"));
            endereco.setCidade(rs.getString("cidade"));
            endereco.setComplemento(rs.getString("complemento"));
            endereco.setEstado(rs.getString("estado"));
            endereco.setId(rs.getInt("id"));
            endereco.setNumero(rs.getInt("numero"));
            endereco.setRua(rs.getString("rua"));
        }
        return endereco;
    }

    public void Cadastra(Endereco endereco) throws SQLException {

        String sql = "insert into endereco (rua, estado, numero, complemento, bairro, cep, cidade) values (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, endereco.getRua());
        st.setString(2, endereco.getEstado());
        st.setInt(3, endereco.getNumero());       
        st.setString(4, endereco.getComplemento());
        st.setString(5, endereco.getBairro());
        st.setString(6, endereco.getCep());
        st.setString(7, endereco.getCidade());

        
        //st.setString(2, fornecedor.getDescricao());
        st.execute();
        con.close();
        
    }
    
    public List<Fornecedor> consulta() throws SQLException {
        
            List<Fornecedor> fornecedores = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from fornecedor ");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setNome(rs.getString("nome"));
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
