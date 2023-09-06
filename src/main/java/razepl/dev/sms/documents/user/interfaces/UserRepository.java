package razepl.dev.sms.documents.user.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.sms.documents.user.User;

import java.util.Optional;

/**
 * A repository interface for accessing and managing {@link User} entities.
 * It extends the {@link MongoRepository} interface to inherit common CRUD and pagination operations.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by email address.
     *
     * @param email the email address of the user to find
     * @return an {@link Optional} containing the user with the given email address, or empty if not found
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by name.
     *
     * @param name the name of the user to find
     * @return an {@link Optional} containing the user with the given name, or empty if not found
     */
    Optional<User> findByName(String name);
}
