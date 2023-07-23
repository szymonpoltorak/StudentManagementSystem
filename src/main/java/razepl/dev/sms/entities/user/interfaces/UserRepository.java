package razepl.dev.sms.entities.user.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.sms.entities.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
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

    /**
     * Finds a user by authentication token.
     *
     * @param authToken the authentication token of the user to find
     * @return an Optional containing the user associated with the given authentication token, or empty if not found
     */
//    @Query("select u from User as u inner join JwtToken as t on u.userId = t.user.userId where t.token = :authToken")
//    @Query("{'userId': { $in: db.jwtToken.aggregate([{$match: {token: ?0}} , {$group: { _id: '$user.userId'}} , {$project: { _id: 0, userId: '$_id'}}])}}")
//    Optional<User> findUserByToken(String authToken);


    /**
     * Represents a method to delete a user by email.
     *
     * @param email - The email of the user to delete.
     */
    void deleteByEmail(String email);
}
