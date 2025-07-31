package com.donation.entity;

import java.security.Timestamp;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "donorHistory")
public class DonorHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donor_history_id")
	private Integer donorHistoryId;
	
	private String description;
	
	private String note;
	
    private LocalDate donationDate;

    private Integer unitsDonated;

    private String donationType; // CAMP, HOSPITAL, WALK-IN

    private String location; // Hospital/Camp name or address

    private String bloodGroup; // Optional, for redundancy

    private String remarks; // Optional – sickness, travel, etc.

	@Column(name = "created_at")
	private LocalDate createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "donor_id")
	@JsonBackReference
	private Donor donor;

}

