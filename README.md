### Propósito :

O propósito desse artigo é criar uma aplicação Android cujo conceito de "offline-first" esteja fortemente atrelado ao funcionamento do app.
Para tal usaremos [Realm](https://realm.io/docs/java/latest/) como um banco local, que vem se mostrado uma das melhores escolhas para o desenvolvimento de aplicativos Android, e para a sincronização de dados usaremos a lib [Workmanager](https://developer.android.com/topic/libraries/architecture/workmanager/) junto com a [JobScheduler](https://github.com/evernote/android-job)

### Tema:
O tema do aplicativo será um simples favoritador de músicas. Com uma tela para que o usuário pesquise músicas pelo Spotify, e trazendo o resultado, as músicas poderão ser favoritadas para serem visualizadas sem precisar de internet.

### Flow:
Em nome da simplicidade, o aplicativo não terá nenhum tipo de login, apenas será criado um identificador para cada usuário para relacionar suas músicas salvas.
Uma simples requisição ao GraphQL é feita para buscar uma lista de artistas e albuns baseados em um input do usuário.
A partir dai, aqueles artistas e seus albuns serão salvos localmente, podendo ser acessados a qualquer momento sem precisar de conexão


