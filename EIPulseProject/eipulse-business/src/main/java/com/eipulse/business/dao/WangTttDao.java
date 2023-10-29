package com.eipulse.business.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.eipulse.business.domain.WangTtt;

/**
 * 測試代碼生成器Dao介面
 */
@Repository
public interface WangTttDao extends JpaRepository<WangTtt, String>, JpaSpecificationExecutor<WangTtt> {

}
