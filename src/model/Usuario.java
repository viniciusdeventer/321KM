package model;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private int tipoUsuario;
    private int idReferencia;
    private String nome;
    private int status;

    public Usuario() {}
    public Usuario(int id, String login, String senha, int tipoUsuario, int idReferencia, String nome, int status) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.idReferencia = idReferencia;
        this.nome = nome;
        this.status = status;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

    public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

    public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

    public int getTipo() {
		return tipoUsuario;
	}
	public void setTipo(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public int getIdReferencia() {
		return idReferencia;
	}
	
	public void setIdReferencia(int idReferencia) {
		this.idReferencia = idReferencia;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
