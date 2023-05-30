package model;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Role {
    private int id_role;
    private String nome;
    private String telefone;
    private LocalDate datarole;
    private String email;
    private double preco;
    private String descricao;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String faixaetaria;
    private String instagram;
    private String facebook;
    private String imgCapa;
    private String img1;
    private String img2;
    private String img3;
    private int id_usuario;

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDatarole() {
        return datarole;
    }

    public void setDatarole(LocalDate datarole) {
        this.datarole = datarole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFaixaetaria() {
        return faixaetaria;
    }

    public void setFaixaetaria(String faixaetaria) {
        this.faixaetaria = faixaetaria;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setImgCapa(String imgCapa) {
        this.imgCapa = imgCapa;
    }

    public String getImgCapa() {
        return imgCapa;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public Role() {
        this.id_role = 0;
        this.nome = "nomin";
        this.telefone = "telzin";
        this.datarole = LocalDate.now();
        this.email = "rolezin@email.com";
        this.preco = 0.00;
        this.descricao = "descricaozinha";
        this.estado = "estadin";
        this.cidade = "cidadezinha";
        this.bairro = "barrin";
        this.rua = "ruazinha";
        this.numero = 00;
        this.faixaetaria = "faixinha";
        this.instagram = "instazin";
        this.facebook = "testett";
        this.imgCapa = "imgCapa";
        this.img1 = "img1";
        this.img2 = "img2";
        this.img3 = "img3";
        this.id_usuario = 0;
    }

    

	public Role(int id_role, String nome, String telefone, LocalDate datarole, String email, double preco,
            String descricao, String estado, String cidade, String bairro, String rua, int numero,
            String faixaetaria, String instagram, String facebook, String imgCapa, String img1, String img2,
            String img3, int id_usuario) {
        setId_role(id_role);
        setNome(nome);
        setTelefone(telefone);
        setDatarole(datarole);
        setEmail(email);
        setPreco(preco);
        setDescricao(descricao);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        setNumero(numero);
        setFaixaetaria(faixaetaria);
        setInstagram(instagram);
        setFacebook(facebook);
        setImgCapa(imgCapa);
        setImg1(img1);
        setImg2(img2);
        setImg3(img3);
        setId_usuario(id_usuario);
    }

    @Override
    public String toString() {
        return "Usuario [id_role = " + id_role + ", nome = " + nome + ", telefone = " + telefone
                + ", darole = " + datarole + ", email = " + email + ", preco =" + preco + ", descricao = " + descricao
                + ", estado = " + estado + ", estado = " + estado + ", cidade = " + cidade + ", bairro = "
                + bairro + ", rua = " + rua + ", numero = " + numero + ", faixaetaria = " + faixaetaria
                + ", instagram = "
                + instagram + ", facebook =" + facebook + ", imgCapa =" + imgCapa + ", img1 =" + img1 + ", img2 ="
                + img2 + ", img3 =" + img3 + ", id_usuario =" + id_usuario + "]";
    }

}