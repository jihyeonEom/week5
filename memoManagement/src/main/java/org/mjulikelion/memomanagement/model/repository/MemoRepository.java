package org.mjulikelion.memomanagement.model.repository;

import jakarta.transaction.Transactional;
import org.mjulikelion.memomanagement.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemoRepository extends JpaRepository<Memo, UUID> {

    Memo save(Memo memo);

    List<Memo> findAllMemoByUserId(UUID id);

    Memo findMemoById(UUID memoId);

    @Transactional
    void deleteMemoById(UUID memoId);
}
