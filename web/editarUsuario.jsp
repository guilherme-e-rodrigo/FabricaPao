<%@page import="model.Usuario"%>
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
    <title>Editar Usuarios</title>

    <!-- Icons font CSS-->
    <link href="vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">
    <link href="vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
    <!-- Main CSS-->
    <link href="css/main.css" rel="stylesheet" media="all">
</head>

<body>
    <div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
        <div class="wrapper wrapper--w680">
            <div class="card card-4">
                <div class="card-body">
                    <h2 class="title">Editar Usuario</h2>
                    <% //estou criando uma variável do mesmo do tipo do atributo 
    
                        Usuario user = new Usuario();
                        user = (Usuario) request.getAttribute("usuario");
                        //System.out.println("Id:"+user.getId());
                    %>
                    <form method="post" action="GerenciarUser">
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">ID: </label>
                                    <input class="input--style-4" value="<%=user.getId()%>" type="text" name="id" readonly="true">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Nome</label>
                                    <input class="input--style-4" value="<%=user.getNome()%>" type="text" name="nome">
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Login</label>
                                    <input class="input--style-4" value="<%=user.getLogin()%>" type="text" name="login">
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Senha</label>
                                    <div class="input-group-icon">
                                        <input class="input--style-4 js-mail" value="<%=user.getSenha()%>" type="text" name="senha">
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="input-group">
                                    <label class="label">Admin?</label>
                                    <div class="input-group-icon">
                                    <div class="custom-control custom-radio">
                                    <input type="radio" name="radioAdmin" id="isUser" value="user" class="custom-control-input" required></input>
                                    <label class="custom-control-label">Usuario</label>
                                    <input type="radio" name="radioAdmin" id="isAdmin" value="admin" class="custom-control-input" required></input>
                                    <label class="custom-control-label">Admin</label> 
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row row-space">
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" name="acao" value="Alterar" type="submit">Editar</button>
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