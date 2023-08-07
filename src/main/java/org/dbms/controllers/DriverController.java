package org.dbms.controllers;

import org.dbms.dto.DriverDTO;
import org.dbms.repos.DriverRepo;
import org.dbms.searchModels.DriverSearch;
import org.dbms.service.DriverService;
import org.dbms.storageImpl.DriverStorage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DriverController {
    private final DriverService driverService;
    private final DriverRepo repo;

    public DriverController(DriverService driverService, DriverRepo repo) {
        this.driverService = driverService;
        this.repo = repo;
    }

    @GetMapping("/drivers")
    public List<DriverDTO> getDrivers(@RequestParam(defaultValue = "-1") int expFrom,
                                      @RequestParam(defaultValue = "-1") int expTo,
                                      @RequestParam(defaultValue = "-1") int size,
                                      @RequestParam(defaultValue = "-1") int page) {

        return driverService.getFilteredPage(expFrom, expTo, size, page).stream().map(DriverDTO::new).toList();
    }

    @GetMapping("/drivers/pages-count")
    public int getDriversCount(@RequestParam(defaultValue = "-1") int expFrom,
                                      @RequestParam(defaultValue = "-1") int expTo,
                                      @RequestParam(defaultValue = "-1") int size) {

        return driverService.getFilteredCount(expFrom, expTo, size);
    }

    @GetMapping("/all-drivers")
    public List<DriverDTO> getAll() {
        return repo.findAll().stream().map(DriverDTO::new).toList();
    }
}
