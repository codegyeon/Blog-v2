package com.example.demo.Repository;

import com.example.demo.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepliesRepository extends JpaRepository<Replies,Long> {
}
