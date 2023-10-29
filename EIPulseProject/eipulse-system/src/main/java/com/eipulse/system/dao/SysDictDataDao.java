package com.eipulse.system.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eipulse.common.core.domain.entity.SysDictData;

/**
 * 字典表 數據層
 */
@Repository
public interface SysDictDataDao extends JpaRepository<SysDictData, Long>, JpaSpecificationExecutor<SysDictData> {

	Optional<SysDictData> findByDictTypeAndDictValue(String dictType, String dictValue);

	List<SysDictData> findByDictCodeIn(List<Long> dictIds);

	List<SysDictData> findByDictTypeAndStatusOrderByDictSortAsc(String dictType, String status);

	List<SysDictData> findByDictType(String dictType);

	@Modifying
	@Query(value = "update sys_dict_data set dict_type=?1 where dict_type=?2 ", nativeQuery = true)
	void updateType(String oldDictType, String newDictType);

}
