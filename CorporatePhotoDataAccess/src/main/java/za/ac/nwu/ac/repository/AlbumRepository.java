package za.ac.nwu.ac.repository;

import org.springframework.data.jpa.repository.Query;
import za.ac.nwu.ac.domain.persistence.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    //@Query("SELECT alb.albumName FROM Album alb INNER JOIN Photo pht WHERE alb.photos.contains(:photo) = true") //*:list<Album>
    List<Album> findByPhotosPhotoId(Long photoId);
}
