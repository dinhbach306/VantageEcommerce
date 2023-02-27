package com.magnus.library.service;

import com.magnus.library.dto.AdminDto;
import com.magnus.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
