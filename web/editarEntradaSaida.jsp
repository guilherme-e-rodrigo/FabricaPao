<%@page import="model.EntradaSaida"%>
<%@page import="model.Fornecedor"%>
<%@page import="dao.FornecedorDAO"%>
<%@page import="model.Produto"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProdutoDAO"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Colorlib Templates">
    <meta name="author" content="Colorlib">
    <meta name="keywords" content="Colorlib Templates">

    <!-- Title Page-->
    <title>Editar Fornecedor</title>

    <!-- Icons font CSS-->
    <link href="vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">
    <link href="vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <!-- Main CSS-->
    <link href="css/main.css" rel="stylesheet" media="all">
</head>

<body>
    <% //estou criando uma variável do mesmo do tipo do atributo 
    EntradaSaida produto = new EntradaSaida();
    produto = (EntradaSaida) request.getAttribute("estoque");
%>
    <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Editar entrada/saida de produto</h2>
                    <form action="GerenciarProduto"method="POST">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">ID</label>
                                    <input class="input--style-4" value="<%=produto.getId()%>" type="text" name="id">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Produto</label>
                                    <select name="produto">
                                    <% ProdutoDAO prodDAO = new ProdutoDAO();
                                    List<Produto> produtos = prodDAO.consulta();
                                     for (Produto p: produtos) {
                                    %>
                                    <option value="<%=p.getId()%>"><%=p.getNome()%></option>
                                    <%} %>
                                    </select><br>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Quantidade</label>
                                    <input class="input--style-4" value="<%=produto.getQuantidade()%>" type="text" name="quantidade">
                                </div>
                            </div>
                                
                        </div>
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Entrada</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4" type="checkbox" name="entrada" value="true"/>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" value="Alterar" name="acao" type="submit">Editar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>



</body><!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->