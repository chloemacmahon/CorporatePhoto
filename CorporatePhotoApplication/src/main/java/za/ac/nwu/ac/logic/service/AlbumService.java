package za.ac.nwu.ac.logic.service;

import za.ac.nwu.ac.domain.persistence.album.Album;

import java.util.List;

public interface AlbumService {

    Album findAlbumById(Long albumId);

    void updateAlbumName(Long albumId, String albumName);

    void deleteAlbum(Long albumId, Long userId);

    void deletePhotoFromAlbum(Long photoId, Long albumId);

    List<Album> findAlbumsThatContainsPhoto(Long photoId);

}
