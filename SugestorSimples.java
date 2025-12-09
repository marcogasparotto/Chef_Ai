import java.util.List;

/**
 * Classe concreta que herda de SugestorBase (Pilar: Herança e Polimorfismo).
 * Implementa a lógica específica de construção do prompt.
 */
public class SugestorSimples extends SugestorBase { 

    @Override 
    public String sugerir(
        List<Ingrediente> ingredientes, 
        String restricaoAlimentar, 
        String tipoRefeicao
    ) throws Exception {
        
        System.out.println("\nBuscando sugestões de 3 receitas...");

       
        StringBuilder listaIngredientes = new StringBuilder();
        for (Ingrediente i : ingredientes) {
            listaIngredientes.append("- ").append(i.toString()).append("\n");
        }

        String restricoes = restricaoAlimentar.isEmpty() ? "sem restrições" : restricaoAlimentar;
        String tipo = tipoRefeicao.isEmpty() ? "qualquer refeição" : tipoRefeicao;
        
        String promptUsuario = 
            "Com base nos seguintes ingredientes que tenho disponíveis, sugerir **EXATAMENTE TRÊS receitas rápidas** (máximo 30 minutos cada). " +
            "As receitas devem ser do tipo '" + tipo + "' e atender às restrições '" + restricoes + "'. " +
            "Obrigatório: tente usar pelo menos a maioria dos ingredientes fornecidos. Para cada receita, siga rigorosamente esta ordem de formatação e use Markdown para destacar as seções:" +
            "\n\n**1. Nome da Receita**" +
            "\n**2. Tempo de Preparo**" +
            "\n**3. Lista de Ingredientes Necessários** (listando o que o usuário tem e o que falta)" +
            "\n**4. Modo de Preparo** (passo a passo detalhado)" +
            "\n\nIngredientes disponíveis:\n" + listaIngredientes.toString();

       
        String jsonInputString = montarJsonRequisicao(promptUsuario);
        
       
        String respostaJson = enviarRequisicao(jsonInputString);
        
     
        return extrairConteudo(respostaJson);
    }
    

    private String montarJsonRequisicao(String promptUsuario) {
        String json = String.format(
            "{\n" +
            "  \"model\": \"%s\",\n" +
            "  \"messages\": [\n" +
            "    {\"role\": \"user\", \"content\": \"%s\"}\n" +
            "  ],\n" +
            "  \"temperature\": 0.7\n" +
            "}",
            APIConfig.getModelName(), 
            promptUsuario.replace("\"", "\\\"").replace("\n", "\\n")
        );
        return json;
    }
}