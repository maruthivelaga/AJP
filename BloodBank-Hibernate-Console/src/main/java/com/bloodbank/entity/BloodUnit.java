package com.bloodbank.entity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * BloodUnit Entity representing a blood unit in the inventory
 */
@Entity
@Table(name = "blood_units")
public class BloodUnit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;
    
    @Column(name = "blood_type", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;
    
    @Column(name = "rhesus", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Rhesus rhesus;
    
    @Column(name = "donor_id", nullable = false, length = 50)
    private String donorId;
    
    @Column(name = "collection_date", nullable = false)
    private LocalDate collectionDate;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    // Enums
    public enum BloodType {
        A, B, AB, O
    }

    public enum Rhesus {
        POSITIVE("+"), NEGATIVE("-");
        
        private String symbol;
        
        Rhesus(String symbol) {
            this.symbol = symbol;
        }
        
        public String getSymbol() {
            return symbol;
        }
        
        @Override
        public String toString() {
            return symbol;
        }
    }

    public enum Status {
        AVAILABLE, RESERVED, ISSUED, QUARANTINED
    }

    // Constructors
    public BloodUnit() {
    }

    public BloodUnit(BloodType bloodType, Rhesus rhesus, String donorId, 
                     LocalDate collectionDate, LocalDate expiryDate, Status status) {
        this.bloodType = bloodType;
        this.rhesus = rhesus;
        this.donorId = donorId;
        this.collectionDate = collectionDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Rhesus getRhesus() {
        return rhesus;
    }

    public void setRhesus(Rhesus rhesus) {
        this.rhesus = rhesus;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Check if blood unit is expired
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    /**
     * Get full blood type (e.g., A+, O-, AB+)
     */
    public String getFullBloodType() {
        return bloodType + rhesus.getSymbol();
    }

    @Override
    public String toString() {
        return String.format("Unit ID: %d | Blood Type: %s | Donor: %s | Collected: %s | Expires: %s | Status: %s",
                unitId, getFullBloodType(), donorId, collectionDate, expiryDate, status);
    }
}
