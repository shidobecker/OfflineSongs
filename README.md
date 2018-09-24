### Propósito :

O propósito desse artigo é criar uma aplicação Android cujo conceito de "offline-first" esteja fortemente atrelado ao funcionamento do app.
Para tal usaremos Realm como um banco local, que vem se mostrado uma das melhores escolhas para o desenvolvimento de aplicativos Android, e para a sincronização de dados usaremos a lib Workmanager junto com a JobScheduler

### Tema:
O tema do aplicativo será um simples favoritador de músicas. Com uma tela para que o usuário pesquise músicas pelo Spotify, e trazendo o resultado, as músicas poderão ser favoritadas para serem visualizadas sem precisar de internet.

### Flow:
Em nome da simplicidade, uma simples requisição ao GraphQL é feita para buscar uma lista de artistas e albuns baseados em um input do usuário.
A partir dai, aqueles artistas e seus albuns serão salvos localmente, podendo ser acessados a qualquer momento sem precisar de conexao
op
