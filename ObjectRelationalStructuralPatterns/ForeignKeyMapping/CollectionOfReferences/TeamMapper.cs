class TeamMapper {
    public Team Find(long id) {
        return (Team) AbstractFind(id);
    }

    protected override String TableName {
        get {
            return "Teams";
        }
    }

    protected override void doLoad(DomainObject obj, DataRow row) {
        Team team = (Team) obj;
        team.Name = (String) row["name"];
        team.Players = MapperRegistry.Player.FindForTeam(team.Id);
    }

    protected override void Save(DomainObject obj, DataRow row){
        Team team = (Team) obj;
        row["name"] = team.Name;
        savePlayers(team);
    }
    private void savePlayers(Team team){
        foreach (Player p in team.Players) {
            MapperRegistry.Player.LinkTeam(p, team.Id);
        }
    }

    public void LinkTeam (Player player, long teamID) {
        DataRow row = FindRow(player.Id);
        row["teamID"] = teamID;
    }
}