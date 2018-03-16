package playground.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import playground.model.Payment;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {

  @Query("{'attributes.beneficiaryParty.name': {$regex : \'?0\', $options: 'i'}}")
  Flux<Payment> findByBeneficiaryName(String name);

}