package pl.pkasiewicz.movieclub.domain.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public
interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUser_EmailAndMovie_Id(String userEmail, Long movieId);
    Optional<Set<Rating>> findByMovie_Id(Long id);
}
