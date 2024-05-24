package com.topilmalar.controller;

import com.topilmalar.payload.Login;
import com.topilmalar.payload.LostItemDto;
import com.topilmalar.payload.Putting;
import com.topilmalar.repository.LostItemRepo;
import com.topilmalar.service.LostItemService;
import com.topilmalar.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lost")
public class LostItemController {

    @Autowired
    LostItemService lostItemService;

    @Autowired
    LostItemRepo lostItemRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/all")
    public HttpEntity<?> getLostItems(){
        return ResponseEntity.ok(lostItemRepo.findAllBy());
    }

    @GetMapping("/filter")
    public HttpEntity<?> filterLostItems( @RequestParam(defaultValue = "null") String type, @RequestParam(defaultValue = "null") String subType, @RequestParam(defaultValue = "null") String region, @RequestParam(defaultValue = "null") String district, @RequestParam(defaultValue = "null") String holat){
        return lostItemService.filterItems(holat, type, subType, region, district);
    }

    @PostMapping("/test")
    public HttpEntity<?> post(@RequestBody Login email){
        System.out.println(email);
        return null;
    }

    @PostMapping
    public HttpEntity<?> saveLostItem(@RequestBody LostItemDto lostItemDto, @RequestHeader String Authorization){
        long userId = Long.parseLong(jwtService.extractSubject(Authorization));
        if(userId != 0){
            return lostItemService.saveLostItem(lostItemDto, userId);
        }
        return ResponseEntity.ok("Xatolik yuz berdi");
    }

    @PutMapping("/{lostItemId}")
    public HttpEntity<?> changeLostItem(@PathVariable Long lostItemId, @RequestHeader String Authorization){
        Long ownerId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.changeLostItem(lostItemId, ownerId);
    }

    @PutMapping("/user/{lostItemId}")
    public HttpEntity<?> changeLostItemByFoundStatus(@PathVariable Long lostItemId, @RequestHeader String Authorization, @RequestBody Putting foundstatus){
        Long userId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.changeLostItemByFoundStatus(lostItemId, userId, foundstatus.foundStatus());
    }

    @PutMapping("/admin/{lostItemId}")
    public HttpEntity<?> changeLostItemByFound(@PathVariable Long lostItemId, @RequestHeader String Authorization, @RequestBody Putting found){
        Long adminId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.changeLostItemByFound(lostItemId, adminId, found.foundStatus());
    }

    @GetMapping("/user")
    public HttpEntity<?> filterByUserId(@RequestHeader String Authorization){
        Long userId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.filterByUserid(userId);
    }

    @GetMapping("/search")
    public HttpEntity<?> search(@RequestParam String registry){
        return lostItemService.search(registry);
    }

    @GetMapping("/statistika")
    public HttpEntity<?> getStatistika(){
        return lostItemService.getStatistika();
    }

    @GetMapping("/admin-page")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public HttpEntity<?> getAdminPage(@RequestHeader String Authorization){
        Long adminId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.getAdminPage(adminId);
    }

    @PutMapping("/admin-page/{lostItemId}")
    public HttpEntity<?> changeLostItemByAdmin(@PathVariable Long lostItemId, @RequestHeader String Authorization){
        Long adminId = Long.parseLong(jwtService.extractSubject(Authorization));
        return lostItemService.changeLostItemByAdmin(lostItemId, adminId);
    }
}
