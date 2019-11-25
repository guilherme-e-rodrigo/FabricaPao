<%@page import="model.Produto"%>
<%@page import="dao.ProdutoDAO"%>
<%@page import="model.EntradaSaida"%>
<%@page import="dao.EntradaSaidaDAO"%>
<%@page import="java.util.List"%>
<%@page import="model.Fornecedor"%>
<%@page import="dao.FornecedorDAO"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Estoque</title>

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

//<!----------------- Consulta CEP-------------!>
    function limpa_formulario_cep() {
            //Limpa valores do formulário de cep.
            document.getElementById('rua').value=("");
            document.getElementById('bairro').value=("");
            document.getElementById('cidade').value=("");
            document.getElementById('estado').value=("");

    }

    function meu_callback(conteudo) {
        if (!("erro" in conteudo)) {
            //Atualiza os campos com os valores.
            document.getElementById('rua').value=(conteudo.logradouro);
            document.getElementById('bairro').value=(conteudo.bairro);
            document.getElementById('cidade').value=(conteudo.localidade);
            document.getElementById('estado').value=(conteudo.uf);
        } //end if.
        else {
            //CEP não Encontrado.
            limpa_formulario_cep();
            alert("CEP não encontrado.");
        }
    }
        
    function pesquisacep(valor) {

        //Nova variável "cep" somente com dígitos.
        var cep = valor.replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(validacep.test(cep)) {

                //Preenche os campos com "..." enquanto consulta webservice.
                document.getElementById('rua').value="...";
                document.getElementById('bairro').value="...";
                document.getElementById('cidade').value="...";
                document.getElementById('estado').value="...";

                //Cria um elemento javascript.
                var script = document.createElement('script');

                //Sincroniza com o callback.
                script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=meu_callback';

                //Insere script no documento e carrega o conteúdo.
                document.body.appendChild(script);

            } //end if.
            else {
                //cep é inválido.
                limpa_formulario_cep();
                alert("Formato de CEP invalido.");
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulario_cep();
        }
    };
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
      </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="login.html"><b>Logout</b></a>
        </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
    <div class="container">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
            <h2>Controle de <b>Estoque</b></h2>
          </div>
          <div class="col-sm-6">
            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Cadastrar Entrada/Saida</span></a>
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
                <th>Produto</th>
                <th>Quantidade</th>
                <th>Entrada/Saida</th>  
                </thead>
                <tbody>
                  
                    <tr>  
                        <% EntradaSaidaDAO dao = new EntradaSaidaDAO();
                            List<EntradaSaida> historico = dao.consulta();
                            int x = 0;
                            for (EntradaSaida ed : historico) {
                            %>
                        <td>
                            <span class="custom-checkbox">
                                <input type="checkbox" id="checkbox2" name="options[]" value="<%=ed.getId()%>">
                                <label for="checkbox2"></label>
                            </span>
                            
                        </td>
                            <td><%=ed.getId()%></td>
                            <td><%=ed.getProduto().getNome()%></td>
                            <td><%=ed.getQuantidade()%></td>
                            <%  String resultado;
                                if(ed.isEntrada() == true){
                                resultado = "+"; }
                                else {
                                resultado = "-";
                                }
                                %><td><h1><%=resultado%></h1></td>
                        <td>
                            <form action="GerenciarEntradaSaida" method="post">
                          <td><input type="submit" value="Editar" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                          <input type="hidden" name="id_editar" value="<%=ed.getId()%>"  id="<%= "id_item"+x%>"  >
                          <td><input type="submit" value="Excluir" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                            </form>
                        </td>
                    </tr>
                    <%}%>
                  </form>
                </tbody>
            </table>    
  <!-- ADD Modal HTML -->
  <div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="GerenciarEntradaSaida" method="post">
          <div class="modal-header">            
            <h4 class="modal-title">Adicionar Produto em Estoque</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          </div>
          <div class="modal-body">          
            <div class="form-group">
              <label>Produto</label>
              <select name="produto">
                <% ProdutoDAO prodDAO = new ProdutoDAO();
                List<Produto> produtos = prodDAO.consulta();
                 for (Produto p: produtos) {
                %>
                <option value="<%=p.getId()%>"><%=p.getNome()%></option>
                <%} %>
                </select><br>
            </div>
            <div class="form-group">
              <label>Quantidade</label>
              <input type="text" name="quantidade" class="form-control" required>
            </div>
            <div class="form-group">  
              <label>Entrada?</label>
            <input type="checkbox" name="entrada" value="true"/>
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