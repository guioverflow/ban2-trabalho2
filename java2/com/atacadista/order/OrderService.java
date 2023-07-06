package com.atacadista.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    // CREATE
    public Order insert(Order order){
        return repository.save(order);
    }

    // SELECT
    public List<Order> selectAll(){
        return repository.findAll();
    }

    public Order selectById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido com esse ID nao existe: " + id)
        );
    }

    // UPDATE
    public Order update(Long id, Order order)  {
        Order orderBean = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido com esse ID nao existe: " + id));
        orderBean.setOrderDate(order.getOrderDate());

        return repository.save(orderBean);
    }

    // DELETE
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public void printList(List<Order> orders) {
        for(Order order : orders) {
            System.out.println(
                    String.format("%-10s - %-10s",
                            order.getIdOrder(),
                            order.getOrderDate().toString()
                    )
            );
        }
    }

    public boolean checkId(Long id) {
        return repository.existsById(id);
    }
}
