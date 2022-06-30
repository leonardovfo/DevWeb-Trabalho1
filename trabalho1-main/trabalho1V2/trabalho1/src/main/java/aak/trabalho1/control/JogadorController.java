package aak.trabalho1.control;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import aak.trabalho1.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import aak.trabalho1.repository.JogadorRepository;
//import org.springframework.*;

@RestController
@RequestMapping("/api/")
public class JogadorController {

    @Autowired
    JogadorRepository jrep;

    /*Listar jogadores*/
    @GetMapping("/jogador")
    public ResponseEntity<List<Jogador>> getAllJogador(@RequestParam(required = false)String nome){
        
        try {

            List<Jogador> ljog = new ArrayList<Jogador>();

            if(nome==null){
                jrep.findAll().forEach(ljog::add);

            }else{
                jrep.findByNomeContaining(nome).forEach(ljog::add);
            }
            if(ljog.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(ljog,HttpStatus.OK);
            }
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /*criar jogador*/
    @PostMapping("/jogador")
    public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jog){

        try {
            Jogador _j=jrep.save(new Jogador(jog.getCjogador(),jog.getNome(),jog.getEmail(),jog.getDatanasc()));
            return new ResponseEntity<>(_j,HttpStatus.CREATED);
        } catch (Exception e) {
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
	//Long id -> int id
    @GetMapping("/jogador/{cjogador}")
    public ResponseEntity<Jogador> getJogadorById(@PathVariable("cjogador") int id){

        Optional<Jogador> data = jrep.findById(id);

        if(data.isPresent()){
            return new ResponseEntity<>(data.get(), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
	
	// long id -> int id
    @PutMapping("/jogador/{id}")
    public ResponseEntity<Jogador> updateJogador(@PathVariable("cjogador") int id, @RequestBody Jogador j)
    {
        Optional<Jogador> data = jrep.findById(id);

        if (data.isPresent())
        {
            Jogador jo = data.get();
            jo.setCjogador(j.getCjogador());
            jo.setEmail(j.getEmail());
            jo.setNome(j.getNome());

            return new ResponseEntity<>(jrep.save(jo), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

     /*
    * remover jogador dado um id*/
		 	// long id -> int id
    @DeleteMapping("/jogador/{cjogador}")
    public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("cjogador") int id)
    {
        try {
            jrep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

     /*
     remover todos os jogadores
    */
    @DeleteMapping("/jogador")
    public ResponseEntity<HttpStatus> deleteAllJogador()
    {
        try {
            jrep.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    
}
