

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
  
}
