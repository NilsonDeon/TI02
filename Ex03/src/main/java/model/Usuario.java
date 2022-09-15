package model;

public class Usuario {
	private int id;
	private String email;
	private String senha;
	private String nome;
	
	public Usuario() {
		this.email = "";
		this.senha = "";
		this.nome = "";
		this.id = -1;
	}
	
	public Usuario(int id, String email, String senha, String nome) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", senha=" + senha + ", nome=" + nome + "]";
	}	
}
