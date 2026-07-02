package mochila;

import java.io.Serializable;

public class Editora implements Serializable{
  
  private int id;
  private String nome;
  private String cnpj;

  public Editora(int id, String nome, String cnpj){
    this.id = id;
    this.nome = nome;
    this.cnpj = cnpj;
  }

  public int getId(){
    return id;
  }

  public String getNome(){
    return nome;
  }

  public void  setNome(String nome){
    this.nome = nome;
  }

  public String getCnpj(){
    return cnpj;
  }

  public void setCnpj(String cnpj){
    this.cnpj = cnpj;
  }
}
