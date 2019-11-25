<%@page import="model.Produto"%>
<%@page import="dao.ProdutoDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Fornecedor"%>
<%@page import="dao.FornecedorDAO"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Produtos</title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>    
<link rel="stylesheet" href="css/styleTable.css">

<script type="text/javascript">
//<----------------- Checkbox ------------------>
$(document).ready(function(){
  // Activate tooltip
  $('[data-toggle="tooltip"]').tooltip();
  
  // Select/Deselect checkboxes
  var checkbox = $('table tbody input[type="checkbox"]');
  $("#selectAll").click(function(){
    if(this.checked){
      checkbox.each(function(){
        this.checked = true;                        
      });
    } else{
      checkbox.each(function(){
        this.checked = false;                        
      });
    } 
  });
  checkbox.click(function(){
    if(!this.checked){
      $("#selectAll").prop("checked", false);
    }
  });
});

</script>
</head>
<body>
    <nav class="navbar navbar-clean">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Paes e Bolos Jurema</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <ul class="nav navbar-nav">
        <li><a href="controleFornecedor.jsp">Fornecedores</a></li>
        <li><a href="controleProdutos.jsp">Produtos</a></li>
        <li><a href="controleUsuarios.jsp">Usuarios</a></li>
        <li><a href="controleEntradaSaida.jsp">Estoque</a></li>
        <li><a href="controlePedidos.jsp">Pedidos</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
    <div class="container">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
            <h2>Controle de <b>Produtos</b></h2>
          </div>
          <div class="col-sm-6">
            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Adicionar Produto</span></a>
            <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Deletar</span></a>           
          </div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
            <th>
              <span class="custom-checkbox">
                <input type="checkbox" id="selectAll">
                <label for="selectAll"></label>
              </span>
            </th>
                <th>ID</th>
                <th>Nome</th>
                <th>Peso</th>
                <th>Data de Producao</th>
                <th>Dias de Validade</th>
                </thead>
                <tbody>
                  <form action="GerenciarProduto" method="post">
                    <tr>  
                        <% ProdutoDAO dao = new ProdutoDAO();
                            List<Produto> produtos = dao.consulta();
                            int x = 0;
                            for (Produto p : produtos) {
                            %>
                        <td>
                            <span class="custom-checkbox">
                                <input type="checkbox" id="checkbox2" name="options[]" value="<%=p.getId()%>">
                                <label for="checkbox2"></label>
                            </span>
                        </td>
                            <td><%=p.getId()%></td>
                            <td><%=p.getNome()%></td>
                            <td><%=p.getPeso()%></td>
                            <td><%=p.getData_producao()%></td>
                            <td><%=p.getValidade_dias()%></td>
                        <td>
                          <td><input type="submit" value="Editar" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                          <input type="hidden" name="id_editar" value="<%=p.getId()%>"  id="<%= "id_item"+x%>"  >
                          <td><input type="submit" value="Excluir" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                        </td>
                        <%}%>
                    </tr>
                  </form>
                </tbody>
            </table>
        </div>
    </div>
  <!-- ADD Modal HTML -->
  <div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="GerenciarProduto" method="post">
          <div class="modal-header">            
            <h4 class="modal-title">Adicionar Produto</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          </div>
          <div class="modal-body">          
            <div class="form-group">
              <label>Nome</label>
              <input type="text" name="nome" class="form-control" required>
            </div>
            <div class="form-group">
              <label>Peso</label>
              <input type="text" name="peso" class="form-control" required>
            </div>
            <div class="form-group">  
              <label>Data de Producao</label>
              <input type="date" name="data_producao" class="form-control" required>
            </div>     
            <div class="form-group">
              <label>Dias de validade</label>
              <input type="text" name="validade_dias" class="form-control" required>
            </div>         
            </div>      
          </div>
          <div class="modal-footer">
            <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
            <input type="submit" name="acao" class="btn btn-success" value="Cadastrar">
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>                                                               