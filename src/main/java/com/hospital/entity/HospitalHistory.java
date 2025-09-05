package com.hospital.entity;

import java.time.LocalDateTime;

import com.hospital.enums.ChangeType;
import com.hospital.enums.HistoryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class HospitalHistory {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Link to Hospital
	    @Column(name = "hospital_id", nullable = false)
	    private Long hospitalId;

	    // Optional: Track user who made the change (could be admin)
	    @Column(name = "changed_by_user_id")
	    private Long changedByUserId;

	    // What field or action was changed
	    @Column(name = "change_type", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private ChangeType changeType;

	    @Column(name = "description", columnDefinition = "TEXT")
	    private String description;

	    // Before and after snapshots for audit (optional but useful)
	    @Column(name = "previous_value", columnDefinition = "TEXT")
	    private String previousValue;

	    @Column(name = "new_value", columnDefinition = "TEXT")
	    private String newValue;

	    @Column(name = "change_timestamp", nullable = false)
	    private LocalDateTime changeTimestamp;

	    // Optional: Reason for change
	    @Column(name = "reason", columnDefinition = "TEXT")
	    private String reason;

	    @Column(name = "status", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private HistoryStatus status = HistoryStatus.SUCCESS; // or FAILED, PENDING

	    // Optional: Track source system or IP
	    @Column(name = "source_ip")
	    private String sourceIp;

	    // Optional: Add auditing info
	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();
}
