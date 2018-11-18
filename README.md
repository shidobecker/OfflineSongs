### Intro:
Offline apps works even when your net connection is lost and you can resume the app as if nothing ever happened. And there's huge benefits to this approach:
 - Build an app that works offline allows your users to get an flexible experience and still keeps their data secure. 
 - Your users will be able to use your app without any particular condition, except having it installed.
 - Users from areas with poor or no internet connection at all (like rural areas or during a travel) can still use your app and enjoy it as much as a normal connection area user
 - Users will see this as competitive advantage between you and your competitors
 - Quicker loading times

### Purpose :
This article's purpose is to create an Android "offline-first" application that is heavily tied to it's behavior
To achieve this result, we are going to need a help of some libraries, each one has a role in our flow
 - [Realm](https://realm.io/docs/java/latest/) as one of the best mobile database as of now, specially if you are already a iOS developer, this might give you a good grasp of classes and repositories' flow we are going to use
 - [Workmanager](https://developer.android.com/topic/libraries/architecture/workmanager/) together with [JobScheduler](https://github.com/evernote/android-job) to synchronize all our data with a backend server (which won't be covered in this article)


### App Theme:
Our app theme will be a simple music bookmark. User can type and save it's favorite artists and songs, all in a offline context and will be sent to the backend server by scheduling a task to do it so.

### Flow:
Em nome da simplicidade, o aplicativo não terá nenhum tipo de login, apenas será criado um identificador para cada usuário para relacionar suas músicas salvas.
Uma simples requisição ao GraphQL é feita para buscar uma lista de artistas e albuns baseados em um input do usuário.
A partir dai, aqueles artistas e seus albuns serão salvos localmente, podendo ser acessados a qualquer momento sem precisar de conexão


