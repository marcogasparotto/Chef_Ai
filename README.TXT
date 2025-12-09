⚙️ Guia de Inicialização Rápida do Projeto ChefAI
Este guia fornece as instruções essenciais para clonar o repositório, configurar a chave da API Groq e executar o projeto Java no ambiente BlueJ.

1. 🖨️ Clonagem do Repositório
Abra o seu terminal (ou Git Bash) e execute o comando de clonagem, substituindo o link se necessário:

Bash
git clone https://github.com/marcogasparotto/Chef_Ai.git
cd Chef_Ai

2. 🔑 Configuração da Chave da API Groq
O projeto utiliza a API da Groq para processamento de linguagem natural. Você precisa obter sua chave de acesso.

Obter a Chave:

Acesse o painel da Groq Cloud: https://console.groq.com/keys.
Crie uma nova chave de API e copie o valor gerado.
Configurar no Código:
Abra a pasta clonada no BlueJ.
Abra a classe APIConfig.java.
Substitua o placeholder SUA_CHAVE_GROQ_AQUI pela chave que você obteve:

Java
// Exemplo:
private static final String GROQ_API_KEY = "gsk_xxxxxxxxx";

3. 🖥️ Execução no BlueJ
O projeto está pronto para rodar com as seguintes classes: ChefAI, APIConfig, Ingrediente, SugestorBase e SugestorSimples.
Compilar: No BlueJ, clique em "Compile" para compilar todas as classes.
Executar: Clique com o botão direito na classe ChefAI.
Selecione o método main(String[] args).
Interação: O programa será iniciado no console, solicitando os dados de entrada (ingredientes, restrições) e exibirá o resultado da API da Groq.
