package service;

import java.util.Scanner;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import dao.DAO;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String usuarioForm;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 2;
	private final int FORM_ORDERBY_NOME = 1;
	private final int FORM_ORDERBY_LOGIN = 3;
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "./src/main/resources/public/cadastrouser.html";
		usuarioForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	usuarioForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umLivro = "";
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, titulo, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Novo Usuário";
				titulo = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getId_usuario();
				name = "Atualizar Usuário (Código " + usuario.getId_usuario() + ")";
				titulo = usuario.getNome();
				buttonLabel = "Atualizar";
			}
			umLivro += "<form id=\"cad-user-form\" class=\"form\" action=\""+ action +"\" method=\"post\" onsubmit=\"loginUser (this)\">\r\n"
					+ "\r\n"
					+ "                                <h3 class=\"text-center text-warning\">Novo Usuário</h3>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"nome\">Nome:</label><br>\r\n"
					+ "                                    <input type=\"text\" name=\"txt_nome\" id=\"txt_nome\" class=\"form-control\" value=\""+ usuario.getNome() +"\">\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"sobrenome\">Sobrenome:</label><br>\r\n"
					+ "                                    <input type=\"text\" name=\"txt_sobrenome\" id=\"txt_sobrenome\" class=\"form-control\" value=\""+usuario.getSobrenome()+"\" >\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"login\">Usuário:</label><br>\r\n"
					+ "                                    <input type=\"text\" name=\"txt_login\" id=\"txt_login\" class=\"form-control\" value=\""+usuario.getNomeusuario()+"\" >\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"email\">Email:</label><br>\r\n"
					+ "                                    <input type=\"text\" name=\"txt_email\" id=\"txt_email\" class=\"form-control\" value=\""+usuario.getEmail()+"\">\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"senha\">Senha:</label><br>\r\n"
					+ "                                    <input type=\"password\" name=\"txt_senha\" id=\"txt_senha\" class=\"form-control\" value=\""+usuario.getSenha()+"\">\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group\">\r\n"
					+ "                                    <label for=\"senha2\">Confirmação de Senha:</label><br>\r\n"
					+ "                                    <input type=\"password\" name=\"txt_senha2\" id=\"txt_senha2\" class=\"form-control\">\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <p class=\"text-center\">Endereço:</p>\r\n"
					+ "\r\n"
					+ "                                <div class=\"cadastro--endereco--linha1\">\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-rua\">\r\n"
					+ "                                        <label for=\"rua\">Rua:</label><br>\r\n"
					+ "                                        <input type=\"text\" name=\"txt_rua\" id=\"txt_rua\" class=\"form-control\" value=\""+usuario.getRua()+"\">\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-numero\">\r\n"
					+ "                                        <label for=\"numero\">Número:</label><br>\r\n"
					+ "                                        <input type=\"text\" name=\"txt_numero\" id=\"txt_numero\" class=\"form-control\" value=\""+usuario.getNumero()+"\">\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"cadastro--endereco--linha2\">\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-complemento\">\r\n"
					+ "                                        <label for=\"complemento\">Complemento:</label><br>\r\n"
					+ "                                        <input type=\"text\" name=\"txt_complemento\" id=\"txt_complemento\"\r\n"
					+ "                                            class=\"form-control\" value=\""+usuario.getComplemento()+"\">\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-bairro\">\r\n"
					+ "                                        <label for=\"bairro\">Bairro:</label><br>\r\n"
					+ "                                        <input type=\"text\" name=\"txt_bairro\" id=\"txt_bairro\" class=\"form-control\" value=\""+usuario.getBairro()+"\">\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"cadastro--endereco--linha3\">\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-cidade\">\r\n"
					+ "                                        <label for=\"cidade\">Cidade:</label><br>\r\n"
					+ "                                        <input type=\"text\" name=\"txt_cidade\" id=\"txt_cidade\" class=\"form-control\" value=\""+usuario.getCidade()+"\">\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                    <div class=\"form-group cadastro--endereco-estado\">\r\n"
					+ "                                        <label for=\"estado\">Estado:</label><br>\r\n"
					+ "                                        <select name=\"txt_estado\" id=\"txt_estado\" class=\"form-select\" value=\""+usuario.getEstado()+"\">\r\n"
					+ "                                            <option value=\"MG\">Minas Gerais</option>\r\n"
					+ "                                            <option value=\"SP\">São Paulo</option>\r\n"
					+ "                                            <option value=\"RJ\">Rio de Janeiro</option>\r\n"
					+ "                                            <option value=\"ES\">Espírito Santo</option>\r\n"
					+ "                                        </select>\r\n"
					+ "                                    </div>\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <p class=\"text-center\">Contatos:</p>\r\n"
					+ "                                <div class=\"form-group cadastro--contato-telefone\">\r\n"
					+ "                                    <label for=\"telefone\">Telefone:</label><br>\r\n"
					+ "                                    <input type=\"text\" name=\"txt_telefone\" id=\"txt_telefone\" class=\"form-control\" value=\""+usuario.getTelefone()+"\">\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"cadastro--contato--linha1\">\r\n"
					+ "                                    <div class=\"form-group cadastro--contato-twitter\">\r\n"
					+ "                                        <label for=\"twitter\">Twitter:</label><br>\r\n"
					+ "                                        <div class=\"input-group mb-3\">\r\n"
					+ "                                            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\r\n"
					+ "                                            <input type=\"text\" name=\"txt_twitter\" id=\"txt_twitter\" class=\"form-control\" value=\""+usuario.getTwitter()+"\" \r\n"
					+ "                                                placeholder=\"Usuário\" aria-label=\"Username\"\r\n"
					+ "                                                aria-describedby=\"basic-addon1\">\r\n"
					+ "                                        </div>\r\n"
					+ "                                    </div>\r\n"
					+ "\r\n"
					+ "                                    <div class=\"form-group cadastro--contato-instagram\">\r\n"
					+ "                                        <label for=\"instagram\">Instagram:</label><br>\r\n"
					+ "                                        <div class=\"input-group mb-3\">\r\n"
					+ "                                            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\r\n"
					+ "                                            <input type=\"text\" name=\"txt_instagram\" id=\"txt_instagram\"\r\n"
					+ "                                                class=\"form-control\" value=\""+usuario.getInstagram()+"\" placeholder=\"Usuário\" aria-label=\"Username\"\r\n"
					+ "                                                aria-describedby=\"basic-addon1\">\r\n"
					+ "                                        </div>\r\n"
					+ "                                    </div>\r\n"
					+ "                                    \r\n"
					+ "                                    <div class=\"form-group cadastro--contato-facebook\">\r\n"
					+ "                                        <label for=\"facebook\">Facebook:</label><br>\r\n"
					+ "                                        <div class=\"input-group mb-3\">\r\n"
					+ "                                            <span class=\"input-group-text\" id=\"basic-addon1\">@</span>\r\n"
					+ "                                            <input type=\"text\" name=\"txt_facebook\" id=\"txt_facebook\"\r\n"
					+ "                                                class=\"form-control\" value=\""+usuario.getFacebook()+"\" placeholder=\"Usuário\" aria-label=\"Username\"\r\n"
					+ "                                                aria-describedby=\"basic-addon1\">\r\n"
					+ "                                        </div>\r\n"
					+ "                                    </div>\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"form-group cadastro--foto-perfil\">\r\n"
					+ "                                    <label for=\"imgperfil\">Imagem de Perfil:</label><br>\r\n"
					+ "                                    <input type=\"file\" name=\"imgperfil\" id=\"imgperfil\" class=\"form-control\"\">\r\n"
					+ "                                </div>\r\n"
					+ "<div class=\"text-center\">\r\n"
					+ "                                <button type=\"submit\" id=\"btn_salvar\" class=\"btn btn-warning\">"+buttonLabel+"</button>\r\n"
					+ "                            </div>"
					+ "                            </form>";
			
		} else if (tipo == FORM_DETAIL){
			umLivro += "<div class=\"container perfil-usuario\">\r\n"
					+ "            <!-- Inserção Perfil -->\r\n"
					+ "            <div class=\"row\">\r\n"
					+ "                <div class=\"col-4\">\r\n"
					+ "                    <div class=\"perfil-usuario--foto-perfil\">\r\n"
					+ "                        <img src=\"http://localhost:6532/imgs/"+usuario.getImagem()+"\" alt=\"Foto de perfil do usuário\">\r\n"					
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "                <div class=\"col-8 perfil-usuario--coluna-infos\">\r\n"
					+ "                    <div class=\"perfil-usuario--infos-gerais\">\r\n"
					+ "                        <h1>"+ usuario.getNome() +" </h1>\r\n"
					+ "							<p >"+ usuario.getSobrenome() +"</p>"
					+ "                        <p style=\"color: var(--vermelho-letras); font-weight: 600;\">Profissão</p>\r\n"
					+ "                        <p style=\"font-weight: 200; margin-bottom: 0; text-align: justify;\">Lorem ipsum dolor sit amet,\r\n"
					+ "                            consectetur adipisicing elit. Sunt amet dolores perferendis\r\n"
					+ "                            voluptate, magnam cum numquam quidem iusto doloribus consequuntur, tenetur sed at tempora\r\n"
					+ "                            facilis delectus quis voluptatem natus, vero fugit voluptates! Rem repudiandae reprehenderit\r\n"
					+ "                            optio iste dolorem officia animi, deleniti amet esse voluptate sequi quia, nulla, quibusdam\r\n"
					+ "                            incidunt quam.</p>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"perfil-usuario--contatos-gerais\">\r\n"
					+ "                        <button><i class=\"fa-solid fa-envelope\"></i>Mensagem</button>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"perfil-usuario--botoes-feed\">\r\n"
					+ "                        <button class=\"perfil-usuario--botoes-feed--sobre tablink w3-red\"\r\n"
					+ "                            onclick=\"abreAbaInfosUsuario(event, 'abaSobre')\"><i\r\n"
					+ "                                class=\"fa-solid fa-user\"></i>Sobre</button><button\r\n"
					+ "                            class=\"perfil-usuario--botoes-feed--recados tablink\"\r\n"
					+ "                            onclick=\"abreAbaInfosUsuario(event, 'abaRecados')\"><i\r\n"
					+ "                                class=\"fa-solid fa-comment\"></i>Recados</button>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "            </div>\r\n"
					+ "\r\n"
					+ "            <div class=\"row\">\r\n"
					+ "                <div class=\"col-4 perfil-usuario--infos-detalhadas\">\r\n"
					+ "                    <div class=\"perfil-usuario--lista-infos-detalhadas\">\r\n"
					+ "                        <div class=\"perfil-usuario--lista-infos-detalhadas--titulo\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 16px; font-weight: 600; margin-bottom: 0.5rem; text-transform: uppercase; color: var(--cinza-borda);\">\r\n"
					+ "                                Gostos</h6>\r\n"
					+ "                        </div>\r\n"
					+ "                        <p>Gosto 1</p>\r\n"
					+ "                        <p>Gosto 2</p>\r\n"
					+ "                        <p>Gosto 3</p>\r\n"
					+ "                        <p>Gosto 4</p>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "\r\n"
					+ "                <div class=\"col-8 perfil-usuario--sobre-infos\">\r\n"
					+ "                    <div id=\"abaSobre\" class=\"sobre-infos--aba\">\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--contatos\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px;\">\r\n"
					+ "                                Informações do Contato:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-2\" style=\"display: inline-block;\">\r\n"
					+ "                                <p>Telefone:</p>\r\n"
					+ "                                <p>Endereço:</p>\r\n"
					+ "                                <p>Email:</p>\r\n"
					+ "                            </div>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-9\" style=\"display: inline-block;\">\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getTelefone() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getRua() +", "+ usuario.getNumero() +" - "+ usuario.getBairro() +", "+ usuario.getCidade() +"/"+ usuario.getEstado() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getEmail() +"</p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--gerais\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px; margin-top: 30px;\">\r\n"
					+ "                                Redes Sociais:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-2\" style=\"display: inline-block;\">\r\n"
					+ "                                <p>Instagram:</p>\r\n"
					+ "                                <p>Twitter:</p>\r\n"
					+ "                                <p>Facebook:</p>\r\n"
					+ "                            </div>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-8\" style=\"display: inline-block;\">\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getInstagram() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getTwitter() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getFacebook() +"</p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div id=\"abaRecados\" class=\"sobre-infos--aba\" style=\"display: none;\">\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--recados\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px;\">\r\n"
					+ "                                Recados:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-12 pagina--perfil--comentarios\">\r\n"
					+ "                                <div class=\"pagina--perfil--comentarios--texto\">\r\n"
					+ "                                    <textarea name=\"cmt_perfil\" id=\"cmt_perfil\"></textarea>\r\n"
					+ "                                    <button type=\"submit\" class=\"btn btn-sm btn-dark\" id=\"btn_EnviarCmtPerfil\" disabled\r\n"
					+ "                                        onclick=\"incluirComentarioPerfil(document.getElementById('cmt_perfil').value)\">Enviar</button>\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"pagina--perfil--comentarios--posts\" id=\"feedCmtPerfil\">\r\n"
					+ "                                    <div class=\"pagina--perfil--comentarios--posts--item\">\r\n"
					+ "                                        <div class=\"pagina--perfil--comentarios--posts--item--foto\">\r\n"
					+ "                                            <img src=\"https://xsgames.co/randomusers/avatar.php?g=pixel\"\r\n"
					+ "                                                alt=\"Foto de perfil\">\r\n"
					+ "                                        </div>\r\n"
					+ "                                        <div class=\"pagina--perfil--comentarios--posts--item--infos\">\r\n"
					+ "                                            <h6>Nome do Usuário</h6>\r\n"
					+ "                                            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quas, enim.</p>\r\n"
					+ "                                            <p><i class=\"fa-regular fa-heart\"></i>0</p>\r\n"
					+ "                                        </div>\r\n"
					+ "                                    </div>\r\n"
					+ "                                </div>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "            </div>\r\n"
					+ "        </div>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		usuarioForm = usuarioForm.replaceFirst("<!--UM-LIVRO-->", umLivro);
		
		
	}
	
	
	public void makeAmigos(int orderBy) {
		String nomeArquivo = "./src/main/resources/public/amigos.html";
		usuarioForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	usuarioForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		
		String listaAmigos="";
		
		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_ID) {               usuarios = usuarioDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {		usuarios = usuarioDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_LOGIN) {			usuarios = usuarioDAO.getOrderByNomeUsuario();
		} else {											usuarios = usuarioDAO.get();
		}
		
		
		
		for (Usuario u : usuarios) {
			
			listaAmigos += 	  
			"<div class=\"campo-amigos--item-amigo\">\r\n"
			+ "    <div class=\"item-amigo--perfil-foto\">\r\n"
			+ "        <img src=\"http://localhost:6532/imgs/"+u.getImagem()+"\"\r\n"
			+ "                alt=\"Foto de perfil\">\r\n"
			+ "</div>"
			+ "    <div class=\"item-amigo--perfil-nome\">\r\n"
			+ "        <a href=\"http://localhost:6532/usuario/pagina/"+u.getId_usuario()+"\" rel=\"noopener noreferrer\">\r\n"
			+ "            <p>"+ u.getNome() +"</p>\r\n"
			+ "        </a>\r\n"
			+ "    </div>\r\n"
			+ "\r\n"
			+ "    <div class=\"item-amigo--botao-adicionar\">\r\n"
			+ "        <button id=\"btnAdicionar\" type=\"button\" value=\"\"><i\r\n"
			+ "                class=\"fa-solid fa-user-plus\"></i> Adicionar</button>\r\n"
			+ "    </div>\r\n"
			+ "</div>";
						
		}		
		usuarioForm = usuarioForm.replaceFirst("<!--LISTAR-AMIGOS-->", listaAmigos);		
	}
	
	public void makeUsuario(Usuario usuario) {
		String nomeArquivo = "./src/main/resources/public/usuario.html";
		usuarioForm = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	usuarioForm += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		
		String perfil="";
		
			
			perfil += "<div class=\"container perfil-usuario\">\r\n"
					+ "            <!-- Inserção Perfil -->\r\n"
					+ "            <div class=\"row\">\r\n"
					+ "                <div class=\"col-4\">\r\n"
					+ "                    <div class=\"perfil-usuario--foto-perfil\">\r\n"
					+ "                        <img src=\"http://localhost:6532/imgs/"+usuario.getImagem()+"\" alt=\"Foto de perfil do usuário\">\r\n"					
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "                <div class=\"col-8 perfil-usuario--coluna-infos\">\r\n"
					+ "                    <div class=\"perfil-usuario--infos-gerais\">\r\n"
					+ "                        <h1>"+ usuario.getNome() +" </h1>\r\n"
					+ "							<p >"+ usuario.getSobrenome() +"</p>"
					+ "                        <p style=\"color: var(--vermelho-letras); font-weight: 600;\">Profissão</p>\r\n"
					+ "                        <p style=\"font-weight: 200; margin-bottom: 0; text-align: justify;\">Lorem ipsum dolor sit amet,\r\n"
					+ "                            consectetur adipisicing elit. Sunt amet dolores perferendis\r\n"
					+ "                            voluptate, magnam cum numquam quidem iusto doloribus consequuntur, tenetur sed at tempora\r\n"
					+ "                            facilis delectus quis voluptatem natus, vero fugit voluptates! Rem repudiandae reprehenderit\r\n"
					+ "                            optio iste dolorem officia animi, deleniti amet esse voluptate sequi quia, nulla, quibusdam\r\n"
					+ "                            incidunt quam.</p>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"perfil-usuario--contatos-gerais\">\r\n"
					+ "                        <button><i class=\"fa-solid fa-envelope\"></i>Mensagem</button>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div class=\"perfil-usuario--botoes-feed\">\r\n"
					+ "                        <button class=\"perfil-usuario--botoes-feed--sobre tablink w3-red\"\r\n"
					+ "                            onclick=\"abreAbaInfosUsuario(event, 'abaSobre')\"><i\r\n"
					+ "                                class=\"fa-solid fa-user\"></i>Sobre</button><button\r\n"
					+ "                            class=\"perfil-usuario--botoes-feed--recados tablink\"\r\n"
					+ "                            onclick=\"abreAbaInfosUsuario(event, 'abaRecados')\"><i\r\n"
					+ "                                class=\"fa-solid fa-comment\"></i>Recados</button>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "            </div>\r\n"
					+ "\r\n"
					+ "            <div class=\"row\">\r\n"
					+ "                <div class=\"col-4 perfil-usuario--infos-detalhadas\">\r\n"
					+ "                    <div class=\"perfil-usuario--lista-infos-detalhadas\">\r\n"
					+ "                        <div class=\"perfil-usuario--lista-infos-detalhadas--titulo\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 16px; font-weight: 600; margin-bottom: 0.5rem; text-transform: uppercase; color: var(--cinza-borda);\">\r\n"
					+ "                                Gostos</h6>\r\n"
					+ "                        </div>\r\n"
					+ "                        <p>Gosto 1</p>\r\n"
					+ "                        <p>Gosto 2</p>\r\n"
					+ "                        <p>Gosto 3</p>\r\n"
					+ "                        <p>Gosto 4</p>\r\n"
					
					+"<div id=\"butoin1\">"
					+ "<a href='http://localhost:6532/usuario/delete/"+usuario.getId_usuario()+"'<button type=\"button\" class=\"btn btn-danger\"><i class=\"fa-solid fa-trash fa-2xl\"></i></button></a>"
					+"</div>"
					+"<div id=\"butoin2\">"
					+ "<a href='http://localhost:6532/usuario/update/"+usuario.getId_usuario()+"'<button type=\"button\" class=\"btn btn-info\"><i class=\"fa-solid fa-pen-to-square fa-2xl\"></i></button></a>"
					+ "</div>"		
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "\r\n"
					+ "                <div class=\"col-8 perfil-usuario--sobre-infos\">\r\n"
					+ "                    <div id=\"abaSobre\" class=\"sobre-infos--aba\">\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--contatos\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px;\">\r\n"
					+ "                                Informações do Contato:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-2\" style=\"display: inline-block;\">\r\n"
					+ "                                <p>Telefone:</p>\r\n"
					+ "                                <p>Endereço:</p>\r\n"
					+ "                                <p>Email:</p>\r\n"
					+ "                            </div>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-9\" style=\"display: inline-block;\">\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getTelefone() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getRua() +", "+ usuario.getNumero() +" - "+ usuario.getBairro() +", "+ usuario.getCidade() +"/"+ usuario.getEstado() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getEmail() +"</p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--gerais\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px; margin-top: 30px;\">\r\n"
					+ "                                Redes Sociais:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-2\" style=\"display: inline-block;\">\r\n"
					+ "                                <p>Instagram:</p>\r\n"
					+ "                                <p>Twitter:</p>\r\n"
					+ "                                <p>Facebook:</p>\r\n"
					+ "                            </div>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-8\" style=\"display: inline-block;\">\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getInstagram() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getTwitter() +"</p>\r\n"
					+ "                                <p style=\"color: var(--vermelho-letras);\">"+ usuario.getFacebook() +"</p>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "\r\n"
					+ "                    <div id=\"abaRecados\" class=\"sobre-infos--aba\" style=\"display: none;\">\r\n"
					+ "                        <div class=\"perfil-usuario--sobre-infos--recados\">\r\n"
					+ "                            <h6\r\n"
					+ "                                style=\"font-size: 14px; font-weight: 600; margin-bottom: 0; text-transform: uppercase; color: var(--cinza-medio); margin-bottom: 15px;\">\r\n"
					+ "                                Recados:</h6>\r\n"
					+ "\r\n"
					+ "                            <div class=\"col-12 pagina--perfil--comentarios\">\r\n"
					+ "                                <div class=\"pagina--perfil--comentarios--texto\">\r\n"
					+ "                                    <textarea name=\"cmt_perfil\" id=\"cmt_perfil\"></textarea>\r\n"
					+ "                                    <button type=\"submit\" class=\"btn btn-sm btn-dark\" id=\"btn_EnviarCmtPerfil\" disabled\r\n"
					+ "                                        onclick=\"incluirComentarioPerfil(document.getElementById('cmt_perfil').value)\">Enviar</button>\r\n"
					+ "                                </div>\r\n"
					+ "\r\n"
					+ "                                <div class=\"pagina--perfil--comentarios--posts\" id=\"feedCmtPerfil\">\r\n"
					+ "                                    <div class=\"pagina--perfil--comentarios--posts--item\">\r\n"
					+ "                                        <div class=\"pagina--perfil--comentarios--posts--item--foto\">\r\n"
					+ "                                            <img src=\"https://xsgames.co/randomusers/avatar.php?g=pixel\"\r\n"
					+ "                                                alt=\"Foto de perfil\">\r\n"
					+ "                                        </div>\r\n"
					+ "                                        <div class=\"pagina--perfil--comentarios--posts--item--infos\">\r\n"
					+ "                                            <h6>Nome do Usuário</h6>\r\n"
					+ "                                            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quas, enim.</p>\r\n"
					+ "                                            <p><i class=\"fa-regular fa-heart\"></i>0</p>\r\n"
					+ "                                        </div>\r\n"
					+ "                                    </div>\r\n"
					+ "                                </div>\r\n"
					+ "                            </div>\r\n"
					+ "                        </div>\r\n"
					+ "                    </div>\r\n"
					+ "                </div>\r\n"
					+ "            </div>\r\n"
					+ "        </div>";
			
						
				
		usuarioForm = usuarioForm.replaceFirst("<!--AMIGO-->", perfil);		
	}
	
	
	
	public Object insert(Request request, Response response) throws Exception {
		String nome = request.queryParams("txt_nome");
		String sobrenome = request.queryParams("txt_sobrenome");
		String nomeusuario = request.queryParams("txt_login");
		String email = request.queryParams("txt_email");
		String senha = DAO.toMD5(request.queryParams("txt_senha"));
		String senha2 = request.queryParams("txt_senha2");
		String rua = request.queryParams("txt_rua");
		int numero = Integer.parseInt(request.queryParams("txt_numero"));
		String complemento = request.queryParams("txt_complemento");
		String bairro = request.queryParams("txt_bairro");
		String cidade = request.queryParams("txt_cidade");
		String estado = request.queryParams("txt_estado");
		String telefone = request.queryParams("txt_telefone");
		String twitter = request.queryParams("txt_twitter");
		String instagram = request.queryParams("txt_instagram");
		String facebook = request.queryParams("txt_facebook");
		String imagem = request.queryParams("imgperfil");
		
		String resp = "";
		
		Usuario usuario = new Usuario(0, nomeusuario, nome, sobrenome, senha, email, facebook, instagram, estado, cidade, bairro, rua, complemento, numero, twitter, telefone, imagem);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuário (" + nomeusuario + ") cadastrado!";
            response.status(302);
            response.header("Location", "http://localhost:6532/login.html");
		} else {
			resp = "Usuário (" + nomeusuario + ") não cadastrado!";
			response.status(404); // 404 Not found
		}
			
		return resp;
	}

	
	public Object get (Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_usuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		usuarioForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return usuarioForm;
	}
	
	public Object get2 (Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_usuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeUsuario(usuario);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		usuarioForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return usuarioForm;
	}
	
	
	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_usuario"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		 makeForm();
    		// form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return usuarioForm; // RETORNO TESTE
	}
	
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return usuarioForm;
	}
	
	public Object getAll2(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeAmigos(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return usuarioForm;
	}
	
	
	public Object update(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":id_usuario"));
		Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
        	usuario.setNome(request.queryParams("txt_nome"));
    		usuario.setSobrenome(request.queryParams("txt_sobrenome"));
    		usuario.setNomeusuario(request.queryParams("txt_login"));
    		usuario.setEmail(request.queryParams("txt_email"));
    		usuario.setSenha(DAO.toMD5(request.queryParams("txt_senha")));  
    		usuario.setRua(request.queryParams("txt_rua"));
    		usuario.setNumero(Integer.parseInt(request.queryParams("txt_numero")));
    		usuario.setComplemento(request.queryParams("txt_complemento"));
    		usuario.setBairro(request.queryParams("txt_bairro"));
    		usuario.setCidade(request.queryParams("txt_cidade"));
    		usuario.setEstado(request.queryParams("txt_estado"));
    		usuario.setTelefone(request.queryParams("txt_telefone"));
    		usuario.setTwitter(request.queryParams("txt_twitter"));
    		usuario.setInstagram(request.queryParams("txt_instagram"));
    		usuario.setFacebook(request.queryParams("txt_facebook"));
        	
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Produto (ID " + usuario.getId_usuario() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (ID " + usuario.getId_usuario() + ") não encontrado!";
        }
		makeForm();
		return usuarioForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_usuario"));
        Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(id);
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não encontrado!";
        }
		makeForm();
		return usuarioForm.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	
	public String autenticar(Request request, Response response) throws Exception {
		String username = request.queryParams("username");
		String senha = request.queryParams("senha");   
		
		if(usuarioDAO.autenticar(username, DAO.toMD5(senha)) == true) {
			Usuario usuario = usuarioDAO.get(username,DAO.toMD5(senha));
			 return "{\"id\": " + usuario.getId_usuario() + ", \"nome\": \""+usuario.getNome()+ "\", \"imagem\": \"" + usuario.getImagem() + "\"}";
		} else {
			return "";
		}
	}
}