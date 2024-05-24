package com.topilmalar.service;

import com.topilmalar.payload.LostItemDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface LostItemService {
    HttpEntity<?> filterItems(String holat, String type, String subType, String region, String district);

    HttpEntity<?> saveLostItem(LostItemDto lostItemDto, Long userId);

    HttpEntity<?> changeLostItem(Long lostItemId, Long ownerId);

    HttpEntity<?> changeLostItemByFoundStatus(Long lostItemId, Long userId, boolean foundstatus);

    HttpEntity<?> changeLostItemByFound(Long lostItemId, Long adminId, boolean found);

    HttpEntity<?> filterByUserid(Long userId);

    HttpEntity<?> getStatistika();

    HttpEntity<?> getAdminPage(Long adminId);

    HttpEntity<?> changeLostItemByAdmin(Long lostItemId, Long adminId);

    HttpEntity<?> search(String registry);

}
