package com.alimberdi.urlshorteningservice.repository;

import com.alimberdi.urlshorteningservice.entity.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRecordRepository extends JpaRepository<UrlRecord, Long> {

	Optional<UrlRecord> findByShortCode(String shortCode);

}
