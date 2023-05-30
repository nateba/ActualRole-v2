package dao;

import model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class RoleDAO extends DAO {	
	public RoleDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert (Role role) {
		boolean status = false;
		try {
			String sql = "INSERT INTO role (nome, telefone, datarole, email, preco, descricao, estado, cidade, bairro, rua, numero, faixaetaria, instagram, facebook, imgcapa, img1, img2, img3, id_usuario) "
		               + "VALUES ('" + role.getNome() + "', '"
		               + role.getTelefone() + "', '" + role.getDatarole() + "', '" + role.getEmail() + "', '" + role.getPreco() + "', '"+ role.getDescricao() + "', '"+ role.getEstado() + "', '"+ role.getCidade() + "', '"+ role.getBairro() + "', '"+ role.getRua() + "', '"+ role.getNumero() + "', '"+ role.getFaixaetaria() + "', '"+ role.getInstagram() + "', '"+ role.getFacebook() + "', '"+ role.getImgCapa() + "', '"+ role.getImg1() + "', '"+ role.getImg2() + "', '"+role.getImg3() + "', '"+role.getId_usuario() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
		    // st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			// st.setDate(0, Date.valueOf(role.getDatarole()));
			System.out.println(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Role get (int id_role) {
		Role role = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM role WHERE id_role = " + id_role;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 role = new Role(rs.getInt("id_role"), rs.getString("nome"), rs.getString("telefone"), rs.getDate("datarole").toLocalDate(), 
	        			 			   rs.getString("email"),
                                        rs.getDouble("preco"),
	        			 			   rs.getString("descricao"),
                                        rs.getString("estado"),
                                        rs.getString("cidade"),
                                        rs.getString("bairro"),
                                        rs.getString("rua"),
                                        rs.getInt("numero"),
                                        rs.getString("faixaetaria"),
                                        rs.getString("instagram"),
                                        rs.getString("facebook"),
                                        rs.getString("imgCapa"),
                                        rs.getString("img1"),
                                        rs.getString("img2"),
                                        rs.getString("img3"),
                                        rs.getInt("id_usuario"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return role;
	}
	
	
	public List<Role> get() {
		return get("");
	}

	
	public List<Role> getOrderById() {
		return get("id_role");
	}
	
	
	public List<Role> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Role> getOrderByFaixaEtaria() {
		return get("faixaetaria");		
	}
	
	
	private List<Role> get (String orderBy) {
		List<Role> roles = new ArrayList<Role>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM role" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Role r = new Role(rs.getInt("id_role"), rs.getString("nome"), rs.getString("telefone"), rs.getDate("datarole").toLocalDate(), 
                rs.getString("email"),
                rs.getDouble("preco"),
                rs.getString("descricao"),
                rs.getString("estado"),
                rs.getString("cidade"),
                rs.getString("bairro"),
                rs.getString("rua"),
                rs.getInt("numero"),
                rs.getString("faixaetaria"),
                rs.getString("instagram"),
                rs.getString("facebook"),
                rs.getString("imgCapa"),
                rs.getString("img1"),
                rs.getString("img2"),
                rs.getString("img3"),
                rs.getInt("id_usuario"));
	            roles.add(r);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return roles;
	}
	
	
	public boolean update (Role role) {
		boolean status = false;
		try {
			String sql = "UPDATE role SET nome = '" + role.getNome() + "', "
					   + "telefone = '" + role.getTelefone() + "', "
					   + "datarole = '" + role.getDatarole() + "', "
					   + "email = '" + role.getEmail() + "', "
					   + "preco = '" + role.getPreco() + "', "
					   + "descricao = '" + role.getDescricao() + "', "
					   + "estado = '" + role.getEstado() + "', "
					   + "cidade = '" + role.getCidade() + "', "
					   + "bairro = '" + role.getBairro() + "', "
					   + "rua = '" + role.getRua() + "', "
					   + "numero = '" + role.getNumero() + "', "
					   + "faixaetaria = '" + role.getFaixaetaria() + "', " 
					   + "instagram = '" + role.getInstagram() + "',"
					   + "facebook = '" + role.getFacebook() + "'," 
					   + "imgCapa = '" + role.getImgCapa() + "',"
                       + "img1 = '" + role.getImg1() + "',"
                       + "img2 = '" + role.getImg2() + "',"
					   + "img3 = '" + role.getImg3() +"' WHERE id_role = " + role.getId_role();
			PreparedStatement st = conexao.prepareStatement(sql);
		    //st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			//st.setDate(0, Date.valueOf(role.getDatarole()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	
	public boolean delete (int id_role) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM role WHERE id_role = " + id_role );
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return status;
	}
	
	
		
}
	
		
