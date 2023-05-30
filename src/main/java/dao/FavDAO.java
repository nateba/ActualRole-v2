package dao;

import model.Fav;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FavDAO extends DAO {
    public FavDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Fav fav) {
        boolean status = false;
        try {
            String sql = "INSERT INTO fav (id_usuario, id_role) "
                    + "VALUES ('" + fav.getId_usuario() + "', '" + fav.getId_role() + "');";
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

    public Fav get(int id_usuario, int id_role) { //MUDAR PARAMETROS PROS DOIS
        Fav fav = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM fav WHERE id_usuario = " + id_usuario + " AND id_role = " + id_role; // MUDAR O SQL PRA PEGAR O ID DO USUARIO E DO ROLE
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                fav = new Fav(rs.getInt("id_usuario"), rs.getInt("id_role"));
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return fav;
    }

    public List<Fav> get() {
        return get("");
    }

    public List<Fav> getOrderById() {
        return get("id_usuario");
    }

    public List<Fav> getOrderByNome() {
        return get("nome");
    }

    private List<Fav> get(String orderBy) {
        List<Fav> favs = new ArrayList<Fav>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM fav" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Fav f = new Fav(rs.getInt("id_usuario"), rs.getInt("id_role"));
                favs.add(f);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return favs;
    }

    public boolean delete(int id_usuario, int id_role) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM fav WHERE id_usuario = " + id_usuario + " AND id_role = " + id_role);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

}