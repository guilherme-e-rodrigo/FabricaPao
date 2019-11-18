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


public class ProdutoDAO {
    
    private Connection con;

    public ProdutoDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public Produto getOneById(int id_produto) throws SQLException, ClassNotFoundException {
        String sql = "select * from produto where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, id_produto);
        ResultSet rs = st.executeQuery();
        Produto produto = new Produto();
        while(rs.next()) {
            produto.setData_producao(rs.getDate("data_producao"));
            FornecedorDAO fornecedorDAO = new FornecedorDAO();
            produto.setFornecedor(fornecedorDAO.getOneById(rs.getInt("id_fornecedor")));
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setPeso(rs.getFloat("peso"));
            produto.setValidade_dias(rs.getInt("validade_dias"));
        }
        return produto;
    }
    
    public void edita(Produto produto) throws SQLException{
        String sql = "update produto set nome = ?, peso = ?, data_producao = ?, id_fornecedor = ?, validade_dias = ? where id = ?;";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, produto.getNome());
        st.setDouble(2, produto.getPeso());
        st.setDate(3, produto.getData_producao());
        st.setInt(4, produto.getFornecedor().getId());
        st.setInt(5, produto.getValidade_dias());
        st.setInt(6, produto.getId());
        st.execute();
        con.close();
    }

    public void Cadastra(Produto produto) throws SQLException {

        String sql = "insert into produto (nome, peso, data_producao, id_fornecedor, validade_dias) values (?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, produto.getNome());
        st.setDouble(2, produto.getPeso());
        st.setDate(3, produto.getData_producao());
        st.setInt(4, produto.getFornecedor().getId());
        st.setInt(5, produto.getValidade_dias());
        st.execute();
        con.close();
        
    }
    
    public List<Produto> consulta() throws SQLException, ClassNotFoundException {
        
            List<Produto> produtos = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from produto");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setData_producao(rs.getDate("data_producao"));
                FornecedorDAO fornecedorDAO = new FornecedorDAO();
                produto.setFornecedor(fornecedorDAO.getOneById(rs.getInt("id_fornecedor")));
                produto.setId(rs.getInt(rs.getInt("id")));
                produto.setNome(rs.getString("nome"));
                produto.setPeso(rs.getFloat("peso"));
                produto.setValidade_dias(rs.getInt("validade_dias"));
                produtos.add(produto);
            }
            rs.close();
            st.close();
            con.close();
            return produtos;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from produto where id = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
