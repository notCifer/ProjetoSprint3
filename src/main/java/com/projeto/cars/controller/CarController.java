package com.projeto.cars.controller;

import java.util.List;
import javax.validation.Valid;
import com.projeto.cars.model.Cars;
import com.projeto.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository CR;

    // BUSCAR TODOS OS CARROS

    @GetMapping
    public List<Cars> ListarTodos() {
        return CR.findAll();
    }

    // FILTRAR POR NOME, COR E MARCA

    @GetMapping(params = "nome")
    public ResponseEntity<List<Cars>> EncontrarPorNome(@RequestParam String nome) {
        if (!CR.findBynome(nome).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBynome(nome));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }  
    }

    @GetMapping(params = "cor")
    public ResponseEntity<List<Cars>> encontrarPorCor(@RequestParam String cor) {
        if (!CR.findBycor(cor).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBycor(cor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }  
    }

    @GetMapping(params = "marca")
    public ResponseEntity<List<Cars>> encontrarPorMarca(@RequestParam String marca) {
        if (!CR.findBymarca(marca).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBymarca(marca));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }  
    }

    // ADICIONAR CARRO

    @PostMapping
    public ResponseEntity<Cars> Cadastrar(@RequestBody @Valid Cars car){
        String CHASSI = car.getChassi();
        if (CR.findBychassi(CHASSI)==null) {
            Cars CarroSalvo = CR.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(CarroSalvo); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  
        }
    }

    // FILTRAR O CARRO MAIS BARATO OU O MAIS CARO

    // api/cars?maisCaro       - Busca o carro mais caro
    @GetMapping(params = "maisCaro")
    public ResponseEntity<Cars> MaisCaro() {
        Cars MaisCaro = CR.findByMaisCaro();
        return ResponseEntity.status(HttpStatus.OK).body(MaisCaro);
    }
    // api/cars?maisBarato     - Busca o carro mais barato
    @GetMapping(params = "maisBarato")
    public ResponseEntity<Cars> MaisBarato() {
        Cars MaisBarato = CR.findByMaisBarato();
        return ResponseEntity.status(HttpStatus.OK).body(MaisBarato);
    }

    // ORDENAR PELO NOME, ANO E VALOR

    // api/cars?ListaNome      - Orderna por ordem alfabetica
    @GetMapping(params = "ListaNome")
    public ResponseEntity<List<Cars>> ListaPorNome() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderBynome());
    }
    // api/cars?ListaAno       - Ordenar do mais antigo para mais recente
    @GetMapping(params = "ListaAno")
    public ResponseEntity<List<Cars>> ListaPorData() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByano());
    }
    // api/cars?ListaValor     - Ordenar do menor valor para o maior
    @GetMapping(params = "ListaValor")
    public ResponseEntity<List<Cars>> ListaPorMaisBarato() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByvalor());
    }

}