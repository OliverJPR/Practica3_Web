<link rel="stylesheet" href="/public/styles/style.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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

            <form class="form-inline my-2 my-lg-0" action="/logout">
                <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">Logout</button>
            </form>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-12">
                <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <form class="col-11 py-5" method="post" action="/agregarArticulo">
                                        <div class="panel px-2 py-3 bg-white">
                                            <label for="titulo"><strong>Titulo</strong> </label>
                                            <input type="text" class="form-control rounded-0" name="titulo" placeholder="Titulo..." required autofocus/>
                                            <br>

                                            <div class="form-group">
                                                <label for="cuerpo"><strong>Cuerpo</strong></label><br>
                                                <textarea name="cuerpo" class="form-control rounded-0"></textarea>
                                                <br>

                                                <div class="form-group">
                                                    <label for="etiquetas"><strong>Etiquetas</strong></label>
                                                    <input name="etiquetas" class="form-control rounded-0"></input>
                                                </div>
                                            </div>
                                        </div>

                                        <button class="btn btn-primary" type="submit">
                                            NUEVO ARTICULO

                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>

