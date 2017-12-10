package playground.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import playground.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

}