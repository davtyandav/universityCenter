package com.davdavtyan.universitycenter.dto.response;

import java.util.List;

public class PaginatedUserResponse {
    private List<UserResponse> data;
    private int totalPages;

    public PaginatedUserResponse(List<UserResponse> data, int totalPages) {
        this.data = data;
        this.totalPages = totalPages;
    }

    // Getters and Setters
    public List<UserResponse> getData() { return data; }
    public void setData(List<UserResponse> data) { this.data = data; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
}