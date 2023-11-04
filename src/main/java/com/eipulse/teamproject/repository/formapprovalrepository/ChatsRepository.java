package com.eipulse.teamproject.repository.formapprovalrepository;
import com.eipulse.teamproject.entity.formapproval.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChatsRepository extends JpaRepository<Chats, Integer> {

    @Query(value="from Chats where roomId = ?1")
    List<Chats> findRoom(Integer id);

}
