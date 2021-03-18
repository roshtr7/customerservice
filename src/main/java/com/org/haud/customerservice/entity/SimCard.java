package com.org.haud.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sim_cards")
public class SimCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "ICCID", unique = true)
	private String ICCID;

	@Column(name = "IMSI", unique = true)
	private String IMSI;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimCard other = (SimCard) obj;
		if (ICCID == null) {
			if (other.ICCID != null)
				return false;
		} else if (!ICCID.equals(other.ICCID))
			return false;
		if (IMSI == null) {
			if (other.IMSI != null)
				return false;
		} else if (!IMSI.equals(other.IMSI))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ICCID == null) ? 0 : ICCID.hashCode());
		result = prime * result + ((IMSI == null) ? 0 : IMSI.hashCode());
		return result;
	}

}
