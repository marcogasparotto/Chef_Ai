import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ChefAI - Sugestor de Receitas Inteligente
 * Classe principal para execução.
 */
public class ChefAI {

    private List<Ingrediente> ingredientesDisponiveis = new ArrayList<>(); 
    private SugestorSimples sugestor = new SugestorSimples(); 
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🤖 Bem-vindo ao ChefAI - Sugestor de Receitas Inteligente (Modo Simples)!");
        System.out.println("-----------------------------------------------------------------------");

        try {
            APIConfig.getApiKey();
        } catch (IllegalStateException e) {
            System.err.println("\n❌ ERRO FATAL: " + e.getMessage());
            return;
        }
        
        ChefAI app = new ChefAI();
        app.executar();
    }

    private void executar() {
        coletarIngredientes();
        
        String restricaoAlimentar = "";
        System.out.print("\nRestrições alimentares (ex: vegetariana; vazio = nenhuma): ");
        restricaoAlimentar = scanner.nextLine().trim();

        String tipoRefeicao = "";
        System.out.print("Tipo de refeição (ex: café da manhã; vazio = qualquer): ");
        tipoRefeicao = scanner.nextLine().trim();


        try {
            String receitaBruta = sugestor.sugerir(ingredientesDisponiveis, restricaoAlimentar, tipoRefeicao);
            
            System.out.println("\n\n✅ Sugestão Recebida (Texto Bruto do LLM):");
            System.out.println("---------------------------------------------------------");
            System.out.println(receitaBruta); 
            System.out.println("---------------------------------------------------------");
            
        } catch (Exception e) {
            System.err.println("\n\n❌ Ocorreu um erro durante a operação do ChefAI:");
            System.err.println(e.getMessage());
            System.err.println("\nVerifique se sua chave Groq é válida e se a conexão de internet está ativa.");
        } finally {
            scanner.close();
        }
    }

    private void coletarIngredientes() {
        System.out.println("\n--- 📝 Cadastro de Ingredientes Disponíveis ---");
        System.out.println("Digite o nome dos seus ingredientes (ex: ovo, leite). Digite 'FIM' para parar.");
        
        while (true) {
            System.out.print("Ingrediente (nome, ou 'FIM'): ");
            String nome = scanner.nextLine().trim();
            
            if (nome.equalsIgnoreCase("FIM")) {
                break;
            }
            
            if (!nome.isEmpty()) {
                System.out.print("Quantidade (ex: 2 unidades, 500g, 1 copo): ");
                String quantidade = scanner.nextLine().trim();
                
                Ingrediente novoIngrediente = new Ingrediente(nome, quantidade); 
                ingredientesDisponiveis.add(novoIngrediente);
            }
        }
    }
}