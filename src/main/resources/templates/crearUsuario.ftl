
<!doctype html>
<html lang="es">
<head>
    <title>Blog de Web</title>
    <link rel="stylesheet" href="/public/styles/style.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Blog</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">

                <li class="nav-item active">
                    <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/agregarArticulo">Nuevo Post</a>
                </li>

                <form class="form-inline my-2 my-lg-0" action="/logout">
                    <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
                </form>
        </ul>
    </div>
</nav>

<body>

<form class="container"  method="post" action="/agregarUsuario">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="formGroupExampleInput">Nombre de Usuario*</label>
                    <input name="username" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Introduzca Nombre Usuario" required>
                </div>
            </div>
            <div class="form-row" >
                <div class="col-md-6 mb-3">
                    <label for="formGroupExampleInput2">Contrasena*</label>
                    <input name="password" type="password" class="form-control" id="exampleInputEmail1" placeholder="Introduzca Contrasena" required>
                </div>
            </div>


            <div class="">
                <label for="formGroupExampleInput2">Rol*</label>

                <div class="radio">

                    <input class="form-check-input" type="checkbox" name="administrator">
                    <label class="form-check-label" for="administrator">Administrador </label>
                </div>
                <div class="radio">
                    <input class="form-check-input" type="checkbox" name="autor">
                    <label class="form-check-label" for="autor">Autor</label>
                </div>

            <button type="submit" class="btn btn-primary">Anadir</button>

        </form>


</body>







<#--                                    <div class="form-group row">-->
<#--                                        <div class="col-sm-10">-->
<#--                                            <button type="submit" class="btn btn-primary">Submit</button>-->
<#--                                        </div>-->




