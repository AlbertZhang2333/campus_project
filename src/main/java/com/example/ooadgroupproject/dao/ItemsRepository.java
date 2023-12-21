package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ItemsRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findByName(String name);


}
