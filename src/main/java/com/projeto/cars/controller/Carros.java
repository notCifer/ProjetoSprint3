package com.projeto.cars.controller;

import java.util.List;
import javax.validation.Valid;
import com.projeto.cars.model.Cars;
import com.projeto.cars.model.Filtra;
import com.projeto.cars.model.Ordena;
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

    /*
     * -----------------------------------------------------------------------------
     * ADICIONAR CARRO
     */
    @ApiOperation(value = "Cadastra um novo Carro")
    @PostMapping
    public ResponseEntity<Cars> Cadastrar(@RequestBody @Valid Cars car) {
        String CHASSI = car.getChassi();
        if (CR.findBychassi(CHASSI) == null) {
            Cars CarroSalvo = CR.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(CarroSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
     * -----------------------------------------------------------------------------
     * BUSCAR TODOS OS CARROS OU
     * -------------------------------------------------------------------------
     * FILTRAR POR NOME, COR E MARCA
     *
     * /api/cars?nome=X     - Filtra pelo nome. 
     * /api/cars?marca=X    - Filtra pela marca.
     * /api/cars?cor=X      - Filtra pela cor.
     */
    @ApiOperation(value = "Buscar Carros")
    @GetMapping
    public List<Cars> EncontrarPor(@Valid @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cor", required = false) String cor,
            @RequestParam(value = "marca", required = false) String marca,
            @RequestParam(value = "Filtra", required = false) Filtra EnumFiltra,
            @RequestParam(value = "Ordena", required = false) Ordena EnumLista) {

        return CR.findAll();
    }

    /*
     * -----------------------------------------------------------------------------
     * FILTRAR O CARRO MAIS BARATO OU O MAIS CARO
     * 
     * /api/cars?Filtra=Caro    - Busca o carro mais caro. 
     * /api/cars?Filtra=Barato  - Busca o carro mais barato.
     */

    @ApiIgnore
    @GetMapping(params = "Filtra")
    public ResponseEntity<List<Cars>> Filtra(@RequestParam(value = "Filtra", required = false) Filtra L) {

        String s = L.toString();
        if (s.equals("Caro")) {
            List<Cars> MaisCaro = CR.findByMaisCaro();
            return ResponseEntity.status(HttpStatus.OK).body(MaisCaro);
        } else if (s.equals("Barato")) {
            List<Cars> MaisBarato = CR.findByMaisBarato();
            return ResponseEntity.status(HttpStatus.OK).body(MaisBarato);
        }
        return null;
    }

    /*-----------------------------------------------------------------------------
     * ORDENAR PELO NOME, ANO E VALOR
     * 
     * /api/cars?Ordena=Nome   - Orderna por ordem alfabética.
     * /api/cars?Ordena=Ano    - Ordenar do mais antigo para mais recente.
     * /api/cars?Ordena=Valor  - Ordenar do menor valor para o maior.
     */
    @ApiIgnore
    @GetMapping(params = "Ordena")
    public ResponseEntity<List<Cars>> Ordena(@RequestParam(value = "Ordena", required = false) Ordena O) {

        String s = O.toString();

        if (s.equals("Nome")) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.OrderBynome());
        } else if (s.equals("Valor")) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByvalor());
        } else if (s.equals("Ano")) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.OrderByano());
        }
        return null;
    }

    /*
     * PARAMETROS ULTILIZADO PARA AS BUSCAS
     * -----------------------------------------------------------------------------
     */

    @ApiIgnore
    @GetMapping(params = "nome")
    public ResponseEntity<List<Cars>> EncontrarPorNome(@RequestParam @Valid String nome) {
        if (!CR.findBynome(nome).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBynome(nome));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiIgnore
    @GetMapping(params = "cor")
    public ResponseEntity<List<Cars>> encontrarPorCor(@RequestParam @Valid String cor) {
        if (!CR.findBycor(cor).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBycor(cor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiIgnore
    @GetMapping(params = "marca")
    public ResponseEntity<List<Cars>> encontrarPorMarca(@RequestParam @Valid String marca) {
        if (!CR.findBymarca(marca).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(CR.findBymarca(marca));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ApiIgnore
    @GetMapping(params = "Caro")
    public ResponseEntity<List<Cars>> MaisCaro() {
        List<Cars> MaisCaro = CR.findByMaisCaro();
        return ResponseEntity.status(HttpStatus.OK).body(MaisCaro);
    }

    @ApiIgnore
    @GetMapping(params = "Barato")
    public ResponseEntity<List<Cars>> MaisBarato() {
        List<Cars> MaisBarato = CR.findByMaisBarato();
        return ResponseEntity.status(HttpStatus.OK).body(MaisBarato);
    }

}