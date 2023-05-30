package model;

public class Usuario {
    private int id_usuario;
    private String nomeusuario;
    private String nome;
    private String sobrenome;
    private String senha;
    private String email;
    private String facebook;
    private String instagram;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String complemento;
    private int numero;
    private String twitter;
    private String telefone;
    private String imagem;

    public Usuario() {
        this.id_usuario = 0;
        this.nomeusuario = "teste";
        this.nome = "Teste";
        this.sobrenome = "TesteSobre";
        this.senha = "senha";
        this.email = "email@email.com";
        this.facebook = "testefb";
        this.instagram = "testeig";
        this.estado = "testeEstado";
        this.cidade = "testeCidade";
        this.bairro = "testeBairro";
        this.rua = "testeRua";
        this.complemento = "testeComplemento";
        this.numero = 00;
        this.twitter = "testett";
        this.telefone = "testeTelefoe";
        this.imagem = "testeImagem";
    }

    public Usuario(int id_usuario, String nomeusuario, String nome, String sobrenome, String senha, String email,
            String facebook, String instagram, String estado, String cidade, String bairro, String rua,
            String complemento, int numero, String twitter, String telefone, String imagem) {
        setId_usuario(id_usuario);
        setNomeusuario(nomeusuario);
        setNome(nome);
        setSobrenome(sobrenome);
        setSenha(senha);
        setEmail(email);
        setFacebook(facebook);
        setInstagram(instagram);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        setComplemento(complemento);
        setNumero(numero);
        setTwitter(twitter);
        setTelefone(telefone);
        setImagem(imagem);
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    @Override
    public String toString() {
        return "Usuario [id_usuario = " + id_usuario + ", nomeUsuario = " + nomeusuario + ", nome = " + nome
                + ", sobrenome = " + sobrenome + ", senha = " + senha + ", email =" + email + ", facebook = " + facebook
                + ", instagram = " + instagram + ", estado = " + estado + ", cidade = " + cidade + ", bairro = "
                + bairro + ", rua = " + rua + ", complemento = " + complemento + ", numero = " + numero + ", twitter = "
                + twitter + ", telefone =" + telefone + ", imagem =" + imagem +"]";
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId_usuario() == ((Usuario) obj).getId_usuario());
    }
}