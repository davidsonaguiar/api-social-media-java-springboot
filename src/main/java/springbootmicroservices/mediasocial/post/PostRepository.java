package springbootmicroservices.mediasocial.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Integer> {
    public List<PostModel> findByUserId(Integer userId);
}
