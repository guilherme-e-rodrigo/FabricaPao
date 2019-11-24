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
    Produto produto = new Produto();
    produto = (Produto) request.getAttribute("produto");
%>
    <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Editar entrada de produto</h2>
                    <form action="GerenciarProduto"method="POST">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">ID</label>
                                    <input class="input--style-4" type="text" value="<%=produto.getId()%>" name="id">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Nome</label>
                                    <input class="input--style-4" value="<%=produto.getNome()%>" type="text" name="nome">
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Peso</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-mail" value="<%=produto.getPeso()%>" type="text" name="peso">
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Fornecedor</label>
                                    <div class="input-group-icon">
                                    <select name="fornecedores">
                                    <% FornecedorDAO dao = new FornecedorDAO();
                                    List<Fornecedor> fornecedores = dao.consulta();
                                            int x = 0;
                                     for (Fornecedor p: fornecedores) {

                                    %>
                                    <option value="<%=p.getId()%>"><%=p.getNome()%></option>

                                    <%} %>
                                    </select><br>
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Data de producao</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-datepicker" value="<%=produto.getData_producao()%>" type="date" name="data_producao">
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Valido durante</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-datepicker" type="text" value="<%=produto.getValidade_dias()%>" name="validade_dias">Dias</input>            
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

    <!-- Jquery JS-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <!-- Vendor JS-->
    <script src="vendor/select2/select2.min.js"></script>
    <script src="vendor/datepicker/moment.min.js"></script>
    <script src="vendor/datepicker/daterangepicker.js"></script>

    <!-- Main JS-->
    <script src="js/global.js"></script>

</body><!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->