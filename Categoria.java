package mochila;

import java.io.Serializable;

public class Categoria implements Serializable{
  
  private int id;
  private String nome;
  private String descricao;

  public Categoria(int id, String nome, String descricao){
    this.id = id;
    this.nome = nome;
    this.descricao = descricao;
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

  public String getDescricao(){
    resturn descricao;
  }

  public void setDescricao(String descricao){
    this.descricao = descricao;
  }
}

