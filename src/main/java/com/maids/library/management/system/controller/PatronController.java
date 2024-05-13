package com.maids.library.management.system.controller;

import com.maids.library.management.system.dto.PatronDTO;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.model.Patron;
import com.maids.library.management.system.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        var patrons = patronService.getAllPatrons().stream().map(patron -> modelMapper.map(patron, PatronDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(patrons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) throws ApplicationException {
        var patron = patronService.getPatronById(id);
        if (Objects.isNull(patron)) {
            throw new ApplicationException("Patron is not found");
        }
        return new ResponseEntity<>(modelMapper.map(patron, PatronDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatronDTO> addPatron(@Valid @RequestBody PatronDTO patronDTO) {
        patronService.addPatron(modelMapper.map(patronDTO, Patron.class));
        return new ResponseEntity<>(patronDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDTO> updatePatron(@PathVariable Long id, @RequestBody PatronDTO patronDTO) throws ApplicationException {
        var patron = patronService.updatePatron(id, modelMapper.map(patronDTO, Patron.class));
        if (Objects.isNull(patron)) {
            throw new ApplicationException("Patron is not found");
        }

        return new ResponseEntity<>(patronDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
