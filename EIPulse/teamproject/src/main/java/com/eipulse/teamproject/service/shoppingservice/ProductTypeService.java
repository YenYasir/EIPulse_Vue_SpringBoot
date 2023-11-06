package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.entity.shoppingentity.Product;
import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.eipulse.teamproject.entity.shoppingentity.SubType;
import com.eipulse.teamproject.repository.shoppingrepository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }


    //新增主類別
    public ProductType saveProductType(ProductType productType){
        if(productType!=null){
            return productTypeRepository.save(productType);
        }
        throw new UnsupportedOperationException("新增失敗");
    }

    //新增多筆主類別，暫時無用
    public List<ProductType> saveListProductType(List<ProductType> productTypeList){
        return productTypeRepository.saveAll(productTypeList);
    }

    //查詢單主類別所有關聯
    public ProductType findByIdProductType(Integer id){
        Optional<ProductType> optional = productTypeRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new UnsupportedOperationException("查無資料");
    }

    //查詢所有主類別關聯
    public List<ProductType> findAllProductType(){
       return productTypeRepository.findAll();
    }

    //更新主類別名
    public boolean updateProductType(ProductType productType){
        Optional<ProductType> optional = productTypeRepository.findById(productType.getId());
        if(optional.isPresent()){
            productTypeRepository.save(productType);
            return true;
        }else {
            return false;
        }
    }


    //刪除主類別
    public boolean deleteProductType(Integer id){
        Optional<ProductType> optional = productTypeRepository.findById(id);
        if(optional.isPresent()){
            productTypeRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
