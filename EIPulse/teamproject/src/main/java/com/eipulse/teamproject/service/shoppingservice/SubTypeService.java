package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.dto.shoppingdto.SubTypeDTO;
import com.eipulse.teamproject.entity.shoppingentity.ProductType;
import com.eipulse.teamproject.entity.shoppingentity.SubType;
import com.eipulse.teamproject.repository.shoppingrepository.ProductTypeRepository;
import com.eipulse.teamproject.repository.shoppingrepository.SubTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubTypeService {
    private SubTypeRepository subTypeRepository;
    private ProductTypeRepository productTypeRepository;

    @Autowired
    public SubTypeService(SubTypeRepository subTypeRepository, ProductTypeRepository productTypeRepository) {
        this.subTypeRepository = subTypeRepository;
        this.productTypeRepository = productTypeRepository;
    }

    //新增子類別
    public SubType saveSubType(SubTypeDTO subTypeDTO) {
        ProductType productType = productTypeRepository.findById(subTypeDTO.getProductTypeId()).orElseThrow(()-> new EntityNotFoundException("查無主類別"));
        SubType subType = new SubType(productType,subTypeDTO.getSubName());
        return subTypeRepository.save(subType);
    }

    //查詢子類別所有關聯
    public SubType findByIdSubType(Integer id) {
        Optional<SubType> optional = subTypeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UnsupportedOperationException("找無資料");
        }
    }

    //查詢產品的子類別
    public List<SubType> findByProductId(Integer productId) {
        List<SubType> subTypeList = subTypeRepository.findByProductIdWithSubType(productId);
        if (subTypeList != null) {
            return subTypeList;
        } else {
            throw new UnsupportedOperationException("無資料");

        }
    }

    //刪除子類別
    public boolean deleteSubType(Integer id){
        Optional<SubType>optional = subTypeRepository.findById(id);
        if(optional.isPresent()){
            subTypeRepository.deleteById(id);
            return true;
        }else {
            throw new UnsupportedOperationException("無資料");
        }
    }

}
