public class X {

	private int id;
	private String nome;
	private String sobrenome;
	private int idade;
	
	// construtores
	public X() {
		this.id = -1;
		this.nome = "";
		this.sobrenome = "";
		this.idade = 0;
	}
	
	public X(int id, String nome, String sobrenome, int idade) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.idade = idade;
	}
	
	
	// geters e seters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	
	// transformando em string
	@Override
	public String toString() {
		return "X [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", idade=" + idade + "]";
	}	
	
}