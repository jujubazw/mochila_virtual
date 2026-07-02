package mochila;
 
import java.time.LocalDate;
import java.io.Serializable;
 
public class Devolucao implements Serializable {
 
    private static int proximoId = 1;
 
    private int id;
    private Emprestimo emprestimo;
    private LocalDate dataDevolucao;
 
    public Devolucao(Emprestimo emprestimo) {
        this.id = proximoId++;
        this.emprestimo = emprestimo;
        this.dataDevolucao = LocalDate.now();
    }
 
    public void registrarDevolucao() {
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.getExemplar().atualizarStatus(StatusExemplar.DISPONIVEL);
    }

    public int getId() {
        return id;
    }
 
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
 
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
 
    public static void ajustarProximoId(int maiorIdExistente) {
        if (maiorIdExistente >= proximoId) {
            proximoId = maiorIdExistente + 1;
        }
    }
}
