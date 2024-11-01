package com.example.springbootjpa.controller;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.service.ProcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.SQLException;

@RestController
@RequestMapping("/proc")
public class ProcController {

    @Autowired
    private ProcService procService;

    @GetMapping("/query")
    public String query(){
       return procService.query();
    }

    @GetMapping("/query2")
    public String query2() throws SQLException {
        return procService.query2();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/save")
    public String saveOrUpdate(){

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("updateUser");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
        query.setParameter(1, 1);
        query.execute();
        final Object outputParameterValue = query.getOutputParameterValue(2);
        return procService.saveOrUpdate(new User());
    }

    @PostMapping("/merge")
    public String merge(@RequestBody User user){
        return procService.merge(user);
    }
}
