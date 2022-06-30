package aak.trabalho1.repository;
//import org.springframework.*;
import org.springframework.data.jpa.repository.JpaRepository;
import aak.trabalho1.model.Jogador;
import java.util.List;

//como foi deifinido tipo int como id para jogador, o JpaRepository deve ser Integer e nao Long
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

	//cjogador maiusculo
    List<Jogador> findByCjogador(Integer cjogador);

    List<Jogador> findByNomeContaining(String nome);

    
}
