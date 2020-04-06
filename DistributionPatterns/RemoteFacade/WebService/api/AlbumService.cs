class AlbumService {
    [ WebMethod ]
    public AlbumDTO GetAlbum(String key) {
        Album result = new AlbumFinder()[key];
        if (result == null)
            throw new SoapException ("unable to find album with key: " +
                key, SoapException.ClientFaultCode);
        else return new AlbumAssembler().WriteDTO(result);
    }
}