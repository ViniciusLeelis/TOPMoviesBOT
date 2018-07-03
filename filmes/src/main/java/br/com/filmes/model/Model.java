package br.com.filmes.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;

import br.com.filmes.FilmeExemplo;
import br.com.filmes.view.Observer;

public class Model {

	private List<Observer> observers = new LinkedList<Observer>();
	private static Model uniqueInstance;

	public static Model getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers(long chatId, String studentsData) {
		for (Observer observer : observers) {
			observer.update(chatId, studentsData);
		}
	}

	public void listaFilmesAleatorio(Update update) throws FileNotFoundException, IOException {
		try(Reader reader = new FileReader("C:\\Users\\tsoar\\Desktop\\TopMoviesChatBot\\filmes\\src\\main\\java\\br\\com\\filmes\\filmes.txt" )){
			
			Gson gson 			  = new Gson();
			FilmeExemplo[] filmes = gson.fromJson(reader, FilmeExemplo[].class);
			ArrayList<FilmeExemplo>filmesAleatorio = new ArrayList<>(Arrays.asList(filmes));
			this.notifyObservers(update.message().chat().id(), imprimirFilmeAleatorio(filmesAleatorio, update));
		}
	}
	
	public void listaFilmesPorGenero(Update update) throws FileNotFoundException, IOException {
			try(Reader reader = new FileReader("C:\\Users\\tsoar\\Desktop\\TopMoviesChatBot\\filmes\\src\\main\\java\\br\\com\\filmes\\filmes.txt")){
			
			Gson gson 			  = new Gson();
			FilmeExemplo[] filmes = gson.fromJson(reader, FilmeExemplo[].class);
			ArrayList<FilmeExemplo> filmesGenero = new ArrayList<>(Arrays.asList(filmes));
			filmesGenero.clear();
			for (FilmeExemplo f : filmes) {
				if(update.message().text().toUpperCase().equals(f.getGenero().toUpperCase())) {
					filmesGenero.add(f);
					addHistoric(f, update);
				}
			}
			this.notifyObservers(update.message().chat().id(), imprimirFilmes(filmesGenero));
		}
	}
	
	public void listarHistorico (Update update) {
		
		ArrayList<Historic> historics = ModelDAO.getHistoric(update);
		
		if(!historics.isEmpty()) {
			this.notifyObservers(update.message().chat().id(), "HISTORICO DE PESQUISA DE FILMES:" + "\n\n" + imprimeHistorico(historics));
		}else
			this.notifyObservers(update.message().chat().id(), "Voce nao possui historico");
	}
	
	
	public String imprimirFilmeAleatorio(ArrayList<FilmeExemplo> filmes, Update update) {
		Random gerador 		= new Random();
		int numero 			= gerador.nextInt(filmes.size());
		String imprimessao 	= "";
		FilmeExemplo f 		= filmes.get(numero);
			
		addHistoric(f, update);
		imprimessao += "Ranking : " + f.getRank() 	+ "\n" 
					+  "Nome : " 	+ f.getNome() 	+ "\n\n"; 
				//	+  "Imagem : " 	+ f.getImagem() + "\n\n";
		return imprimessao;
	}


	public String imprimirFilmes(ArrayList<FilmeExemplo> filmes) {
		String imprimessao 	= "";
		
		for (FilmeExemplo f : filmes) {
			imprimessao += "Ranking : " + f.getRank() 	+ "\n" 
					+  "Nome : " 	+ f.getNome() 	+ "\n\n"; 
					//+  "Imagem : " 	+ f.getImagem() + "\n\n";
		}
		if(!filmes.isEmpty())
			return imprimessao;
		else
			return "Nenhum resultado encontrado para esse genero";
	}
	
	public void addHistoric(FilmeExemplo filme, Update update) {
		long chatId = update.message().chat().id();
		String nomeFilme = filme.getNome();
		String generoFilme = filme.getGenero();
		
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		Historic historic = new Historic(chatId, nomeFilme, generoFilme, formatador.format( data ));
		ModelDAO.addHistoric(historic);
	}
	
	public String imprimeHistorico (ArrayList<Historic> historics) {
		String impressao = "";
		for (Historic historic : historics) {
			impressao += "Filme: " + historic.getNomeFilme() + "\n" +
						 "Genero: " + historic.getGeneroFilme() + "\n" +
						 "Dia Pesquisado: " + historic.getDiaPesquisado() + "\n\n";
						 
		}
		return impressao;
	}
}


