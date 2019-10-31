import DB.ArticuloDB;
import DB.ComentarioDB;
import DB.EtiquetaDB;
import DB.UsuarioDB;
import clases.Articulo;
import clases.Comentario;
import clases.Etiqueta;
import clases.Usuario;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jasypt.util.text.StrongTextEncryptor;

import java.io.StringWriter;
import java.sql.Date;
import java.util.*;

import static spark.Spark.*;

public class Main {

    static String nombreLogeado = "";
    static Usuario usuarioLogeado;


    public static void main(String[] args) {
        staticFiles.location("/public");
        Usuario noUser = new Usuario("no","no","no",false,false);
        usuarioLogeado = noUser;
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(Main.class, "/");

        UsuarioDB usuarioDB = new UsuarioDB();
        usuarioDB.crearDB();
        if(usuarioDB.countUsuarios() == 0){
            Usuario admin = new Usuario("admin","admin","admin",true,true);
            usuarioDB.insertarUsuario(admin);
            System.out.println("Admin creado con exito.");
        }


        ArticuloDB articuloDB = new ArticuloDB();
        EtiquetaDB etiquetaDB = new EtiquetaDB();
        ComentarioDB comentarioDB = new ComentarioDB();

        before("/", (req, res) -> {
            if (req.cookie("cookieSesion") != null) {
                StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                textEncryptor.setPassword("xdxd178245");
                String sesion = textEncryptor.decrypt(req.cookie("cookieSesion"));

                Usuario oldUsuario = usuarioDB.getSesion(sesion);
                nombreLogeado = oldUsuario.getUsername();
                usuarioLogeado = oldUsuario;
                req.session().attribute("sesion", oldUsuario);

                if (oldUsuario != null) {
                    req.session(true);
                    req.session().attribute("sesion", oldUsuario);
                }
            }



        });

        get("/login", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atr = new HashMap<>();
            Template template = configuration.getTemplate("templates/login.ftl");
            template.process(atr, writer);

            return writer;
        });

        post("/login", (req, res) -> {

            nombreLogeado = req.queryParams("username");
            String password = req.queryParams("password");
            usuarioLogeado = usuarioDB.getUsuario(nombreLogeado, password);

            if (usuarioLogeado != null) {
                req.session().attribute("sesion", usuarioLogeado);

                if (req.queryParams("keepLog") != null) {
                    String sesion = req.session().id();
                    StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                    textEncryptor.setPassword("xdxd178245");
                    String encrypt = textEncryptor.encrypt(sesion);

                    res.cookie("/", "sesion", encrypt, 604800, false);
                    usuarioDB.saveCookies(usuarioLogeado.getId(),req.session().id());
                }

                res.redirect("/");
            } else {
                res.redirect("/login");
            }


            return null;
        });

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atr = new HashMap<>();
            Template template = configuration.getTemplate("templates/home.ftl");

