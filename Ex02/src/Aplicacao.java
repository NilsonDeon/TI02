
public class Aplicacao {
	
	public static void listar(){
		X[] xs = XDAO.getX();
	}

	public static void main(String[] args){
		
		int i = 0;
		
		
		
		while(i < 1){
			
			System.out.println("1 - Inserir\n"
					+ "2 - Listar\n"
					+ "3 - Atualizar\n"
					+ "4 - Excluir\n"
					+ "5 - Sair");
			i++;
			
		}
		
	}
	
}
