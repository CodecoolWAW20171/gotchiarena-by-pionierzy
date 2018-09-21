package com.codecool.pionierzy.gotchiarena.service.GotchiServices;

import com.codecool.pionierzy.gotchiarena.dao.GotchiRepository;
import com.codecool.pionierzy.gotchiarena.model.Gotchi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GotchiServiceImpl implements GotchiService {

    private final GotchiRepository gotchiRepository;

    @Autowired
    public GotchiServiceImpl(GotchiRepository gotchiRepository) {
        this.gotchiRepository = gotchiRepository;
    }

//    @Override
//    public Gotchi findByUserId(Long userId) {
//        return gotchiRepository.findGotchiByUserId(userId);
//    }

    @Override
    public Gotchi findById(Long id) {
        return gotchiRepository.findGotchiById(id);
    }

    @Override
    public void save(Gotchi gotchi) {
        gotchiRepository.save(gotchi);
    }

}
