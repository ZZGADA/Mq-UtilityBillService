package com.example.consumer.controller;

import com.example.consumer.service.impl.UtilityBillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utilityBill")
@RequiredArgsConstructor
public class UtilityBillController {
    private final UtilityBillsService utilityBillsService;

    @GetMapping("/getBill")
    public String getUtilityBill(@RequestParam(required = false) String recipient){
        utilityBillsService.sendBill(recipient);
        return "success";
    }

}
