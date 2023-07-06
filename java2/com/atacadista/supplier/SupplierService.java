package com.atacadista.supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    // CREATE
    public Supplier insert(Supplier supplier){
        return repository.save(supplier);
    }

    // SELECT
    public List<Supplier> selectAll(){
        return repository.findAll();
    }

    public Supplier selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Fornecedor com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Supplier update(Long id, Supplier supplier)  {
        Supplier supplierBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor com esse ID nao existe: " + id));
        supplierBean.setName(supplier.getName());
        supplierBean.setPhone(supplier.getPhone());
        supplierBean.setEmail(supplier.getEmail());
        supplierBean.setCNPJ(supplier.getCNPJ());

        return repository.save(supplierBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
