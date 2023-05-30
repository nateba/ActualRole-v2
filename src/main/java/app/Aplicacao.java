package app;

import static spark.Spark.*;

import service.UsuarioService;
import service.FavService;
import service.RoleService;


public class Aplicacao {
	
	// Criação das variáveis
	private static UsuarioService usuarioService = new UsuarioService();
	private static RoleService roleService = new RoleService();
	private static FavService favService = new FavService();
	
    public static void main(String[] args) {
        port(6532);
        
        staticFiles.location("/public");
        
        // CRUD do Usuário
        post("/usuario/insert", (request, response) -> usuarioService.insert(request, response));

        post("/usuario/login", (request, response) -> usuarioService.autenticar(request, response));

        get("/usuario/:id_usuario", (request, response) -> usuarioService.get(request, response));
        
        get("/usuario/pagina/:id_usuario", (request, response) -> usuarioService.get2(request, response));
        
        get("/usuario/list/:orderby", (request, response) -> usuarioService.getAll(request, response));
        
        get("/usuario/feed/:orderby", (request, response) -> usuarioService.getAll2(request, response));

        get("/usuario/update/:id_usuario", (request, response) -> usuarioService.getToUpdate(request, response));
        
        post("/usuario/update/:id_usuario", (request, response) -> usuarioService.update(request, response));
           
        get("/usuario/delete/:id_usuario", (request, response) -> usuarioService.delete(request, response));
        
        // CRUD do Role
        
        post("/role/insert", (request, response) -> roleService.insert(request, response));

        get("/role/:id_role", (request, response) -> roleService.get(request, response));
        
        get("/role/pagina/:id_role", (request, response) -> roleService.get2(request, response));
        
        get("/role/list/:orderby", (request, response) -> roleService.getAll(request, response));      
        
        get("/role/update/:id_role", (request, response) -> roleService.getToUpdate(request, response));
        
        get("/role/feed/:orderby", (request, response) -> roleService.getAll2(request, response));
        
        post("/role/update/:id_role", (request, response) -> roleService.update(request, response));
           
        get("/role/delete/:id_role", (request, response) -> roleService.delete(request, response));
        
     // CRUD do Fav
        
        post("/fav/insert", (request, response) -> favService.insert(request, response));
        
        get("/perfil/:orderby", (request, response) -> favService.getAll(request, response)); 
        
        get("/fav/delete/:id_usuario/:id_role", (request, response) -> favService.delete(request, response));
    }
}