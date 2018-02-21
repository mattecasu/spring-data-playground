package playground.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@ToString
@Accessors(chain = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {

  @Getter
  @Setter
  @Id
  private String id;

  @Getter
  @Setter
  private String type;

  @Getter
  @Setter
  private Integer version;

  @Getter
  @Setter
  private String organisationId;

  @Getter
  @Setter
  private PaymentAttributes attributes;

//    @Getter
//    @Setter
//    @Transient
//    private Map<String, Object> links;


  public Payment() {
  }


}