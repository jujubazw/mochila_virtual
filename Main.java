package mochila;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Main {
 
    private static final String ARQUIVO_DADOS = "dados.ser";
 
    private static Scanner sc = new Scanner(System.in);
    private static List<Cliente> clientes;
    private static List<Bibliotecario> bibliotecarios;
    private static List<Livro> livros;
    private static List<Autor> autores;
    private static List<Editora> editoras;
    private static List<Categoria> categorias;
    private static List<Emprestimo> emprestimos;
 
    public static void main(String[] args) {
        carregarDados();

    while (true) {
            System.out.println("\n=== Mochila Virtual ===");
            System.out.println("1 - Login Cliente");
            System.out.println("2 - Login Bibliotecário");
            System.out.println("3 - Cadastrar Cliente");
            System.out.println("4 - Cadastrar Bibliotecário");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
 
            switch (lerOpcao()) {
                case 1 -> loginCliente();
                case 2 -> loginBibliotecario();
                case 3 -> cadastrarCliente();
                case 4 -> cadastrarBibliotecario();
                case 0 -> {
                    salvarDados();
                    System.out.println("Até mais.");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }



   private static void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
 
        if (!arquivo.exists()) {
            criarDadosIniciais();
            return;
        }
 
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            DadosStore dados = (DadosStore) in.readObject();
            clientes = dados.clientes;
            bibliotecarios = dados.bibliotecarios;
            livros = dados.livros;
            autores = dados.autores;
            editoras = dados.editoras;
            categorias = dados.categorias;
            emprestimos = dados.emprestimos;
            ajustarContadoresDeId();
            System.out.println("Dados carregados de " + ARQUIVO_DADOS + ".");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Não foi possível ler os dados salvos, iniciando do zero.");
            criarDadosIniciais();
        }
    }

   private static void salvarDados() {
        DadosStore dados = new DadosStore();
        dados.clientes = clientes;
        dados.bibliotecarios = bibliotecarios;
        dados.livros = livros;
        dados.autores = autores;
        dados.editoras = editoras;
        dados.categorias = categorias;
        dados.emprestimos = emprestimos;
 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            out.writeObject(dados);
        } catch (IOException e) {
            System.out.println("Não foi possível salvar os dados: " + e.getMessage());
        }
    }
 
    private static void ajustarContadoresDeId() {
        emprestimos.stream().mapToInt(Emprestimo::getId).max()
                .ifPresent(Emprestimo::ajustarProximoId);
 
        clientes.stream()
                .flatMap(c -> c.getReservas().stream())
                .mapToInt(Reserva::getId).max()
                .ifPresent(Reserva::ajustarProximoId);
    }

    private static void criarDadosIniciais() {
        clientes = new ArrayList<>();
        bibliotecarios = new ArrayList<>();
        livros = new ArrayList<>();
        autores = new ArrayList<>();
        editoras = new ArrayList<>();
        categorias = new ArrayList<>();
        emprestimos = new ArrayList<>();
 
        Bibliotecario admin = new Bibliotecario("Ana Ribeiro", "000.000.000-00", "ana@biblioteca.com", "9999-0000", "B001", "Atendente");
        admin.alterarSenha("1234");
        bibliotecarios.add(admin);
 
        Cliente clienteTeste = new Cliente("Carlos Souza", "111.111.111-11", "carlos@email.com", "8888-1111", "C001", LocalDate.now());
        clienteTeste.alterarSenha("1234");
        clientes.add(clienteTeste);
 
        Editora companhia = new Editora(1, "Companhia das Letras", "12345678000100");
        Editora atica = new Editora(2, "Ática", "22345678000100");
        Editora prentice = new Editora(3, "Prentice Hall", "32345678000100");
        editoras.add(companhia);
        editoras.add(atica);
        editoras.add(prentice);
 
        Autor machado = new Autor(1, "Machado de Assis", "Brasileira");
        Autor aluisio = new Autor(2, "Aluísio Azevedo", "Brasileira");
        Autor martin = new Autor(3, "Robert C. Martin", "Americana");
        autores.add(machado);
        autores.add(aluisio);
        autores.add(martin);
 
        Categoria romance = new Categoria(1, "Romance", "Ficção clássica brasileira");
        Categoria tecnologia = new Categoria(2, "Tecnologia", "Livros sobre programação e software");
        categorias.add(romance);
        categorias.add(tecnologia);
 
        Livro domCasmurro = new Livro("978-85-359-0277-5", "Dom Casmurro", 1899, 1, companhia);
        domCasmurro.adicionarAutor(machado);
        domCasmurro.adicionarCategoria(romance);
        domCasmurro.adicionarExemplar(new Exemplar("978-85-359-0277-5-1", LocalDate.now()));
        domCasmurro.adicionarExemplar(new Exemplar("978-85-359-0277-5-2", LocalDate.now()));
 
        Livro oCortico = new Livro("978-85-260-0355-1", "O Cortiço", 1890, 1, atica);
        oCortico.adicionarAutor(aluisio);
        oCortico.adicionarCategoria(romance);
        oCortico.adicionarExemplar(new Exemplar("978-85-260-0355-1-1", LocalDate.now()));
 
        Livro cleanCode = new Livro("978-0-13-235088-4", "Clean Code", 2008, 1, prentice);
        cleanCode.adicionarAutor(martin);
        cleanCode.adicionarCategoria(tecnologia);
        cleanCode.adicionarExemplar(new Exemplar("978-0-13-235088-4-1", LocalDate.now()));
        cleanCode.adicionarExemplar(new Exemplar("978-0-13-235088-4-2", LocalDate.now()));
        cleanCode.adicionarExemplar(new Exemplar("978-0-13-235088-4-3", LocalDate.now()));
 
        livros.add(domCasmurro);
        livros.add(oCortico);
        livros.add(cleanCode);
 
        admin.adicionarAoAcervo(domCasmurro);
        admin.adicionarAoAcervo(oCortico);
        admin.adicionarAoAcervo(cleanCode);
 
        System.out.println("Nenhum dado salvo encontrado. Criado ambiente de exemplo:");
        System.out.println("Bibliotecário -> matrícula B001, senha 1234");
        System.out.println("Cliente -> matrícula C001, senha 1234");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
 
    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        Cliente cliente = new Cliente(nome, cpf, email, telefone, matricula, LocalDate.now());
        cliente.alterarSenha(senha);
        clientes.add(cliente);
        salvarDados();
        System.out.println("Cliente cadastrado com sucesso.");
    }private static void cadastrarBibliotecario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Cargo: ");
        String cargo = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        Bibliotecario bibliotecario = new Bibliotecario(nome, cpf, email, telefone, matricula, cargo);
        bibliotecario.alterarSenha(senha);
        bibliotecarios.add(bibliotecario);
        salvarDados();
        System.out.println("Bibliotecário cadastrado com sucesso.");
    }
 
    private static void loginCliente() {
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        Cliente cliente = clientes.stream()
                .filter(c -> c.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
 
        if (cliente == null || !cliente.autenticar(senha)) {
            System.out.println("Matrícula ou senha inválidos.");
            return;
        }

       menuCliente(cliente);
    }
 
    private static void loginBibliotecario() {
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
 
        Bibliotecario bibliotecario = bibliotecarios.stream()
                .filter(b -> b.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
 
        if (bibliotecario == null || !bibliotecario.autenticar(senha)) {
            System.out.println("Matrícula ou senha inválidos.");
            return;
        }
 
        menuBibliotecario(bibliotecario);
    }
 
    private static void menuCliente(Cliente cliente) {
        while (true) {
            System.out.println("\n--- Olá, " + cliente.getNome() + " ---");
            System.out.println("1 - Ver livros disponíveis");
            System.out.println("2 - Realizar empréstimo");
            System.out.println("3 - Fazer reserva");
            System.out.println("4 - Meus empréstimos");
            System.out.println("5 - Minhas reservas");
            System.out.println("0 - Logout");
            System.out.print("Opção: ");
 
            switch (lerOpcao()) {
                case 1 -> listarLivrosDisponiveis();
                case 2 -> realizarEmprestimo(cliente);
                case 3 -> fazerReserva(cliente);
                case 4 -> listarEmprestimosCliente(cliente);
                case 5 -> listarReservasCliente(cliente);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
    private static void menuBibliotecario(Bibliotecario bibliiotecario){
     while(true){
      System.out.println("\n--- Olá, " + bibliotecario.getNome() + " (" + bibliotecario.getCargo() + ") ---");
      System.out.println("1 - Cadastrar livro");
      System.out.println("2 - Resgistrar empréstimo");
      System.out.println("3 - Rgistrar devolução");
      System.out.println("4 - Ver acervo");
      System.out.println("5 - Gerenciar acervo");
      System.out.println("6 - Gerar relatório");
      System.out.println("0 - Logout");
      System.out.print("Opção: ");

      switch(lerOpcao()){
       case 1 -> cadastrarLivro(bibliotecario);
       case 2 -> registrarEmprestimo(bibliotecario);
       case 3 -> registrarDevolucao();
       case 4 -> listarLivrosDisponiveis();
       case 5 -> {
        bibliotecario.gerenciarAcervo();
        salvarDados();
        System.out.println("Acervo atualizado.");
       }
       case 6 -> System.out.println(bibliotecario.gerarRelatorio());
       case 0 -> {
        return;
       }
        default -> System.out.println("Opção inválida.");
       }
     }
    }

 private static void cadastrarLivro(Bibliotecario bibliotecario){
  System.out.print("ISBN: ");
  String isbn = sc.nextLine();
  System.out.print("Título: ");
  String titulo = sc.nextLine();
  System.out.print("Ano de publicação: ");
  int ano = lerInt();
  System.out.print("Edição: ");
  int edicao = lerInt();

  System.out.print("Nome da editora: ");
  String nomeEditora = sc.nextLine();
  Editora editora = buscarOuCriarEditora(nomeEditora);

  Livro livro = new Livro(isbn, titulo, ano, edicao, editora);

  System.out.print("Nome do autor: ");
  String nomeAutor = sc.nextLine();
  livro.adicionarAutor(buscarOuCriarAutor(nomeAutor));

  System.out.print("Categoria: ");
  String nomeCategoria = sc.nextLine();
  livro.adicionarCategoria(buscarOuCriarCategoria(nomeCategoria));

  System.out.print("Quantos exemplares deseja cadastrar? ");
  int qtd = lerInt();
  for(int i = 1; i <= qtd; i++){
   String codigo = isbn + "-" +i;
   livro.adicionarExemplar(new Exemplar(codigo, LocalDate.now()));
  }

  livros.add(livro);
  bibliotecario.adicionarAoAcervo(livro);
  salvarDados();
  System.out.println("Livro cadastrado com + " + qtd + " exemplar(es).");
 }

 private static Editora buscarOuCriarEditora(String nome){
  return editoras.stream()
   .filter(e -> e.getNome().equalsIgnorecase(nome))
   .findFirst()
   .orElseGet(() -> {
    Editora nova = new Editora(editoras.size() + 1, nome, "");
    editoras.add(nova);
    return nova;
   });
 }

 private static Autor buscarOuCriarAutor(String nome){
  return autores.stream()
   .filter(a -> a.getNome().equalsIgnorecase(nome))
   .findFirst()
   .orElseGet(() -> {
    Autor novo = new Autor(autores.size() + 1, nome, "");
    autores.add(novo);
    return novo;
   });
 }

 private static Categoria buscarOuCriarCategoria(String nome){
  return categorias.stream()
   .filter(c -> c.getNome().equalsIgnorecase(nome))
   .findFirst()
   .orElseGet(() -> {
    Categoria nova = new Categoria(Categorias.size() + 1, nome, "");
    editoras.add(nova);
    return nova;
   });
 }

 private static void listarLivrosDisponiveis(){
  if(livros.isEmpty()){
   System.out.println("Nenhum livro cadastrado ainda.");
   return;
  }

  for(Livro livro : livros){
   long disponiveis = livro.listarExemplares().stream()
    .filter(Exemplar::verificarDisponibilidade)
    .count();
   System.out.print(livro.getIsbn() + " - " + livro.getTitulo() + " (" +disponiveis + "diponíveis)");
  }
 }

private static void realizarEmprestimo(Cliente cliente){
 System.out.print("ISBN do livro: ");
 String isbn = sc.nextLine();

 Livro livro : livros.stream()
  .filter(1 -> 1.getIsbn().equals(isbn))
  .findFirst()
  .orElse(null);

 if(livro == null){
  System.out.println("Livro não encontrado.");
  return;
 }

 Exemplar exemplar = livro.listarExemplares().stream()
  .filter(Exemplar::verificarDisponibilidade)
  .findFirst()
  .orElse(null);

 if (exemplar == null){
  System.out.println("Não há exemplares disponíveis para este livro.");
  return;
 }
  

 
  
  









 
