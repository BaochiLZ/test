package com.itheima.ssm.service;

import com.itheima.ssm.domain.Product;

import java.util.List;

public interface IProductService {

    public List<Product> findAll(int page, int size) throws Exception;
    public void save(Product product);
    public Product findById(String id) throws Exception;
    public void update(Product product);
}
