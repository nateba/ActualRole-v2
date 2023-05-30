package service;

import java.util.Scanner;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import dao.RoleDAO;
import dao.UsuarioDAO;
import model.Usuario;
import model.Role;
import spark.Request;
import spark.Response;

public class RoleService {

    private RoleDAO roleDAO = new RoleDAO();
    private String roleForm;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_NOME = 2;
    private final int FORM_ORDERBY_LOGIN = 3;

    public RoleService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Role(), FORM_ORDERBY_NOME);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Role(), orderBy);
    }

    public void makeForm(int tipo, Role role, int orderBy) {
        String nomeArquivo = "./src/main/resources/public/cadastrorole.html";
        roleForm = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                roleForm += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umLivro = "";
        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
            String action = "/role/";
            String name, titulo, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Novo Role";
                titulo = "";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + role.getId_role();
                name = "Atualizar Role (Código " + role.getId_role() + ")";
                titulo = role.getNome();
                buttonLabel = "Atualizar";
            }
            umLivro += "<form id=\"cad-event-form\" class=\"form\" action=\""+ action +"\" method=\"post\" \">\r\n"
                    + "                                <h3 class=\"text-center text-warning\">Cadastrar Rolê</h3>\r\n"
                    + "                                <div class=\"cadastro--evento--nome--linha1\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--nome\">\r\n"
                    + "                                        <label for=\"nome\">Nome do Evento:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_nome\" id=\"evt_nome\" class=\"form-control\" value=\""+role.getNome()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--data\">\r\n"
                    + "                                        <label for=\"data\">Data do Evento:</label><br>\r\n"
                    + "                                        <input type=\"date\" name=\"evt_data\" id=\"evt_data\" class=\"form-control\" value=\""+role.getDatarole().toString()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <p class=\"text-center\">Endereço:</p>\r\n"
                    + "                                <div class=\"cadastro--evento--endereco--linha1\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-rua\">\r\n"
                    + "                                        <label for=\"rua\">Rua:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_rua\" id=\"evt_rua\" class=\"form-control\" value=\""+role.getRua()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-numero\">\r\n"
                    + "                                        <label for=\"numero\">Número:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_numero\" id=\"evt_numero\" class=\"form-control\" value=\""+role.getNumero()+"\" >\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <div class=\"cadastro--evento--endereco--linha2\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-complemento\">\r\n"
                    + "                                        <label for=\"complemento\">Complemento:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_complemento\" id=\"evt_complemento\"\r\n"
                    + "                                            class=\"form-control\" >\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-bairro\">\r\n"
                    + "                                        <label for=\"bairro\">Bairro:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_bairro\" id=\"evt_bairro\" class=\"form-control\" value=\""+role.getBairro()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <div class=\"cadastro--evento--endereco--linha3\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-cidade\">\r\n"
                    + "                                        <label for=\"cidade\">Cidade:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_cidade\" id=\"evt_cidade\" class=\"form-control\" value=\""+role.getCidade()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--endereco-estado\">\r\n"
                    + "                                        <label for=\"estado\">Estado:</label><br>\r\n"
                    + "                                        <select name=\"evt_estado\" id=\"evt_estado\" class=\"form-select\" value=\""+role.getEstado()+"\">\r\n"
                    + "                                            <option value=\"MG\">Minas Gerais</option>\r\n"
                    + "                                            <option value=\"SP\">São Paulo</option>\r\n"
                    + "                                            <option value=\"RJ\">Rio de Janeiro</option>\r\n"
                    + "                                            <option value=\"ES\">Espírito Santo</option>\r\n"
                    + "                                        </select>\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <p class=\"text-center\">Descrição do Evento:</p>\r\n"
                    + "                                <div class=\"form-group cadastro--evento--descricao\">\r\n"
                    + "                                    <textarea name=\"evt_descricao\" id=\"evt_descricao\" value=\""+role.getDescricao()+"\"></textarea>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <p class=\"text-center\">Filtros:</p>\r\n"
                    + "                                <div class=\"cadastro--evento--filtros--linha1\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--filtros-idade\">\r\n"
                    + "                                        <label for=\"idade\">Faixa Etária:</label><br>\r\n"
                    + "                                        <select name=\"evt_idade\" id=\"evt_idade\" class=\"form-select\" value=\""+role.getFaixaetaria()+"\" >\r\n"
                    + "                                            <option value=\"18\">Maior de 18</option>\r\n"
                    + "                                            <option value=\"0\">Livre</option>\r\n"
                    + "                                        </select>\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--filtros-preco\">\r\n"
                    + "                                        <label for=\"preco\">Preço:</label><br>\r\n"
                    + "                                        <div class=\"input-group mb-3\">\r\n"
                    + "                                            <span class=\"input-group-text\" id=\"basic-addon1\">RS</span>\r\n"
                    + "                                            <input type=\"text\" name=\"evt_preco\" id=\"evt_preco\" class=\"form-control\" value=\""+role.getPreco()+"\" \r\n"
                    + "                                                placeholder=\"XX,XX\" aria-label=\"0\" aria-describedby=\"basic-addon1\">\r\n"
                    + "                                        </div>\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--filtros-categoria\">\r\n"
                    + "                                        <label for=\"categoria\">Categoria:</label><br>\r\n"
                    + "                                        <select name=\"evt_categoria\" id=\"evt_categoria\" class=\"form-select\">\r\n"
                    + "                                            <option value=\"Arte e Cinema\">Arte e Cinema</option>\r\n"
                    + "                                            <option value=\"Esportes\">Esportes</option>\r\n"
                    + "                                            <option value=\"Festas e Shows\">Festas e Shows</option>\r\n"
                    + "                                            <option value=\"Gastronomia\">Gastronomia</option>\r\n"
                    + "                                            <option value=\"Infantil\">Infantil</option>\r\n"
                    + "                                            <option value=\"\">Todas</option>\r\n"
                    + "                                        </select>\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <p class=\"text-center\">Contatos:</p>\r\n"
                    + "                                <div class=\"cadastro--evento--contato--linha1\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--contato-telefone\">\r\n"
                    + "                                        <label for=\"telefone\">Telefone:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_telefone\" id=\"evt_telefone\" class=\"form-control\"value=\""+role.getTelefone()+"\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--contato-email\">\r\n"
                    + "                                        <label for=\"email\">Email:</label><br>\r\n"
                    + "                                        <input type=\"text\" name=\"evt_email\" id=\"evt_email\" class=\"form-control\" value=\""+role.getEmail()+"\" >\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <div class=\"cadastro--evento--contato--linha2\">\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--contato-instagram\">\r\n"
                    + "                                        <label for=\"instagram\">Instagram:</label><br>\r\n"
                    + "                                        <div class=\"input-group mb-3\">\r\n"
                    + "                                            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\r\n"
                    + "                                            <input type=\"text\" name=\"evt_instagram\" id=\"evt_instagram\"\r\n"
                    + "                                                class=\"form-control\" value=\""+role.getInstagram()+"\" placeholder=\"Usuário\" aria-label=\"Username\"\r\n"
                    + "                                                aria-describedby=\"basic-addon1\">\r\n"
                    + "                                        </div>\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--evento--contato-facebook\">\r\n"
                    + "                                        <label for=\"facebook\">Facebook:</label><br>\r\n"
                    + "                                        <div class=\"input-group mb-3\">\r\n"
                    + "                                            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\r\n"
                    + "                                            <input type=\"text\" name=\"evt_facebook\" id=\"evt_facebook\"\r\n"
                    + "                                                class=\"form-control\" value=\""+role.getFacebook()+"\" placeholder=\"Usuário\" aria-label=\"Username\"\r\n"
                    + "                                                aria-describedby=\"basic-addon1\">\r\n"
                    + "                                        </div>\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <div class=\"form-group cadastro--foto-capa-evento\">\r\n"
                    + "                                    <label for=\"imgcapa\">Imagem de Capa (1:1):</label><br>\r\n"
                    + "                                    <input type=\"file\" name=\"imgEvtCapa\" id=\"imgEvtCapa\" class=\"form-control\">\r\n"
                    + "                                </div>\r\n"
                    + "\r\n"
                    + "                                <div class=\"cadastro--foto--varias\">\r\n"
                    + "                                    <div class=\"form-group cadastro--foto-foto1-evento\">\r\n"
                    + "                                        <label for=\"imgevt1\">Imagem do Evento (2:1):</label><br>\r\n"
                    + "                                        <input type=\"file\" name=\"imgEvtFoto1\" id=\"imgEvtFoto1\" class=\"form-control\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--foto-foto2-evento\">\r\n"
                    + "                                        <label for=\"imgevt2\">Imagem do Evento (2:1):</label><br>\r\n"
                    + "                                        <input type=\"file\" name=\"imgEvtFoto2\" id=\"imgEvtFoto2\" class=\"form-control\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                    <div class=\"form-group cadastro--foto-foto3-evento\">\r\n"
                    + "                                        <label for=\"imgevt3\">Imagem do Evento (2:1):</label><br>\r\n"
                    + "                                        <input type=\"file\" name=\"imgEvtFoto3\" id=\"imgEvtFoto3\" class=\"form-control\">\r\n"
                    + "                                    </div>\r\n"
                    + "                                </div>\r\n"
                    + "<input type=\"text\" name=\"usuario_id\" id=\"usuario_id\" hidden >"
                    +"<div class=\"text-center\">\r\n"                   
                    + "                                <button type=\"submit\" id=\"btn_salvar_evt\" class=\"btn btn-warning\">"+buttonLabel+"</button>\r\n"
                    + "                            </div>"
                    + "                            </form>";
            

        } else if (tipo == FORM_DETAIL) {
            umLivro += "<p><b>"+role.getNome()+"</b></p>";

        } else {
            System.out.println("ERRO! Tipo não identificado " + tipo);
        }
        roleForm = roleForm.replaceFirst("<!--UM-LIVRO2-->", umLivro);

        
    }
    
  
    
    
    public void makeRole(Role role) {
		String nomeArquivo = "./src/main/resources/public/evento.html";
		roleForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	roleForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.get(role.getId_usuario());
		String rolezin="";
		String action = "/fav/insert";
			
			rolezin += "<div class=\"container\">\r\n"
					+ "            <div class=\"row pagina--evento--titulo-principal\">\r\n"
					+ "                <div class=\"col-12\">\r\n"
					+ "                    <h1 id=\"evt_NomeEvento\">"+role.getNome()+"</h1>\r\n"
					+ "                </div>\r\n"
					+        "<p id=\"Organizador\">"+usuario.getNome()+"</p>\r\n"
					+ "<input type=\"text\" name=\"usuarioDono_id\" id=\"usuarioDono_id\" value="+usuario.getId_usuario()+" hidden>"
					+ "            </div>\r\n"
					+ "            <div class=\"row\">\r\n"
					+ "                <div class=\"col-8 pagina--evento--carrossel\">\r\n"
					+ "                    <div id=\"carouselExampleIndicators\" class=\"carousel slide\" data-bs-ride=\"true\">\r\n"
					+ "                        <div class=\"carousel-indicators\">\r\n"
					+ "                            <button type=\"button\" data-bs-target=\"#carouselExampleIndicators\" data-bs-slide-to=\"0\"\r\n"
					+ "                                class=\"active\" aria-current=\"true\" aria-label=\"Slide 1\"></button>\r\n"
					+ "                            <button type=\"button\" data-bs-target=\"#carouselExampleIndicators\" data-bs-slide-to=\"1\"\r\n"
					+ "                                aria-label=\"Slide 2\"></button>\r\n"
					+ "                            <button type=\"button\" data-bs-target=\"#carouselExampleIndicators\" data-bs-slide-to=\"2\"\r\n"
					+ "                                aria-label=\"Slide 3\"></button>\r\n"
					+ "                        </div>\r\n"
					+ "                        <div class=\"carousel-inner\" id=\"evt_ImagensEvento\">\r\n"
					+ "                            <div class=\"carousel-item active\">\r\n"
					+ "                                <img src=\"http://localhost:6532/imgs/roles/"+role.getImg1()+"\" class=\"d-block w-100\" alt=\"Foto do evento\">\r\n"
					+ "                            </div>\r\n"
					+ "                            <div class=\"carousel-item\">\r\n"
					+ "                                <img src=\"http://localhost:6532/imgs/roles/"+role.getImg2()+"\" class=\"d-block w-100\" alt=\"Foto do evento\">\r\n"
					+ "                            </div>\r\n"
					+ "                            <div class=\"carousel-item\">\r\n"
					+ "                                <img src=\"http://localhost:6532/imgs/roles/"+role.getImg3()+"\" class=\"d-block w-100\" alt=\"Foto do evento\">\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"pagina--evento--comentarios\">\r\n"
					+ "                        <div class=\"pagina--evento--comentarios--titulo\">\r\n"
					+ "                            <h5>Comentários (<span id=\"numCmtRoles\"></span>)</h5>\r\n"
					+ "                        </div>\r\n"
					+ "\r\n"
					+ "                        <div class=\"pagina--evento--comentarios--texto\">\r\n"
					+ "                            <textarea name=\"cmt_evento\" id=\"cmt_evento\"></textarea>\r\n"
					+ "                            <button type=\"submit\" class=\"btn btn-sm btn-dark\" id=\"btn_EnviarCmtRole\" disabled\r\n"
					+ "                                onclick=\"incluirComentarioRole(document.getElementById('cmt_evento').value)\">Enviar</button>\r\n"
					+ "                        </div>\r\n"
					+ "\r\n"
					+ "                        <div class=\"pagina--evento--comentarios--posts\" id=\"feedCmtRoles\">\r\n"
					+ "                            <div class=\"pagina--evento--comentarios--posts--item\">\r\n"
					+ "                                <div class=\"pagina--evento--comentarios--posts--item--foto\">\r\n"
					+ "                                    <img src=\"https://xsgames.co/randomusers/avatar.php?g=pixel\" alt=\"Foto de perfil\">\r\n"
					+ "                                </div>\r\n"
					+ "                                <div class=\"pagina--evento--comentarios--posts--item--infos\">\r\n"
					+ "                                    <h6>Nome do Usuário</h6>\r\n"
					+ "                                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quas, enim.</p>\r\n"
					+ "                                    <p><i class=\"fa-regular fa-heart\"></i>0</p>\r\n"
					+ "                                </div>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "\r\n"
					+ "                <div class=\"col-4\" id=\"evt_InfosEvento\">\r\n"
					+ "                    <div class=\"pagina--evento--descricao\">\r\n"
					+ "                        <p>"+role.getDescricao()+"</p>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"pagina--evento--localizacao\">\r\n"
					+ "                        <h6 class=\"text-center\">Como chegar?</h6>\r\n"
					+ "                        <p class=\"text-center\">"+role.getRua()+", "+role.getNumero()+" - "+role.getBairro()+", "+role.getCidade()+"</p>\r\n"
					+ "                        <div class=\"text-center\">\r\n"
					+ "                            <a\r\n"
					+ "                                href=\"https://www.google.com.br/maps/place/Rua Tebas, 350 - Vera Cruz, Belo Horizonte - MG\"><button\r\n"
					+ "                                    type=\"button\" class=\"btn btn-warning\"><i\r\n"
					+ "                                        class=\"fa-solid fa-map-location-dot\"></i>Ver no Mapa</button></a>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"pagina--evento--contatos\">\r\n"
					+ "                        <h6 class=\"text-center\">Contatos:</h6>\r\n"
					+ "                        <div class=\"pagina--evento--contatos--principais\">\r\n"
					+ "                            <p><i class=\"fa-solid fa-phone\"></i>"+role.getTelefone()+"</p>\r\n"
					+ "                            <p><i class=\"fa-solid fa-envelope\"></i>"+role.getEmail()+"</p>\r\n"
					+ "                        </div>\r\n"
					+ "\r\n"
					+ "                        <div class=\"pagina--evento--contatos--sociais\">\r\n"
					+ "                            <p><a href=\"https://www.instagram.com/\"><i class=\"fa-brands fa-instagram fa-xl\"></i></a></p>\r\n"
					+ "                            <p><a href=\"https://www.facebook.com/\"><i\r\n"
					+ "                                        class=\"fa-brands fa-square-facebook fa-xl\"></i></a></p>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+"<div id=\"butoin1\">"
					+ "<a href='http://localhost:6532/role/delete/"+role.getId_role()+"'<button type=\"button\" class=\"btn btn-danger\"><i class=\"fa-solid fa-trash fa-2xl\"></i></button></a>"
					+"</div>"
					+"<div id=\"butoin2\">"
					+ "<a href='http://localhost:6532/role/update/"+role.getId_role()+"'<button type=\"button\" class=\"btn btn-info\"><i class=\"fa-solid fa-pen-to-square fa-2xl\"></i></button></a>"
					+ "</div>"
					+ "                    <div class=\"pagina--evento--confirmacao\">\r\n"
					+ "                        <h6 class=\"text-center\">Você vai comparecer?</h6>\r\n"
					+ "                        <div class=\"pagina--evento--confirmacao--usuarios\" id=\"feedConfirmados\">\r\n"
					+ "                            <img src=\"https://randomuser.me/api/portraits/men/66.jpg\" alt=\"Foto de perfil\">\r\n"
					+ "                        </div>\r\n"					
					+ "\r\n"
					+ "                        <button onclick=\"inserirConfirmacaoEvento();\" id=\"btn_ConfirmaPresenca\"><i class=\"fa-solid fa-thumbs-up fa-lg\"></i></button>\r\n"
					+ "                    </div>\r\n"
					+ "<form id=\"cad-fav-form\" class=\"form\" action=\""+ action +"\" method=\"post\" \">\r\n"
                    + "<input type=\"text\" name=\"usuario_id\" id=\"usuario_id\" hidden >"
					+ "<input type=\"text\" name=\"role_id\" id=\"role_id\" hidden >"
                    +"<div class=\"text-center\">\r\n"   
					+"<i class=\"fa-regular fa-star fa-flip fa-2xl\" style=\"color:rgb(255,165,0);\"></i>"   
                    + "                                <button type=\"submit\" id=\"btn_salvar_fav\" class=\"btn btn-warning\">ADICIONAR AOS FAVORITOS</button>\r\n"
                    +"<i class=\"fa-regular fa-star fa-flip fa-2xl\" style=\"color:rgb(255,165,0);\"></i>" 
                    + "                            </div>"
                    + "                            </form>"
					+ "                </div>\r\n"
					+ "            </div>\r\n"
					+ "        </div>";
			
			 
		            

		       

				
		roleForm = roleForm.replaceFirst("<!--ROLE-->", rolezin);		
		
	}
    
    public void makeRoles(int orderBy) {
		String nomeArquivo = "./src/main/resources/public/roles.html";
		roleForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	roleForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		
		String listaRoles="";
		
		List<Role> roles;
		if (orderBy == FORM_ORDERBY_ID) {               roles = roleDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {		roles = roleDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_LOGIN) {			roles = roleDAO.getOrderByFaixaEtaria();
		} else {											roles = roleDAO.get();
		}
		
		
		
		for (Role r : roles) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = usuarioDAO.get(r.getId_usuario());
			listaRoles += 	  
			"<div class=\"role--card-item\">\r\n"
			+ "    <div class=\"role--card-item--imagem\">\r\n"
			+ "        <a href=\"http://localhost:6532/role/pagina/"+r.getId_role()+"\"><img src=\"http://localhost:6532/imgs/roles/"+r.getImgCapa()+"\" alt=\"Foto do rolê\"></a>\r\n"
			+ "    </div>\r\n"
			+ "    <div class=\"role--card-item--infos\">\r\n"
			+ "        <a href=\"http://localhost:6532/role/pagina/"+r.getId_role()+"\">\r\n"
			+ "            <h3>"+r.getNome()+"</h3>\r\n"
			+ "        </a>\r\n"
			+ "        <a href=\"http://localhost:6532/role/pagina/"+r.getId_role()+"\">\r\n"
			+ "            <p class=\"recii--organizador\">"+usuario.getNome()+"</p>\r\n"
			+ "        </a>\r\n"
			+ "        <div class=\"role--card-item--infos-detalhes--titulos\">\r\n"
			+ "            <p><span style=\"font-weight: 600;\">Endereço:</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 600;\">Entrada:</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 600;\">Data:</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 600;\">Avaliação:</span></p>\r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"role--card-item--infos-detalhes--descricao\">\r\n"
			+ "            <p><span style=\"font-weight: 200;\">"+r.getRua()+","+r.getNumero()+" - "+r.getBairro()+", "+r.getCidade()+"/"+r.getEstado()+"</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 200;\">RS "+r.getPreco()+"</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 200;\">"+r.getDatarole()+"</span></p>\r\n"
			+ "            <p><span style=\"font-weight: 200; color: var(--amarelo-marca);\">☆☆☆☆☆</span></p>\r\n"
			+ "        </div>\r\n"
			+ "    </div>\r\n"
			+ "    <div class=\"role--card-item--botao-detalhes\">\r\n"
			+ "        <a href=\"http://localhost:6532/role/pagina/"+r.getId_role()+"\"><button>Ver Detalhes</button></a>\r\n"
			+ "    </div>\r\n"
			+ "    <div class=\"role--card-item--botao-favoritar\">\r\n"
			+ "        <button id=\"btnDivFavoritar[i]\" onclick=\"incluirFavorito([i])\"><i class=\"fa-regular fa-heart fa-2xl\"></i></button>\r\n"
			+ "    </div>\r\n"
			+ "</div>";
						
		}		
		roleForm = roleForm.replaceFirst("<!--LISTAR-ROLES-->", listaRoles);		
	}	
    
    
    public Object insert(Request request, Response response) {
        String nome = request.queryParams("evt_nome");
        String telefone = request.queryParams("evt_telefone");
        LocalDate datarole = LocalDate.parse(request.queryParams("evt_data"));
        String rua = request.queryParams("evt_rua");
        int numero = Integer.parseInt(request.queryParams("evt_numero"));
        String bairro = request.queryParams("evt_bairro");
        String cidade = request.queryParams("evt_cidade");
        String estado = request.queryParams("evt_estado");
        double preco = Double.parseDouble(request.queryParams("evt_preco"));
        String descricao = request.queryParams("evt_descricao");
        String faixaetaria = request.queryParams("evt_idade");
        String email = request.queryParams("evt_email");
        String instagram = request.queryParams("evt_instagram");
        String facebook = request.queryParams("evt_facebook");
        String imgCapa = request.queryParams("imgEvtCapa");
        String img1 = request.queryParams("imgEvtFoto1");
        String img2 = request.queryParams("imgEvtFoto2");
        String img3 = request.queryParams("imgEvtFoto3");
        int idUsuario = Integer.parseInt(request.queryParams("usuario_id"));

        String resp = "";

        Role role = new Role(0, nome, telefone, datarole, email, preco, descricao, estado, cidade, bairro, rua, numero,
                faixaetaria, instagram, facebook, imgCapa, img1, img2, img3, idUsuario);

        if (roleDAO.insert(role) == true) {
            resp = "Role (" + nome + ") cadastrado!";
            response.status(302);
            response.header("Location", "http://localhost:6532/role/list/1");
        } else {
            resp = "Role (" + nome + ") não cadastrado!";
            response.status(404); // 404 Not found
        }

        return resp;
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_role"));
        Role role = (Role) roleDAO.get(id);

        if (role != null) {
            response.status(200); // success
            makeForm(FORM_DETAIL, role, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
            makeForm();
            roleForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                    "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return roleForm;
    }
    
    public Object get2 (Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_role"));		
		Role role = (Role)roleDAO.get(id);
		
		if (role != null) {
			response.status(200); // success
			makeRole(role);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		roleForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return roleForm;
	}

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_role"));
        Role role = (Role) roleDAO.get(id);

        if (role != null) {
            response.status(200); // success
            makeForm(FORM_UPDATE, role, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
            makeForm();
            // form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\"
            // value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp
            // +"\">");
        }

        return roleForm; // RETORNO TESTE
    }

    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));     
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return roleForm;
    }
    
    public Object getAll2(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeRoles(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return roleForm;
    }


    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_role"));
		Role role = roleDAO.get(id);
        String resp = "";       

        if (role != null) {
        	role.setNome(request.queryParams("evt_nome"));
        	role.setDatarole(LocalDate.parse(request.queryParams("evt_data")));
    		role.setRua(request.queryParams("evt_rua"));
    		role.setNumero(Integer.parseInt(request.queryParams("evt_numero")));
    		role.setBairro(request.queryParams("evt_bairro"));
    		role.setCidade(request.queryParams("evt_cidade")); 
    		role.setEstado(request.queryParams("evt_cidade"));
            role.setPreco(Double.parseDouble(request.queryParams("evt_preco")));
            role.setDescricao(request.queryParams("evt_descricao"));
            role.setFaixaetaria(request.queryParams("evt_idade"));
            role.setTelefone(request.queryParams("evt_telefone"));
            role.setEmail(request.queryParams("evt_email"));
            role.setInstagram(request.queryParams("evt_instagram"));
            role.setEmail(request.queryParams("evt_email"));
            role.setFacebook(request.queryParams("evt_facebook"));
            role.setImgCapa(request.queryParams("imgEvtCapa"));
            role.setImg1(request.queryParams("imgEvtFoto1"));
            role.setImg2(request.queryParams("imgEvtFoto2"));
            role.setImg3(request.queryParams("imgEvtFoto3"));
    		
        	
        	roleDAO.update(role);
        	response.status(200); // success
            resp = "Produto (ID " + role.getId_role() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (ID " + role.getId_role() + ") não encontrado!";
        }
		makeForm();
		return roleForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_role"));
        Role role = roleDAO.get(id);
        String resp = "";

        if (role != null) {
            roleDAO.delete(id);
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não encontrado!";
        }
        makeForm();
        return roleForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
                "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    
}