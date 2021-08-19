package com.sifat.products.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sifat.products.model.Product;
import com.sifat.products.repository.ProductRepository;

import java.util.List;


@RestController
@RequestMapping ("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    //GetMapping - Lista todos os produtos cadastrados
    @GetMapping
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    //PostMapping - Criar o produto com Nome, Unidade e Preco dentro do body como JSON

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // DeleteMapping - Deleta o produto criado com o ID informado. Retornando um statos de No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteProduct(@PathVariable Long id){

        productRepository.deleteById(id);

    }

//PutMapping - Atualizar o produto, caso o ID enviado não existir ele vai criar outro com os Nome, Unidade e Preco dentro do body como JSON
    @PutMapping("/{id}")
    public Product updateProducts(@PathVariable Long id , @RequestBody Product product){
       var update =  productRepository.findById(id).map(p -> {
            p.setNome(product.getNome());
            p.setUnidade(product.getUnidade());
            p.setPreco(product.getPreco());
            return productRepository.save(p);
        }).orElseGet(()->{
            product.setId(id);
          return   productRepository.save(product);
        });
    return update;

    }



}
