package playground.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import playground.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    @Query("{'attributes.beneficiaryParty.name': {$regex : \'?0\', $options: 'i'}}")
    Page<Payment> findByBeneficiaryName(String name, Pageable pageable);

}