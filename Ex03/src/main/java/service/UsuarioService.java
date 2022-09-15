package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_EMAIL = 2;
	private final int FORM_ORDERBY_NOME = 3;
	private final int FORM_ORDERBY_SENHA = 4;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_EMAIL);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUsuario = "";
		if(tipo != FORM_INSERT) {
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Usuario</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, email, buttonLabel, senha, nome;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuário";
				email = "";
				senha = "";
				nome = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getId();
				name = "Atualizar Usuario (Id " + usuario.getId() + ")";
				email = usuario.getEmail();
				senha = usuario.getSenha();
				nome = usuario.getNome();
				buttonLabel = "Atualizar";
			}
			umUsuario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ email +"\"></td>";
			umUsuario += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"text\" name=\"senha\" value=\""+ senha +"\"></td>";
			umUsuario += "\t\t\t<td>Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (Id " + usuario.getId() + ")</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Email: "+ usuario.getEmail() +"</td>";
			umUsuario += "\t\t\t<td>Senha: "+ usuario.getSenha() +"</td>";
			umUsuario += "\t\t\t<td>Nome: "+ usuario.getNome() +"</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USUARIO>", umUsuario);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Usuários</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_ID + "\"><b>Id</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_EMAIL + "\"><b>Email</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_SENHA + "\"><b>Senha</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_ID) {                 	usuarios = usuarioDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {		usuarios = usuarioDAO.getOrderByEmail();
		} else if (orderBy == FORM_ORDERBY_NOME) {			usuarios = usuarioDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_SENHA) {			usuarios = usuarioDAO.getOrderBySenha();
		} else {											usuarios = usuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario p : usuarios) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getEmail() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getSenha() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/delete/" + p.getId() + "\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		String nome = request.queryParams("nome");

		String resp = "";
		
		Usuario usuario = new Usuario(-1, email, senha, nome);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Email (" + email + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Email (" + email + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_EMAIL);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_EMAIL);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setNome(request.queryParams("nome"));
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario (Id " + usuario.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (Id \" + usuario.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Usuario usuario = usuarioDAO.get(id);
        String resp = "";       
        if (usuario != null) {
            usuarioDAO.delete(id);
            response.status(200); // success
            resp = "Usuario (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}