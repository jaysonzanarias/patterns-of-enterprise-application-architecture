package WebPresentationPatterns.ApplicationController;

import java.util.Map;

public class AssetApplicationController implements ApplicationController {
    private Response getResponse(String commandString, AssetStatus state) {
        return (Response) getResponseMap(commandString).get(state);
    }

    private Map getResponseMap(String key) {
        return (Map) events.get(key);
    }

    private Map events = new HashMap();

    public DomainCommand getDomainCommand(String commandString, Map params) {
        Response reponse = getResponse(commandString, getAssetStatus(params));
        return reponse.getDomainCommand();
    }

    private AssetStatus getAssetStatus(Map params) {
        String id = getParam("assetID", params);
        Asset asset = Asset.find(id);
        return asset.getStatus();
    }

    private String getParam(String key, Map params) {
        return ((String[]) params.get(key))[0];
    }

    public String getView(String commandString, Map params) {
        return getResponse(commandString, getAssetStatus(params)).getViewUrl();
    }

    public void addResponse(String event, Object state, Class domainCommand, String view) {
        Response newResponse = new Response(domainCommand, view);
        if (!events.containsKey(event)) {
            events.put(event, new HashMap());
        }
        getResponseMap(event).put(state, newResponse);
    }

    private static void loadApplicationController(AssetApplicationController appController) {
        appController = AssetApplicationController.getDefault();
        appController.addResponse("return", AssetStatus.ON_LEASE, GatherReturnDetailsCommand.class,
                                  "return");
        appController.addResponse("return", AssetStatus.IN_INVENTORY, NullAssetCommand.class,
                                  "illegalAction");
        appController.addResponse("damage", AssetStatus.ON_LEASE, InventoryDamageCommand.class,
                                  "leaseDamage");
        appController.addResponse("damage", AssetStatus.IN_INVENTORY, LeaseDamageCommand.class,
                                  "inventoryDamage");
    }
}
