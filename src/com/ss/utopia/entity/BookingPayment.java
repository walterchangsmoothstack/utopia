package com.ss.utopia.entity;

public class BookingPayment {

	private Integer id;
	private String stripe_id;
	private boolean refunded;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStripe_id() {
		return stripe_id;
	}

	public void setStripe_id(String stripe_id) {
		this.stripe_id = stripe_id;
	}

	public boolean isRefunded() {
		return refunded;
	}

	public void setRefunded(boolean refunded) {
		this.refunded = refunded;
	}

}
