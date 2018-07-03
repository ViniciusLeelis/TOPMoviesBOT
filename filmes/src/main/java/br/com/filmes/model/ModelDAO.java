package br.com.filmes.model;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.pengrad.telegrambot.model.Update;

public class ModelDAO {
	
	private static ModelDAO modelDAO;
	private static ObjectContainer database;

	public static ModelDAO getInstance() {
		if (modelDAO == null) {
			modelDAO = new ModelDAO();
		}
		return modelDAO;
	}

	private static ObjectContainer connect() {
		if (database == null)
			database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "db/bancoFilmes.db4o");
		return database;
	}

	//Add data
	public static void addHistoric(Historic historic) {
		database = connect();
		database.store(historic);
		database.commit();
	}


	//Get data
	public static ArrayList<Historic> getHistoric(Update update) {
		long chatId = update.message().chat().id();
		
		database = connect();
		Query query = database.query();
		query.constrain(Historic.class);
		
		ObjectSet<Historic> allHistoric = query.execute();
		ArrayList<Historic> userHistoric = new ArrayList<Historic>();
		
		for (Historic historic : allHistoric) {
			if (historic.getChatId() == chatId) 
				userHistoric.add(historic);
		}
		
		if (userHistoric.isEmpty()) {
			userHistoric = null;
		}
		
		return userHistoric;
	}

}
