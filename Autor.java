package mochila;

import java.io.Serializable;

public class Autor implements Serializable{
  private int id;
  private String nome;
  private String nacionalidade;

  public Autor(int id, String nome, String nacionalidade){
    this.id = id;
    this.nome = nome;
    this.nacionalidade = nacionalidade;
  }
  
}
