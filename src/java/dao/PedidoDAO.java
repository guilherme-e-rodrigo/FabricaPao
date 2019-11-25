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
import model.Pedido;


public class PedidoDAO {
    
    private Connection con;

    public PedidoDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }
    
    public Produto getOneById(int id_produto) throws SQLException, ClassNotFoundException {
        String sql = "select * from pedido where id = ?";
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
    
    public void edita(Pedido pedido) throws SQLException{

            String sql = "update pedido set cliente = ?, data_pedido = ?, qtd_paes = ?, valor = ? where id = ?;";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pedido.getCliente());
            st.setString(2, pedido.getDataPedido());
            st.setInt(3, pedido.getQtdPaes());
            st.setFloat(4, pedido.getValor());
            st.setInt(5, pedido.getId());
            st.execute();
            con.close();
        
        
    }

    public void Cadastra(Pedido pedido) throws SQLException {
            String sql = "insert into pedido (cliente, data_pedido, qtd_paes, valor) values (?, ?, ?, ?);";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, pedido.getCliente());
            st.setString(2, pedido.getDataPedido());
            st.setInt(3, pedido.getQtdPaes());
            st.setFloat(4, pedido.getValor());
            st.execute();
            con.close();
    }
    
    
    public List<Pedido> consulta() throws SQLException, ClassNotFoundException {
        
            List<Pedido> pedidos = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from pedido");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setCliente(rs.getString("cliente"));
                pedido.setDataPedido(rs.getString("data_pedido"));
                pedido.setQtdPaes(rs.getInt("qtd_paes"));
                pedido.setValor(rs.getFloat("valor"));
            }
            rs.close();
            st.close();
            con.close();
            return pedidos;
   }
       
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from pedido where id = ?;");
            st.setInt(1, id);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
