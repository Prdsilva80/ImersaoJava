import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String IMDB_API_KEY = System.getenv("IMDB_API_KEY");
        final String url = "https://imdb-api.com/en/API/Top250Movies/" + IMDB_API_KEY;
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (int i = 0; i < 5; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mURL da imagem:\u001b[m " + filme.get("image"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            for (int n = 1; n <= numeroEstrelinhas; n++) {
                System.out.print("⭐");
            }
            System.out.println("\n");
        }
    }
}