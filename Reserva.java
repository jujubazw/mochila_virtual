package mochila;
 
import java.time.LocalDate;
import java.io.Serializable;
 
public class Reserva implements Serializable {
 
    private static int proximoId = 1;
 
    private int id;
    private Cliente cliente;
    private Livro livro;
    private LocalDate dataReserva;
    private StatusReserva status;
    private int posicaoFila;
 
    public Reserva(Cliente cliente, Livro livro) {
        this.id = proximoId++;
        this.cliente = cliente;
        this.livro = livro;
        this.dataReserva = LocalDate.now();
        this.status = StatusReserva.PENDENTE;
        this.posicaoFila = livro.getReservasAtivas() + 1;
    }
 
    public void cancelarReserva() {
        this.status = StatusReserva.CANCELADO;
    }
 
    public void notificarCliente() {
        this.status = StatusReserva.NOTIFICADO;
    }

    public int getId() {
        return id;
    }
 
    public Cliente getCliente() {
        return cliente;
    }
 
    public Livro getLivro() {
        return livro;
    }
 
    public LocalDate getDataReserva() {
        return dataReserva;
    }
 
    public StatusReserva getStatus() {
        return status;
    }
 
    public int getPosicaoFila() {
        return posicaoFila;
    }
 
    public static void ajustarProximoId(int maiorIdExistente) {
        if (maiorIdExistente >= proximoId) {
            proximoId = maiorIdExistente + 1;
        }
    }
}
