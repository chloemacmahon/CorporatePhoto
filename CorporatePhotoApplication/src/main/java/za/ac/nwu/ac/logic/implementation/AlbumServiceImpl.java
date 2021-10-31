package za.ac.nwu.ac.logic.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.exception.AlbumNotFoundException;
import za.ac.nwu.ac.domain.exception.CouldNotDeletePhotoException;
import za.ac.nwu.ac.domain.exception.CouldNotUpdateAlbum;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.logic.service.AlbumService;
import za.ac.nwu.ac.repository.AlbumRepository;
import za.ac.nwu.ac.repository.PhotoRepository;
import za.ac.nwu.ac.repository.UserAccountRepository;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository albumRepository;

    private UserAccountRepository userAccountRepository;

    private PhotoRepository photoRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, UserAccountRepository userAccountRepository, PhotoRepository photoRepository) {
        this.albumRepository = albumRepository;
        this.userAccountRepository = userAccountRepository;
        this.photoRepository = photoRepository;
    }

    public Album findAlbumById(Long albumId){
        if (albumRepository.findById(albumId).isPresent()) {
            return albumRepository.findById(albumId).get();
        } else {
            throw new AlbumNotFoundException();
        }
    }

    public void updateAlbumName(Long albumId, String albumName){
        try {
            if (albumRepository.findById(albumId).isPresent()) {
                Album album = albumRepository.findById(albumId).get();
                album.setAlbumName(albumName);
                albumRepository.save(album);
            } else {
                throw new CouldNotUpdateAlbum("Album not in database");
            }
        } catch (Exception e){
            throw new CouldNotUpdateAlbum("Could not update album, nested expression is ("+ e.getLocalizedMessage()+")");
        }
    }

    public void deleteAlbum(Long albumId, Long userId){
        if(userAccountRepository.findById(userId).isPresent()) {
            UserAccount userAccount = userAccountRepository.findById(userId).get();
            Album album = findAlbumById(albumId);
            userAccount.getAlbums().remove(album);
            userAccountRepository.save(userAccount);
            albumRepository.delete(album);
        } else {
            throw new CouldNotUpdateAlbum("Could not delete album");
        }
    }

    public void deletePhotoFromAlbum(Long photoId, Long albumId) {
        try {
            Album album = albumRepository.findById(albumId).get();
            album.removePhotoFromAlbum(photoRepository.findById(photoId).get());
            albumRepository.save(album);
        } catch (Exception e) {
            throw new CouldNotDeletePhotoException("Photo could not be removed, nested exception is (" + e.getLocalizedMessage() + ")");
        }
    }

    public List<Album> findAlbumsThatContainsPhoto(Long photoId){
        return albumRepository.findByPhotosPhotoId(photoId);
    }
}
