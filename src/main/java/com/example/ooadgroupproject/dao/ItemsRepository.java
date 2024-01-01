package com.example.ooadgroupproject.dao;

import com.example.ooadgroupproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findByName(String name);



}
