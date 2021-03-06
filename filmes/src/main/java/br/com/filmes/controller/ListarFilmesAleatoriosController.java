package br.com.filmes.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.pengrad.telegrambot.model.Update;

import br.com.filmes.model.Model;
import br.com.filmes.view.View;


public class ListarFilmesAleatoriosController implements FilmeController {

	private Model model;
	private View view;

	public ListarFilmesAleatoriosController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void pesquisar(Update update) throws FileNotFoundException, IOException {
		view.sendTypingMessage(update);
		model.listaFilmesAleatorio(update);
	}

}
