package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.model.Metallum;
import org.acme.model.User;
import org.acme.utils.MetallumScraper;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class MineraeService {
    @Inject
    MetallumScraper metallumScraper;
    @Inject
    UserService userService;
    @Transactional
    public Metallum addMetallum(String user, Metallum metallum) {
        Metallum newMetallum = new Metallum();
        newMetallum.setName(metallum.getName().toUpperCase().replace("-", " "));
        newMetallum.setDescription("https://en.wikipedia.org/wiki/".concat(metallum.getName().toLowerCase().replace(" ", "-")));
        Metallum metallumInfo = metallumScraper.getMetallumInfo(newMetallum);
        newMetallum.setImageUrl(metallumInfo.getImageUrl());
        newMetallum.setCategory(metallumInfo.getCategory());
        newMetallum.setColor(metallumInfo.getColor());
        newMetallum.setCrystalSystem(metallumInfo.getCrystalSystem());
        newMetallum.setMohsScale(metallumInfo.getMohsScale());
        newMetallum.setFormula(metallumInfo.getFormula());
        newMetallum.setUltravioletFluorescence(metallumInfo.getUltravioletFluorescence());
        newMetallum.setOtherProperties(metallumInfo.getOtherProperties());
        addMetallumToUser(user, newMetallum).persist();
        return newMetallum;
    }

    @Transactional
    public Metallum addMetallumToUser(String username, Metallum metallum) {
        User user = userService.findUserByUsername(username);
        metallum.setUser(user);
        user.getMetallum().add(metallum);
        user.persist();
        return metallum;
    }

    public Metallum findMetallumByName(String name) {
        return Metallum.find("name", name).firstResult();
    }

    public List<Metallum> getAllMetallum() {
        return Metallum.listAll();
    }

    public List<Metallum> getFilteredMetallum(String filter) {
        return getAllMetallum()
                .stream()
                .filter(metallum -> metallum.getColor().equalsIgnoreCase(filter))
                .collect(Collectors.toList());
    }

    public List<Metallum> getAllMetallumByUsername(String username) {
        User user = userService.findUserByUsername(username);
        long userID = user.getId();
        System.out.println("userID = " + userID);
        return Metallum.find("user.id", userID).list();
    }

    public List<Metallum> getFilteredMetallumByUsername(String username, String filter) {
        return getAllMetallumByUsername(username)
                .stream()
                .filter(metallum -> metallum.getColor().equalsIgnoreCase(filter))
                .collect(Collectors.toList());
    }
}
