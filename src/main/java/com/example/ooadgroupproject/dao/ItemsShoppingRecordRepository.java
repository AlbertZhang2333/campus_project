package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsShoppingRecordRepository extends JpaRepository<ItemsShoppingRecord, Long> {
    public List<ItemsShoppingRecord> findByUserMail(String userMail);
    public List<ItemsShoppingRecord> findByItemName(String itemName);
    public List<ItemsShoppingRecord> findByUserMailAndItemName(String userMail, String itemName);
    public Optional<ItemsShoppingRecord> findById(long id);


}
