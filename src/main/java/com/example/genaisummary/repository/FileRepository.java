package com.example.genaisummary.repository;

import com.example.genaisummary.entity.InputFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<InputFile, Long> {
}
