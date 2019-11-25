<%@page import="java.util.List"%>
<%@page import="model.Fornecedor"%>
<%@page import="dao.FornecedorDAO"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Fornecedores</title>

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
            <h2>Controle de <b>Fornecedores</b></h2>
          </div>
          <div class="col-sm-6">
            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Cadastrar novo Fornecedor</span></a>
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
                <th>Telefone</th>
                <th>E-mail</th>
                <th>Cnpj</th>
                </thead>
                <tbody>
                  <form action="GerenciarFornecedor" method="post">
                    <tr>  
                        <% FornecedorDAO dao = new FornecedorDAO();
                            List<Fornecedor> fornecedores = dao.consulta();
                            int x = 0;
                            for (Fornecedor f : fornecedores) {
                            %>
                        <td>
                            <span class="custom-checkbox">
                                <input type="checkbox" id="checkbox2" name="options[]" value="<%=f.getId()%>">
                                <label for="checkbox2"></label>
                            </span>
                            
                        </td>
                            <td><%=f.getId()%></td>
                            <td><%=f.getNome()%></td>
                            <td><%=f.getTelefone()%></td>
                            <td><%=f.getEmail()%></td>
                            <td><%=f.getCnpj()%></td>
                        <td>
                          <td><input type="submit" value="Editar" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                          <input type="hidden" name="id_editar" value="<%=f.getId()%>"  id="<%= "id_item"+x%>"  >
                          <td><input type="submit" value="Excluir" name="acao" class="btn btn-outline-info" ><i class="fas fa-pen"></i></input></td>
                     </td>
                    </tr>
                    <%x++;}%>
                  </form>
                </tbody>
            </table>    
  <!-- ADD Modal HTML -->
  <div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
      <div class="modal-content">
        <form action="GerenciarFornecedor" method="post">
          <div class="modal-header">            
            <h4 class="modal-title">Adicionar Fornecedor</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          </div>
          <div class="modal-body">          
            <div class="form-group">
              <label>Nome</label>
              <input type="text" name="nome" class="form-control" required>
            </div>
            <div class="form-group">
              <label>Telefone</label>
              <input type="text" name="telefone" class="form-control" required>
            </div>
            <div class="form-group">  
              <label>E-Mail</label>
              <input type="text" name="email" class="form-control" required>
            </div>     
            <div class="form-group">
              <label>Cnpj</label>
              <input type="text" name="cnpj" class="form-control" required>
            </div>     
            <div class="form-group">
              <label>Cep</label>
              <input name="cep" type="text" id="cep" value="" class="form-control" size="10" maxlength="9"
               onblur="pesquisacep(this.value);" required />
            </div>          
            <div class="form-group">
              <label>Estado</label>
              <input name="estado" type="text" id="estado" class="form-control" size="2" required/>
            </div>     
            <div class="form-group">
              <label>Cidade</label>
             <input name="cidade" class="form-control" type="text" id="cidade" size="40" required/>
            </div>     
            <div class="form-group">
              <label>Bairo</label>
              <input name="bairro" type="text" class="form-control" id="bairro" size="40" required />
            </div>     
            <div class="form-group">
              <label>Rua</label>
              <input name="rua" class="form-control" type="text" id="rua" size="60" required />
            </div>
            <div class="form-group">
              <label>Numero</label>
              <input name="numero" type="number" class="form-control" id="numero" size="60" required />
            </div>     
            <div class="form-group">
              <label>Complemento</label>
              <input name="complemento" type="text" class="form-control" id="complemento" size="60" />
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