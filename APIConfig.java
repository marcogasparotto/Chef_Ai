/**
 * Classe para centralizar a configuração da API.
 * ATENÇÃO: É aqui que você deve inserir sua chave Groq.
 */
public class APIConfig {
    
    // =========================================================================
    // 🛑 ATENÇÃO: COLOQUE SUA CHAVE DA GROQ AQUI DENTRO DAS ASPAS
    // =========================================================================
    private static final String GROQ_API_KEY = "coloque sua chave aqui!!";

    public static String getApiKey() {
        if (GROQ_API_KEY.equals("SUA_CHAVE_GROQ_AQUI") || GROQ_API_KEY.isEmpty()) {
            throw new IllegalStateException("A chave da API Groq não foi configurada. Por favor, edite APIConfig.java e insira sua chave.");
        }
        return GROQ_API_KEY;
    }

    public static String getModelName() {
        return "llama-3.3-70b-versatile";
    }
}