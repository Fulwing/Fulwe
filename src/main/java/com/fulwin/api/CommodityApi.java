package com.fulwin.api;

import com.fulwin.pojo.Commodity;
import com.fulwin.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api")
public class CommodityApi {

    @Autowired
    private CommodityService commodityService;

    @GetMapping("/commodities")
    public ResponseEntity<List<Commodity>> getCommodities(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int itemsPerPage
    ) {
        // Calculate the offset to retrieve the correct page of data
        int offset = (page - 1) * itemsPerPage;

        // Retrieve paginated commodities from the service
        List<Commodity> commodities = commodityService.getCommoditiesPage(offset, itemsPerPage);

        return ResponseEntity.ok(commodities);
    }
}
