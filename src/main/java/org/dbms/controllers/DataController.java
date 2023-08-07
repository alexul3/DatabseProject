package org.dbms.controllers;

import org.dbms.data.DataGenerator;
import org.dbms.data.DataPerform;
import org.dbms.data.DataPerformMongo;
import org.dbms.dto.DriverDTO;
import org.dbms.searchModels.DriverSearch;
import org.dbms.service.DriverService;
import org.dbms.storageImpl.DriverStorage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DataController {
    private DataGenerator dataGenerator;
    private DataPerformMongo dataPerform;
    public DataController(DataGenerator dataGenerator, DataPerformMongo dataPerform) {
        this.dataGenerator = dataGenerator;
        this.dataPerform = dataPerform;
    }

    @GetMapping("/generate-data")
    public String getDriversCount() {
        dataGenerator.generate();
        return "success";
    }

    @GetMapping("/test-select")
    public String testSelect() {
        return dataPerform.select() + "";
    }


    @GetMapping("/test-insert")
    public String testInsert() {
        return dataPerform.insert(1000) + "";
    }

    @GetMapping("/test-update")
    public String testUpdate() {
        return dataPerform.update(1000) + "";
    }

    @GetMapping("/test-delete")
    public String testDelete() {
        return dataPerform.delete() + "";
    }

}
