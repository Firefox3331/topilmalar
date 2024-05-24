package com.topilmalar.service;

import com.topilmalar.entity.*;
import com.topilmalar.payload.LostItemDto;
import com.topilmalar.projection.LostItemsPro;
import com.topilmalar.projection.OwnerPro;
import com.topilmalar.projection.Statistika;
import com.topilmalar.repository.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LostItemServiceImpl implements LostItemService{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private LostItemRepo lostItemRepo;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    SubTypeRepo subTypeRepo;
    @Autowired
    RegionRepo regionRepo;
    @Autowired
    DistrictRepo districtRepo;
    @Autowired
    UserRepo userRepo;


    @Override
    public HttpEntity<?> filterItems(String holat, String type, String subType, String region, String district) {
        String query = "select\n" +
                "    li.id as id,\n" +
                "    t.name as type,\n" +
                "    sb.name as subType,\n" +
                "    r.name as region,\n" +
                "    d.name as district,\n" +
                "    li.found,\n" +
                "    li.registry,\n" +
                "    li.lost_date as lostDate,\n" +
                "    li.organization as organization,\n" +
                "    li.status as status,\n" +
                "    u.username as username,\n" +
                "    li.description as description,\n" +
                "    li.owner_id as ownerId\n" +
                "from lost_items li\n" +
                "         join type t on li.type_id = t.id\n" +
                "         join sub_type sb on li.sub_type_id = sb.id\n" +
                "         join region r on li.region_id = r.id\n" +
                "         join district d on li.district_id = d.id\n" +
                "         join users u on li.users_id = u.id";

        if(!holat.equals("null") ||  !type.equals("null") || !subType.equals("null") || !region.equals("null") || !district.equals("null")){
            query += " where found is false and ";
            if(!holat.equals("null")){
                query += " li.status = '" + holat + "' and\n";
            }
            if(!type.equals("null")){
                query += "li.type_id = "+type+" and\n";
            }
            if(!subType.equals("null")){
                query += "li.sub_type_id = "+subType+" and\n";
            }
            if(!region.equals("null")){
                query += "li.region_id = "+region+" and\n";
            }
            if(!district.equals("null")){
                query += "li.district_id = " +district+ " and \n";
            }
            query += " 10 > 1";
        }else{
            query += " where found is false ";
        }
        return ResponseEntity.ok(jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LostItemsPro.class)));
    }

    @Override
    public HttpEntity<?> saveLostItem(LostItemDto lostItemDto, Long userId) {
        Optional<Type> typeOpt = typeRepo.findById(lostItemDto.typeId());
        Optional<SubType> subTypeOpt = subTypeRepo.findById(lostItemDto.subTypeId());
        Optional<Region> regionOpt = regionRepo.findById(lostItemDto.regionId());
        Optional<District> districtOpt = districtRepo.findById(lostItemDto.districtId());
        Optional<Users> userOpt = userRepo.findById(userId);
        if(typeOpt.isPresent() && subTypeOpt.isPresent() && regionOpt.isPresent() && districtOpt.isPresent() && userOpt.isPresent()){
            LostItems lost = LostItems.builder()
                    .type(typeOpt.get())
                    .subType(subTypeOpt.get())
                    .region(regionOpt.get())
                    .district(districtOpt.get())
                    .users(userOpt.get())
                    .lostDate(LocalDateTime.now())
                    .found(false)
                    .status(Status.valueOf(lostItemDto.status()))
                    .registry(generateRegistryNumber(lostItemDto.typeId()))
                    .description(lostItemDto.description())
                    .organization(lostItemDto.organization())
                    .build();
            lostItemRepo.save(lost);
        }

        return ResponseEntity.ok("Success");
    }

    @Override
    public HttpEntity<?> changeLostItem(Long lostItemId, Long ownerId) {
        Optional<LostItems> lostItemsOpt = lostItemRepo.findById(lostItemId);
        Optional<Users> ownerOpt = userRepo.findById(ownerId);
        if(lostItemsOpt.isPresent() && ownerOpt.isPresent()){
            LostItems lostItems = lostItemsOpt.get();
            lostItems.setOwner(ownerOpt.get());
            lostItemRepo.save(lostItems);
        }
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> changeLostItemByFoundStatus(Long lostItemId, Long userId, boolean foundstatus) {
        Optional<LostItems> lostItemsOpt = lostItemRepo.findById(lostItemId);
        Optional<Users> ownerOpt = userRepo.findById(userId);
        if(lostItemsOpt.isPresent() && ownerOpt.isPresent()){
            if(ownerOpt.get().getId().equals(lostItemsOpt.get().getUsers().getId())){
                LostItems lostItems = lostItemsOpt.get();
                lostItems.setFoundstatus(foundstatus);
                lostItemRepo.save(lostItems);
            }
        }
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> changeLostItemByFound(Long lostItemId, Long adminId, boolean found) {
        Optional<LostItems> lostItemsOpt = lostItemRepo.findById(lostItemId);
        Optional<Users> ownerOpt = userRepo.findById(adminId);
        if(lostItemsOpt.isPresent() && ownerOpt.isPresent()){
            for (Roles role : ownerOpt.get().getRoles()) {
                if(role.getName().equals("ROLE_ADMIN")){
                    LostItems lostItems = lostItemsOpt.get();
                    lostItems.setFound(found);
                    lostItemRepo.save(lostItems);
                    System.out.println("lostItemId is: "+lostItemId + ", adminId is: " + adminId + ", found is: " + found);
                }
            }
        }
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> filterByUserid(Long userId) {
        List<OwnerPro> allByUsersId = lostItemRepo.findAllByUsersIdAndOwnerIsNotNullAndFoundstatusIsFalse(userId);
        System.out.println("owners are: "+allByUsersId);
        return ResponseEntity.ok(allByUsersId);
    }

    @Override
    public HttpEntity<?> getStatistika() {
        Statistika totalStatistika = lostItemRepo.findTotalStatistika();
        List<Statistika> statistika = lostItemRepo.findStatistika();
        statistika.add(totalStatistika);
        return ResponseEntity.ok(statistika);
    }

    @Override
    public HttpEntity<?> getAdminPage(Long adminId) {
        Optional<Users> byId = userRepo.findById(adminId);
        if(byId.isPresent()){
            for (Roles role : byId.get().getRoles()) {
                if(role.getName().equals("ROLE_SUPER_ADMIN") || role.getName().equals("ROLE_ADMIN")){
                    List<OwnerPro> all = lostItemRepo.findAllByOwnerIdIsNotNullAndFoundstatusIsTrueAndFoundIsFalse();
                    return ResponseEntity.ok(all);
                }
            }
        }
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> changeLostItemByAdmin(Long lostItemId, Long adminId) {
        return null;
    }

    @Override
    public HttpEntity<?> search(String registry) {
        String query = "select\n" +
                "    li.id as id,\n" +
                "    t.name as type,\n" +
                "    sb.name as subType,\n" +
                "    r.name as region,\n" +
                "    d.name as district,\n" +
                "    li.found,\n" +
                "    li.registry,\n" +
                "    li.lost_date as lostDate,\n" +
                "    li.organization as organization,\n" +
                "    li.status as status,\n" +
                "    u.username as username,\n" +
                "    li.description as description,\n" +
                "    li.owner_id as ownerId\n" +
                "from lost_items li\n" +
                "         join type t on li.type_id = t.id\n" +
                "         join sub_type sb on li.sub_type_id = sb.id\n" +
                "         join region r on li.region_id = r.id\n" +
                "         join district d on li.district_id = d.id\n" +
                "         join users u on li.users_id = u.id where registry = '"+registry+"'";
        List<LostItemsPro> query1 = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(LostItemsPro.class));
        return ResponseEntity.ok(query1);
    }

    public String generateRegistryNumber(Long typeId){
        String last = (Long.parseLong(lostItemRepo.findLast().getRegistry().substring(3)) - 0)+1 + "";
        String a = "";
        String registry = typeId == 1 ? "DOC" : typeId == 2 ? "OBJ" : typeId == 3 ? "TEC" : "NuLL";
        if(!registry.equals("NuLL")){
            while(last.length() + a.length() <= 9){
                a += "0";
            }
        }
        return registry +a+ last;
    }
}


