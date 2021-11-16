package za.ac.nwu.ac.repository.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.nwu.ac.domain.persistence.album.SharedAlbum;

public interface SharedAlbumRepository extends JpaRepository<SharedAlbum, Long> {
}
