package com.donation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor 
@Builder
@Entity
@Table(name = "donor")

public class Donor {
	@Id
	private Integer donorId; // refering from user
	
	private String bloodGroup;
	
    private String donationEligibilityStatus; // eligible, not eligible, pending approval
	 
	private Boolean isAvailableToDonate;
	
	
	  private LocalDate lastDonationDate;
	    private LocalDate nextEligibleDate;
	    private Integer totalDonations;
	    
	    
	    private Integer totalUnitsDonated;
	   
	    
	    private Boolean isEligibleToDonate;
	    private String ineligibilityReason; // e.g., "Low hemoglobin", "Medical condition"
	    private LocalDate temporarilyIneligibleUntil;
	    
	    private Boolean isActive; // If donor is still participating
	    private Boolean isVerified; // If user has passed eligibility verification
	    private String registeredVia; // e.g., "app", "web", "camp"

	    private Double weightInKg;
	    private Double hemoglobinLevel;       // g/dL
	    private Boolean hasChronicDiseases;   // e.g., diabetes, hypertension
	    private String recentMedications;
	    private String medicalConditions; 
	    
	    
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
		
	@OneToOne(mappedBy = "DonorHistory")
	@JsonManagedReference
	private List<DonorHistory> history;

	public DonorHistory  addHistory(DonorHistory donorHistory) {
		getHistory().add(donorHistory);
		donorHistory.setDonor(this);
		
		return donorHistory;
	}
	
	public DonorHistory removeHistory(DonorHistory donorHistory) {
		getHistory().remove(donorHistory);
		donorHistory.setDonor(null);
		
		return donorHistory;
	}


}

//@FeignClient(name = "user-service")
//public interface UserClient {
//    @GetMapping("/api/users/{id}")
//    UserDto getUserById(@PathVariable("id") Long id);
//}