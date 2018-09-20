package com.codecool.pionierzy.gotchiarena.dao;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GotchiRepository extends JpaRepository<Gotchi, Long> {

    Gotchi findGotchiById(Long id);

    Gotchi findGotchiByUserId(Long id);



}
