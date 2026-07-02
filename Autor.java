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

  public int getId(){
    return id;
  }

  public String getNome(){
    return nome;
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public String getNacionalidade(){
    return nacionalidade;
  }

  public void setNacionalidade(String nacionalidade){
    this.nacionalidade = nacionalidade;
  }
}
