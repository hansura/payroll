/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.payrollservice.util;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author birhane
 */
public interface Common<T,Z> {


    @PostMapping(value = "",produces = { "application/json"})
    Z store(T t) ;

    @PostMapping(value = "/list",produces = { "application/json"})
    Iterable<Z> store(List<T> t);

    @GetMapping(value = "/{id}", produces = { "application/json"})
    Z show(@PathVariable long id);

    @PutMapping(value = "/{id}",produces = { "application/json"})
    Z update(@PathVariable long id, T t);

    @DeleteMapping(value = "/{id}",produces = { "application/json"})
    boolean delete(@PathVariable long id);

    /**
     *
     * @return
     */
//    @GetMapping("/")
//    Iterable<Z> getAll();

    @GetMapping(value = "/all",produces = { "application/json"})
    Iterable<Z> getAll(Pageable pageable);


}
