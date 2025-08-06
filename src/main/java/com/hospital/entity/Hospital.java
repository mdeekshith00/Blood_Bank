package com.hospital.entity;

import java.time.LocalDateTime;

import com.hospital.enums.ApprovalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Hospitals")
public class Hospital {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Reference to the user entity for login and security
	    @Column(name = "user_id", nullable = false, unique = true)
	    private Long userId;

	    @Column(name = "hospital_name", nullable = false, length = 100)
	    private String hospitalName;

	    @Column(name = "registration_number", nullable = false, unique = true, length = 50)
	    private String registrationNumber;

//	    @Column(name = "license_document_url")
//	    @Lob
//	    private String licenseDocumentUrl;  // Store in S3 or File Service or store in db 

	    @Column(name = "contact_person", nullable = false)
	    private String contactPerson;

	    @Column(name = "contact_email", nullable = false)
	    private String contactEmail;

	    @Column(name = "contact_phone", nullable = false)
	    private String contactPhone;

	    @Column(name = "alternate_phone")
	    private String alternatePhone;

	    @Column(name = "website")
	    private String website;

	    // --- Address Info ---
	    @Column(name = "address_line1", nullable = false)
	    private String addressLine1;

	    @Column(name = "address_line2")
	    private String addressLine2;

	    @Column(name = "city", nullable = false)
	    private String city;

	    @Column(name = "state", nullable = false)
	    private String state;

	    @Column(name = "pincode", nullable = false)
	    private String pincode;

	    @Column(name = "country", nullable = false)
	    private String country;

	    // --- Operational Info ---
	    @Column(name = "is_active", nullable = false)
	    private boolean isActive = true;

	    @Column(name = "approval_status", nullable = false)
	    @Enumerated(EnumType.STRING)
	    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING; // PENDING, APPROVED, REJECTED

	    @Column(name = "verified", nullable = false)
	    private boolean verified = false;

	    @Column(name = "last_login")
	    private LocalDateTime lastLogin;

	    // --- Audit Info ---
	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt = LocalDateTime.now();

	


}
