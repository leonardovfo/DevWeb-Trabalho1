package aak.trabalho1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import dw.mensalistas.model.Jogador;
import aak.trabalho1.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    
    List<Pagamento> findByCpagamento(Integer cpagamento);
    List<Pagamento> findByCjogador(Integer cjogador);
    //List<Jogador>
    
}
