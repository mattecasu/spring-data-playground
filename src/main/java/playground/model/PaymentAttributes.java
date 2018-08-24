package playground.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Accessors(chain = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentAttributes {

  public PaymentAttributes() {
  }

  @Getter
  @Setter
  private Float amount;

  @Getter
  @Setter
  private Party beneficiaryParty;

  @Getter
  @Setter
  private String currency;

  @Getter
  @Setter
  private String paymentId;

  @Getter
  @Setter
  private String processingDate;

  @ToString
  @Accessors(chain = true)
  @EqualsAndHashCode
  @JsonInclude(JsonInclude.Include.NON_NULL)
  static class Party {

    public Party() {
    }

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String accountNumber;

    @Getter
    @Setter
    private String bankIdCode;

  }

}