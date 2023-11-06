package com.eipulse.teamproject.repository.formapprovalrepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eipulse.teamproject.entity.formapproval.Chats;

public interface ChatsRepository extends JpaRepository<Chats, Integer> {

	@Query(value = "from Chats where roomId = ?1 ORDER BY chatsId DESC")
	Page<Chats> findRoom(Integer id, Pageable pageable);

}
