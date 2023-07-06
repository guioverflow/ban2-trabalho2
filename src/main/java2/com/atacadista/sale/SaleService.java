package com.atacadista.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    @Autowired
    private SaleRepository repository;

    // CREATE
    public Sale insert(Sale sale){
        return repository.save(sale);
    }

    // SELECT
    public List<Sale> selectAll(){
        return repository.findAll();
    }

    public Sale selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Venda com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Sale update(Long id, Sale sale)  {
        Sale saleBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda com esse ID nao existe: " + id));
        saleBean.setSaleDate(sale.getSaleDate());

        return repository.save(saleBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public void printList(List<Sale> saleList) {
        for(Sale sale : saleList) {
            System.out.println(
                    String.format("%-10s - %-10s",
                            sale.getIdSale(),
                            sale.getSaleDate().toString()
                    )
            );
        }
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
