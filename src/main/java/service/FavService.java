package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Statement;
import dao.FavDAO;
import dao.RoleDAO;
import model.Fav;
import model.Role;

import java.io.File;
import spark.Request;
import spark.Response;

public class FavService {
	private FavDAO favDAO = new FavDAO();
	private String favForm;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;

	public FavService() {
		makeForm();
	}
	
	public void makeForm() {
        makeForm(new Fav());
    }
	
	// faz o formulario hidden pra pegar os 2 id necessario
	
	public void makeForm(Fav fav) {
        String nomeArquivo = "./src/main/resources/public/evento.html";
        favForm = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                favForm += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umFav = "";

        String action = "/fav/insert";
            
            umFav += "<form id=\"cad-event-form\" class=\"form\" action=\""+ action +"\" method=\"post\" \">\r\n"
                    + "<input type=\"text\" name=\"usuario_id\" id=\"usuario_id\" hidden >"
					+ "<input type=\"text\" name=\"role_id\" id=\"role_id\" hidden >"
                    +"<div class=\"text-center\">\r\n"                   
                    + "                                <button type=\"submit\" id=\"btn_salvar_evt\" class=\"btn btn-warning\">ADD</button>\r\n"
                    + "                            </div>"
                    + "                            </form>";
            

       

        favForm = favForm.replaceFirst("<!--FAVZAO-->", umFav);   //COLOCAR O FAVZAO DENTRO DA PAGINA DO ROLE

    }
	
	// acho q nao vai precisar, pois nn vo precisar de uma pagina de um fav
	
	public void makeFav(Fav fav) {
		String nomeArquivo = "./src/main/resources/public/fav.html"; // nao existe essa pagina d fav
		favForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	favForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		
		String favzin="";
		
			
			favzin += "";
			
						
				
		favForm = favForm.replaceFirst("<!--FAV-->", favzin);		
	}

	// vai exibir os favs na pagina do perfil.html
	
	public void makeFavs(int orderBy) {
		String nomeArquivo = "./src/main/resources/public/perfil.html";
		favForm = "";
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while (entrada.hasNext()) {
				favForm += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String listaFavs = "";

		List<Fav> favs;
		if (orderBy == FORM_ORDERBY_ID) {
			favs = favDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {
			favs = favDAO.getOrderByNome();
		} else {
			favs = favDAO.get();
		}

		for (Fav f : favs) {
			RoleDAO roleDAO = new RoleDAO();
			FavDAO favDAO = new FavDAO();
			Fav fav = favDAO.get(f.getId_usuario(),f.getId_role()); 
			Role role = roleDAO.get(f.getId_role());
			listaFavs += "<div class=\"col-3 role-favorito--card\">\r\n"
					+ "                    <div class=\"role-favorito--card--item\">\r\n"
					+ "                        <div class=\"role-favorito--card--item--imagem\">\r\n"					
					+"<img src=\"http://localhost:6532/imgs/roles/"+role.getImgCapa()+"\" alt=\"Foto do rolê\">"
					+ "                            <button><a href=\"http://localhost:6532/fav/delete/"+fav.getId_usuario()+"/"+fav.getId_role()+"\"> <i class=\"fa-solid fa-circle-xmark fa-lg\"></i>  </a> </button>\r\n"
					+ "                        </div>\r\n"
					+ "                        <div class=\"role-favorito--card--item--nome text-center\">\r\n"
					+ "                            <h6>"+role.getNome()+"</h6>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>";

		}
		
		favForm = favForm.replaceFirst("<!--LISTAR-FAVS-->", listaFavs);
	}
	
	//inserir o fav
	
	public Object insert(Request request, Response response) {
        
        int id_usuario = Integer.parseInt(request.queryParams("usuario_id"));
        int id_role = Integer.parseInt(request.queryParams("role_id"));

        String resp = "";

        Fav fav = new Fav(id_usuario, id_role);

        if (favDAO.insert(fav) == true) {
            resp = "Fav (" + id_role + ") cadastrado!";
            response.status(302);
            response.header("Location", "http://localhost:6532/perfil/1");
        } else {
            resp = "Fav (" + id_role + ") não cadastrado!";
            response.status(404); // 404 Not found
        }

        return resp;
    }
		
	// acho q nao vai precisar, só o get do DAO e ai tem q mudar os parametros la
	
	public Object get (Request request, Response response) {        // COLOCAR O ID DO ROLE E DO USUARIO NESSE GET
		int id = Integer.parseInt(request.params(":id_usuario"));
        int id2 = Integer.parseInt(request.params(":id_role"));
        
        Fav fav = favDAO.get(id, id2);
		
		if (fav != null) {
			response.status(200); // success
			makeFav(fav);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		favForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return favForm;
	}
	

	public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeFavs(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return favForm;
    }
	
	
	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_usuario"));
        int id2 = Integer.parseInt(request.params(":id_role"));
        
        Fav fav = favDAO.get(id, id2);
        String resp = "";

        if (fav != null) {
            favDAO.delete(id, id2);
            response.status(200); // success
            resp = "Fav (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Fav (" + id + ") não encontrado!";
        }
        makeFavs(1);
        return favForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}
