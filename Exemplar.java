





package mochila;

import java.time.LocalDate;
import java.io.Serializable;

public class Exemplar implements Serializable{
  
  private String codigoExemplar;
  private StatusExemplar status;
  private LocalDate dataAquisicao;
  
  public Exemplar(String codigoExemplar, LocalDate dataAquisicao){
    this.codigoExemplar = codigoExemplar;
    this.dataAquisicao = dataAquisicao;
    this.status = StatusExemplar.DISPONIVEL;
  }

  public boolean verificarDisponibilidade(){
    return status == StatusExemplar.DISPONIVEL;
  }

  public void atualizarStatus(StatusExemplar status){
    this.status = status;
  }

  public String getCodigoExemplar(){
    return codigoExemplar;
  }

  public void setCodigoExemplar(String codigoExemplar){
    this.codigoExemplar = codigoExemplar;
  }
  
  

}