            List<Articulo> articuloList = articuloDB.listarArticulos();
            for(int i = 0; i < articuloList.size(); i++){
                articuloList.get(i).setListaEtiqueta(etiquetaDB.getEtiquetas(articuloList.get(i).getId()));
            }
            atr.put("admin",usuarioLogeado.isAdministrator());
            atr.put("autor",usuarioLogeado.isAutor());
            atr.put("LosArticulos",articuloList);
            template.process(atr,writer);
            return writer;
        });

        get("/logout", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atr = new HashMap<>();

            usuarioLogeado.setAutor(false);
            usuarioLogeado.setAdministrator(false);

            res.redirect("/");
            return writer;
        });

        post("/agregarUsuario", (req, res) -> {

            String user = req.queryParams("username");
            String nombre = req.queryParams("nombre");
            String password = req.queryParams("password");
            String administrator = req.queryParams("administrator");

            if(administrator == null){
                administrator = "false";
            }else if(administrator.equals("on")){
                administrator="true";
            }

            String autor = req.queryParams("autor");

            if(autor == null){
                autor = "false";
            }else if(autor.equals("on")){
                autor="true";
            }

            Usuario u =  new Usuario(user,nombre,password,Boolean.valueOf(administrator),Boolean.valueOf(autor));

            usuarioDB.insertarUsuario(u);


            res.redirect("/");

            return null;
        });

        get("/agregarArticulo", (req, res) -> {
            StringWriter writer = new StringWriter();
            Template temp = configuration.getTemplate("templates/agregarArticulo.ftl");

            temp.process(null, writer);

            return writer;
        });

        post("/agregarArticulo", (req, res) -> {

            String titulo = req.queryParams("titulo");
            String cuerpo = req.queryParams("cuerpo");
            String etiquetas = req.queryParams("etiquetas");
            List<String> listaEtiquetas = Arrays.asList(etiquetas.split(","));

            Long idArticulo =  new Long(0);
            if(articuloDB.countArticulos() != 0){
                idArticulo = articuloDB.lastArticulo();

            }
            System.out.println(idArticulo);
            Long countEtiqueta = new Long(0);
            if(etiquetaDB.countEtiquetas() != 0){
                countEtiqueta =  etiquetaDB.lastEtiqueta();
            }
            ArrayList<Etiqueta> et =  new ArrayList<>();
            for(int i = 0; i < listaEtiquetas.size(); i++){
                Etiqueta e = new Etiqueta(listaEtiquetas.get(i));
                et.add(e);
                etiquetaDB.insertarEtiqueta(e);
            }

            Date d = new Date(System.currentTimeMillis());

            Articulo a =  new Articulo(titulo,cuerpo,Long.valueOf(0),d,null,et);
            articuloDB.insertarArticulo(a);

            for(int i = 0; i < listaEtiquetas.size(); i++){
                articuloDB.insertarArticuloEtiqueta(idArticulo,countEtiqueta);
                countEtiqueta++;
            }

            res.redirect("/");

            return null;
        });




        path("/articulo", () -> {

            get("/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/indexArticulo.ftl");
                System.out.println(req.params("id"));
                Articulo articulo = articuloDB.getArticuloId(Long.parseLong(req.params("id")));
                articulo.setListaEtiqueta(etiquetaDB.getEtiquetas(articulo.getId()));
                articulo.setListaComentarios(comentarioDB.getComentario(articulo.getId()));
                atributos.put("articulo", articulo);
                atributos.put("username", nombreLogeado);
                atributos.put("admin", usuarioLogeado.isAdministrator());
                atributos.put("autor", usuarioLogeado.isAutor());
                template.process(atributos, writer);

                return writer;
            });



            post("/:id/comentar", (req, res) -> {
                Long idArticulo = Long.parseLong(req.params("id"));
                String comentario = req.queryParams("comentario");
                Long autor = usuarioLogeado.getId();
                Comentario c = new Comentario(comentario,autor);


                Long countComentario = 0L;
                if(comentarioDB.countComentario() != 0){
                    countComentario =  comentarioDB.lastComentario();
                }
                comentarioDB.insertarComentario(c);
                articuloDB.insertarArticuloComentario(idArticulo,countComentario);
                res.redirect("/articulo/" + idArticulo);
                return null;
            });

            get("/eliminar/:id", (req, res) -> {
                if (usuarioLogeado.isAdministrator() || usuarioLogeado.isAutor()) {
                    StringWriter writer = new StringWriter();
                    Map<String, Object> atributos = new HashMap<>();
                    Template template = configuration.getTemplate("templates/eliminarArticulo.ftl");

                    Articulo articulo = articuloDB.getArticuloId(Long.parseLong(req.params("id")));

                    atributos.put("articulo", articulo);
                    template.process(atributos, writer);

                    return writer;
                }
                res.redirect("/");
                return null;
            });


            get("/editar/:id", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("templates/editarArticulo.ftl");

                Articulo articulo = articuloDB.getArticuloId(Long.parseLong(req.params("id")));

                atributos.put("articulo", articulo);
                atributos.put("autor", usuarioLogeado.isAutor());
                atributos.put("admin", usuarioLogeado.isAdministrator());
                template.process(atributos, writer);

                return writer;
            });

        });

        post("/eliminar/:id", (req, res) -> {
            if (usuarioLogeado.isAdministrator() || usuarioLogeado.isAutor()) {
                articuloDB.borrarArticulo(Long.valueOf(req.params("id")));
            }
            res.redirect("/");
            return null;
        });


        post("/editar/:id", (req, res) -> {
            long id = Integer.parseInt(req.params("id"));
            String titulo = req.queryParams("titulo");
            String cuerpo = req.queryParams("cuerpo");

            articuloDB.editarArticulo(id,titulo,cuerpo);

            res.redirect("/");

            return null;
        });

        path("/usuario", () -> {
            //Ruta para agregar un estudiante
            get("/crearUsuario", (req, res) -> {
                StringWriter writer = new StringWriter();
                Template temp = configuration.getTemplate("templates/crearUsuario.ftl");

                temp.process(null, writer);

                return writer;
            });
        });



    }

}