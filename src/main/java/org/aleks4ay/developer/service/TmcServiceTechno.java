package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.TmcDao;
import org.aleks4ay.developer.model.Tmc;

import java.util.*;
import java.util.stream.Collectors;


public class TmcServiceTechno {

    public static Map<String, String> getTechnoIdAll() {
        TmcService tmcService = new TmcService(new TmcDao(ConnectionPool.getInstance()));
        return doTechnoFilter(tmcService.findAll())
                .stream()
                .collect(Collectors.toMap(Tmc::getId, Tmc::getDescr));
    }

    private static List<Tmc> doTechnoFilter(List<Tmc> allTmcList) {
        Set<String> idFoldersWithTechno = new TreeSet<>();
        idFoldersWithTechno.add("    19");
        idFoldersWithTechno.add("   GGP");
        return getTechnoChildren(idFoldersWithTechno, new ArrayList<>(), allTmcList);
    }

    private static List<Tmc> getTechnoChildren(Set<String> idFoldersWithTechno, List<Tmc> technoItems, List<Tmc> tmcList) {
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

