package com.edu.schema;

import java.time.LocalDateTime;

public class ApiResponseIs {
    private LocalDateTime timestamp;
    private String message;
    private Long resourceId; // Optional: to include the ID of the created/updated/deleted resource

    public ApiResponseIs(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    public ApiResponseIs(String message, Long resourceId) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.resourceId = resourceId;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Long getResourceId() {
        return resourceId;
    }
}
