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


//import org.springframework.*;
import aak.trabalho1.repository.PagamentoRepository;

@RestController
@RequestMapping("/api/")
public class PagamentoController {

    @Autowired
    PagamentoRepository prep;

    /*Listar pagamentos*/
    @GetMapping("/pagamento")
    public ResponseEntity<List<Pagamento>> getAllPagamento(@RequestParam(required = false)Integer cpagamento){
        
        try {

            List<Pagamento> pj = new ArrayList<Pagamento>();

            if(cpagamento==null){
                prep.findAll().forEach(pj::add);

            }else{
                prep.findByCpagamento(cpagamento).forEach(pj::add);
            }
            if(pj.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(pj,HttpStatus.OK);
            }
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /*criar pagamento*/
    @PostMapping("/pagamento")
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pag){

        try {
            Pagamento _p=prep.save(new Pagamento(pag.getCpagamento(), pag.getAno(),pag.getMes(),pag.getValor(), pag.getCjogador()));
            return new ResponseEntity<>(_p,HttpStatus.CREATED);
        } catch (Exception e) {
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
	//Long id -> int id
    @GetMapping("/pagamento/{cpagamento}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable("cpagamento") int id){

        Optional<Pagamento> data = prep.findById(id);

        if(data.isPresent()){
            return new ResponseEntity<>(data.get(), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
	
	// long id -> int id
    @PutMapping("/pagamento/{id}")
    public ResponseEntity<Pagamento> updatePagamento(@PathVariable("cpagamento") int id, @RequestBody Pagamento p)
    {
        Optional<Pagamento> data = prep.findById(id);

        if (data.isPresent())
        {
            Pagamento pa = data.get();
            pa.setCpagamento(p.getCpagamento());
            pa.setAno(p.getAno());
            pa.setMes(p.getMes());
            pa.setValor(p.getValor());
            pa.setCjogador(p.getCjogador());
            /*jo.setCjogador(j.getCjogador());
            jo.setEmail(j.getEmail());
            jo.setNome(j.getNome());*/

            return new ResponseEntity<>(prep.save(pa), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

     /*
    * remover pagamento dado um id*/
		 	// long id -> int id
    @DeleteMapping("/pagamento/{cpagamento}")
    public ResponseEntity<HttpStatus> deletePagamento(@PathVariable("cpagamento") int id)
    {
        try {
            prep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

     /*
     remover todos os pagamentos
    */
    @DeleteMapping("/pagamento")
    public ResponseEntity<HttpStatus> deleteAllPagamento()
    {
        try {
            prep.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    
}
