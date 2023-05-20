package com.capstone.webserver.repository;

import com.capstone.webserver.entity.nfc.Nfc;
import com.capstone.webserver.entity.user.Auditor;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface NFCRepository extends CrudRepository<Nfc, Long> {
    String findByNfcNumber(String nfcNumber);
    ArrayList<Nfc> findAll();
}
