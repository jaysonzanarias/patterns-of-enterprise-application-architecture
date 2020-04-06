package DistributionPatterns.RemoteFacade.JavaSessionBeanAsRemoteFacade.remote;

import DistributionPatterns.RemoteFacade.JavaSessionBeanAsRemoteFacade.api.AlbumService;

import java.rmi.RemoteException;

public class AlbumServiceBean implements AlbumService {
    public AlbumDTO getAlbum(String id) throws RemoteException {
        return new AlbumAssembler().writeDTO(Registry.findAlbum(id));
    }

    public String getAlbumXml(String id) throws RemoteException {
        AlbumDTO dto = new AlbumAssembler().writeDTO(Registry.findAlbum(id));
        return dto.toXmlString();
    }

    public void createAlbum(String id, AlbumDTO dto) throws RemoteException {
        new AlbumAssembler().createAlbum(id, dto);
    }

    public void createAlbum(String id, String xml) throws RemoteException {
        AlbumDTO dto = AlbumDTO.readXmlString(xml);
        new AlbumAssembler().createAlbum(id, dto);
    }

    public void updateAlbum(String id, AlbumDTO dto) throws RemoteException {
        new AlbumAssembler().updateAlbum(id, dto);
    }

    public void updateAlbum(String id, String xml) throws RemoteException {
        AlbumDTO dto = AlbumDTO.readXmlString(xml);
        new AlbumAssembler().updateAlbum(id, dto);
    }
}
