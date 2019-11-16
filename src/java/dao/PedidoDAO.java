package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConexaoBD;
import model.Cliente;
import model.Pedido;

public class PedidoDAO {
    
    private Connection con;

    public PedidoDAO() throws ClassNotFoundException {
            this.con = new ConexaoBD().getConnection();
            System.out.println("Conectado!");
    }

    public void Cadastra(Pedido pedido) throws SQLException {

        String sql = "insert into pedido (data_pedido, quantidade, total, id_livro, id_cliente) values (?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(sql);
        st.setDate(1, pedido.getData());
        st.setInt(2, pedido.getQuantidade());
        st.setFloat(3, pedido.getTotal());
        st.setInt(4, pedido.getId_livro());
        st.setInt(5, pedido.getId_cliente());
        st.execute();
        con.close();
        
    }
    
    public List<Pedido> consulta() throws SQLException {
        
            List<Pedido> pedidos = new ArrayList();
            PreparedStatement st = con.prepareStatement("select * from pedido order by id_pedido;");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setData(rs.getDate("data_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setId_livro(rs.getInt("id_livro"));
                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setQuantidade(rs.getInt("quantidade"));
                pedido.setTotal(rs.getFloat("total"));
                pedidos.add(pedido);
            }
            rs.close();
            st.close();
            con.close();
            return pedidos;
   }
       
    public void remove(int c) throws SQLException {
        try {
            PreparedStatement st = con.prepareStatement("delete from pedido where id_pedido = ?;");
            st.setInt(1, c);
            st.execute();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        con.close();
    }
    
}
