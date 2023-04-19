package com.sparta.hanghae99springlv1.repository;

import com.sparta.hanghae99springlv1.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findByIdAndUsername(Long id, String username);
}
