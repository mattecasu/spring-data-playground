package playground.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
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
  private ChargesInformation chargesInformation;

  @Getter
  @Setter
  private String currency;

  @Getter
  @Setter
  private Party debtorParty;

  @Getter
  @Setter
  private String endToEndReference;

  @Getter
  @Setter
  private Fx fx;

  @Getter
  @Setter
  private String numericReference;

  @Getter
  @Setter
  private String paymentId;

  @Getter
  @Setter
  private String paymentPurpose;

  @Getter
  @Setter
  private String paymentScheme;

  @Getter
  @Setter
  private String paymentType;

  @Getter
  @Setter
  private String processingDate;

  @Getter
  @Setter
  private String reference;

  @Getter
  @Setter
  private String schemePaymentType;

  @Getter
  @Setter
  private String schemePaymentSubtype;

  @Getter
  @Setter
  private Party sponsorParty;


  @ToString
  @Accessors(chain = true)
  @EqualsAndHashCode
  @JsonInclude(JsonInclude.Include.NON_NULL)
  static class Party {

    public Party() {
    }

    @Getter
    @Setter
    private String accountName;

    @Getter
    @Setter
    private String accountNumber;

    @Getter
    @Setter
    private String accountNumberCode;

    @Getter
    @Setter
    private Integer accountType;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String bankId;

    @Getter
    @Setter
    private String bankIdCode;

    @Getter
    @Setter
    private String name;

  }

  @ToString
  @Accessors(chain = true)
  @EqualsAndHashCode
  @JsonInclude(JsonInclude.Include.NON_NULL)
  static class ChargesInformation {

    public ChargesInformation() {
    }

    @Getter
    @Setter
    private String bearerCode;

    @Getter
    @Setter
    private List<Charge> senderCharges;

    @Getter
    @Setter
    private Float receiverChargesAmount;

    @Getter
    @Setter
    private String receiverChargesCurrency;

    @ToString
    @Accessors(chain = true)
    @EqualsAndHashCode
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Charge {

      public Charge() {
      }

      @Getter
      @Setter
      private Float amount;
      @Getter
      @Setter
      private String currency;
    }
  }

  @ToString
  @Accessors(chain = true)
  @EqualsAndHashCode
  @JsonInclude(JsonInclude.Include.NON_NULL)
  static class Fx {

    public Fx() {
    }

    @Getter
    @Setter
    private String contractReference;
    @Getter
    @Setter
    private Float exchangeRate;
    @Getter
    @Setter
    private Float originalAmount;
    @Getter
    @Setter
    private String originalCurrency;
  }


}
