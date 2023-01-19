import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> text = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Kishen's Strings: %s", text.toString());
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    text.add(parameters[1]);
                    }
                    
                return String.format("String %s added to list!", parameters[1]);
                }
            } if (url.getPath().contains("/search")) {
                String[] stringsToReturn = new String[100];
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    for (int i=0; i<text.size(); i++) {
                        if (text.get(i).contains(parameters[1])) {
                            stringsToReturn[i] = text.get(i);
                        }
                    }
                }
                return String.format("Strings %s matched!", stringsToReturn.toString());
            }
            return "404 Not Found!";
        }
    }


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
