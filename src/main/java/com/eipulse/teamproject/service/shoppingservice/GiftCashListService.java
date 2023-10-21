package com.eipulse.teamproject.service.shoppingservice;

import com.eipulse.teamproject.entity.shoppingentity.GiftCashList;
import com.eipulse.teamproject.repository.shoppingrepository.GiftCashListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class GiftCashListService {

    private GiftCashListRepository giftCashListRepository;

    @Autowired
    public GiftCashListService(GiftCashListRepository giftCashListRepository) {
        this.giftCashListRepository = giftCashListRepository;
    }

    public boolean saveGifCash(GiftCashList giftCashList){
        GiftCashList result =  giftCashListRepository.save(giftCashList);
        if(result!=null){
            return true;
        }
        return false;
    }

    public GiftCashList findByIdGiftCash(Integer id){
        GiftCashList result = giftCashListRepository.findById(id).orElseThrow(()-> new RuntimeException("查無資料"));
        return result;
    }

    public List<GiftCashList> findAllGiftCash(){
        return giftCashListRepository.findAll();
    }
    public boolean updateGiftCash(GiftCashList newGiftCashList){
        GiftCashList oldGiftCash = giftCashListRepository.findById(newGiftCashList.getId()).orElseThrow(()-> new RuntimeException("無此資料無法更新"));
        GiftCashList result = giftCashListRepository.save(oldGiftCash);
        return true;
    }

    public boolean deleteGiftCash(Integer id){
        GiftCashList giftCash = giftCashListRepository.findById(id).orElseThrow(()-> new RuntimeException("無此資料無法更新"));
        giftCashListRepository.deleteById(id);
        return true;
    }
}
