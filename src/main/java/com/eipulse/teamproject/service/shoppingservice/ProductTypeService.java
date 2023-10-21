package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.eipulse.teamproject.repository.shoppingrepository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService {
    private ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ProductType saveProductType(ProductType productType){
        if(productType!=null){
            return productTypeRepository.save(productType);
        }
        throw new UnsupportedOperationException("新增失敗");
    }
    public List<ProductType> saveListProductType(List<ProductType> productTypeList){
        return productTypeRepository.saveAll(productTypeList);
    }
    public ProductType findByIdProductType(Integer id){
        Optional<ProductType> optional = productTypeRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new UnsupportedOperationException("查無資料");
    }
    public List<ProductType> findAllProductType(){
        return productTypeRepository.findAll();
    }
    public boolean updateProductType(ProductType productType){
        Optional<ProductType> optional = productTypeRepository.findById(productType.getId());
        if(optional.isPresent()){
            productTypeRepository.save(productType);
            return true;
        }else {
            return false;
        }
    }


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
