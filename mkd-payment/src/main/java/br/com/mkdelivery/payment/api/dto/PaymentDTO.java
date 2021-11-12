package br.com.mkdelivery.payment.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PaymentSlipDTO.class, name = "SLIP"),
    @JsonSubTypes.Type(value = PaymentCreditCardDTO.class, name = "CARD")
})
public abstract class PaymentDTO {

	@JsonProperty("id")
	protected String uuid;
	
	protected StatusPagamento statusPagamento;

	@JsonIgnore
	protected PaymentDTO nextProcessor;

	public abstract Payment convertToPayment(PaymentDTO dto);
	
	public abstract PaymentDTO convertToDTO(Payment payment);

}
