package dao;

import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO extends DAO {	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert (Usuario usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuario (nomeusuario, nome, sobrenome, senha, email, facebook, instagram, estado, cidade, bairro, rua, complemento, numero, twitter, telefone, imagem) "
		               + "VALUES ('" + usuario.getNomeusuario() + "', '"
		               + usuario.getNome() + "', '" + usuario.getSobrenome() + "', '" + usuario.getSenha() + "', '" + usuario.getEmail() + "', '"+ usuario.getFacebook() + "', '"+ usuario.getInstagram() + "', '"+ usuario.getEstado() + "', '"+ usuario.getCidade() + "', '"+ usuario.getBairro() + "', '"+ usuario.getRua() + "', '"+ usuario.getComplemento() + "', '"+ usuario.getNumero() + "', '"+ usuario.getTwitter() + "', '"+ usuario.getTelefone() + "', '"+usuario.getImagem() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
		    // st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			// st.setDate(2, Date.valueOf(produto.getDataValidade()));
			System.out.println(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario get (int id_usuario) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE id_usuario = " + id_usuario;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 usuario = new Usuario(rs.getInt("id_usuario"), rs.getString("nomeusuario"), rs.getString("nome"), rs.getString("sobrenome"), 
	        			 			   rs.getString("senha"),
                                        rs.getString("email"),
	        			 			   rs.getString("facebook"),
                                        rs.getString("instagram"),
                                        rs.getString("estado"),
                                        rs.getString("cidade"),
                                        rs.getString("bairro"),
                                        rs.getString("rua"),
                                        rs.getString("complemento"),
                                        rs.getInt("numero"),
                                        rs.getString("twitter"),
                                        rs.getString("telefone"),
                                        rs.getString("imagem"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return usuario;
	}
	
	public Usuario get (String login, String senha) {
	    Usuario usuario = null;
	    
	    try {
	        Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	        String sql = "SELECT * FROM usuario WHERE nomeusuario LIKE '" + login + "' AND senha LIKE '" + senha  + "'";
	        ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	usuario = new Usuario(rs.getInt("id_usuario"), 
	        			 rs.getString("nomeusuario"), 
	        			 rs.getString("nome"), 
	        			 rs.getString("sobrenome"), 
			 			 rs.getString("senha"),
                         rs.getString("email"),
			 			 rs.getString("facebook"),
                         rs.getString("instagram"),
                         rs.getString("estado"),
                         rs.getString("cidade"),
                         rs.getString("bairro"),
                         rs.getString("rua"),
                         rs.getString("complemento"),
                         rs.getInt("numero"),
                         rs.getString("twitter"),
                         rs.getString("telefone"),
                         rs.getString("imagem"));
	        }
	        st.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    
	    return usuario;
	}
	
	
	public List<Usuario> get() {
		return get("");
	}

	
	public List<Usuario> getOrderById() {
		return get("id_usuario");
	}
	
	
	public List<Usuario> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Usuario> getOrderByNomeUsuario() {
		return get("nomeusuario");		
	}
	
	
	private List<Usuario> get (String orderBy) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Usuario u = new Usuario(rs.getInt("id_usuario"), rs.getString("nomeusuario"), rs.getString("nome"), rs.getString("sobrenome"), 
                rs.getString("senha"),
                rs.getString("email"),
                rs.getString("facebook"),
                rs.getString("instagram"),
                rs.getString("estado"),
                rs.getString("cidade"),
                rs.getString("bairro"),
                rs.getString("rua"),
                rs.getString("complemento"),
                rs.getInt("numero"),
                rs.getString("twitter"),
                rs.getString("telefone"),
                rs.getString("imagem"));
	            usuarios.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return usuarios;
	}
	
	
	public boolean update (Usuario usuario) {
		boolean status = false;
		try {
			String sql = "UPDATE usuario SET nomeusuario = '" + usuario.getNomeusuario() + "', "
					   + "nome = '" + usuario.getNome() + "', "
					   + "sobrenome = '" + usuario.getSobrenome() + "', "
					   + "senha = '" + usuario.getSenha() + "', "
					   + "email = '" + usuario.getEmail() + "', "
					   + "facebook = '" + usuario.getFacebook() + "', "
					   + "instagram = '" + usuario.getInstagram() + "', "
					   + "estado = '" + usuario.getEstado() + "', "
					   + "cidade = '" + usuario.getCidade() + "', "
					   + "bairro = '" + usuario.getBairro() + "', "
					   + "rua = '" + usuario.getRua() + "', "
					   + "complemento = '" + usuario.getComplemento() + "', " 
					   + "numero = '" + usuario.getNumero() + "',"
					   + "twitter = '" + usuario.getTwitter() + "'," 
					   + "telefone = '" + usuario.getTelefone() + "',"
					   + "imagem = '" + usuario.getImagem() +"' WHERE id_usuario = " + usuario.getId_usuario();
			PreparedStatement st = conexao.prepareStatement(sql);
		    //st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			//st.setDate(2, Date.valueOf(produto.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	
	public boolean delete (int id_usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE id_usuario = " + id_usuario );
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	public boolean autenticar(String login, String senha) {
	    boolean resp = false;
	    
	    try {
	        String sql = "SELECT * FROM usuario WHERE nomeusuario = ? AND senha = ?";
	        PreparedStatement pstmt = conexao.prepareStatement(sql);
	        pstmt.setString(1, login);
	        pstmt.setString(2, senha);
	        
	        ResultSet rs = pstmt.executeQuery();
	        
	        resp = rs.next();
	        
	        pstmt.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    
	    return resp;
	}		
}
