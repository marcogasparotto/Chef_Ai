import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Classe Abstrata base para todos os sugestores de receitas (Pilar: Abstração e Herança).
 * Contém a lógica de comunicação HTTP comum a todas as implementações.
 */
public abstract class SugestorBase {

    protected static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public abstract String sugerir(
        List<Ingrediente> ingredientes, 
        String restricaoAlimentar, 
        String tipoRefeicao
    ) throws Exception;
 
    protected String enviarRequisicao(String jsonInputString) throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + APIConfig.getApiKey()); 
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);           
        }

        int responseCode = conn.getResponseCode();
        
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String errorMsg = "Erro na API. Código de Resposta: " + responseCode;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                errorMsg += "\nDetalhes do Erro: " + response.toString();
            }
            throw new Exception(errorMsg); 
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    protected String extrairConteudo(String respostaJson) throws Exception {
        
        int inicioContent = respostaJson.indexOf("\"content\":");
        if (inicioContent == -1) {
            throw new Exception("Falha ao encontrar o campo 'content' na resposta da API. Resposta inválida.");
        }
       
        String subStringAposContent = respostaJson.substring(inicioContent + 11);

        int fimAspa = -1;
        int i = 0;
        int aspasEncontradas = 0;
     
        while (i < subStringAposContent.length()) {
            if (subStringAposContent.charAt(i) == '"' && (i == 0 || subStringAposContent.charAt(i - 1) != '\\')) {
                aspasEncontradas++;
            }
            if (aspasEncontradas == 2) {
                fimAspa = i;
                break;
            }
            i++;
        }
        
        if (fimAspa == -1) {
            throw new Exception("Falha ao localizar o fechamento do campo 'content'. O LLM pode ter quebrado o JSON.");
        }
        
        String content = subStringAposContent.substring(1, fimAspa);
        
        content = content.replace("\\n", "\n").replace("\\\"", "\"").replace("\\t", "    ");
        
        return content;
    }
}