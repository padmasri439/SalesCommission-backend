package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@RestController
@CrossOrigin(origins = "*")
public class SalesController {

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;


    @TrackExecutionTime
    @PostMapping("/sales")
    public ResponseEntity<List<Commission>> computeSalesAndSaveToDB(@RequestBody Sales s) {
        List<Product> products = s.getProducts();
        List<Salesman> salesmans = s.getSalesman();
        TreeMap<Long, Salesman> salesmanTreeMap = new TreeMap<>();
        for (Salesman sm : salesmans) {
            salesmanTreeMap.put(sm.getSalesmanId(), sm);
        }
        List<Commission> commissions = new ArrayList<>();
        for (Product product : products) {

            Salesman salesman = salesmanTreeMap.get(product.getSalesmanId());
            salesmanRepository.save(salesman);

            Double salesAmount = product.getQuantity() * product.getMrpperUnit();
            Double commissionAmount = (double) Math.round(salesAmount * (salesman.getCommissionRate() / 100.0));

            Commission commission = new Commission();
            commission.setProductName(product.getProductName());
            commission.setSalesmanName(salesman.getSalesmanName());
            commission.setQuantity(product.getQuantity());
            commission.setSalesAmount(salesAmount);
            commission.setSalesmanArea(salesman.getSalesmanArea());
            commission.setSalesmanCommission(commissionAmount);
            Date date = new Date();
            SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = d.format(date);
            commission.setCreatedDate(strDate);
            commissions.add(commission);
        }
        commissionRepository.saveAll(commissions);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @TrackExecutionTime
    @GetMapping("/sales")
    public ResponseEntity<List<Commission>> getAllSales() {
        List<Commission> sales = commissionRepository.findAll();
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    @TrackExecutionTime
    @GetMapping("/sales/date")
    public List<Commission> getCommissionByDate(@RequestParam("date") String date) {
        return commissionRepository.findByCreatedDate(date);
    }

}