package model;

public class Fav {
    private int id_role;
    private int id_usuario;

    public Fav() {
        this.id_usuario = 0;
        this.id_role = 0;
    }

    public Fav(int id_usuario, int id_role) {
        setId_usuario(id_usuario);
        setId_role(id_role);
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Usuario [id_usuario =" + id_usuario + ", id_role = " + id_role + "]";
    }
}