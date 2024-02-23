package com.juank.service.googleBookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juank.entity.Book;
import com.juank.exception.BookException;

import reactor.core.publisher.Mono;



@Service
public class GoogleBookService {

	/** 
	 * While RestTemplate uses the caller thread for each event (HTTP call), WebClient will create something like a “task” for each event. 
	 * Behind the scenes, the Reactive framework will queue those “tasks” and execute them only when the appropriate response is available.
	 * The notification will be produced only when the response is ready.
	 * */
	private WebClient webClient;
	
	Logger logger = LoggerFactory.getLogger(GoogleBookService.class);
	
//	@PostConstruct
//	public void init() {
//		webClient = WebClient.builder().baseUrl("www.googleapis.com/books/v1/volumes")
//				.build();
//	}
//	
	public List<Book> findByFilters(Map<String, String> bookMap)   {
		String uri = buildURI(bookMap);
		
		JsonObject responseJson = apiCallGetJsonFormat(uri);
		List<Book> books = getBooksFromJson(responseJson); 
		
		return books;
		
	}
	
	private List<Book> getBooksFromJson(JsonObject responseJson) {
		
		if(responseJson.get("totalItems").getAsInt() == 0) return new ArrayList<Book>();
		
		JsonArray items = responseJson.getAsJsonArray("items");
		
		List<Book> books = new ArrayList<>();
		
		for(JsonElement obj:items) {
			
			JsonObject book = obj.getAsJsonObject();
			JsonElement el = book.get("volumeInfo");
			Book bookObj = getBookObjFromJsonElement(el);
			bookObj.setGoogleId(obj.getAsJsonObject().get("id").toString());
			books.add(bookObj);
		}
		
		return books;
	}

	private Book getBookObjFromJsonElement(JsonElement el) {
		Book book = new Book();	
		book.setId(el.getAsJsonObject().has("googleId")? Long.parseLong(el.getAsJsonObject().get("googleId").toString()) : 1 );
		book.setTitle(el.getAsJsonObject().get("title").toString());
		book.setDescription(el.getAsJsonObject().has("description") ? el.getAsJsonObject().get("description").toString() : "");
		book.setPageCount(el.getAsJsonObject().has("pageCount") ? Integer.valueOf(el.getAsJsonObject().get("pageCount").toString()): 0);
		book.setISBN(el.getAsJsonObject().has("isbn") ? el.getAsJsonObject().get("isbn").toString():"");
        JsonArray authorsArray = el.getAsJsonObject().get("authors").getAsJsonArray();
        List<String> authors = new ArrayList<>();
        for(int i=0; i<authorsArray.size(); i++) {
        	authors.add(authorsArray.get(i).toString());
        }
        book.setAuthors(authors);
		book.setImageLink(el.getAsJsonObject().has("imageLinks") ? el.getAsJsonObject().get("imageLinks").getAsJsonObject().get("thumbnail").toString() : "" );
		return book;
	}

	
	private JsonObject apiCallGetJsonFormat(String uri) {
		webClient = WebClient.builder().baseUrl("https://www.googleapis.com/books/v1/volumes")
				.build();
		
		WebClient.ResponseSpec responseSpec = webClient.get()
			    .uri(uri)
			    .retrieve();
		
		JsonObject resultJson = new Gson().fromJson(responseSpec.bodyToMono(String.class).block(), JsonObject.class);
		return resultJson;
	}
	
	private String buildURI(Map<String, String> bookMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		for (Entry<String, String> entry : bookMap.entrySet()) {
			String key = entry.getValue().replace(" ", "+");
			
			if("q".equals(entry.getKey())) {
				sb.append("q=" + key);
			} else if("inauthor".equals(entry.getKey())) {
				sb.append("+inauthor:" +key);
			} else if("intitle".equals(entry.getKey())) {
				sb.append("+intitle:" +key);
			} else if("isbn".equals(entry.getKey())) {
				sb.append("+isbn:" +key);
			} else if("inpublisher".equals(entry.getKey())) {
				sb.append("+inpublisher:" +key);
			} else if("subject".equals(entry.getKey())) {
				sb.append("+subject:" +key);
			}
			
			//sb.append("&");
		}
		//sb.deleteCharAt(sb.length()-1);  
			
		return sb.toString();
	}
	
	private ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			logger.info("Request {} {}", clientRequest.method(), clientRequest.url());
			return Mono.just(clientRequest);
		});
	}
	
	private ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			logger.info("Response status code{}", clientResponse.statusCode());
			return Mono.just(clientResponse);
		});
	}
	
}
