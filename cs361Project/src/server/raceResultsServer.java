package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import cs361Project.ExportedRun;
import cs361Project.ExportedRunner;

public class raceResultsServer {
    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static boolean gotMessageFlag = false;
    static ArrayList<ExportedRun> expRun = new ArrayList<ExportedRun>();

    public static void main(String[] args) throws Exception {

        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        //server.createContext("/mystyle.css", new CSSHandler());
        
        // create a context to get the request to display the results
        server.createContext("/displayresults", new DefaultHandler());
        
        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
        server.setExecutor(null); // creates a default executor

        // get it going
        System.out.println("Starting Server...");
        server.start();
    }

    static class DefaultHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

        	t.getResponseHeaders().set("Content-Type", "Text/html");
        	
            //String response = "<link rel=\"stylesheet\" type=\"text/css\" href=\"/mystyle.css\">";
            String response = getHeader();
            
            for(ExportedRun e : expRun) {
            	response += "<div class=\"raceContainer\">" +
            				"<div class=\"eventTitle\">" + e.raceType + " Race</div>" +
            				"<div class=\"runNumber\">Run #" + e.raceNumber +"</div>" +
            				"<table><tr class=\"header\"><td class=\"place\">Place</td><td class=\"runner\">Runner</td><td class=\"name\">Name</td><td class=\"elapsed\">Elapsed</td>";
            	int i = 1;
            	for(ExportedRunner r : e.runners) {
            		response += "<tr>" +
								"<td class=\"place\">" + i + ".</td>" +
								"<td class=\"runner\">" + r.number + "</td>" +
								"<td class=\"name\">" + " " + "</td>" +
								"<td class=\"elapsed\">" + r.elapsedTime + "</td>" +
								"</tr>";
            		++i;
            	}
            	
            	response += "</table></div>";
            }
            
            // write out the response
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    private static String getHeader() {
    	String res = "<style>" +
						"body { font-family: Verdana, Helvetica, Arial, sans-serif; }" +
						".raceContainer { margin-bottom: 40px; padding-bottom: 40px; border-bottom: 2px solid gray; }" +
						"table { margin-top: 5px; min-width: 400px; border-collapse: collapse; }" +
						"table tr:nth-child(2n) { background-color: #DDD; }" +
						"th, td { padding: 1px 8px; font-size: .9em; }" +
						".place { text-align: right; }" +
						".header, .eventTitle { font-weight: bold; }" +
						".runNumber { font-size: .9em; font-style: italic; }" +
					"</style>" +
					"<h2>Race Results</h2>";
    	return res;
    }

    static class PostHandler implements HttpHandler {
        public void handle(HttpExchange transmission) throws IOException {

            // set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = transmission.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }

            // respond to the POST with ROGER
            String postResponse = "RODGER JSON RECIEVED";

            System.out.println("response: " + sharedResponse);
            
            Gson g = new Gson();
            ExportedRun fromJson = g.fromJson(sb.toString(), (new TypeToken<ExportedRun>(){}).getType());
            expRun.add(fromJson);
           
            // create our response String to use in other handler
            sharedResponse += "<br/><hr/><br/>" + sb.toString();
            
            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());
            transmission.sendResponseHeaders(300, sharedResponse.length());
            outputStream.write(sharedResponse.getBytes());
            outputStream.close();
        }
    }

}
