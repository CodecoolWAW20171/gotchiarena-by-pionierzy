package com.codecool.pionierzy.gotchiarena.service.GotchiServices;

import com.codecool.pionierzy.gotchiarena.model.Gotchi;

public interface GotchiService {

    Gotchi findByUserId(Long userId);

    Gotchi findById(Long id);

    void save(Gotchi gotchi);
}
