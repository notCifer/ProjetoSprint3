package com.projeto.cars.controller;

import java.util.List;
import javax.validation.Valid;
import com.projeto.cars.model.Cars;
import com.projeto.cars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "API REST Carros")
@RequestMapping(value = "/api/cars")
@CrossOrigin(origins = "*")
public class Carros {

    @Autowired
    private CarRepository CR;

    /* -------------------------------------------------------------------------------------
    * BUSCAR TODOS OS CARROS
    */
    
    @ApiOperation(value = "Retorna uma lista de Carros")
    @GetMapping()
    public List<Cars> ListarTodos() {
        return CR.findAll();
    }

    /* -------------------------------------------------------------------------------------
    * ADICIONAR CARRO
    */ 
    @ApiOperation(value = "Cadastra um novo Carro")
    @PostMapping("/cars")
    public ResponseEntity<Cars> Cadastrar(@RequestBody @Valid Cars car) {
        String CHASSI = car.getChassi();
        if (CR.findBychassi(CHASSI) == null) {
            Cars CarroSalvo = CR.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(CarroSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* -------------------------------------------------------------------------------------   
    * FILTRAR POR NOME, COR E MARCA
    *
    * /api/cars?nome=X      - Filtra pelo nome.
    * /api/cars?marca=X     - Filtra pela marca.   
    * /api/cars?cor=X       - Filtra pela cor.
    */

    @ApiIgnore
    @GetMapping(params = "nome")
    public ResponseEntity<List<Cars>> EncontrarPorNome(@RequestParam String nome) {
        if (!CR.findBynome(nome).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBynome(nome));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiIgnore
    @GetMapping(params = "cor")
    public ResponseEntity<List<Cars>> encontrarPorCor(@RequestParam String cor) {
        if (!CR.findBycor(cor).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBycor(cor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiIgnore
    @GetMapping(params = "marca")
    public ResponseEntity<List<Cars>> encontrarPorMarca(@RequestParam String marca) {
        if (!CR.findBymarca(marca).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBymarca(marca));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /* ----------------------------------------------------------------------------- 
     * FILTRAR O CARRO MAIS BARATO OU O MAIS CARO
     * 
     * /api/cars?Caro   - Busca o carro mais caro.
     * /api/cars?Barato - Busca o carro mais barato.
     */

    @ApiIgnore
    @GetMapping(params = "Caro")
    public ResponseEntity<Cars> MaisCaro() {
        Cars MaisCaro = CR.findByMaisCaro();
        return ResponseEntity.status(HttpStatus.OK).body(MaisCaro);
    }

    @ApiIgnore
    @GetMapping(params = "Barato")
    public ResponseEntity<Cars> MaisBarato() {
        Cars MaisBarato = CR.findByMaisBarato();
        return ResponseEntity.status(HttpStatus.OK).body(MaisBarato);
    }

    /*-----------------------------------------------------------------------------
     * ORDENAR PELO NOME, ANO E VALOR
     * 
     * /api/cars?ListaNome   - Orderna por ordem alfabética.
     * /api/cars?ListaAno    - Ordenar do mais antigo para mais recente.
     * /api/cars?ListaValor  - Ordenar do menor valor para o maior.
     */

    @ApiIgnore
    @ApiOperation(value = "Lista por ordem alfabética")
    @GetMapping(params = "ListaNome")
    public ResponseEntity<List<Cars>> ListaPorNome() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderBynome());
    }

    @ApiIgnore
    @ApiOperation(value = "Lista por ano de fabricação do mais antigo para o mais recente")
    @GetMapping(params = "ListaAno")
    public ResponseEntity<List<Cars>> ListaPorData() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByano());
    }

    @ApiIgnore
    @ApiOperation(value = "Lista por valores do menor para o maior")
    @GetMapping(params = "ListaValor")
    public ResponseEntity<List<Cars>> ListaPorMaisBarato() {
        return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByvalor());
    }

}