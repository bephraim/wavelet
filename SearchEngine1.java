import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> bank = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String s = "";
            for (String ss : bank) {
                s = ss + "\n";
            }
            return "Strings are: \n" + s;

        } else if (url.getPath().equals("/add")) {
            String[] param = url.getQuery().split("=");
            if (param[0].equals("s")) {
                bank.add(param[1]);
            }
            return String.format(param[1] + " added!");
        } else if (url.getPath().contains("/search")) {
            //System.out.println("Path: " + url.getPath());
            String se = "";
           
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {

                    for (int i = 0; i < bank.size(); i++) {
                        if ((bank.get(i)).indexOf(parameters[1]) != -1) {
                            se += bank.get(i) + " ";
                        }
                    }
                }
                return se;
        }
        else {
            return "404 Not Found!";
        }
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
