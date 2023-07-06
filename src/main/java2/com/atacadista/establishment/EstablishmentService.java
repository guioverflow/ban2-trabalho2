package com.atacadista.establishment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstablishmentService {

    @Autowired
    private EstablishmentRepository repository;

    // CREATE
    public Establishment insert(Establishment establishment){
        return repository.save(establishment);
    }

    // SELECT
    public List<Establishment> selectAll(){
        return repository.findAll();
    }

    public Establishment selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Estabelecimento com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Establishment update(Long id, Establishment establishment)  {
        Establishment establishmentBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento com esse ID nao existe: " + id));

        establishmentBean.setPhone(establishment.getPhone());
        establishmentBean.setCNPJ(establishment.getCNPJ());

        return repository.save(establishmentBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
