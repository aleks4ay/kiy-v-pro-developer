package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.TmcDao;
import org.aleks4ay.developer.model.Tmc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;


public class TmcServiceTechno {
    private static final Logger log = LoggerFactory.getLogger(TmcServiceTechno.class);

    public static void main(String[] args) {
        new TmcServiceTechno().run();
    }

    public void run() {
        TmcService tmcService = new TmcService(new TmcDao(ConnectionPool.getInstance()));
        List<Tmc> listRowTechnoTmc = doTechnoFilter(tmcService.findAll());
        System.out.println(listRowTechnoTmc.size());

/*        if (listRowTechnoTmc.isEmpty()) {
            return;
        }

        TmcDao tmcDaoTechno = new TmcDaoTechnoJdbc(connPostgres);
        List<Tmc> listNewTmc = new ArrayList<>();
        List<Tmc> listUpdatingTmc = new ArrayList<>();
        Map<String, Tmc> oldTmc = tmcDaoTechno.getAll()
                .stream()
                .collect(Collectors.toMap(Tmc::getId, Tmc::getTmc));
        for (Tmc tmc : listRowTechnoTmc) {
            String idComparedTmc = tmc.getId();
            if (!oldTmc.containsKey(idComparedTmc)) {
                listNewTmc.add(tmc);
            } else if (!oldTmc.get(idComparedTmc).equalsTechno(tmc)) {
                listUpdatingTmc.add(tmc);
                oldTmc.remove(idComparedTmc);
            }
            else {
                oldTmc.remove(idComparedTmc);
            }
        }
        if (listNewTmc.size() > 0) {
            tmcDaoTechno.saveAll(listNewTmc);
        }
        if (listUpdatingTmc.size() > 0) {
            tmcDaoTechno.updateAll(listUpdatingTmc);
        }
        if (oldTmc.size() > 0) {
            for (Tmc tmc : oldTmc.values()) {
                log.warn("   NEED DELETE TMC with id '{}', '{}'.", tmc.getId(), tmc.getDescr());
            }
//            tmcDaoTechno.deleteAll(oldTmc.keySet());
        }
        utilDao.closeConnection(connPostgres);*/
    }

    private List<Tmc> doTechnoFilter(List<Tmc> allTmcList) {
        Set<String> idFoldersWithTechno = new TreeSet<>();
        idFoldersWithTechno.add("    19");
        return getTechnoChildren(idFoldersWithTechno, new ArrayList<>(), allTmcList);
    }

    private List<Tmc> getTechnoChildren(Set<String> idFoldersWithTechno, List<Tmc> technoItems, List<Tmc> tmcList) {
        Set<String> newFoldersWithTechno = new TreeSet<>();
        if (idFoldersWithTechno.size() == 0) {
            return technoItems;
        }
        else {
            for (String folderName : idFoldersWithTechno) {
                for (Tmc tmc : tmcList) {
                    if (tmc.getIdParent().equals(folderName)) {
                        if (tmc.getIsFolder() == 2) {
                            technoItems.add(tmc);
                        } else {
                            newFoldersWithTechno.add(tmc.getId());
                        }
                    }
                }
            }
            return getTechnoChildren(newFoldersWithTechno, technoItems, tmcList);
        }
    }
}

