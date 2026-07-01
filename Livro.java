package mochila;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Livro implements Serializable{

  private String isbn;
  private String titulo;
  private int anoPublicacao;
  private int edicao;
  private Editora editora;
  
  private List<Exemplar> exemplares = new ArrayList<>();
  private List<Autor> autores = new ArrayList<>();
  private List<Categoria> categorias = new ArrayList<>();
  private List<Reserva> reservas = new ArrayList<>();

  public Livro(String isbn, String titulo, int anoPublicacao, int edicao, Editora editora){
    this.isbn = isbn;
    this.titulo = titulo;
    this.anoPublicacao = anoPublicacao;
    this.edicao = edicao;
    this.editora = editora;
  }

public boolean buscarDisponibilidade(){
  return exemplares.stream().anyMatch(Exemplar::verificarDisponibilidade);
}
