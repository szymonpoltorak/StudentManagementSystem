package razepl.dev.sms.entities.token.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.sms.entities.token.JwtToken;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<JwtToken, Long> {
    /**
     * Method to find token object inside database by the given token string
     *
     * @param jwtToken token string
     * @return {@link Optional} of {@link JwtToken} instance
     */
    Optional<JwtToken> findByToken(String jwtToken);

//    /**
//     * Method used to return the list of all tokens that user have based on his id.
//     *
//     * @param id the id of the user
//     * @return List of {@link JwtToken} of the user
//     */
//    @Query("""
//                select t from JwtToken as t
//                inner join User as u on (t.user.userId = u.userId)
//                where u.userId = :id and (t.isExpired = false or t.isRevoked = false)
//            """)
//    List<JwtToken> findAllValidTokensByUserId(Long id);
}
